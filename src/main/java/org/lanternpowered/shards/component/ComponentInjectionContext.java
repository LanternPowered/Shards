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

import org.lanternpowered.shards.ComponentHolder;

import java.util.ArrayDeque;
import java.util.Deque;

public final class ComponentInjectionContext {

    private static final ThreadLocal<ComponentInjectionContext> context = ThreadLocal.withInitial(ComponentInjectionContext::new);

    /**
     * Gets the {@link ComponentInjectionContext} for the current {@link Thread}.
     *
     * @return The inject context
     */
    public static ComponentInjectionContext current() {
        return context.get();
    }

    private final Deque<AbstractComponentHolder> deque = new ArrayDeque<>();

    /**
     * Joins the {@link ComponentInjectionContext} with the specified
     * {@link ComponentHolder}.
     *
     * @param componentHolder The component holder
     */
    public void join(AbstractComponentHolder componentHolder) {
        checkNotNull(componentHolder, "componentHolder");
        this.deque.add(componentHolder);
    }

    /**
     * Exits the current context and returns the {@link AbstractComponentHolder}.
     *
     * @return The component holder
     */
    public AbstractComponentHolder exit() {
        return this.deque.pop();
    }

    /**
     * Gets the current {@link AbstractComponentHolder}
     * in the {@link ComponentInjectionContext}.
     *
     * @return The current component holder
     */
    public AbstractComponentHolder get() {
        return this.deque.peek();
    }
}
