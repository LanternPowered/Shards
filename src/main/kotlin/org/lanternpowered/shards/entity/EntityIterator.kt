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
 * An iterator for [Entity]s.
 */
interface EntityIterator {

  /**
   * Returns the next element in the iteration.
   */
  operator fun next(): Entity

  /**
   * Returns `true` if the iteration has more elements.
   */
  operator fun hasNext(): Boolean
}

/**
 * Performs the given [operation] for the remaining entities in this
 * [EntityIterator].
 */
inline fun EntityIterator.forEach(operation: (Entity) -> Unit) {
  while (hasNext())
    operation(next())
}

/**
 * Returns this [EntityIterator] as a normal [Iterator].
 */
fun EntityIterator.generic(): Iterator<Entity> =
  object : Iterator<Entity> {
    override fun hasNext(): Boolean = this@generic.hasNext()
    override fun next(): Entity = this@generic.next()
  }

/**
 * Returns this [EntityIterator] as a [Sequence].
 */
fun EntityIterator.asSequence(): EntitySequence =
  object : EntitySequence {
    var requested = false
    override fun iterator(): EntityIterator {
      if (requested)
        throw IllegalStateException(
          "This sequence is constrained to only be iterated once.")
      requested = true
      return this@asSequence
    }
  }
