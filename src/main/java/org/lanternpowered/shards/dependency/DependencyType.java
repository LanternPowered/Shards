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
package org.lanternpowered.shards.dependency;

import org.lanternpowered.shards.Component;
import org.lanternpowered.shards.ComponentHolder;
import org.lanternpowered.shards.Dyn;
import org.lanternpowered.shards.Opt;

/**
 * Represents how strict a dependency {@link Component} type should be present
 * on a {@link ComponentHolder} for the depending {@link Component} to function.
 */
public enum DependencyType {

    /**
     * The dependency {@link Component} is optionally available for
     * the target {@link ComponentHolder} in order for the depending
     * {@link Component} to function.
     * <p>
     * The equivalent of this by using annotations is specifying a
     * {@link Opt} with a specific {@link Component} type, for example:
     * <pre>
     * {@code
     *     @Inject Opt<FooComponent> optFooComponent;
     * }
     * </pre>
     */
    OPTIONAL,

    /**
     * The dependency {@link Component} must be available for the
     * the target {@link ComponentHolder} in order for the depending
     * {@link Component} to function.
     * <p>
     * The equivalent of this by using annotations is specifying a
     * a specific {@link Component} type, for example:
     * <pre>
     * {@code
     *     @Inject FooComponent fooComponent;
     * }
     * </pre>
     */
    REQUIRED,

    /**
     * The dependency {@link Component} must be available for the
     * the target {@link ComponentHolder} in order for the depending
     * {@link Component} to function. But allows swapping of the
     * implementation through the methods
     * {@link ComponentHolder#replaceComponent(Class, Class)} and
     * {@link ComponentHolder#replaceComponent(Class, Component)}.
     * <p>
     * The equivalent of this by using annotations is specifying a
     * {@link Dyn} with a specific {@link Component} type, for example:
     * <pre>
     * {@code
     *     @Inject Dyn<FooComponent> fooComponent;
     * }
     * </pre>
     */
    REQUIRED_DYNAMIC,
}
