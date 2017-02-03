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

import com.google.common.reflect.TypeToken;
import org.lanternpowered.shards.inject.Binder;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public interface ProcessorContext {

    static ProcessorContext of(Class<?> target) {
        return of(TypeToken.of(target));
    }

    static ProcessorContext of(TypeToken<?> target) {
        return new ProcessorContextImpl(target);
    }

    /**
     * Gets the {@link TypeToken} of the target class
     * that is being processed.
     *
     * @return The type token
     */
    TypeToken<?> getTarget();

    /**
     * Adds a piece of data to the context.
     *
     * @param param The param
     * @param value The value
     */
    <T> void put(Param<T> param, T value);

    /**
     * Adds a piece of data to the context.
     *
     * @param param The param
     * @param value The value
     */
    <T> T putIfAbsent(Param<T> param, T value);

    /**
     * Adds a piece of data to the context.
     *
     * @param param The param
     * @param supplier The value supplier
     */
    <T> T computeIfAbsent(Param<T> param, Supplier<T> supplier);

    /**
     * Gets the object for the specified key.
     *
     * @param param The param
     * @return The value if present
     */
    <T> Optional<T> get(Param<T> param);

    /**
     * Processes this context for the specified {@link Binder} and {@link Processor}s.
     *
     * @param binder The binder
     * @param processors The processors
     * @throws ProcessorException If something went wrong while processing the context
     */
    void process(Binder binder, List<Processor> processors) throws ProcessorException;
}
