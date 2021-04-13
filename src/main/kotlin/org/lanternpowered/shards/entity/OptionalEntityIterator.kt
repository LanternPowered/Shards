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
interface OptionalEntityIterator : Iterator<EntityReference> {

  override fun next(): EntityReference = this.next()

  /**
   * Gets the next optional entity without boxing.
   */
  fun nextEntity(): EntityReference

  /**
   * Gets the next nullable entity.
   */
  fun nextNullableEntity(): Entity? = this.nextEntity().orNull()
}
