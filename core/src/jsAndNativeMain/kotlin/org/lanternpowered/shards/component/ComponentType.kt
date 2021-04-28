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

import org.lanternpowered.shards.util.AccessMode
import org.lanternpowered.shards.util.unsafeCast
import kotlin.jvm.JvmSynthetic
import kotlin.reflect.KClass

actual abstract class ComponentType<T : Component> {

  @JvmSynthetic
  internal actual val internalType: InternalComponentType<T>

  @JvmSynthetic
  internal actual val accessMode: AccessMode

  /**
   * Constructs a new [ComponentType] with the specified [Component]
   * instantiator.
   */
  actual constructor(instantiator: () -> T) {
    val componentClass = instantiator()::class.unsafeCast<KClass<T>>()
    internalType = resolveInternalComponentType(componentClass, instantiator)
    accessMode = AccessMode.Undefined
  }

  internal constructor(
    internalType: InternalComponentType<T>, accessMode: AccessMode
  ) {
    this.internalType = internalType
    this.accessMode = accessMode
  }

  actual final override fun equals(other: Any?): Boolean =
    other is ComponentType<*> && other.internalType == internalType

  actual final override fun hashCode(): Int =
    internalType.hashCode()

  actual final override fun toString(): String =
    internalType.toString()

  actual companion object
}
