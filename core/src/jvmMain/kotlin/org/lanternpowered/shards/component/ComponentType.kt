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

import org.lanternpowered.shards.util.unsafeCast
import java.lang.reflect.ParameterizedType
import kotlin.reflect.KClass

actual abstract class ComponentType<T : Component> {

  @JvmSynthetic
  internal actual val internalType: InternalComponentType<T>

  /**
   * Constructs a new [ComponentType] with the specified [Component]
   * instantiator.
   */
  actual constructor(componentClass: KClass<T>) {
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
    val componentType = supertype.actualTypeArguments[0]
    check(componentType is Class<*>) {
      "The generic parameter type must be a non-parameterized Class<*>." }
    val componentClass = componentType.unsafeCast<Class<T>>().kotlin
    internalType = resolveInternalComponentType(componentClass)
  }

  internal constructor(internalType: InternalComponentType<T>) {
    this.internalType = internalType
  }

  actual final override fun equals(other: Any?): Boolean =
    other is ComponentType<*> && other.internalType == internalType

  actual final override fun hashCode(): Int =
    internalType.hashCode()

  actual final override fun toString(): String =
    internalType.toString()

  actual companion object {

    /**
     * Returns the [ComponentType] for the specified type component class.
     */
    @JvmStatic
    fun <T : Component> of(componentClass: KClass<T>): ComponentType<T> =
      resolveInternalComponentType(componentClass).componentType

    /**
     * Returns the [ComponentType] for the specified type component class.
     */
    @JvmStatic
    fun <T : Component> of(componentClass: Class<T>): ComponentType<T> =
      resolveInternalComponentType(componentClass).componentType
  }
}
