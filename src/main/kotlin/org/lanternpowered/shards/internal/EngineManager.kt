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

import it.unimi.dsi.fastutil.ints.IntArrayList
import org.lanternpowered.shards.Engine
import org.lanternpowered.shards.component.Component
import org.lanternpowered.shards.component.ComponentType
import org.lanternpowered.shards.entity.EntityMutator
import org.lanternpowered.shards.entity.EntitySequence
import org.lanternpowered.shards.entity.forEach
import org.lanternpowered.shards.entity.modify
import org.lanternpowered.shards.test.HealthComponent

internal object EngineManager {

  /**
   * An array with all the constructed engines.
   */
  private val engines = arrayOfNulls<Engine?>(4000)

  private var lock = Any()
  private val reusedIds = IntArrayList()
  private var idCounter = 0

  operator fun get(id: Int): Engine = engines[id]!!

  fun create(): Engine {
    synchronized(lock) {
      val id =
        if (reusedIds.isEmpty) idCounter++
        else reusedIds.removeInt(0)
      val engine = Engine(id)
      engines[id] = engine
      return engine
    }
  }

  fun destroy(engine: Engine) {
    synchronized(lock) {
      reusedIds.add(engine.id)
      engines[engine.id] = null
    }
  }

  fun modify(reference: InternalEntityRef, fn: EntityMutator.() -> Unit) =
    this[reference.engine].modify(reference.entityId, fn)

  fun isActive(reference: InternalEntityRef): Boolean =
    this[reference.engine].isActive(reference.entityId, reference.version)

  fun containsComponent(
    reference: InternalEntityRef, type: ComponentType<*>
  ): Boolean = this[reference.engine].containsComponent(reference.entityId, type)

  fun <T : Component> getComponentOrNull(
    reference: InternalEntityRef, type: ComponentType<T>
  ): T? = this[reference.engine].getComponentOrNull(reference.entityId, type)

  fun <T : Component> getComponent(
    reference: InternalEntityRef, type: ComponentType<T>
  ): T = this[reference.engine].getComponent(reference.entityId, type)

  fun <T : Component> addComponent(
    reference: InternalEntityRef, type: ComponentType<T>
  ): T = this[reference.engine].addComponent(reference.entityId, type)

  fun <T : Component> getOrAddComponent(
    reference: InternalEntityRef, type: ComponentType<T>
  ): T = this[reference.engine].getOrAddComponent(reference.entityId, type)
}

fun test(list: EntitySequence) {
  list.forEach { entity ->
    entity.modify(HealthComponent) {
      health -= 1.0
    }
  }
}
