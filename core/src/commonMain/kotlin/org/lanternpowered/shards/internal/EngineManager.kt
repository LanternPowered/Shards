/*
 * Shards
 *
 * Copyright (c) LanternPowered <https://www.lanternpowered.org>
 * Copyright (c) contributors
 *
 * This work is licensed under the terms of the MIT License (MIT). For
 * a copy, see 'LICENSE.txt' or <https://opensource.org/licenses/MIT>.
 */
package org.lanternpowered.shards.internal

import org.lanternpowered.shards.Engine
import org.lanternpowered.shards.component.Component
import org.lanternpowered.shards.component.ComponentType
import org.lanternpowered.shards.entity.EntityMutator
import org.lanternpowered.shards.entity.InternalEntityRef
import org.lanternpowered.shards.internal.util.IntList

internal object EngineManager {

  /**
   * An array with all the constructed engines.
   */
  private val engines = arrayOfNulls<Engine?>(4000)

  private var lock = Any()
  private val reusedIds = IntList()
  private var idCounter = 0

  operator fun get(id: Int): Engine = engines[id]!!

  fun create(): Engine {
    //synchronized(lock) {
      val id =
        if (reusedIds.isEmpty) idCounter++
        else reusedIds.removeAt(0)
      val engine = Engine(id)
      engines[id] = engine
      return engine
    //}
  }

  fun destroy(engine: Engine) {
    //synchronized(lock) {
      reusedIds.add(engine.id)
      engines[engine.id] = null
    //}
  }

  fun modify(ref: InternalEntityRef, fn: EntityMutator.() -> Unit) =
    this[ref.engine].modify(ref.entityId, fn)

  fun isActive(ref: InternalEntityRef): Boolean =
    this[ref.engine].isActive(ref.entityId, ref.version)

  fun containsComponent(
    ref: InternalEntityRef, type: ComponentType<*>
  ): Boolean = this[ref.engine].containsComponent(ref.entityId, type)

  fun <T : Component> getComponentOrNull(
    ref: InternalEntityRef, type: ComponentType<T>
  ): T? = this[ref.engine].getComponentOrNull(ref.entityId, type)

  fun <T : Component> getComponent(
    ref: InternalEntityRef, type: ComponentType<T>
  ): T = this[ref.engine].getComponent(ref.entityId, type)

  fun <T : Component> addComponent(
    ref: InternalEntityRef, type: ComponentType<T>
  ): T = this[ref.engine].addComponent(ref.entityId, type)

  fun <T : Component> getOrAddComponent(
    ref: InternalEntityRef, type: ComponentType<T>
  ): T = this[ref.engine].getOrAddComponent(ref.entityId, type)
}
