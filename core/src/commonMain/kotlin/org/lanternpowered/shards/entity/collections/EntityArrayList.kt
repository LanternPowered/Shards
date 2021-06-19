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

/**
 * Represents an array list of [Entity]s.
 */
expect class EntityArrayList : MutableEntityList {

  /**
   * Constructs a new [EntityArrayList].
   */
  constructor()

  /**
   * Constructs a new [EntityArrayList] with the specified initial [capacity].
   */
  constructor(capacity: Int)

  /**
   * Constructs a new [EntityArrayList] with the specified initial entities
   * from the specified [collection].
   */
  constructor(collection: EntityCollection)
}
