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

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class AbstractComponentHolder implements ComponentHolder {

    private final Map<Class<?>, Component> components = new ConcurrentHashMap<>();

    @Override
    public <T extends Component> T addComponent(Class<T> type) {
        return addComponent(ComponentType.get(type));
    }

    public <T extends Component> T addComponent(ComponentType<T> component) {
        return null;
    }

    @Override
    public boolean addComponent(Component component) throws IllegalArgumentException {
        checkNotNull(component, "component");
        final AbstractComponent component1 = (AbstractComponent) component;
        synchronized (component1.lock) {
            checkArgument(component1.holder == null,
                    "The Component %s is already attached to a different ComponentHolder.", component);
            if (this.components.putIfAbsent(component.getClass(), component) != null) {
                return false;
            }
            attachComponentData(component1);
            return true;
        }
    }

    private void detachComponentData(AbstractComponent component) {
        component.holder = null;
    }

    private boolean attachComponentData(AbstractComponent component) {
        // First, prepare the dependencies of this component holder
        // for the target component
        if (!attachRequirements(component.componentType.getRequirements())) {
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
