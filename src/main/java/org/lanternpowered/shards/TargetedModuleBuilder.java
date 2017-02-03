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

import java.lang.annotation.Annotation;
import java.util.function.Predicate;

/**
 * Represents the target of a {@link Module}.
 */
public interface TargetedModuleBuilder {

    /**
     * Binds the {@link Module} to a specific scope. All the target {@link Component}s
     * annotated with the specified {@link Annotation} type will be targeted.
     *
     * @param annotation The annotation type
     */
    void toScope(Class<? extends Annotation> annotation);

    /**
     * Binds the {@link Module} to a specific {@link Component} type. All the target
     * {@link Component}s that extend/implement the specified {@link Component} type
     * will be targeted.
     *
     * @param componentType The component type
     */
    void to(Class<? extends Component> componentType);

    /**
     * Binds the {@link Module} to a specific {@link Component} type. All the target
     * {@link Component}s that are matched by the {@link Predicate} will be targeted.
     *
     * @param componentTypeTester The component type tester
     */
    void to(Predicate<Class<? extends Component>> componentTypeTester);
}
