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
import java.lang.reflect.ParameterizedType
import kotlin.reflect.KClass

actual abstract class ComponentType<T : Component> {

  @JvmSynthetic
  internal actual val internalType: InternalComponentType<T>

  /**
   * Constructs a new [ComponentType] with the specified [Component]
   * instantiator.
   */
  actual constructor(instantiator: () -> T) {
    @Suppress("UNCHECKED_CAST")
    val componentClass = instantiator()::class as KClass<T>
    internalType = resolveInternalComponentType(componentClass)
  }

  /**
   * Constructs a new [ComponentType] with the default [Component]
   * instantiator.
   */
  constructor() {
    val javaClass = javaClass
    val superclass = javaClass.superclass
    check(superclass == ComponentType::class.java) {
      "Only direct subclasses of ComponentType are allowed." }
    val supertype = javaClass.genericSuperclass
    check(supertype is ParameterizedType) {
      "Direct subclasses of ComponentType must be a parameterized type." }
    @Suppress("UNCHECKED_CAST")
    val componentClass = (supertype.rawType as Class<T>).kotlin
    internalType = resolveInternalComponentType(componentClass)
  }

  /**
   * Internal constructor.
   */
  @Suppress("UNUSED_PARAMETER")
  internal constructor(componentClass: KClass<T>, ignored: Nothing?) {
    internalType = resolveInternalComponentType(componentClass)
  }

  actual final override fun equals(other: Any?): Boolean =
    other is ComponentType<*> && other.internalType == internalType

  actual final override fun hashCode(): Int =
    internalType.hashCode()

  actual final override fun toString(): String =
    internalType.toString()
}
