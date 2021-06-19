/*
 * Shards
 *
 * Copyright (c) LanternPowered <https://www.lanternpowered.org>
 * Copyright (c) contributors
 *
 * This work is licensed under the terms of the MIT License (MIT). For
 * a copy, see 'LICENSE.txt' or <https://opensource.org/licenses/MIT>.
 */
@file:Suppress("NOTHING_TO_INLINE", "OVERRIDE_BY_INLINE", "FunctionName")

package org.lanternpowered.shards

import org.lanternpowered.shards.component.Component
import org.lanternpowered.shards.component.ComponentType
import org.lanternpowered.shards.component.componentType
import org.lanternpowered.shards.entity.Entity
import org.lanternpowered.shards.entity.EntityContext
import org.lanternpowered.shards.entity.EntityId
import org.lanternpowered.shards.entity.EntityMutator
import org.lanternpowered.shards.internal.UniverseManager
import org.lanternpowered.shards.entity.sequences.EntitySeq0
import kotlin.js.JsName
import kotlin.jvm.JvmSynthetic

/**
 * Constructs a new [Universe].
 */
@JsName("createUniverse")
fun Universe(): Universe = UniverseManager.create()

/**
 * Represents a universe that will manage all the components and entities.
 */
abstract class Universe internal constructor(
  @JvmSynthetic
  internal val id: Int
) : EntityContext() {

  inline fun <R> use(block: Universe.() -> R): R = block()

  private inner class EntityMutatorImpl : EntityMutator() {

    /**
     * The target entity id.
     */
    var entityId = EntityId(0)

    override fun <T : Component> set(
      type: ComponentType<T>, component: T
    ) = this@Universe.setComponent(entityId, type, component)

    override fun set(component: Component) =
      this@Universe.setComponent(entityId, component)

    override fun contains(type: ComponentType<*>): Boolean =
      this@Universe.containsComponent(entityId, type)

    override fun <T : Component> get(type: ComponentType<T>): T =
      this@Universe.getComponent(entityId, type)

    override fun <T : Component> getOrNull(type: ComponentType<T>): T? =
      this@Universe.getComponentOrNull(entityId, type)
  }

  /**
   * Creates a new [Entity].
   */
  fun createEntity(): Entity {
    TODO()
  }

  // TODO: Revisit this when multithreading
  private var mutator = EntityMutatorImpl()

  inline fun modify(entityId: EntityId, fn: EntityMutator.() -> Unit) {
    mutatorWithId(entityId).apply(fn)
  }

  @PublishedApi
  internal fun mutatorWithId(entityId: EntityId): EntityMutator {
    mutator.entityId = entityId
    return mutator
  }

  abstract fun entityQuery(): EntitySeq0

  abstract fun isActive(entityId: EntityId, version: Int): Boolean

  abstract fun isActive(entityId: EntityId): Boolean

  abstract fun getEntity(entityId: EntityId): Entity

  abstract fun containsComponent(
    entityId: EntityId, type: ComponentType<*>
  ): Boolean

  abstract fun <T : Component> getComponentOrNull(
    entityId: EntityId, type: ComponentType<T>
  ): T?

  abstract fun <T : Component> getComponent(
    entityId: EntityId, type: ComponentType<T>
  ): T

  abstract fun <T : Component> setComponent(
    entityId: EntityId, type: ComponentType<T>, component: T
  )

  fun setComponent(
    entityId: EntityId, component: Component
  ) = setComponent(entityId, component.componentType, component)

  override val Entity.isActive: Boolean
    get() = isActive(ref.entityId, ref.version)

  override fun Entity.contains(
    type: ComponentType<*>
  ): Boolean = containsComponent(id, type)

  override fun <T : Component> Entity.get(
    type: ComponentType<T>
  ): T = getComponent(id, type)

  override fun <T : Component> Entity.getOrNull(
    type: ComponentType<T>
  ): T? = getComponentOrNull(id, type)

  override fun <T : Component> Entity.set(
    type: ComponentType<T>, component: T
  ) = setComponent(id, type, component)

  override fun Entity.set(component: Component) =
    setComponent(id, component)

  /**
   * Destroys the universe. This must be called when you no longer needs this
   * universe.
   */
  fun destroy() {
    UniverseManager.destroy(this)
  }

}
