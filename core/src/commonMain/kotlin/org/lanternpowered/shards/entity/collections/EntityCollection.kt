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
 * Represents a collection of [Entity]s.
 */
interface EntityCollection : EntityIterable {

  /**
   * Returns the size of the collection.
   */
  val size: Int

  /**
   * Returns whether the collection is empty (contains no entities).
   */
  fun isEmpty(): Boolean

  /**
   * Checks if the specified entity is contained in this collection.
   */
  operator fun contains(entity: Entity): Boolean

  /**
   * Checks if all entities in the specified collection are contained in
   * this collection.
   */
  fun containsAll(entities: Collection<Entity>): Boolean

  /**
   * Checks if all entities in the specified collection are contained in
   * this collection.
   */
  fun containsAll(entities: EntityCollection): Boolean
}
