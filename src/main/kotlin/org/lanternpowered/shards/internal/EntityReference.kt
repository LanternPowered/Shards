/*
 * Shards
 *
 * Copyright (c) LanternPowered <https://www.lanternpowered.org>
 * Copyright (c) contributors
 *
 * This work is licensed under the terms of the MIT License (MIT). For
 * a copy, see 'LICENSE.txt' or <https://opensource.org/licenses/MIT>.
 */
package org.lanternpowered.shards.internal

import org.lanternpowered.shards.entity.EntityId

/**
 * Represents a reference to an entity of a specific engine.
 */
@Suppress("NON_PUBLIC_PRIMARY_CONSTRUCTOR_OF_INLINE_CLASS")
@PublishedApi
internal inline class EntityReference internal constructor(val value: Long) {

  constructor(id: EntityId, engine: Int) :
    this((engine.toLong() shl 32) or id.value.toLong())

  val id: EntityId
    get() = EntityId((value and 0xffffffffL).toInt())

  val engine: Int
    get() = (value ushr 32).toInt()

  val isEmpty: Boolean
    get() = (value and 0xffffffffL).toInt() == Int.MAX_VALUE

  companion object {

    /**
     * Represents an "empty" entity reference.
     */
    val Empty = EntityReference(EntityId(Int.MAX_VALUE), 0)
  }
}
