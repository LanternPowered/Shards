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
 * Constructs a new [ComponentType] with the specified type [T].
 */
inline fun <reified T : Component> componentType(): ComponentType<T> =
  SimpleComponentType(T::class)

/**
 * Constructs a new [ComponentType] with the specified type component class.
 */
fun <T : Component> ComponentType(componentClass: KClass<T>): ComponentType<T> =
  SimpleComponentType(componentClass)

/**
 * Represents the type of a [Component].
 *
 * @constructor Constructs a new [ComponentType] with the specified
 *              `componentClass`.
 */
abstract class ComponentType<T : Component> {

  // TODO: Delete this
  constructor() {
    /*
    val theClass = this::class
    val superClass = theClass.superclass
    check(superClass == ComponentType::class.java) {
      "Only direct subclasses of ComponentType are allowed." }
    val superType = theClass.genericSuperclass
    check(superType is ParameterizedType) {
      "Direct subclasses of ComponentType must be a parameterized type." }
    val id = this.idCounter.getAndIncrement()
    @Suppress("UNCHECKED_CAST")
    val componentClass = (superType.rawType as Class<T>).kotlin
    return ResolvedComponentType(id, componentClass)*/
    internalType = resolveInternalComponentType(Component::class) as InternalComponentType<T>
  }

  internal constructor(componentClass: KClass<T>, ignored: Unit?) {
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

@PublishedApi
internal class SimpleComponentType<T : Component>(componentClass: KClass<T>) :
  ComponentType<T>(componentClass, null)
