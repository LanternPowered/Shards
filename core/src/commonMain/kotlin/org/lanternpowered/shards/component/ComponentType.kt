/*
 * Shards
 *
 * Copyright (c) LanternPowered <https://www.lanternpowered.org>
 * Copyright (c) contributors
 *
 * This work is licensed under the terms of the MIT License (MIT). For
 * a copy, see 'LICENSE.txt' or <https://opensource.org/licenses/MIT>.
 */
@file:Suppress("FunctionName")

package org.lanternpowered.shards.component

import org.lanternpowered.shards.internal.InternalComponentType

/**
 * Represents the type of a [Component].
 *
 * @constructor Constructs a new [ComponentType] with the specified [Component]
 *              instantiator.
 */
expect abstract class ComponentType<T : Component>(instantiator: () -> T) {

  /**
   * Information resolved about the component type.
   */
  internal val internalType: InternalComponentType<T>

  final override fun equals(other: Any?): Boolean
  final override fun hashCode(): Int
  final override fun toString(): String
}
