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

import org.lanternpowered.shards.Engine
import org.lanternpowered.shards.aspect.Aspect
import org.lanternpowered.shards.component.Component
import org.lanternpowered.shards.component.ComponentType
import org.lanternpowered.shards.entity.Entity
import org.lanternpowered.shards.entity.EntityContext
import org.lanternpowered.shards.entity.EntityDsl
import org.lanternpowered.shards.entity.EntityId
import org.lanternpowered.shards.entity.EntityIterable
import org.lanternpowered.shards.entity.EntityMutator

/**
 * Represents a system that can process [Component] related to a holder.
 *
 * @constructor Constructs a new system with a certain [aspect] that will be
 * used to determine which entities and its components will be processed.
 */
@EntityDsl
abstract class System(val aspect: Aspect = Aspect.Empty) : EntityContext {

  constructor(type: ComponentType<*>) : this(Aspect.require(type))

  /**
   * The engine this system is attached to.
   */
  private var _engine: Engine? = null

  @get:JvmSynthetic
  internal val engine: Engine
    get() = _engine!!

  /**
   * Returns if the entity contains a component of the specified [type].
   */
  @JvmSynthetic
  operator fun Entity.contains(type: ComponentType<*>): Boolean =
    this@System.engine.containsComponent(id, type)

  /**
   * Gets the component instance of the given [type] and fails
   * if the component wasn't found.
   */
  @JvmSynthetic
  override fun <T : Component> Entity.get(type: ComponentType<T>): T =
    this@System.engine.getComponent(id, type)

  /**
   * Gets the component instance of the given [type] and returns `null`
   * if the component wasn't found.
   */
  @JvmSynthetic
  override fun <T : Component> Entity.getOrNull(type: ComponentType<T>): T? =
    this@System.engine.getComponentOrNull(id, type)

  /**
   * Adds the component of the given [type] to the entity and gets
   * the instance.
   */
  @JvmSynthetic
  override fun <T : Component> Entity.add(type: ComponentType<T>): T =
    this@System.engine.addComponent(id, type)

  /**
   * Modifies the entity with the given [operation].
   */
  fun Entity.modify(operation: @EntityDsl EntityMutator.() -> Unit): Entity {
    this@System.engine.modify(id, operation)
    return this
  }

  /**
   * Modifies all the entities in the [EntityIterable] using the given
   * [operation].
   */
  fun EntityIterable.modifyAll(operation: @EntityDsl EntityMutator.() -> Unit) {
    for (entity in this)
      entity.modify(operation)
  }

  /**
   * Returns if the entity contains a component of the specified [type].
   */
  fun contains(
    entityId: EntityId, type: ComponentType<*>
  ): Boolean = engine.containsComponent(entityId, type)

  /**
   * Gets the component instance of the given [type] and fails
   * if the component wasn't found.
   */
  fun <T : Component> get(
    entityId: EntityId, type: ComponentType<T>
  ): T = engine.getComponent(entityId, type)

  /**
   * Gets the component instance of the given [type] and returns `null`
   * if the component wasn't found.
   */
  fun <T : Component> getOrNull(
    entityId: EntityId, type: ComponentType<T>
  ): T? = engine.getComponentOrNull(entityId, type)

  /**
   * Adds the component of the given [type] to the entity and gets
   * the instance.
   */
  fun <T : Component> add(
    entityId: EntityId, type: ComponentType<T>
  ): T = engine.addComponent(entityId, type)
}
