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

import org.lanternpowered.shards.component.ComponentType
import org.lanternpowered.shards.internal.InternalEntityRef

/**
 * Given an [iterator] function constructs a [Sequence] that returns values
 * through the [Iterator] provided by that function.
 *
 * The values are evaluated lazily, and the sequence is potentially infinite.
 */
inline fun EntitySequence(
  crossinline iterator: () -> EntityIterator
): EntitySequence = object : EntitySequence {
  override fun iterator(): EntityIterator = iterator()
}

/**
 * Represents a sequence of [Entity]s.
 */
interface EntitySequence {

  /**
   * Returns an [EntityIterator] that returns the entities from the sequence.
   *
   * Throws an exception if the sequence is constrained to be iterated once and
   * `iterator` is invoked the second time.
   */
  operator fun iterator(): EntityIterator
}

/**
 * Represents an empty [EntitySequence].
 */
object EmptyEntitySequence : EntitySequence {
  override fun iterator(): EntityIterator = EmptyEntityIterator
}

/**
 * Performs the given [operation] for the all entities in this [EntitySequence].
 */
inline fun EntitySequence.forEach(crossinline operation: (Entity) -> Unit) {
  if (this is AbstractEntitySequence) {
    forEach(EntityFunction0(operation))
  } else {
    for (entity in this)
      operation(entity)
  }
}

/**
 * Returns an [EntitySequence] that only contains [Entity]s which contain a
 * component with the specified [ComponentType].
 */
fun EntitySequence.with(type: ComponentType<*>): EntitySequence {
  return if (this is AbstractEntitySequence) {
    with(type)
  } else {
    filter { it.contains(type) }
  }
}

/**
 * Returns an [EntitySequence] that only contains [Entity]s which do
 * not contain a component with the specified [ComponentType].
 */
fun EntitySequence.without(type: ComponentType<*>): EntitySequence {
  return if (this is AbstractEntitySequence) {
    without(type)
  } else {
    filter { !it.contains(type) }
  }
}

/**
 * Returns `true` if sequence has at least one entity.
 */
fun EntitySequence.any(): Boolean =
  iterator().hasNext()

/**
 * Returns `true` if at least one entity matches the given [predicate].
 */
inline fun EntitySequence.any(
  predicate: (Entity) -> Boolean
): Boolean {
  for (element in this) {
    if (predicate(element))
      return true
  }
  return false
}

/**
 * Applies the given [transform] function to each entity of the original
 * collection and appends the results to the given [destination].
 */
inline fun <R, C : MutableCollection<in R>> EntitySequence.mapTo(
  destination: C, transform: (Entity) -> R
): C {
  for (item in this)
    destination.add(transform(item))
  return destination
}

/**
 * Returns a sequence containing the results of applying the given transform
 * function to each entity in the original sequence.
 */
inline fun <R> EntitySequence.map(
  crossinline transform: (Entity) -> R
): Sequence<R> = TransformingEntitySequence(this, EntityFunction0(transform))

/**
 * Returns a sequence containing only entities matching the given [predicate].
 */
inline fun EntitySequence.filter(
  crossinline predicate: (Entity) -> Boolean
): EntitySequence = FilteringEntitySequence(this, EntityPredicate(predicate))

@PublishedApi
internal class TransformingEntitySequence<R>(
  val sequence: EntitySequence,
  val transform: EntityFunction0<R>
) : Sequence<R> {
  override fun iterator(): Iterator<R> = object : Iterator<R> {
    val itr = sequence.iterator()
    override fun hasNext(): Boolean = itr.hasNext()
    override fun next(): R = transform(itr.next())
  }
}

@PublishedApi
internal class FilteringEntitySequence(
  val sequence: EntitySequence,
  val predicate: EntityPredicate
) : EntitySequence {
  override fun iterator(): EntityIterator = object : EntityIterator {
    val itr = sequence.iterator()

    // -1 for unknown, 0 for done, 1 for continue
    var state = 0
    var next = Entity(InternalEntityRef.Empty)

    fun findNext() {
      while (hasNext()) {
        val entity = itr.next()
        if (predicate(entity)) {
          next = entity
          state = 1
          return
        }
      }
      state = 0
    }

    override fun hasNext(): Boolean {
      if (state == -1)
        findNext()
      return state == 1
    }

    override fun next(): Entity {
      if (state == -1)
        findNext()
      if (state == 0)
        throw NoSuchElementException()
      state = 0
      return next
    }
  }
}

/**
 * Represents an internal [EntitySequence] that can be extended to optimize
 * certain operations.
 */
@PublishedApi
internal abstract class AbstractEntitySequence : EntitySequence {

  open fun with(type: ComponentType<*>): EntitySequence =
    filter { entity -> entity.contains(type) }

  open fun without(type: ComponentType<*>): EntitySequence =
    filter { entity -> !entity.contains(type) }

  open fun forEach(operation: EntityFunction0<Unit>) {
    for (entity in this)
      operation(entity)
  }
}
