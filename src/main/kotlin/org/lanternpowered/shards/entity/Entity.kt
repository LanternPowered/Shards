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
import org.lanternpowered.shards.internal.EngineManager
import org.lanternpowered.shards.internal.InternalEntityRef
import org.lanternpowered.shards.Engine

/**
 * Internal constructor to reduce duplication when creating [Entity]s from
 * reference values.
 */
internal fun Entity(referenceValue: Long): Entity =
  Entity(InternalEntityRef(referenceValue))

/**
 * Represents an entity of an [Engine].
 */
@EntityDsl
@Suppress("NON_PUBLIC_PRIMARY_CONSTRUCTOR_OF_INLINE_CLASS")
inline class Entity internal constructor(
  @PublishedApi internal val ref: InternalEntityRef
) {

  /**
   * The id of the entity.
   */
  val id: EntityId
    get() = ref.entityId

  /**
   * The engine this entity belongs to.
   */
  val engine: Engine
    get() = EngineManager[ref.engine]

  override fun toString(): String =
    "Entity(id=$id, engineId=${ref.engine})"

  /**
   * Applies the given function to this entity.
   */
  @Deprecated(message = "Prefer to use Entity.modify")
  fun apply(fn: Entity.() -> Unit): Entity {
    fn(this)
    return this
  }
}

/**
 * Whether the [Entity] is still active and exists in its [Engine].
 */
val Entity.isActive: Boolean
  get() = EngineManager.isActive(ref)

/**
 * Modifies the entity with the given [operation].
 */
fun Entity.modify(operation: @EntityDsl EntityMutator.() -> Unit): Entity {
  EngineManager.modify(ref, operation)
  return this
}

/**
 * Returns if the entity contains a component of the specified [type].
 */
operator fun Entity.contains(type: ComponentType<*>): Boolean =
  EngineManager.containsComponent(ref, type)

/**
 * Gets the component instance of the given [type] and fails if the component
 * wasn't found.
 */
fun <T : Component> Entity.get(type: ComponentType<T>): T =
  EngineManager.getComponent(ref, type)

/**
 * Gets the component instance of the given [type] and returns `null` if the
 * component wasn't found.
 */
fun <T : Component> Entity.getOrNull(type: ComponentType<T>): T? =
  EngineManager.getComponentOrNull(ref, type)

/**
 * Adds the component of the specified [type] to the entity and gets the
 * instance. An [IllegalArgumentException] will be thrown if the entity
 * already owns a component of the specified [type].
 */
fun <T : Component> Entity.add(type: ComponentType<T>): T =
  EngineManager.addComponent(ref, type)

/**
 * Gets the component of the specified [type] if it exists, otherwise a new
 * component will be created.
 */
fun <T : Component> Entity.getOrAdd(type: ComponentType<T>): T =
  EngineManager.getOrAddComponent(ref, type)

/**
 * Adds the component of the specified [type] to the entity and gets the
 * instance. An [IllegalArgumentException] will be thrown if the entity
 * already owns a component of the specified [type]. The [operation] will be
 * applied to the constructed component.
 */
fun <T : Component> Entity.add(
  type: ComponentType<T>, operation: T.() -> Unit
): T = add(type).apply { modify(operation) }

/**
 * Gets the component of the specified [type] and applies the given
 * [operation] to it. An [IllegalArgumentException] will be thrown if the
 * entity doesn't own a component with the specified [type].
 */
inline fun <T : Component> Entity.modify(
  type: ComponentType<T>, operation: T.() -> Unit
): T = get(type).apply { modify(operation) }

/**
 * Gets the component of the specified [type] if it exists, otherwise a new
 * component will be created. The [operation] will be applied to the
 * retrieved or constructed component.
 */
fun <T : Component> Entity.modifyOrAdd(
  type: ComponentType<T>, operation: T.() -> Unit
): T = getOrAdd(type).apply { modify(operation) }
