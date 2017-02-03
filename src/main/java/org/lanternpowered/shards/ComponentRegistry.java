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
package org.lanternpowered.shards;

import com.google.inject.Module;
import org.lanternpowered.shards.processor.Processor;

public interface ComponentRegistry {

    /**
     * Registers a {@link Processor} that will be applied to all
     * the processed {@link Component} types.
     *
     * @param processor The processor
     */
    void registerProcessor(Processor processor);

    /**
     * Registers a {@link Processor} that will be applied to all
     * the processed {@link Component} types.
     *
     * @param target The target the insert the new processor after
     * @param processor The processor
     */
    void registerProcessorAfter(Class<? extends Processor> target, Processor processor);

    /**
     * Registers a {@link Processor} that will be applied to all
     * the processed {@link Component} types.
     *
     * @param target The target the insert the new processor before
     * @param processor The processor
     */
    void registerProcessorBefore(Class<? extends Processor> target, Processor processor);

    /**
     * Register a global {@link Module} that should be used for the injection
     * of all the {@link Component}s.
     *
     * @param module The module
     */
    default void registerGlobalModule(Module module) {
        registerModule(module).to(Component.class);
    }

    /**
     * Register a {@link Module} that should target a specific set of {@link Component}
     * that can be specified through the {@link TargetedModuleBuilder}.
     *
     * @param module The module
     * @return The targeted module builder
     */
    TargetedModuleBuilder registerModule(Module module);
}
