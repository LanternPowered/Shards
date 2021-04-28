/*
 * Shards
 *
 * Copyright (c) LanternPowered <https://www.lanternpowered.org>
 * Copyright (c) contributors
 *
 * This work is licensed under the terms of the MIT License (MIT). For
 * a copy, see 'LICENSE.txt' or <https://opensource.org/licenses/MIT>.
 */
package org.lanternpowered.shards.component

import org.lanternpowered.shards.system.System

/**
 * Represents a component of a component holder (also known as entity). There
 * are generally two ways to compose a component.
 *
 * The first way is to only define the data which should be attached to the
 * component and logic will be separated into a [System].
 *
 * Modifications to components are recommend to be handled through [modify].
 */
interface Component

/**
 * Modifies the component using the specified [operation] and updates the last
 * modified field if the component is an [InvalidatableComponent].
 */
inline fun <T : Component> T.modify(operation: T.() -> Unit) {
  operation()
  // Update the last time the component was modified
  if (this is InvalidatableComponent)
    invalidate()
}

/**
 * Modifies the component using the specified [operation] and updates the last
 * modified field if the component is an [InvalidatableComponent].
 */
inline fun <T : InvalidatableComponent> T.modify(operation: T.() -> Unit) {
  operation()
  // Update the last time the component was modified
  invalidate()
}
