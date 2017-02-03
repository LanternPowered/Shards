/*
 * This file is part of Shards, licensed under the MIT License (MIT).
 *
 * Copyright (c) LanternPowered <https://www.lanternpowered.org>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the Software), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, andor sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED AS IS, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.lanternpowered.shards.component;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import com.google.inject.Module;
import org.lanternpowered.shards.Component;
import org.lanternpowered.shards.ComponentRegistry;
import org.lanternpowered.shards.TargetedModuleBuilder;
import org.lanternpowered.shards.component.processor.ComponentParameterProcessor;
import org.lanternpowered.shards.component.processor.DependencyPostProcessor;
import org.lanternpowered.shards.component.processor.DependencyTypeProcessor;
import org.lanternpowered.shards.component.processor.HolderParameterProcessor;
import org.lanternpowered.shards.component.processor.HolderPostProcessor;
import org.lanternpowered.shards.component.processor.WarnComponentTypeProcessor;
import org.lanternpowered.shards.processor.Processor;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class ComponentRegistryImpl implements ComponentRegistry {

    private final static ComponentRegistryImpl INSTANCE = new ComponentRegistryImpl();

    public static ComponentRegistryImpl get() {
        return INSTANCE;
    }

    private final List<Processor> processors = new ArrayList<>();
    private final List<TargetedModule> targetedModules = new ArrayList<>();

    {
        registerProcessor(new WarnComponentTypeProcessor());
        registerProcessor(new DependencyTypeProcessor());
        registerProcessor(new HolderParameterProcessor());
        registerProcessor(new ComponentParameterProcessor());
        registerProcessor(new HolderPostProcessor());
        registerProcessor(new DependencyPostProcessor());
    }

    public List<Processor> getProcessors() {
        return this.processors;
    }

    public List<TargetedModule> getTargetedModules() {
        return this.targetedModules;
    }

    @Override
    public void registerProcessor(Processor processor) {
        checkNotNull(processor, "processor");
        this.processors.add(processor);
        ComponentType.invalidateAll();
    }

    @Override
    public void registerProcessorAfter(Class<? extends Processor> target, Processor processor) {
        registerProcessor(getProcessorIndex(target), processor);
    }

    @Override
    public void registerProcessorBefore(Class<? extends Processor> target, Processor processor) {
        registerProcessor(getProcessorIndex(target) - 1, processor);
    }

    private void registerProcessor(int index, Processor processor) {
        checkNotNull(processor, "processor");
        if (index >= this.processors.size()) {
            this.processors.add(processor);
        } else {
            this.processors.add(index, processor);
        }
        ComponentType.invalidateAll();
    }

    private int getProcessorIndex(Class<? extends Processor> target) {
        checkNotNull(target, "target");
        int i = 0;
        for (Processor processor1 : this.processors) {
            if (target.isInstance(processor1)) {
                break;
            }
            i++;
        }
        checkArgument(i != 0, "Unable to find a Processor of the type: %s", target.getName());
        return i;
    }

    @Override
    public TargetedModuleBuilder registerModule(Module module) {
        checkNotNull(module, "module");
        return new TargetedModuleBuilderImpl(module);
    }

    private final class TargetedModuleBuilderImpl implements TargetedModuleBuilder {

        private final Module module;

        TargetedModuleBuilderImpl(Module module) {
            this.module = module;
        }

        @Override
        public void toScope(Class<? extends Annotation> annotation) {
            checkNotNull(annotation, "annotation");
            targetedModules.add(new TargetedModule(this.module, type -> type.isAnnotationPresent(annotation)));
            ComponentType.invalidateAll();
        }

        @Override
        public void to(Class<? extends Component> componentType) {
            checkNotNull(componentType, "componentType");
            targetedModules.add(new TargetedModule(this.module, componentType::isAssignableFrom));
            ComponentType.invalidate(componentType);
        }

        @Override
        public void to(Predicate<Class<? extends Component>> componentTypeTester) {
            checkNotNull(componentTypeTester, "componentTypeTester");
            //noinspection unchecked
            targetedModules.add(new TargetedModule(this.module, (Predicate<Class<?>>) componentTypeTester));
            ComponentType.invalidateAll();
        }
    }
}
