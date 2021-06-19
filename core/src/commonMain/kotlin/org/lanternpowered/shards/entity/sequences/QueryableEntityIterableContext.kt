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

import org.lanternpowered.shards.entity.Entity
import org.lanternpowered.shards.entity.collections.EntityIterable
import org.lanternpowered.shards.entity.collections.EntityIterator
import org.lanternpowered.shards.entity.collections.EntityList
import org.lanternpowered.shards.entity.collections.generic

/**
 * Maps an [EntityIterable] into a [QueryableEntityContext].
 */
internal class QueryableEntityIterableContext(
  private val iterator: () -> EntityIterator
) : QueryableEntityContext {

  private fun filter(
    query: InternalQuery,
    context: InternalQueryContext =
      DefaultEntitySequenceContext.create(query.reads)
  ): Sequence<Entity> {
    var sequence = Sequence { iterator().generic() }
    for (filter in query.filters) {
      sequence = sequence
        .filter { entity -> filter.testEntity(context, entity) }
    }
    return sequence
  }

  override fun <R> map(
    query: InternalQuery,
    operation: InternalQueryContext.(Entity) -> R
  ): Sequence<R> {
    val context = DefaultEntitySequenceContext.create(query.reads)
    return filter(query, context)
      .map { entity -> context.operation(entity) }
  }

  override fun forEach(
    query: InternalQuery,
    operation: InternalQueryContext.(Entity) -> Unit
  ) {
    val context = DefaultEntitySequenceContext.create(query.reads)
    filter(query, context).forEach { entity -> context.operation(entity) }
  }

  override fun forAll(
    query: InternalQuery,
    operation: InternalQueryContext.(EntityList) -> Unit
  ) {
    val context = DefaultEntitySequenceContext.create(query.reads)
    val entityList = filter(query, context).toEntityList()
    context.operation(entityList)
  }

  override fun first(query: InternalQuery): Entity =
    filter(query).first()

  override fun firstOrNull(query: InternalQuery): Entity? =
    filter(query).firstOrNull()

  override fun last(query: InternalQuery): Entity =
    filter(query).last()

  override fun lastOrNull(query: InternalQuery): Entity? =
    filter(query).lastOrNull()
}
