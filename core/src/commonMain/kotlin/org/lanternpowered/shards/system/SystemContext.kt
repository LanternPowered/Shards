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

  abstract val entities: EntitySeq0

  abstract fun <R> async(
    operation: suspend CoroutineScope.() -> R
  ): Deferred<R>

  /**
   * Returns if the entity contains a component with the specified [type].
   */
  abstract fun Entity.contains(type: KClass<out Component>): Boolean

  abstract fun Entity.remove(type: KClass<out Component>)

  abstract fun <T : Component> Entity.get(type: KClass<out T>): T

  abstract fun <T : Component> Entity.getOrNull(type: KClass<out T>): T?

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
