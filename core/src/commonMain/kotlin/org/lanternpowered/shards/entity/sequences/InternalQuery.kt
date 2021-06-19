/*
 * Shards
 *
 * Copyright (c) LanternPowered <https://www.lanternpowered.org>
 * Copyright (c) contributors
 *
 * This work is licensed under the terms of the MIT License (MIT). For
 * a copy, see 'LICENSE.txt' or <https://opensource.org/licenses/MIT>.
 */
package org.lanternpowered.shards.entity.sequences

import org.lanternpowered.shards.component.ComponentType
import org.lanternpowered.shards.entity.Entity
import org.lanternpowered.shards.entity.collections.EntityList

internal interface QueryableEntityContext {

  fun <R> map(
    query: InternalQuery,
    operation: InternalQueryContext.(Entity) -> R
  ): Sequence<R>

  fun forEach(
    query: InternalQuery,
    operation: InternalQueryContext.(Entity) -> Unit
  )

  fun forAll(
    query: InternalQuery,
    operation: InternalQueryContext.(EntityList) -> Unit
  )

  fun first(query: InternalQuery): Entity

  fun firstOrNull(query: InternalQuery): Entity?

  fun last(query: InternalQuery): Entity

  fun lastOrNull(query: InternalQuery): Entity?
}

internal typealias InternalQueryContext =
  EntitySeqContext5<*, *, *, *, *, *, *, *, *, *>

internal interface InternalQuery {
  val reads: List<ComponentType<*>>
  val modifies: List<ComponentType<*>>
  val filters: List<Filter>
}
