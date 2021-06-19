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
 * Represents an empty [EntityList].
 */
object EmptyEntityList : EntityList {
  override fun get(index: Int): Entity = throw NoSuchElementException()
  override fun indexOf(entity: Entity): Int = -1
  override fun lastIndexOf(entity: Entity): Int = -1
  override val size: Int get() = 0
  override fun isEmpty(): Boolean = true
  override fun contains(entity: Entity): Boolean = false
  override fun containsAll(entities: Collection<Entity>): Boolean =
    entities.isEmpty()
  override fun containsAll(entities: EntityCollection): Boolean =
    entities.isEmpty()
  override fun iterator(): EntityIterator = EmptyEntityIterator
}

/**
 * Represents an empty [EntityCollection].
 */
object EmptyEntityCollection : EntityCollection {
  override val size: Int get() = 0
  override fun isEmpty(): Boolean = true
  override fun contains(entity: Entity): Boolean = false
  override fun containsAll(entities: Collection<Entity>): Boolean =
    entities.isEmpty()
  override fun containsAll(entities: EntityCollection): Boolean =
    entities.isEmpty()
  override fun iterator(): EntityIterator = EmptyEntityIterator
}

/**
 * Represents an empty [EntityIterator].
 */
object EmptyEntityIterator : EntityIterator {
  override fun next(): Entity = throw NoSuchElementException()
  override fun hasNext(): Boolean = false
}
