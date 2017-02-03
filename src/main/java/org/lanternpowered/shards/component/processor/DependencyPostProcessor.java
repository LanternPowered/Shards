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

import static org.lanternpowered.shards.component.processor.HolderPostProcessor.createDynType;
import static org.lanternpowered.shards.component.processor.HolderPostProcessor.createOptType;

import com.google.common.base.MoreObjects;
import com.google.common.reflect.TypeToken;
import com.google.inject.Provider;
import org.lanternpowered.shards.Dyn;
import org.lanternpowered.shards.Opt;
import org.lanternpowered.shards.component.DependencySpec;
import org.lanternpowered.shards.component.provider.ComponentProvider;
import org.lanternpowered.shards.component.provider.ConstantDynComponentProvider;
import org.lanternpowered.shards.component.provider.ConstantOptComponentProvider;
import org.lanternpowered.shards.component.provider.DynComponentProvider;
import org.lanternpowered.shards.component.provider.OptComponentProvider;
import org.lanternpowered.shards.dependency.DependencyType;
import org.lanternpowered.shards.inject.Binder;
import org.lanternpowered.shards.component.Params;
import org.lanternpowered.shards.processor.ProcessorException;
import org.lanternpowered.shards.processor.PostProcessor;
import org.lanternpowered.shards.processor.ProcessorContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;

public class DependencyPostProcessor implements PostProcessor {

