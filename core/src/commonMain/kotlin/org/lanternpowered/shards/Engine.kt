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
import org.lanternpowered.shards.component.modify
import org.lanternpowered.shards.entity.Entity
import org.lanternpowered.shards.entity.EntityContext
import org.lanternpowered.shards.entity.EntityId
import org.lanternpowered.shards.entity.EntityMutator
import org.lanternpowered.shards.internal.EngineManager
import kotlin.js.JsName
import kotlin.jvm.JvmSynthetic

/**
 * Constructs a new [Engine].
 */
@JsName("createEngine")
fun Engine(): Engine = EngineManager.create()

/**
 * Represents the engine that will manage all the components and entities.
 */
class Engine internal constructor(
  @JvmSynthetic
  internal val id: Int
) : EntityContext {

  inline fun <R> use(block: Engine.() -> R): R = block()

  private inner class EntityMutatorImpl : EntityMutator() {

    /**
     * The target entity id.
     */
    var entityId = EntityId(0)

    override fun contains(type: ComponentType<*>): Boolean =
      this@Engine.containsComponent(entityId, type)

    override fun <T : Component> get(type: ComponentType<T>): T =
      this@Engine.getComponent(entityId, type)

    override fun <T : Component> getOrNull(type: ComponentType<T>): T? =
      this@Engine.getComponentOrNull(entityId, type)

    override fun <T : Component> add(type: ComponentType<T>): T =
      this@Engine.addComponent(entityId, type)

    override fun <T : Component> getOrAdd(type: ComponentType<T>): T =
      this@Engine.getOrAddComponent(entityId, type)
  }

  /**
   * Creates a new [Entity].
   */
  fun createEntity(): Entity {
    TODO()
  }

  // TODO: Revisit this when multithreading
  private var mutator = EntityMutatorImpl()

  fun modify(entityId: EntityId, fn: EntityMutator.() -> Unit) {
    this.mutator.entityId = entityId
    this.mutator.fn()
  }

  fun isActive(entityId: EntityId, version: Int): Boolean {
    TODO()
  }

  fun isActive(entityId: EntityId): Boolean {
    TODO()
  }

  fun getEntity(entityId: EntityId): Entity {
    TODO()
  }

  fun containsComponent(
    entityId: EntityId, type: ComponentType<*>
  ): Boolean {
    TODO()
  }

  fun <T : Component> getComponentOrNull(
    entityId: EntityId, type: ComponentType<T>
  ): T? {
    TODO()
  }

  fun <T : Component> getComponent(
    entityId: EntityId, type: ComponentType<T>
  ): T {
    TODO()
  }

  fun <T : Component> addComponent(
    entityId: EntityId, type: ComponentType<T>
  ): T {
    TODO()
  }

  fun <T : Component> getOrAddComponent(
    entityId: EntityId, type: ComponentType<T>
  ): T {
    TODO()
  }

  override inline val Entity.isActive: Boolean
    get() = isActive(ref.entityId, ref.version)

  override fun Entity.modify(
    operation: EntityMutator.() -> Unit
  ): Entity {
    modify(id, operation)
    return this
  }

  override inline fun Entity.contains(
    type: ComponentType<*>
  ): Boolean = containsComponent(id, type)

  override inline fun <T : Component> Entity.get(
    type: ComponentType<T>
  ): T = getComponent(id, type)

  override inline fun <T : Component> Entity.getOrNull(
    type: ComponentType<T>
  ): T? = getComponentOrNull(id, type)

  override inline fun <T : Component> Entity.add(
    type: ComponentType<T>
  ): T = addComponent(id, type)

  override inline fun <T : Component> Entity.add(
    type: ComponentType<T>, operation: T.() -> Unit
  ): T = addComponent(id, type).apply { modify(operation) }

  override inline fun <T : Component> Entity.getOrAdd(
    type: ComponentType<T>
  ): T = getOrAddComponent(id, type)

  /**
   * Destroys the engine. This must be called when
   * you no longer needs this engine.
   */
  fun destroy() {
    EngineManager.destroy(this)
  }
}
