/*
 * Shards
 *
 * Copyright (c) LanternPowered <https://www.lanternpowered.org>
 * Copyright (c) contributors
 *
 * This work is licensed under the terms of the MIT License (MIT). For
 * a copy, see 'LICENSE.txt' or <https://opensource.org/licenses/MIT>.
 */
package org.lanternpowered.shards.entity

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
