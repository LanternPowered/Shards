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

/**
 * Represents a component of an entity. Components should be immutable and
 * only contain data, not any or a limited amount of logic.
 *
 * Component classes are not allowed to be abstract or subclassed. Ideally is
 * a component a data or value class.
 */
interface Component

/**
 * Modifies the component using the specified [operation] and updates the last
 * modified field if the component is an [InvalidatableComponent].
 */
inline fun <T : Component> T.modify(operation: T.() -> Unit) {
  operation()
}
