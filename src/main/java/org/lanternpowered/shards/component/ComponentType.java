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

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.base.MoreObjects;
import com.google.common.base.Throwables;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.TypeToken;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import org.lanternpowered.shards.Component;
import org.lanternpowered.shards.inject.AbstractModule;
import org.lanternpowered.shards.inject.DelegateBinder;
import org.lanternpowered.shards.processor.Processor;
import org.lanternpowered.shards.processor.ProcessorContext;
import org.lanternpowered.shards.processor.ProcessorException;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public final class ComponentType<T extends Component> {

    public static <T extends Component> ComponentType<T> get(Class<T> componentType) {
        checkNotNull(componentType, "componentType");
        try {
            //noinspection unchecked
            return cache.get(componentType);
        } catch (ExecutionException e) {
            throw Throwables.propagate(e);
        }
    }

    private static final LoadingCache<Class<? extends Component>, ComponentType> cache =
            CacheBuilder.newBuilder().build(new CacheLoader<Class<? extends Component>, ComponentType>() {
                @Override
                public ComponentType load(Class<? extends Component> key) throws Exception {
                    // System.out.println(key.getName());
                    return load0(key);
                }
            });

    public static void invalidate(Class<? extends Component> component) {
        cache.invalidateAll(TypeToken.of(component).getTypes().rawTypes());
    }

    public static void invalidateAll() {
        cache.invalidateAll();
    }

    private static ComponentType load0(Class<? extends Component> type) {
        final ProcessorContext context = ProcessorContext.of(TypeToken.of(type));
        final List<Processor> processors = ImmutableList.copyOf(ComponentRegistryImpl.get().getProcessors());
        final List<TargetedModule> targetedModules = ImmutableList.copyOf(ComponentRegistryImpl.get().getTargetedModules());
        final Module module = new AbstractModule() {
            @Override
            protected void configure() {
                for (TargetedModule targetedModule : targetedModules) {
                    if (targetedModule.isApplicable(type)) {
                        install(targetedModule.getModule());
                    }
                }
                try {
                    context.process(new DelegateBinder(binder()), processors);
                } catch (ProcessorException e) {
                    throw Throwables.propagate(e);
                }
            }
        };
        final Injector injector = Guice.createInjector(module);
        return new ComponentType<>(type, ImmutableSet.copyOf(context.get(Params.DEPENDENCIES).get()),
                ImmutableSet.copyOf(context.get(Params.HOLDER_CHECKS).get()), injector);
    }

    private final Class<T> componentType;
    private final Set<DependencySpec> dependencies;
    private final Set<Class<?>> ownerTypes;
    private final Injector injector;

    private ComponentType(Class<T> componentType, Set<DependencySpec> requirements, Set<Class<?>> ownerTypes,
            Injector injector) {
        this.dependencies = ImmutableSet.copyOf(requirements);
        this.ownerTypes = ImmutableSet.copyOf(ownerTypes);
        this.componentType = componentType;
        this.injector = injector;
    }

    /**
     * Gets a {@link Collection} with all the {@link DependencySpec}s
     * in this collection.
     *
     * @return The dependencies
     */
    public Collection<DependencySpec> getDependencies() {
        return this.dependencies;
    }

    /**
     * Gets the {@link Class} of the component type.
     *
     * @return The component class
     */
    public Class<T> getComponentType() {
        return this.componentType;
    }

    public Injector getInjector() {
        return this.injector;
    }

    public Set<Class<?>> getOwnerTypes() {
        return this.ownerTypes;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("type", this.componentType.getName())
                .toString();
    }
}
