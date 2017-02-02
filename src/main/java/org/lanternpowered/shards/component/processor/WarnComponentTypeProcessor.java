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
package org.lanternpowered.shards.component.processor;

import com.google.common.reflect.TypeToken;
import com.google.inject.ImplementedBy;
import com.google.inject.ProvidedBy;
import org.lanternpowered.shards.Component;
import org.lanternpowered.shards.inject.Binder;
import org.lanternpowered.shards.processor.ProcessorContext;
import org.lanternpowered.shards.processor.ProcessorException;
import org.lanternpowered.shards.processor.TypeProcessor;
import org.lanternpowered.shards.util.logger.Logger;

public class WarnComponentTypeProcessor implements TypeProcessor {

    static final Logger LOGGER = Logger.get("ComponentProcessor");

    @Override
    public void process(ProcessorContext context, TypeToken<?> type, Binder binder) throws ProcessorException {
        final Class<?> raw = type.getRawType();
        // Only run this processor for components
        if (!Component.class.isAssignableFrom(raw)) {
            return;
        }
        if (raw.isAnnotationPresent(ImplementedBy.class) ||
                raw.isAnnotationPresent(ProvidedBy.class)) {
            LOGGER.warn("A Component implementation should not be manually provided through ImplementedBy or ProvidedBy.");
        }
    }
}
