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
 * Represents a list of [Entity]s.
 */
interface EntityList : EntityCollection {

  /**
   * Returns the entity at the specified index in the list.
   */
  operator fun get(index: Int): Entity

  /**
   * Returns the index of the first occurrence of the specified entity in the
   * list, or -1 if the specified entity is not contained in the list.
   */
  fun indexOf(entity: Entity): Int

  /**
   * Returns the index of the last occurrence of the specified entity in the
   * list, or -1 if the specified entity is not contained in the list.
   */
  fun lastIndexOf(entity: Entity): Int
}
