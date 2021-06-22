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
 * Represents a mutable collection of [Entity]s.
 */
interface MutableEntityCollection : EntityCollection {

  /**
   * Adds the specified entity to the collection.
   *
   * @return `true` if the entity has been added; `false` otherwise
   */
  fun add(entity: Entity): Boolean

  /**
   * Removes a single instance of the specified entity from this collection,
   * if it is present.
   *
   * @return `true` if the entity has been successfully removed;
   *         `false` otherwise
   */
  fun remove(entity: Entity): Boolean

  /**
   * Removes all entities from this collection.
   */
  fun clear()
}
