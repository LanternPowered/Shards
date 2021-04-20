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
import org.lanternpowered.shards.internal.resolveInternalComponentType
import kotlin.jvm.JvmSynthetic
import kotlin.reflect.KClass

/**
 * Represents the type of a [Component].
 */
open class ComponentType<T : Component> {

  /**
   * Constructs a new [ComponentType] with the specified [Component]
   * instantiator.
   */
  constructor(instantiator: () -> T) {
    @Suppress("UNCHECKED_CAST")
    val componentClass = instantiator()::class as KClass<T>
    internalType = resolveInternalComponentType(componentClass)
  }

  /**
   * Internal constructor used in JVM and JS targets.
   */
  internal constructor(componentClass: KClass<T>, ignored: Nothing? = null) {
    internalType = resolveInternalComponentType(componentClass)
  }

  /**
   * Information resolved about the component type.
   */
  @JvmSynthetic
  internal val internalType: InternalComponentType<T>

  internal val instantiator: () -> T
    get() = internalType.instantiator

  final override fun equals(other: Any?): Boolean =
    other is ComponentType<*> && other.internalType == internalType

  final override fun hashCode(): Int =
    internalType.hashCode()

  final override fun toString(): String =
    internalType.toString()
}
