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

import com.google.common.reflect.TypeToken;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Test;
import org.lanternpowered.shards.component.ComponentType;
import org.lanternpowered.shards.component.processor.ComponentParameterProcessor;
import org.lanternpowered.shards.component.processor.DependencyPostProcessor;
import org.lanternpowered.shards.component.processor.DependencyTypeProcessor;
import org.lanternpowered.shards.component.processor.HolderParameterProcessor;
import org.lanternpowered.shards.component.processor.HolderPostProcessor;
import org.lanternpowered.shards.component.processor.WarnComponentTypeProcessor;
import org.lanternpowered.shards.inject.AbstractModule;
import org.lanternpowered.shards.inject.DelegateBinder;
import org.lanternpowered.shards.processor.Processor;
import org.lanternpowered.shards.processor.ProcessorContext;
import org.lanternpowered.shards.processor.ProcessorContextImpl;
import org.lanternpowered.shards.processor.ProcessorException;
import org.lanternpowered.shards.test.components.ExtendedTestComponent;
import org.lanternpowered.shards.test.components.TestComponent;

import java.util.ArrayList;
import java.util.List;

public class ComponentsTest {

    @Test
    public void test() {
        final List<Processor> processorList = new ArrayList<>();
        processorList.add(new WarnComponentTypeProcessor());
        processorList.add(new DependencyTypeProcessor());
        processorList.add(new HolderParameterProcessor());
        processorList.add(new ComponentParameterProcessor());
        processorList.add(new HolderPostProcessor());
        processorList.add(new DependencyPostProcessor());

        final ProcessorContextImpl context = new ProcessorContextImpl(TypeToken.of(ExtendedTestComponent.class));
        Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                try {
                    context.process(new DelegateBinder(binder()), processorList);
                } catch (ProcessorException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
