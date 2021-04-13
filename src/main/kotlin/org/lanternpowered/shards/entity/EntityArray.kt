/*
 * Shards
 *
 * Copyright (c) LanternPowered <https://www.lanternpowered.org>
 * Copyright (c) contributors
 *
 * This work is licensed under the terms of the MIT License (MIT). For
 * a copy, see 'LICENSE.txt' or <https://opensource.org/licenses/MIT>.
 */
@file:Suppress("FunctionName")

package org.lanternpowered.shards.entity

import org.lanternpowered.shards.internal.EntityReference

/**
 * Converts the iterable of [Entity]s to an [EntityArray].
 */
fun EntityIterable.toEntityArray(): EntityArray {
  val value = LongArray(count())
  val itr = iterator()
  var index = 0
  while (itr.hasNext())
    value[index++] = itr.next().reference.value
  return EntityArray(value)
}

/**
 * Converts the iterable of [Entity]s to an [EntityArray].
 */
fun Iterable<Entity>.toEntityArray(): EntityArray {
  val value = LongArray(count())
  val itr = iterator()
  var index = 0
  while (itr.hasNext())
    value[index++] = itr.next().reference.value
  return EntityArray(value)
}

/**
 * Converts the list of [Entity]s to an [EntityArray].
 */
fun List<Entity>.toEntityArray(): EntityArray {
  val value = LongArray(size)
  for (index in indices)
    value[index] = this[index].reference.value
  return EntityArray(value)
}

/**
 * Constructs a new [EntityArray] with the given size and init block.
 */
inline fun EntityArray(size: Int, init: (index: Int) -> Entity): EntityArray {
  val value = LongArray(size)
  for (index in 0 until size)
    value[index] = init(index).reference.value
  return EntityArray(value)
}

/**
 * Returns the last valid index for the array.
 */
val EntityArray.lastIndex: Int
  get() = size - 1

/**
 * Returns the range of valid indices for the array.
 */
val EntityArray.indices: IntRange
  get() = IntRange(0, lastIndex)

/**
 * Represents an array of entities.
 */
@Suppress("NON_PUBLIC_PRIMARY_CONSTRUCTOR_OF_INLINE_CLASS")
inline class EntityArray @PublishedApi internal constructor(
  private val value: LongArray
) : EntityList {

  /**
   * The number of elements in the array.
   */
  override val size: Int
    get() = value.size

  /**
   * Gets the [OptionalEntity] at the given [index].
   */
  override fun get(index: Int): Entity =
    Entity(EntityReference(value[index]))

  /**
   * Sets the [OptionalEntity] at the given [index].
   */
  operator fun set(index: Int, entity: Entity) {
    value[index] = entity.reference.value
  }

  override fun indexOf(entity: Entity): Int =
    value.indexOf(entity.reference.value)

  override fun lastIndexOf(entity: Entity): Int =
    value.lastIndexOf(entity.reference.value)

  override fun isEmpty(): Boolean = size == 0

  override fun contains(entity: Entity): Boolean =
    value.any { it == entity.reference.value }

  override fun containsAll(entities: Collection<Entity>): Boolean =
    entities.all(::contains)

  override fun containsAll(entities: EntityCollection): Boolean =
    entities.all(::contains)

  override fun iterator(): EntityIterator {
    val itr = this.value.iterator()
    return object : EntityIterator {
      override fun next(): Entity = Entity(itr.nextLong())
      override fun hasNext(): Boolean = itr.hasNext()
    }
  }
}

/**
 * Performs the given [operation] for the all entities in this [EntityArray].
 */
inline fun EntityArray.forEach(operation: (Entity) -> Unit) {
  for (index in indices)
    operation(this[index])
}
