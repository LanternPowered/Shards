/*
 * Shards
 *
 * Copyright (c) LanternPowered <https://www.lanternpowered.org>
 * Copyright (c) contributors
 *
 * This work is licensed under the terms of the MIT License (MIT). For
 * a copy, see 'LICENSE.txt' or <https://opensource.org/licenses/MIT>.
 */
package org.lanternpowered.shards.system

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import org.lanternpowered.shards.component.Component
import org.lanternpowered.shards.entity.Entity
import kotlin.reflect.KClass

/**
 * Represents a context in which a [System] will be executed.
 */
abstract class SystemContext : CoroutineScope {

  /**
   * A sequence of all the entities in the current engine.
   */
  abstract val entities: EntitySeq0

  /**
   * Executes an async task within the scope of the current [System] execution.
   *
   * All launched async tasks will be waited for after [System.execute]. If
   * the async task returns a result that is needed for execution, then it can
   * be awaited for using [Deferred.await].
   */
  abstract fun <R> async(
    operation: suspend CoroutineScope.() -> R
  ): Deferred<R>

  /**
   * Returns if the entity contains a component with the specified [type].
   */
  abstract fun Entity.contains(type: KClass<out Component>): Boolean

  /**
   * Removes the [Component] with the given [type] from the [Entity]. Returns
   * `true` if the component existed, `false` otherwise.
   */
  abstract fun Entity.remove(type: KClass<out Component>): Boolean

  /**
   * Returns the [Component] with the given [type] which is held by the
   * [Entity]. If no compound is found, an [IllegalStateException] will be
   * thrown.
   */
  abstract fun <T : Component> Entity.get(type: KClass<out T>): T

  /**
   * Returns the [Component] with the given [type] which is held by the
   * [Entity]. If no compound is found, `null` will be returned instead.
   */
  abstract fun <T : Component> Entity.getOrNull(type: KClass<out T>): T?

  /**
   * Sets the [Component] on the [Entity].
   */
  abstract fun Entity.set(value: Component)

  /**
   * Returns if the entity contains a component with the specified type [T].
   */
  inline fun <reified T : Component> Entity.contains(): Boolean =
    contains(T::class)

  inline fun <reified T : Component> Entity.remove() = remove(T::class)

  inline fun <reified T : Component> Entity.get() = get(T::class)

  inline fun <reified T : Component> Entity.getOrNull() = getOrNull(T::class)
}
