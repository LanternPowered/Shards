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
package org.lanternpowered.shards.test;

import com.google.inject.name.Names;
import org.junit.Test;
import org.lanternpowered.shards.component.AbstractComponentHolder;
import org.lanternpowered.shards.component.ComponentRegistryImpl;
import org.lanternpowered.shards.inject.AbstractModule;
import org.lanternpowered.shards.test.components.ExtendedTestComponent;
import org.lanternpowered.shards.test.components.TestComponent;

public class ComponentsTest {

    @Test
    public void test() {
        ComponentRegistryImpl.get().registerModule(new AbstractModule() {
            @Override
            protected void configure() {
                bindConstant().annotatedWith(Names.named("AgentNumber")).to("007");
            }
        }).to(TestComponent.class);

        final AbstractComponentHolder componentHolder = new FooComponentHolder();
        final ExtendedTestComponent component = componentHolder.addComponent(ExtendedTestComponent.class).get();
        System.out.println("Agent -> " + component.agentNumber);
    }
}
