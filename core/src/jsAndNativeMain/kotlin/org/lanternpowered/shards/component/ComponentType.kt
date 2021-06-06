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

import kotlin.reflect.KClass

actual abstract class ComponentType<T : Component> internal constructor(
  internal actual val internalType: InternalComponentType<T>
) {

  /**
   * Constructs a new [ComponentType] with the specified [Component]
   * instantiator.
   */
  actual constructor(componentClass: KClass<T>) :
    this(resolveInternalComponentType(componentClass))

  actual final override fun equals(other: Any?): Boolean =
    other is ComponentType<*> && other.internalType == internalType

  actual final override fun hashCode(): Int =
    internalType.hashCode()

  actual final override fun toString(): String =
    internalType.toString()

  actual companion object
}
