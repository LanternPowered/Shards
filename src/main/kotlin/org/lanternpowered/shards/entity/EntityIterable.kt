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

import org.lanternpowered.shards.Engine
import org.lanternpowered.shards.internal.EngineManager

/**
 * Represents an iterable of [Entity]s.
 */
interface EntityIterable {

  /**
   * Returns an [EntityIterator] that can be used to iterate over the
   * entities in this iterable.
   */
  operator fun iterator(): EntityIterator
}

/**
 * Performs a bulk operation on the [EntityIterable], use this to reduce
 * engine lookups when handling multiple entities.
 */
internal inline fun EntityIterable.performInBulk(
  operation: (Engine, Entity) -> Unit
) {
  var lastEngineId = -1
  var engine: Engine? = null
  for (entity in this) {
    val engineId = entity.reference.engine
    // Cache the last accessed engine to reduce lookups
    if (engineId != lastEngineId) {
      engine = EngineManager[engineId]
      lastEngineId = engineId
    }
    operation(engine!!, entity)
  }
}

/**
 * Modifies all the entities in the [EntityIterable] using the given
 * [operation].
 */
fun EntityIterable.modifyAll(operation: @EntityDsl EntityMutator.() -> Unit) =
  performInBulk { engine, entity -> engine.modify(entity.id, operation) }

/**
 * Returns `true` if all entities match the given [predicate].
 */
inline fun EntityIterable.all(predicate: (Entity) -> Boolean): Boolean {
  if (this is EntityCollection && isEmpty())
    return true
  for (entity in this) {
    if (!predicate(entity))
      return false
  }
  return true
}

/**
 * Returns `true` if at least one entity matches the given [predicate].
 */
inline fun EntityIterable.any(predicate: (Entity) -> Boolean): Boolean {
  if (this is EntityCollection && isEmpty())
    return false
  for (entity in this) {
    if (predicate(entity))
      return true
  }
  return false
}

/**
 * Returns the number of entities in this iterable.
 */
fun EntityIterable.count(): Int {
  if (this is EntityCollection)
    return size
  var count = 0
  for (entity in this)
    count++
  return count
}

/**
 * Returns the number of entities in this iterable that match the given
 * [predicate].
 */
inline fun EntityIterable.count(predicate: (Entity) -> Boolean): Int {
  if (this is EntityCollection && isEmpty())
    return 0
  var count = 0
  for (entity in this) {
    if (predicate(entity))
      count++
  }
  return count
}

/**
 * Performs the given [operation] for the all entities in this [EntityIterable].
 */
inline fun EntityIterable.forEach(operation: (Entity) -> Unit) {
  for (entity in this)
    operation(entity)
}

/**
 * Performs the given [action] on each entity, providing sequential index with
 * the entity.
 *
 * @param [action] function that takes the index of an entity and the entity
 * itself and performs the action on the entity.
 */
inline fun EntityIterable.forEachIndexed(
  action: (index: Int, Entity) -> Unit
) {
  var index = 0
  for (item in this)
    action(index++, item)
}

/**
 * Applies the given [transform] function to each entity of the original
 * collection and appends the results to the given [destination].
 */
inline fun <R, C : MutableCollection<in R>> EntityIterable.mapTo(
  destination: C, transform: (Entity) -> R
): C {
  for (item in this)
    destination.add(transform(item))
  return destination
}

/**
 * Returns a list containing the results of applying the given [transform]
 * function to each entity in the original collection.
 */
inline fun <R> EntityIterable.map(transform: (Entity) -> R): List<R> =
  mapTo(ArrayList(collectionSizeOrDefault(10)), transform)

/**
 * Returns a list containing the results of applying the given [transform]
 * function to each entity and its index in the original collection.
 *
 * @param [transform] function that takes the index of an entity and the
 * entity itself and returns the result of the transform applied to the
 * entity.
 */
inline fun <R> EntityIterable.mapIndexed(
  transform: (index: Int, Entity) -> R
): List<R> = mapIndexedTo(ArrayList(collectionSizeOrDefault(10)), transform)

/**
 * Applies the given [transform] function to each entity and its index in the
 * original collection and appends the results to the given [destination].
 *
 * @param [transform] function that takes the index of an entity and the
 * entity itself and returns the result of the transform applied to the
 * entity.
 */
inline fun <R, C : MutableCollection<in R>> EntityIterable.mapIndexedTo(
  destination: C, transform: (index: Int, Entity) -> R
): C {
  var index = 0
  for (item in this)
    destination.add(transform(index++, item))
  return destination
}

/**
 * Returns a list containing only elements matching the given [predicate].
 */
inline fun EntityIterable.filter(predicate: (Entity) -> Boolean): EntityList =
  filterTo(EntityArrayList(), predicate)

/**
 * Appends all entities matching the given [predicate] to the given
 * [destination].
 */
inline fun <C : MutableEntityCollection> EntityIterable.filterTo(
  destination: C, predicate: (Entity) -> Boolean
): C {
  for (entity in this) {
    if (predicate(entity))
      destination.add(entity)
  }
  return destination
}

/**
 * Appends all entities matching the given [predicate] to the given
 * [destination].
 */
inline fun <C : MutableCollection<in Entity>> EntityIterable.filterTo(
  destination: C, predicate: (Entity) -> Boolean
): C {
  for (entity in this) {
    if (predicate(entity))
      destination.add(entity)
  }
  return destination
}

@PublishedApi
internal fun EntityIterable.collectionSizeOrDefault(default: Int): Int =
  if (this is EntityCollection) size else default
