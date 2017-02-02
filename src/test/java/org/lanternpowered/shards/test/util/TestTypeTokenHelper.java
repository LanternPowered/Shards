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
package org.lanternpowered.shards.test.util;

import static org.junit.Assert.assertEquals;
import static org.lanternpowered.shards.util.TypeTokenHelper.toTypeLiteral;
import static org.lanternpowered.shards.util.TypeTokenHelper.toTypeToken;

import com.google.common.reflect.TypeParameter;
import com.google.common.reflect.TypeToken;
import com.google.inject.TypeLiteral;
import org.junit.Test;
import org.lanternpowered.shards.Component;
import org.lanternpowered.shards.Opt;
import org.lanternpowered.shards.test.components.TestComponent;

public class TestTypeTokenHelper {

    @Test
    public void testToString() {
        final TypeToken<Opt<TestComponent>> typeToken = new TypeToken<Opt<TestComponent>>() {};
        final TypeLiteral<Opt<TestComponent>> typeLiteral = new TypeLiteral<Opt<TestComponent>>() {};
        assertEquals(typeToken.toString(), typeLiteral.toString());
    }

    @Test
    public void testToLiteral() {
        final TypeToken<Opt<TestComponent>> typeToken = new TypeToken<Opt<TestComponent>>() {};
        final TypeLiteral<Opt<TestComponent>> typeLiteral = toTypeLiteral(typeToken);
        assertEquals(typeToken.toString(), typeLiteral.toString());
    }

    @Test
    public void testToLiteral2() {
        final TypeToken<Opt<TestComponent>> typeToken = createOpt(TestComponent.class);
        final TypeLiteral<Opt<TestComponent>> typeLiteral = toTypeLiteral(typeToken);
        assertEquals(typeToken.toString(), typeLiteral.toString());
    }

    @Test
    public void testToToken() {
        final TypeLiteral<Opt<TestComponent>> typeLiteral = new TypeLiteral<Opt<TestComponent>>() {};
        final TypeToken<Opt<TestComponent>> typeToken = toTypeToken(typeLiteral);
        assertEquals(typeToken.toString(), typeLiteral.toString());
    }

    private <T extends Component> TypeToken<Opt<T>> createOpt(Class<T> componentType) {
        return new TypeToken<Opt<T>>() {}
                .where(new TypeParameter<T>() {}, componentType);
    }
}
