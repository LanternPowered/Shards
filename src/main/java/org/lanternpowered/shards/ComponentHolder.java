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
package org.lanternpowered.shards;

import java.util.Collection;
import java.util.Optional;

public interface ComponentHolder {

    /**
     * Attempts to attach a {@link Component} of the given type to this {@link ComponentHolder},
     * if there is already a component of the given type, then that instance will be returned.
     * <p>
     * This method expects that when the {@link Component} is either abstract or an interface,
     * a default implementation is provided through the {@link ComponentRegistry}.
     *
     * @param type The component type to attach
     * @return The component instance
     */
    <T extends Component> T addComponent(Class<T> type);

    /**
     * Attempts to attach the given {@link Component} to this {@link ComponentHolder}. The method
     * will return {@code true} if it was successful, adding a component will be successful if there
     * isn't a {@link Component} with the same type.
     *
     * @param component The component to attach
     * @return Whether the attachment was successful
     * @throws IllegalArgumentException If the given component instance is already attached
     */
    boolean addComponent(Component component) throws IllegalArgumentException;

    /**
     * Attempts to replace the {@link Component} attached to the given type. If there are multiple
     * ones found, only the first possible one will be replaced. If there were none found, the
     * {@link Component} will just be attached to this holder.
     *
     * @param type The component type to replace
     * @param component The new component instance
     * @return Whether the replacement was successful
     * @throws IllegalArgumentException If the given component instance is already attached
     */
    <T extends Component, I extends T> boolean replaceComponent(Class<T> type, I component) throws IllegalArgumentException;

    /**
     * Attempts to replace the {@link Component} attached to the given type. If there are multiple
     * ones found, only the first possible one will be replaced. If there were none found, the
     * {@link Component} will just be attached to this holder.
     *
     * @param type The component type to replace
     * @param component The new component type
     * @return Whether the replacement was successful
     * @throws IllegalArgumentException If the given component instance is already attached
     */
    <T extends Component, I extends T> boolean replaceComponent(Class<T> type, Class<I> component) throws IllegalArgumentException;

    /**
     * Gets the {@link Component} of the given type if present, otherwise {@link Optional#empty()}.
     * <p>
     * Only the first {@link Component} will be returned if there
     * are multiple ones for the given type.
     *
     * @param type The component type
     * @return The component instance if present
     */
    <T extends Component> Optional<T> getComponent(Class<T> type);

    /**
     * Gets a {@link Collection} with all the {@link Component}s
     * of the given type.
     *
     * @param type The component type
     * @return A collection with the components
     */
    <T extends Component> Collection<T> getComponents(Class<T> type);

    /**
     * Attempts to remove all the {@link Component}s that match the given type, all the components
     * that were removed will be present in the result {@link Collection}.
     *
     * @param type The component type
     * @return A collection with the removed components
     */
    <T extends Component> Collection<T> removeComponent(Class<T> type);
}
