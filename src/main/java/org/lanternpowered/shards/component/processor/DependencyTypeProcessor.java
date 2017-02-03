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
package org.lanternpowered.shards.component.processor;

import com.google.common.reflect.TypeToken;
import org.lanternpowered.shards.Component;
import org.lanternpowered.shards.component.DependencySpec;
import org.lanternpowered.shards.inject.Binder;
import org.lanternpowered.shards.component.Params;
import org.lanternpowered.shards.processor.ProcessorContext;
import org.lanternpowered.shards.processor.TypeProcessor;
import org.lanternpowered.shards.dependency.Dependency;
import org.lanternpowered.shards.dependency.Dependencies;

import java.util.ArrayList;
import java.util.List;

/**
 * This processor collects {@link DependencySpec}s from component classes.
 */
public class DependencyTypeProcessor implements TypeProcessor {

    @Override
    public void process(ProcessorContext context, TypeToken<?> type, Binder binder) {
        final Class<?> raw = type.getRawType();
        // Only run this processor for components
        if (!Component.class.isAssignableFrom(raw)) {
            return;
        }
        final List<DependencySpec> dependencies = context.computeIfAbsent(Params.DEPENDENCIES, ArrayList::new);
        final Dependencies dependencies1 = raw.getAnnotation(Dependencies.class);
        if (dependencies1 != null) {
            final Dependency[] dependenciesArray = dependencies1.value();
            for (Dependency dependency : dependenciesArray) {
                dependencies.add(new DependencySpec(dependency.value(),
                        dependency.optional() ? DependencySpec.Type.OPTIONAL : DependencySpec.Type.DYNAMIC_REQUIRED, dependency.autoAttach()));
            }
        }
    }
}
