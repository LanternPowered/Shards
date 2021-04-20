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

import org.lanternpowered.shards.internal.InternalComponentType
import org.lanternpowered.shards.internal.resolveInternalComponentType
import kotlin.jvm.JvmSynthetic
import kotlin.reflect.KClass

actual abstract class ComponentType<T : Component> {

  /**
   * Constructs a new [ComponentType] with the specified [Component]
   * instantiator.
   */
  actual constructor(instantiator: () -> T) {
    @Suppress("UNCHECKED_CAST")
    val componentClass = instantiator()::class as KClass<T>
    internalType = resolveInternalComponentType(componentClass, instantiator)
  }

  /**
   * Internal constructor used in the JS target.
   */
  @Suppress("UNUSED_PARAMETER")
  internal constructor(componentClass: KClass<T>, ignored: Nothing? = null) {
    internalType = resolveInternalComponentType(componentClass)
  }

  @JvmSynthetic
  internal actual val internalType: InternalComponentType<T>

  actual final override fun equals(other: Any?): Boolean =
    other is ComponentType<*> && other.internalType == internalType

  actual final override fun hashCode(): Int =
    internalType.hashCode()

  actual final override fun toString(): String =
    internalType.toString()
}
