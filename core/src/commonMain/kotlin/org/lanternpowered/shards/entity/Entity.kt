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
import org.lanternpowered.shards.component.componentType
import org.lanternpowered.shards.internal.EngineManager
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.jvm.JvmInline

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
@JvmInline
value class Entity internal constructor(
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
}

@PublishedApi
internal fun <T : Component> Entity.set(
  type: ComponentType<T>, component: T
) = EngineManager.setComponent(ref, type, component)

/**
 * Whether the [Entity] is still active and exists in its [Engine].
 */
val Entity.isActive: Boolean
  get() = EngineManager.isActive(ref)

/**
 * Returns if the entity contains a component with the specified [type].
 */
fun Entity.contains(type: ComponentType<*>): Boolean =
  EngineManager.containsComponent(ref, type)

/**
 * Returns the component with the specified [type]. Throws an
 * [IllegalArgumentException] if the component wasn't found.
 */
fun <T : Component> Entity.get(type: ComponentType<T>): T =
  EngineManager.getComponent(ref, type)

/**
 * Gets the component instance of the given [type]. Returns `null` if the
 * component wasn't found.
 */
fun <T : Component> Entity.getOrNull(type: ComponentType<T>): T? =
  EngineManager.getComponentOrNull(ref, type)

/**
 * Returns the component of the specified [type] and applies the given
 * transform [operation] to it. Throws an [IllegalArgumentException] if the
 * component wasn't found.
 */
inline fun <T : Component> Entity.transform(
  type: ComponentType<T>, operation: (T) -> T
): T {
  contract { callsInPlace(operation, InvocationKind.EXACTLY_ONCE) }
  return operation(get(type)).also { set(type, it) }
}

/**
 * Sets the [component] on the entity.
 */
fun Entity.set(component: Component) =
  EngineManager.setComponent(ref, component)

/**
 * Attempts to set the component if the entity doesn't contain a component
 * with the same type. Returns the previous component, otherwise the
 * component which is set and was provided by the [supplier].
 */
inline fun <T : Component> Entity.getOrSet(
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
inline fun <reified T : Component> Entity.getOrSet(supplier: () -> T): T {
  contract { callsInPlace(supplier, InvocationKind.AT_MOST_ONCE) }
  return getOrSet(componentType(), supplier)
}

/**
 * Modifies the entity with the given [operation].
 */
inline fun Entity.modify(
  operation: @EntityDsl EntityMutator.() -> Unit
): Entity {
  contract { callsInPlace(operation, InvocationKind.EXACTLY_ONCE) }
  EngineManager.modify(ref, operation)
  return this
}
