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

import kotlin.jvm.JvmInline

/**
 * An internal representation of a reference to an entity of a specific
 * universe. Each reference also contains a version, which can be used to
 * determine whether an entity id has been reused (higher version than before).
 */
@PublishedApi
@JvmInline
internal value class InternalEntityRef(val value: Long) {

  constructor(id: EntityId, universe: Int, version: Int) :
    this((version.toLong() shl 48) or
      (universe.toLong() shl 32) or id.value.toLong())

  /**
   * The id of the entity this reference points to, specific to the universe
   * the entity exists/existed in.
   */
  val entityId: EntityId
    get() = EntityId((value and 0xffffffffL).toInt())

  /**
   * The version of the entity reference, when an entity is created a
   * "generation" or "version" is generated representing the number of times
   * an entity id has been reused. This can be used to determine whether an
   * reference with a specific entity id still points to the same entity, or
   * if it was previously destroyed and a new entity was created using the
   * same id.
   */
  val version: Int
    get() = (value ushr 48).toInt()

  /**
   * The id of the universe this reference points to.
   */
  val universe: Int
    get() = (value ushr 32).toInt() and 0xffff

  /**
   * Whether this represents an empty reference.
   */
  val isEmpty: Boolean
    get() = (value and 0xffffffffL).toInt() == Int.MAX_VALUE

  companion object {

    /**
     * Represents an "empty" entity reference.
     */
    val Empty = InternalEntityRef(EntityId(Int.MAX_VALUE), 0, 0)
  }
}
