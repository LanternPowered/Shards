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
package org.lanternpowered.shards.impl;

import com.google.common.base.MoreObjects;
import org.lanternpowered.shards.Component;

import java.util.Objects;

final class RequirementWrapper {

    private final Class<? extends Component> type;
    private final boolean autoAttach;

    RequirementWrapper(Class<? extends Component> type, boolean autoAttach) {
        this.autoAttach = autoAttach;
        this.type = type;
    }

    public Class<? extends Component> getType() {
        return this.type;
    }

    public boolean getAutoAttach() {
        return this.autoAttach;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("type", this.type.getName())
                .add("autoAttach", this.autoAttach)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof RequirementWrapper)) {
            return false;
        }
        final RequirementWrapper other = (RequirementWrapper) o;
        return this.type == other.type && this.autoAttach == other.autoAttach;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.type, this.autoAttach);
    }
}
