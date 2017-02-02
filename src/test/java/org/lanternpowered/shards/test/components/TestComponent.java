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
package org.lanternpowered.shards.test.components;

import com.google.inject.Inject;
import org.lanternpowered.shards.Holder;
import org.lanternpowered.shards.OnAttach;
import org.lanternpowered.shards.OnDetach;
import org.lanternpowered.shards.Opt;
import org.lanternpowered.shards.impl.AbstractComponent;
import org.lanternpowered.shards.test.FooComponentHolder;

public class TestComponent extends AbstractComponent {

    // Inject the holder of the component,
    // can be any interface, the annotation should be there
    @Inject @Holder public FooComponentHolder holder;

    // All the default inject components are required
    @Inject public ExtendedOtherComponent other;

    // The foo component is optional, the status of this
    // is dynamically updated.
    @Inject public Opt<ExtendedOtherComponent> optFooComponent;

    public AnotherTestComponent another;

    @OnAttach
    public void onAttach() {
        System.out.println("onAttach");
    }

    @OnDetach
    public void onDetach() {
    }

    @Inject
    private void setOtherComponent(AnotherTestComponent another) {
        System.out.println("Set other test component: " + another);
        this.another = another;
    }
}
