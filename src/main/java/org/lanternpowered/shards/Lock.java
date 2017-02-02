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

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Defines whether the target {@link Component} type can be removed from
 * it's {@link ComponentHolder}. There are multiple grades with locking:
 * <table>
 *   <tr>
 *     <td>Replaceable</td>
 *     <td>The implementation of the {@link Component} type may be replaced by a different one.</td>
 *   </tr>
 *   <tr>
 *     <td>Full</td>
 *     <td>The implementation of the {@link Component} cannot be removed or replaced.</td>
 *   </tr>
 * </table>
 */
@Target(TYPE)
@Retention(RUNTIME)
public @interface Lock {

    /**
     * Gets the {@link Type} of the lock.
     *
     * @return The type
     */
    Type type();

    /**
     * Represents the type of the {@link Lock}.
     */
    enum Type {
        /**
         * The implementation of the {@link Component} type may be replaced by a different one.
         */
        REPLACEABLE,
        /**
         * The implementation of the {@link Component} cannot be removed or replaced.
         */
        FULL,
    }
}
