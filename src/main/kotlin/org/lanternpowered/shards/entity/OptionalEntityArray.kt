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

import org.lanternpowered.shards.internal.EntityReference

/**
 * Represents an array of entities.
 */
@Suppress("NON_PUBLIC_PRIMARY_CONSTRUCTOR_OF_INLINE_CLASS")
inline class OptionalEntityArray private constructor(
  private val array: LongArray
) {

  /**
   * Constructs a new [OptionalEntityArray] with the given size.
   */
  constructor(size: Int) :
    this(LongArray(size) { EntityReference.Empty.value })

  /**
   * The number of elements in the array.
   */
  val size: Int
    get() = array.size

  /**
   * Gets the [OptionalEntity] at the given [index].
   */
  operator fun get(index: Int): OptionalEntity =
    OptionalEntity(EntityReference(array[index]))

  /**
   * Gets the nullable [Entity] at the given [index].
   */
  fun getNullable(index: Int): Entity? = this[index].orNull()

  /**
   * Sets the [OptionalEntity] at the given [index].
   */
  operator fun set(index: Int, entity: OptionalEntity) {
    array[index] = entity.reference.value
  }

  /**
   * Sets the [OptionalEntity] at the given [index].
   */
  operator fun set(index: Int, entity: Entity) {
    array[index] = entity.reference.value
  }

  /**
   * Sets the [OptionalEntity] at the given [index].
   */
  operator fun set(index: Int, entity: Entity?) {
    array[index] = OptionalEntity.ofNullable(entity).reference.value
  }

  /**
   * An iterator to iterate over entities.
   */
  operator fun iterator(): OptionalEntityIterator {
    val itr = array.iterator()
    return object : OptionalEntityIterator {
      override fun nextEntity(): OptionalEntity =
        OptionalEntity(EntityReference(itr.nextLong()))
      override fun hasNext(): Boolean = itr.hasNext()
    }
  }

  /**
   * Filters out all the "empty" entities and gets an [EntityArray].
   */
  fun filterToEntityArray(): EntityArray {
    var size = 0
    for (i in array.indices) {
      if (!EntityReference(array[i]).isEmpty)
        size++
    }
    val array = LongArray(size)
    var index = 0
    for (i in array.indices) {
      val value = array[i]
      if (!EntityReference(value).isEmpty)
        array[index++] = value
    }
    return EntityArray(array)
  }
}