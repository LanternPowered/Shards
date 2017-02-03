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
package org.lanternpowered.shards.processor;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

import com.google.common.collect.Iterables;
import com.google.common.reflect.TypeToken;
import com.google.inject.Inject;
import org.lanternpowered.shards.inject.Binder;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

final class ProcessorContextImpl implements ProcessorContext {

    private final Map<Integer, Object> params = new HashMap<>();
    private final TypeToken<?> target;

    ProcessorContextImpl(TypeToken<?> target) {
        this.target = target;
    }

    @Override
    public TypeToken<?> getTarget() {
        return this.target;
    }

    @Override
    public <T> void put(Param<T> param, T value) {
        requireNonNull(value, "value");
        this.params.put(param.internalId, value);
    }

    @Override
    public <T> T putIfAbsent(Param<T> param, T value) {
        requireNonNull(value, "value");
        //noinspection unchecked
        return (T) this.params.putIfAbsent(param.internalId, value);
    }

    @Override
    public <T> T computeIfAbsent(Param<T> param, Supplier<T> supplier) {
        requireNonNull(supplier, "supplier");
        //noinspection unchecked
        return (T) this.params.computeIfAbsent(param.internalId, param1 -> supplier.get());
    }

    @Override
    public <T> Optional<T> get(Param<T> param) {
        //noinspection unchecked
        return Optional.ofNullable((T) this.params.get(param.internalId));
    }

    @Override
    public void process(Binder binder, List<Processor> processors) throws ProcessorException {
        final List<TypeProcessor> typeProcessors = processors.stream()
                .filter(p -> p instanceof TypeProcessor).map(p -> (TypeProcessor) p).collect(Collectors.toList());
        final List<PostProcessor> postProcessors = processors.stream()
                .filter(p -> p instanceof PostProcessor).map(p -> (PostProcessor) p).collect(Collectors.toList());
        final List<ParameterProcessor> parameterProcessors = processors.stream()
                .filter(p -> p instanceof ParameterProcessor).map(p -> (ParameterProcessor) p).collect(Collectors.toList());
        process(binder, this.target, typeProcessors, parameterProcessors, new HashSet<>());
        for (PostProcessor postProcessor : postProcessors) {
            postProcessor.process(this, binder);
        }
    }

    private void process(Binder binder, TypeToken<?> typeToken, List<TypeProcessor> typeProcessors,
            List<ParameterProcessor> parameterProcessors, Set<Type> processed) throws ProcessorException {
        final Class<?> type = typeToken.getRawType();
        for (TypeProcessor typeProcessor : typeProcessors) {
            typeProcessor.process(this, typeToken, binder);
        }
        for (Field field : type.getDeclaredFields()) {
            field.setAccessible(true);
            if (!field.isAnnotationPresent(Inject.class) &&
                    !field.isAnnotationPresent(javax.inject.Inject.class)) {
                continue;
            }
            final TypeToken<?> fieldType = TypeToken.of(field.getGenericType());
            for (ParameterProcessor parameterProcessor : parameterProcessors) {
                try {
                    if (parameterProcessor.process(this, fieldType, field, binder)) {
                        break;
                    }
                } catch (ProcessorException e) {
                    throw new ProcessorException(format("An error occurred while processing a field: [%s#%s] of type [%s]",
                            type.getName(), field.getName(), fieldType.toString()), e);
                }
            }
        }
        for (Executable executable : Iterables.concat(Arrays.asList(type.getDeclaredConstructors()), Arrays.asList(type.getDeclaredMethods()))) {
            executable.setAccessible(true);
            if (!executable.isAnnotationPresent(Inject.class) &&
                    !executable.isAnnotationPresent(javax.inject.Inject.class)) {
                continue;
            }
            final Type[] genericParameterTypes = executable.getGenericParameterTypes();
            final Annotation[][] annotations = executable.getParameterAnnotations();
            for (int i = 0; i < genericParameterTypes.length; i++) {
                final AnnotatedElement annotatedElement = new ArrayAnnotatedElement(annotations[i]);
                final TypeToken<?> parameterType = TypeToken.of(genericParameterTypes[i]);
                for (ParameterProcessor parameterProcessor : parameterProcessors) {
                    try {
                        if (parameterProcessor.process(this, parameterType, annotatedElement, binder)) {
                            break;
                        }
                    } catch (ProcessorException e) {
                        throw new ProcessorException(format("An error occurred while processing a method/constructor parameter: "
                                        + "[%s#%s] at the parameter %s of type [%s]",
                                type.getName(), executable.getName(), i, parameterType.toString()), e);
                    }
                }
            }
        }
        for (Type interf : type.getGenericInterfaces()) {
            if (processed.add(interf)) {
                process(binder, TypeToken.of(interf), typeProcessors, parameterProcessors, processed);
            }
        }
        final Class<?> superClass = type.getSuperclass();
        if (superClass != null && superClass != Object.class) {
            process(binder, TypeToken.of(type.getGenericSuperclass()), typeProcessors, parameterProcessors, processed);
        }
    }
}
