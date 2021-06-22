/*
 * Shards
 *
 * Copyright (c) LanternPowered <https://www.lanternpowered.org>
 * Copyright (c) contributors
 *
 * This work is licensed under the terms of the MIT License (MIT). For
 * a copy, see 'LICENSE.txt' or <https://opensource.org/licenses/MIT>.
 */
package org.lanternpowered.shards.entity.collections

import org.lanternpowered.shards.entity.Entity

/**
 * Represents a mutable list of [Entity]s.
 */
interface MutableEntityList : MutableEntityCollection, EntityList {

  /**
   * Replaces the entity at the specified position in this list with the
   * specified entity.
   *
   * @return The entity previously at the specified position
   */
  operator fun set(index: Int, entity: Entity): Entity

  /**
   * Inserts an entity into the list at the specified [index].
   */
  fun add(index: Int, entity: Entity)

  /**
   * Removes an entity at the specified [index] from the list.
   *
   * @return The entity that has been removed
   */
  fun removeAt(index: Int): Entity
}
