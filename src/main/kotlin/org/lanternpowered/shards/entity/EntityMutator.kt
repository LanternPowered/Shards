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
import org.lanternpowered.shards.component.modify

/**
 * Represents a mutator to alter [Entity]s.
 */
abstract class EntityMutator {

  /**
   * Returns if the entity contains a component of the specified [type].
   */
  abstract operator fun contains(type: ComponentType<*>): Boolean

  /**
   * Gets the component of the specified [type].
   */
  abstract fun <T : Component> get(type: ComponentType<T>): T

  /**
   * Gets the component of the specified [type] and applies the given
   * [operation] to it. An [IllegalArgumentException] will be thrown if the
   * entity doesn't own a component with the specified [type].
   */
  inline fun <T : Component> modify(
    type: ComponentType<T>, operation: T.() -> Unit
  ): T = get(type).apply { modify(operation) }

  /**
   * Gets the component instance of the specified [type] and returns `null` if
   * the component wasn't found.
   */
  abstract fun <T : Component> getOrNull(type: ComponentType<T>): T?

  /**
   * Adds the component of the specified [type] to the entity and gets the
   * instance. An [IllegalArgumentException] will be thrown if the entity
   * already owns a component of the specified [type].
   */
  abstract fun <T : Component> add(type: ComponentType<T>): T

  /**
   * Gets the component of the specified [type] if it exists, otherwise a new
   * component will be created.
   */
  abstract fun <T : Component> getOrAdd(type: ComponentType<T>): T

  /**
   * Adds the component of the specified [type] to the entity and gets the
   * instance. An [IllegalArgumentException] will be thrown if the entity
   * already owns a component of the specified [type]. The [operation] will be
   * applied to the constructed component.
   */
  inline fun <T : Component> add(
    type: ComponentType<T>, operation: T.() -> Unit
  ): T = add(type).apply { modify(operation) }

  /**
   * Gets the component of the specified [type] if it exists, otherwise a new
   * component will be created. The [operation] will be applied to the
   * retrieved or constructed component.
   */
  inline fun <T : Component> modifyOrAdd(
    type: ComponentType<T>, operation: T.() -> Unit
  ): T = getOrAdd(type).apply { modify(operation) }

  /**
   * Returns if the entity contains a component of the specified [type].
   */
  @Suppress("unused", "UNUSED_PARAMETER")
  @Deprecated(message = "Use the mutator functions.",
    level = DeprecationLevel.ERROR, replaceWith = ReplaceWith("contains(type)"))
  @JvmSynthetic
  fun Entity.contains(type: ComponentType<*>): Boolean =
    error("Use the mutator functions.")

  /**
   * Gets the component instance of the given [type] and fails
   * if the component wasn't found.
   */
  @Suppress("unused", "UNUSED_PARAMETER")
  @Deprecated(message = "Use the mutator functions.",
    level = DeprecationLevel.ERROR, replaceWith = ReplaceWith("get(type)"))
  @JvmSynthetic
  fun <T : Component> Entity.get(type: ComponentType<T>): T =
    error("Use the mutator functions.")

  /**
   * Gets the component instance of the given [type] and returns `null`
   * if the component wasn't found.
   */
  @Suppress("RedundantNullableReturnType", "unused", "UNUSED_PARAMETER")
  @Deprecated(message = "Use the mutator functions.",
    level = DeprecationLevel.ERROR,
    replaceWith = ReplaceWith("getOrNull(type)")
  )
  @JvmSynthetic
  fun <T : Component> Entity.getOrNull(type: ComponentType<T>): T? =
    error("Use the mutator functions.")

  /**
   * Adds the component of the given [type] to the entity and gets
   * the instance.
   */
  @Suppress("unused", "UNUSED_PARAMETER")
  @Deprecated(message = "Use the mutator functions.",
    level = DeprecationLevel.ERROR, replaceWith = ReplaceWith("add(type)"))
  @JvmSynthetic
  fun <T : Component> Entity.add(type: ComponentType<T>): T =
    error("Use the mutator functions.")


  /**
   * Gets the component of the specified [type] if it exists, otherwise a new
   * component will be created.
   */
  @Suppress("unused", "UNUSED_PARAMETER")
  @Deprecated(message = "Use the mutator functions.",
    level = DeprecationLevel.ERROR, replaceWith = ReplaceWith("getOrAdd(type)"))
  @JvmSynthetic
  fun <T : Component> Entity.getOrAdd(type: ComponentType<T>): T =
    error("Use the mutator functions.")

  /**
   * Adds the component of the specified [type] to the entity and gets the
   * instance. An [IllegalArgumentException] will be thrown if the entity
   * already owns a component of the specified [type]. The [operation] will be
   * applied to the constructed component.
   */
  @Suppress("unused", "UNUSED_PARAMETER")
  @Deprecated(message = "Use the mutator functions.",
    level = DeprecationLevel.ERROR,
    replaceWith = ReplaceWith("add(type, operation)")
  )
  @JvmSynthetic
  fun <T : Component> Entity.add(
    type: ComponentType<T>, operation: T.() -> Unit
  ): T = error("Use the mutator functions.")

  /**
   * Gets the component of the specified [type] and applies the given
   * [operation] to it. An [IllegalArgumentException] will be thrown if the
   * entity doesn't own a component with the specified [type].
   */
  @Suppress("unused", "UNUSED_PARAMETER")
  @Deprecated(message = "Use the mutator functions.",
    level = DeprecationLevel.ERROR,
    replaceWith = ReplaceWith("modify(type, operation)")
  )
  @JvmSynthetic
  fun <T : Component> Entity.modify(
    type: ComponentType<T>, operation: T.() -> Unit
  ): T = error("Use the mutator functions.")

  /**
   * Gets the component of the specified [type] if it exists, otherwise a new
   * component will be created. The [operation] will be applied to the
   * retrieved or constructed component.
   */
  @Suppress("unused", "UNUSED_PARAMETER")
  @Deprecated(message = "Use the mutator functions.",
    level = DeprecationLevel.ERROR,
    replaceWith = ReplaceWith("modify(type, operation)")
  )
  @JvmSynthetic
  fun <T : Component> Entity.modifyOrAdd(
    type: ComponentType<T>, operation: T.() -> Unit
  ): T = error("Use the mutator functions.")
}
