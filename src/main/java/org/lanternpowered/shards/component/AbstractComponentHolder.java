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

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import org.lanternpowered.shards.Component;
import org.lanternpowered.shards.ComponentHolder;
import org.lanternpowered.shards.util.GuavaCollectors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Nullable;

public class AbstractComponentHolder implements ComponentHolder {

    private final Map<Class<?>, AbstractComponent> components = new ConcurrentHashMap<>();

    @Override
    public <T extends Component> Optional<T> addComponent(Class<T> type) {
        return addComponent(ComponentType.get(type));
    }

    public <T extends Component> Optional<T> addComponent(ComponentType<T> component) {
        AbstractComponent component1 = this.components.get(component.getComponentType());
        if (component1 != null) {
            //noinspection unchecked
            return Optional.of((T) component1);
        }
        final List<Class<? extends Component>> attach = new ArrayList<>();
        if (!canAddComponent(component, attach)) {
            return Optional.empty();
        }
        attach.forEach(this::addComponent);
        final ComponentInjectionContext context = ComponentInjectionContext.current();
        context.join(this);
        final T instance;
        try {
            instance = component.getInjector().getInstance(component.getComponentType());
        } finally {
            context.exit();
        }
        component1 = (AbstractComponent) instance;
        synchronized (component1.lock) {
            component1.holder = this;
        }
        this.components.put(component.getComponentType(), component1);
        return Optional.of(instance);
    }

    private boolean canAddComponent(ComponentType<?> component, @Nullable List<Class<? extends Component>> attach) {
        final Collection<DependencySpec> dependencySpecs = component.getDependencies();
        for (DependencySpec spec : dependencySpecs) {
            if (!this.components.containsKey(spec.getType()) && spec.getDependencyType() != DependencySpec.Type.OPTIONAL) {
                if (spec.getAutoAttach()) {
                    if (canAddComponent(ComponentType.get(spec.getType()), null)) {
                        if (attach != null) {
                            attach.add(spec.getType());
                        }
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean addComponent(Component component) throws IllegalArgumentException {
        checkNotNull(component, "component");
        final AbstractComponent component1 = (AbstractComponent) component;
        synchronized (component1.lock) {
            checkArgument(component1.holder == null,
                    "The Component %s is already attached to a different ComponentHolder.", component);
            if (this.components.putIfAbsent(component.getClass(), component1) != null) {
                return false;
            }
            component1.holder = this;
            final ComponentInjectionContext context = ComponentInjectionContext.current();
            context.join(this);
            try {
                component1.componentType.getInjector().injectMembers(component1);
            } finally {
                context.exit();
            }
            return true;
        }
    }
    @Override
    public <T extends Component, I extends T> boolean replaceComponent(Class<T> type, I component) throws IllegalArgumentException {
        checkNotNull(type, "type");
        checkNotNull(component, "component");
        final AbstractComponent component1 = (AbstractComponent) component;
        final List<Class<? extends Component>> attach = new ArrayList<>();
        if (!canAddComponent(component1.componentType, attach)) {
            return false;
        }
        synchronized (component1.lock) {
            checkArgument(component1.holder == null,
                    "The Component %s is already attached to a different ComponentHolder.", component);
            for (Map.Entry<Class<?>, AbstractComponent> entry : this.components.entrySet()) {
                if (type.isInstance(entry.getValue())) {
                    final AbstractComponent component2 = entry.getValue();
                    synchronized (component2.lock) {
                        attach.forEach(this::addComponent);
                        final ComponentInjectionContext context = ComponentInjectionContext.current();
                        context.join(this);
                        try {
                            component1.componentType.getInjector().injectMembers(component1);
                        } finally {
                            context.exit();
                        }
                        component1.holder = this;
                        entry.setValue(component1);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public <T extends Component, I extends T> boolean replaceComponent(Class<T> type, Class<I> component) throws IllegalArgumentException {
        checkNotNull(type, "type");
        checkNotNull(component, "component");
        final ComponentType<I> componentType = ComponentType.get(component);
        final List<Class<? extends Component>> attach = new ArrayList<>();
        if (!canAddComponent(componentType, attach)) {
            return false;
        }
        for (Map.Entry<Class<?>, AbstractComponent> entry : this.components.entrySet()) {
            if (type.isInstance(entry.getValue())) {
                final AbstractComponent component2 = entry.getValue();
                synchronized (component2.lock) {
                    attach.forEach(this::addComponent);
                    final ComponentInjectionContext context = ComponentInjectionContext.current();
                    context.join(this);
                    final T instance;
                    try {
                        instance = componentType.getInjector().getInstance(component);
                    } finally {
                        context.exit();
                    }
                    final AbstractComponent component1 = (AbstractComponent) instance;
                    component1.holder = this;
                    synchronized (component1.lock) {
                        entry.setValue(component1);
                    }
                    return true;
                }
            }
        }
        return false;
    }

    private void detachComponentData(AbstractComponent component) {
        component.holder = null;
    }

    private boolean attachComponentData(AbstractComponent component) {
        // First, prepare the dependencies of this component holder
        // for the target component
        if (!attachRequirements(component.componentType.getDependencies())) {
            return false;
        }
        // Now, inject the component data into the component instance
        component.holder = this;
        return true;
    }

    private boolean attachRequirements(Collection<DependencySpec> requirements) {
        for (DependencySpec requirement : requirements) {
            final Optional optComponent = getComponent(requirement.getType());
            if (!optComponent.isPresent()) {
                // This component holder is missing the dependency and cannot
                // be attached automatically, just fail
                if (!requirement.getAutoAttach()) {
                    return false;
                }
                addComponent(requirement.getType());
            }
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Component> Optional<T> getComponent(Class<T> type) {
        final Component component = this.components.get(type);
        if (component != null) {
            return Optional.of((T) component);
        }
        return this.components.values().stream()
                .filter(type::isInstance)
                .map(type::cast)
                .findFirst();
    }

    @Override
    public <T extends Component> Collection<T> getComponents(Class<T> type) {
        return this.components.values().stream()
                .filter(type::isInstance)
                .map(type::cast)
                .collect(GuavaCollectors.toImmutableList());
    }

    @Override
    public <T extends Component> Collection<T> removeComponent(Class<T> type) {
        return null;
    }
}
