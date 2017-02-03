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
import org.lanternpowered.shards.Component;
import org.lanternpowered.shards.Dyn;
import org.lanternpowered.shards.Opt;
import org.lanternpowered.shards.component.DependencySpec;
import org.lanternpowered.shards.dependency.DependencyType;
import org.lanternpowered.shards.inject.Binder;
import org.lanternpowered.shards.component.Params;
import org.lanternpowered.shards.processor.ParameterProcessor;
import org.lanternpowered.shards.processor.ProcessorContext;
import org.lanternpowered.shards.dependency.AutoAttach;
import org.lanternpowered.shards.processor.ProcessorException;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;

public class ComponentParameterProcessor implements ParameterProcessor {

    private final static TypeVariable<Class<Opt>> OPT_PARAM = Opt.class.getTypeParameters()[0];
    private final static TypeVariable<Class<Dyn>> DYN_PARAM = Dyn.class.getTypeParameters()[0];

    @Override
    public boolean process(ProcessorContext context, TypeToken<?> type, AnnotatedElement annotations, Binder binder) throws ProcessorException {
        Class<?> raw = type.getRawType();
        DependencyType dependencyType = DependencyType.REQUIRED;
        // Only run this processor for components
        // The component can also be wrapped in a Opt object
        if (Opt.class.isAssignableFrom(raw)) {
            raw = type.resolveType(OPT_PARAM).getRawType();
            dependencyType = DependencyType.OPTIONAL;
        } else if (Dyn.class.isAssignableFrom(raw)) {
            raw = type.resolveType(DYN_PARAM).getRawType();
            dependencyType = DependencyType.REQUIRED_DYNAMIC;
        }
        if (!Component.class.isAssignableFrom(raw)) {
            return false;
        }
        final boolean autoAttach = annotations.isAnnotationPresent(AutoAttach.class);
        final List<DependencySpec> dependencies = context.computeIfAbsent(Params.DEPENDENCIES, ArrayList::new);
        //noinspection unchecked
        dependencies.add(new DependencySpec((Class<? extends Component>) raw, dependencyType, autoAttach));
        return true;
    }
}