    @SuppressWarnings("unchecked")
    @Override
    public void process(ProcessorContext context, Binder binder) throws ProcessorException {
        List<DependencySpec> dependencies = context.computeIfAbsent(Params.DEPENDENCIES, ArrayList::new);

        // TODO: Check circular dependencies

        final Map<Class<?>, TempDependency> tempDependencies = new HashMap<>();
        for (DependencySpec spec : dependencies) {
            final TempDependency temp = tempDependencies.computeIfAbsent(spec.getType(), type -> new TempDependency());
            final DependencyType type = spec.getDependencyType();
            if (type == DependencyType.REQUIRED) {
                temp.required = true;
            } else if (type == DependencyType.REQUIRED_DYNAMIC) {
                temp.dynamicRequired = true;
            } else {
                temp.optional = true;
            }
            if (spec.getAutoAttach()) {
                temp.autoAttach = true;
            }
        }

        // Get the most exact dependencies
        final Map<Class<?>, TempDependency> exactDependencies = new HashMap<>(tempDependencies);
        for (Map.Entry<Class<?>, TempDependency> entry : new HashMap<>(tempDependencies).entrySet()) {
            //noinspection unchecked
            final Set<Class<?>> subTypes = new HashSet(TypeToken.of(entry.getKey()).getTypes().rawTypes());
            // Don't process the current class
            subTypes.remove(entry.getKey());
            subTypes.forEach(type -> {
                final TempDependency tempDependency = tempDependencies.get(type);
                if (tempDependency != null) {
                    if (entry.getValue().required) {
                        // Only suggest the more exact type if it is actually required
                        exactDependencies.remove(type);
                        tempDependency.exactTypes.add(entry.getKey());
                    } else {
                        // Since the sub types is optional, let's suggest the
                        // component type as the more precise one
                        tempDependency.optionalExactTypes.add(entry.getKey());
                    }
                }
            });
        }

        // A set with all the dependencies that should be registered when constructing the component
        final Set<DependencySpec> registerDependencies = new HashSet<>();

        // Log duplicate exact types and map the injector providers
        for (Map.Entry<Class<?>, TempDependency> entry : tempDependencies.entrySet()) {
            if (entry.getValue().exactTypes.size() > 1) {
                WarnComponentTypeProcessor.LOGGER.warn("There are multiple possible Component mappings for the type [{}]: {}",
                        entry.getKey().getName(), Arrays.toString(entry.getValue().exactTypes.stream().map(Class::getName).toArray()));
            } else if (entry.getValue().exactTypes.isEmpty() && entry.getValue().optionalExactTypes.size() > 1) {
                WarnComponentTypeProcessor.LOGGER.warn("There are multiple possible optional Component mappings for the type [{}]: {}",
                        entry.getKey().getName(), Arrays.toString(entry.getValue().optionalExactTypes.stream().map(Class::getName).toArray()));
            }
            final TempDependency temp = entry.getValue();
            // Exact type
            if (exactDependencies.containsKey(entry.getKey())) {
                if (temp.required) {
                    if (temp.provider == null) {
                        temp.provider = new ComponentProvider(entry.getKey());
                    }
                    // Register the provider
                    binder.bind((Class) entry.getKey()).toProvider(temp.provider);
                }
                if (temp.dynamicRequired) {
                    if (temp.dynProvider == null) {
                        temp.dynProvider = temp.required ? new ConstantDynComponentProvider(entry.getKey()) :
                                new DynComponentProvider(entry.getKey());
                    }
                    // Register the provider
                    binder.bind(createDynType((Class) entry.getKey())).toProvider(temp.dynProvider);
                }
                if (temp.optional) {
                    if (temp.optProvider == null) {
                        temp.optProvider = temp.required ? new ConstantOptComponentProvider(entry.getKey()) :
                                new OptComponentProvider(entry.getKey());
                    }
                    // Register the provider
                    binder.bind(createOptType((Class) entry.getKey())).toProvider(temp.optProvider);
                }
                registerDependencies.add(new DependencySpec((Class) entry.getKey(), temp.required ? DependencyType.REQUIRED :
                        temp.dynamicRequired ? DependencyType.REQUIRED_DYNAMIC : DependencyType.OPTIONAL, temp.autoAttach));
            } else {
                Class<?> exactType = null;
                if (!entry.getValue().exactTypes.isEmpty()) {
                    exactType = entry.getValue().exactTypes.iterator().next();
                } else if (!entry.getValue().optionalExactTypes.isEmpty()) {
                    exactType = entry.getValue().optionalExactTypes.iterator().next();
                } else {
                    registerDependencies.add(new DependencySpec((Class) entry.getKey(), temp.required ? DependencyType.REQUIRED :
                            temp.dynamicRequired ? DependencyType.REQUIRED_DYNAMIC : DependencyType.OPTIONAL, temp.autoAttach));
                }
                if (exactType != null) {
                    final TempDependency temp1 = exactDependencies.get(exactType);
                    if (temp.required) {
                        if (temp1.provider == null) {
                            temp1.provider = new ComponentProvider(exactType);
                        }
                        // Register the provider
                        binder.bind((Class) entry.getKey()).toProvider(temp1.provider);
                    }
                    if (temp.dynamicRequired) {
                        if (temp1.dynProvider == null) {
                            temp1.dynProvider = temp1.required ? new ConstantDynComponentProvider(exactType) :
                                    new DynComponentProvider(exactType);
                        }
                        // Register the provider
                        binder.bind(createDynType((Class) entry.getKey())).toProvider(temp1.dynProvider);
                    }
                    if (temp.optional) {
                        if (temp1.optProvider == null) {
                            temp1.optProvider = temp1.required ? new ConstantOptComponentProvider(exactType) :
                                    new OptComponentProvider(exactType);
                        }
                        // Register the provider
                        binder.bind(createOptType((Class) entry.getKey())).toProvider(temp1.optProvider);
                    }
                }
            }
        }

        context.put(Params.DEPENDENCIES, new ArrayList<>(registerDependencies));
    }

    private static final class TempDependency {

        /**
         * The flag to define if the required type provider should be injected.
         */
        private boolean required;

        /**
         * The flag to define if the optional type provider should be injected.
         */
        private boolean optional;

        /**
         * The flag to define if the dynamicRequired type provider should be injected.
         */
        private boolean dynamicRequired;

        /**
         * The flag to define whether the component should be attached automatically.
         */
        private boolean autoAttach;

        /**
         * The provider that should be used for the dependency.
         */
        @Nullable private Provider provider;

        /**
         * The provider that should be used for the optional dependency.
         */
        @Nullable private Provider<Opt> optProvider;

        /**
         * The provider that should be used for the dynamic dependency.
         */
        @Nullable private Provider<Dyn> dynProvider;

        private Set<Class<?>> exactTypes = new HashSet<>();
        private Set<Class<?>> optionalExactTypes = new HashSet<>();

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("required", this.required)
                    .add("dynamicRequired", this.dynamicRequired)
                    .add("optional", this.optional)
                    .add("autoAttach", this.autoAttach)
                    .add("exactTypes", Arrays.toString(this.exactTypes.stream().map(Class::getName).toArray()))
                    .add("optionalExactTypes", Arrays.toString(this.optionalExactTypes.stream().map(Class::getName).toArray()))
                    .toString();
        }
    }
}
