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

import kotlinx.atomicfu.locks.reentrantLock
import kotlinx.atomicfu.locks.withLock
import org.lanternpowered.shards.Universe
import org.lanternpowered.shards.component.Component
import org.lanternpowered.shards.component.ComponentType
import org.lanternpowered.shards.entity.EntityMutator
import org.lanternpowered.shards.entity.InternalEntityRef
import org.lanternpowered.shards.internal.util.IntList

@PublishedApi
internal object UniverseManager {

  /**
   * An array with all the constructed universes.
   */
  private val universes = arrayOfNulls<Universe?>(4000)

  private val lock = reentrantLock()
  private val reusedIds = IntList()
  private var idCounter = 0

  operator fun get(id: Int): Universe = universes[id]!!

  fun create(): Universe {
    lock.withLock {
      val id =
        if (reusedIds.isEmpty) idCounter++
        else reusedIds.removeAt(0)
      val universe = Universe(id)
      universes[id] = universe
      return universe
    }
  }

  fun destroy(universe: Universe) {
    lock.withLock {
      reusedIds.add(universe.id)
      universes[universe.id] = null
    }
  }

  inline fun modify(ref: InternalEntityRef, fn: EntityMutator.() -> Unit) =
    this[ref.universe].modify(ref.entityId, fn)

  fun isActive(ref: InternalEntityRef): Boolean =
    this[ref.universe].isActive(ref.entityId, ref.version)

  fun containsComponent(
    ref: InternalEntityRef, type: ComponentType<*>
  ): Boolean = this[ref.universe].containsComponent(ref.entityId, type)

  fun <T : Component> getComponentOrNull(
    ref: InternalEntityRef, type: ComponentType<T>
  ): T? = this[ref.universe].getComponentOrNull(ref.entityId, type)

  fun <T : Component> getComponent(
    ref: InternalEntityRef, type: ComponentType<T>
  ): T = this[ref.universe].getComponent(ref.entityId, type)

  fun <T : Component> setComponent(
    ref: InternalEntityRef, type: ComponentType<T>, component: T
  ) = this[ref.universe].setComponent(ref.entityId, type, component)

  fun setComponent(
    ref: InternalEntityRef, component: Component
  ) = this[ref.universe].setComponent(ref.entityId, component)
}
