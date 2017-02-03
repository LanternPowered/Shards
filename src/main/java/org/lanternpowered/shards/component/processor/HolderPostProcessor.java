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

import static java.lang.String.format;

import com.google.common.reflect.TypeParameter;
import com.google.common.reflect.TypeToken;
import org.lanternpowered.shards.Dyn;
import org.lanternpowered.shards.Opt;
import org.lanternpowered.shards.component.provider.HolderProvider;
import org.lanternpowered.shards.component.HolderSpec;
import org.lanternpowered.shards.component.provider.OptHolderProvider;
import org.lanternpowered.shards.inject.Binder;
import org.lanternpowered.shards.component.Params;
import org.lanternpowered.shards.processor.ProcessorException;
import org.lanternpowered.shards.processor.PostProcessor;
import org.lanternpowered.shards.processor.ProcessorContext;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class HolderPostProcessor implements PostProcessor {

    @Override
    public void process(ProcessorContext context, Binder binder) throws ProcessorException {
        final List<HolderSpec> specs = context.computeIfAbsent(Params.HOLDER_TYPES, ArrayList::new);

        final List<Class<?>> types = specs.stream()
                .filter(type -> !type.getType().isInterface())
                .map(HolderSpec::getType)
                .collect(Collectors.toList());
        // Make sure that it is possible to attach the component to any holder
        for (int i = 0; i < types.size(); i++) {
            for (int k = 0; k < types.size(); k++) {
                if (i != k) {
                    final Class<?> a = types.get(i);
                    final Class<?> b = types.get(k);
                    if (!a.isAssignableFrom(b) && !b.isAssignableFrom(a)) {
                        throw new ProcessorException(format("The holder specs '%s' and '%s' can never be used in the same Component.",
                                a.getName(), b.getName()));
                    }
                }
            }
        }

        // Now we need to register providers for all the found holder types, but first, remove duplicates by using a set
        for (HolderSpec spec : new HashSet<>(specs)) {
            if (spec.isRequired()) {
                //noinspection unchecked
                binder.bind((Class) spec.getType()).toProvider(HolderProvider.INSTANCE);
            } else {
                //noinspection unchecked
                binder.bind(createOptType(spec.getType())).toProvider(new OptHolderProvider(spec.getType()));
            }
            WarnComponentTypeProcessor.LOGGER.info("Holder spec {}", spec);
        }

        // Now remove dependency types that are inherited, ect, to reduce the amount
        // of instance checks when trying to apply the component.

        // Only do instance checks for the required
        final Set<Class<?>> checks = specs.stream()
                .filter(HolderSpec::isRequired)
                .map(HolderSpec::getType)
                .collect(Collectors.toSet());
        // Reduce the checks
        for (Class<?> type : new HashSet<>(checks)) {
            if (!checks.contains(type)) {
                continue;
            }
            checks.removeAll(TypeToken.of(type).getTypes().rawTypes());
        }

        // TODO: Store instance checks in component type
    }

    static <T> TypeToken<Opt<T>> createOptType(Class<T> type) {
        return new TypeToken<Opt<T>>() {}
                .where(new TypeParameter<T>() {}, type);
    }

    static <T> TypeToken<Dyn<T>> createDynType(Class<T> type) {
        return new TypeToken<Dyn<T>>() {}
                .where(new TypeParameter<T>() {}, type);
    }
}
