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
interface EntityReferenceIterator {

  /**
   * Returns whether there's a next entity reference in this iterator.
   */
  operator fun hasNext(): Boolean

  /**
   * Returns the next entity reference.
   */
  operator fun next(): EntityReference
}

/**
 * Returns this [EntityReferenceIterator] as an [EntityIterator] that skips
 * inactive entities.
 */
fun EntityReferenceIterator.filterToEntity(): EntityIterator =
  ReferenceAsEntityIterator(this)

internal class ReferenceAsEntityIterator(
  private val iterator: EntityReferenceIterator
) : EntityIterator {

  companion object {

    const val Found = 1
    const val Unknown = 0
    const val End = -1
  }

  private var next = Entity(InternalEntityRef.Empty)
  private var state = 0

  private fun findNext() {
    if (state == Found)
      return
    while (iterator.hasNext()) {
      val ref = iterator.next()
      if (ref.isPresent()) {
        next = ref.asEntity()
        state = Found
        return
      }
    }
  }

  override fun next(): Entity {
    findNext()
    if (state == End)
      throw NoSuchElementException()
    state = Unknown
    return next
  }

  override fun hasNext(): Boolean {
    findNext()
    return state == Found
  }
}
