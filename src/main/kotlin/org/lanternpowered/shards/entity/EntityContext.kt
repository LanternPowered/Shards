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

import org.lanternpowered.shards.Engine
import org.lanternpowered.shards.component.Component
import org.lanternpowered.shards.component.ComponentType
import org.lanternpowered.shards.component.modify

interface EntityContext {

  /**
   * Whether the [Entity] is still active and exists in its [Engine].
   */
  @get:JvmSynthetic
  val Entity.isActive: Boolean

  /**
   * Modifies the entity with the given [operation].
   */
  @JvmSynthetic
  fun Entity.modify(operation: @EntityDsl EntityMutator.() -> Unit): Entity

  /**
   * Returns if the entity contains a component of the specified [type].
   */
  @JvmSynthetic
  operator fun Entity.contains(type: ComponentType<*>): Boolean

  /**
   * Gets the component instance of the given [type] and fails
   * if the component wasn't found.
   */
  @JvmSynthetic
  fun <T : Component> Entity.get(type: ComponentType<T>): T

  /**
   * Gets the component instance of the given [type] and returns `null`
   * if the component wasn't found.
   */
  @JvmSynthetic
  fun <T : Component> Entity.getOrNull(type: ComponentType<T>): T?

  /**
   * Adds the component of the specified [type] to the entity and gets the
   * instance. An [IllegalArgumentException] will be thrown if the entity
   * already owns a component of the specified [type].
   */
  @JvmSynthetic
  fun <T : Component> Entity.add(type: ComponentType<T>): T

  /**
   * Gets the component of the specified [type] if it exists, otherwise a new
   * component will be created.
   */
  @JvmSynthetic
  fun <T : Component> Entity.getOrAdd(type: ComponentType<T>): T

  /**
   * Adds the component of the specified [type] to the entity and gets the
   * instance. An [IllegalArgumentException] will be thrown if the entity
   * already owns a component of the specified [type]. The [operation] will be
   * applied to the constructed component.
   */
  @JvmSynthetic
  fun <T : Component> Entity.add(
    type: ComponentType<T>, operation: T.() -> Unit
  ): T = add(type).apply { modify(operation) }

  /**
   * Gets the component of the specified [type] and applies the given
   * [operation] to it. An [IllegalArgumentException] will be thrown if the
   * entity doesn't own a component with the specified [type].
   */
  @JvmSynthetic
  fun <T : Component> Entity.modify(
    type: ComponentType<T>, operation: T.() -> Unit
  ): T = get(type).apply { modify(operation) }

  /**
   * Gets the component of the specified [type] if it exists, otherwise a new
   * component will be created. The [operation] will be applied to the
   * retrieved or constructed component.
   */
  @JvmSynthetic
  fun <T : Component> Entity.modifyOrAdd(
    type: ComponentType<T>, operation: T.() -> Unit
  ): T = getOrAdd(type).apply { modify(operation) }
}
