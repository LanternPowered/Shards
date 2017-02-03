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

import static com.google.common.base.Preconditions.checkState;

import org.lanternpowered.shards.Component;
import org.lanternpowered.shards.ComponentHolder;

import javax.annotation.Nullable;

public abstract class AbstractComponent implements Component {

    final ComponentType<?> componentType = ComponentType.get(getClass());

    /**
     * The lock of this {@link Component}.
     */
    final Object lock = new Object();

    /**
     * The {@link ComponentHolder} of this {@link Component}.
     */
    @Nullable ComponentHolder holder;

    /**
     * The current amount of dependencies
     * depending on this component.
     */
    int dependencies = 0;

    @Override
    public final ComponentHolder getHolder() {
        synchronized (this.lock) {
            checkState(this.holder != null, "This Component isn't attached to a ComponentHolder.");
            return this.holder;
        }
    }
}
