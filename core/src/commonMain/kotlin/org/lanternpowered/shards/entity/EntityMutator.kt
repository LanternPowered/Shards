/*
 * Shards
 *
 * Copyright (c) LanternPowered <https://www.lanternpowered.org>
 * Copyright (c) contributors
 *
 * This work is licensed under the terms of the MIT License (MIT). For
 * a copy, see 'LICENSE.txt' or <https://opensource.org/licenses/MIT>.
 */
package org.lanternpowered.shards.entity

import org.lanternpowered.shards.component.Component
import org.lanternpowered.shards.component.ComponentType
import org.lanternpowered.shards.component.componentType
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * Represents a mutator to alter [Entity]s.
 */
abstract class EntityMutator {

  @PublishedApi
  internal abstract fun <T : Component> set(
    type: ComponentType<T>, component: T
  )

  /**
   * Returns if the entity contains a component with the specified [type].
   */
  abstract fun contains(type: ComponentType<*>): Boolean

  /**
   * Returns the component with the specified [type]. Throws an
   * [IllegalArgumentException] if the component wasn't found.
   */
  abstract fun <T : Component> get(type: ComponentType<T>): T

  /**
   * Returns the component instance with the specified [type]. Returns `null` if
   * the component wasn't found.
   */
  abstract fun <T : Component> getOrNull(type: ComponentType<T>): T?

  /**
   * Returns the component of the specified [type] and applies the given
   * transform [operation] to it. Throws an [IllegalArgumentException] if the
   * component wasn't found.
   */
  inline fun <T : Component> transform(
    type: ComponentType<T>, operation: (T) -> T
  ): T {
    contract { callsInPlace(operation, InvocationKind.EXACTLY_ONCE) }
    return operation(get(type)).also { set(type, it) }
  }

  /**
   * Sets the [component] on the entity.
   */
  abstract fun set(component: Component)

  /**
   * Attempts to set the component if the entity doesn't contain a component
   * with the same type. Returns the previous component, otherwise the
   * component which is set and was provided by the [supplier].
   */
  inline fun <T : Component> getOrSet(
    type: ComponentType<T>, supplier: () -> T
  ): T {
    contract { callsInPlace(supplier, InvocationKind.AT_MOST_ONCE) }
    val previous = getOrNull(type)
    if (previous != null)
      return previous
    val component = supplier()
    set(type, component)
    return component
  }

  /**
   * Attempts to set the component if the entity doesn't contain a component
   * with the same type. Returns the previous component, otherwise the
   * component which is set and was provided by the [supplier].
   */
  inline fun <reified T : Component> getOrSet(supplier: () -> T): T {
    contract { callsInPlace(supplier, InvocationKind.AT_MOST_ONCE) }
    return getOrSet(componentType(), supplier)
  }
}
