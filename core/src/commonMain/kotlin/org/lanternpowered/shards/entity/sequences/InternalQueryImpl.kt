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

import org.lanternpowered.shards.component.Component
import org.lanternpowered.shards.component.ComponentType
import org.lanternpowered.shards.entity.Entity
import org.lanternpowered.shards.entity.collections.EntityList

internal data class InternalQueryImpl(
  val context: QueryableEntityContext,
  override val reads: List<ComponentType<*>> = emptyList(),
  override val modifies: List<ComponentType<*>> = emptyList(),
  override val filters: List<Filter> = emptyList()
) : InternalQuery {

  fun reads(type: ComponentType<*>) =
    copy(reads = reads + type)

  fun modifies(type: ComponentType<*>) =
    copy(modifies = modifies + type, reads = reads + type)

  fun without(type: ComponentType<*>) =
    copy(filters = filters + WithoutFilter(type))

  fun with(type: ComponentType<*>) =
    copy(filters = filters + WithFilter(type))

  fun <T : Component> withFiltered(
    type: ComponentType<T>,
    predicate: (T) -> Boolean
  ) = copy(filters = filters + WithFilter(type, predicate))

  fun filter(
    predicate: InternalQueryContext.(Entity) -> Boolean
  ) = copy(filters = filters + EntityFilter(predicate))

  fun <R> map(operation: InternalQueryContext.(Entity) -> R): Sequence<R> =
    context.map(this, operation)

  fun forEach(operation: InternalQueryContext.(Entity) -> Unit) =
    context.forEach(this, operation)

  fun forAll(operation: InternalQueryContext.(EntityList) -> Unit) =
    context.forAll(this, operation)

  fun first(): Entity =
    context.first(this)

  fun firstOrNull(): Entity? =
    context.firstOrNull(this)

  fun last(): Entity =
    context.last(this)

  fun lastOrNull(): Entity? =
    context.lastOrNull(this)
}
