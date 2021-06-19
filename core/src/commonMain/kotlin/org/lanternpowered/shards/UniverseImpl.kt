/*
 * Shards
 *
 * Copyright (c) LanternPowered <https://www.lanternpowered.org>
 * Copyright (c) contributors
 *
 * This work is licensed under the terms of the MIT License (MIT). For
 * a copy, see 'LICENSE.txt' or <https://opensource.org/licenses/MIT>.
 */
package org.lanternpowered.shards

import org.lanternpowered.shards.component.Component
import org.lanternpowered.shards.component.ComponentType
import org.lanternpowered.shards.entity.Entity
import org.lanternpowered.shards.entity.EntityId
import org.lanternpowered.shards.entity.collections.EntityList
import org.lanternpowered.shards.entity.sequences.InternalQuery
import org.lanternpowered.shards.entity.sequences.QueryableEntityContext
import org.lanternpowered.shards.entity.sequences.EntitySequence
import org.lanternpowered.shards.entity.sequences.EntitySequenceImpl
import org.lanternpowered.shards.entity.sequences.InternalQueryContext

internal class UniverseImpl(id: Int) : Universe(id) {

  private val queryContext = object : QueryableEntityContext {

    override fun <R> map(
      query: InternalQuery,
      operation: InternalQueryContext.(Entity) -> R
    ): Sequence<R> {
      TODO("Not yet implemented")
    }

    override fun forEach(
      query: InternalQuery,
      operation: InternalQueryContext.(Entity) -> Unit
    ) {
      TODO("Not yet implemented")
    }

    override fun forAll(
      query: InternalQuery,
      operation: InternalQueryContext.(EntityList) -> Unit
    ) {
      TODO("Not yet implemented")
    }

    override fun first(query: InternalQuery): Entity {
      TODO("Not yet implemented")
    }

    override fun firstOrNull(query: InternalQuery): Entity? {
      TODO("Not yet implemented")
    }

    override fun last(query: InternalQuery): Entity {
      TODO("Not yet implemented")
    }

    override fun lastOrNull(query: InternalQuery): Entity? {
      TODO("Not yet implemented")
    }
  }

  override fun entityQuery(): EntitySequence = EntitySequenceImpl(queryContext)

  override fun isActive(entityId: EntityId, version: Int): Boolean {
    TODO("Not yet implemented")
  }

  override fun isActive(entityId: EntityId): Boolean {
    TODO("Not yet implemented")
  }

  override fun getEntity(entityId: EntityId): Entity {
    TODO("Not yet implemented")
  }

  override fun containsComponent(
    entityId: EntityId,
    type: ComponentType<*>
  ): Boolean {
    TODO("Not yet implemented")
  }

  override fun <T : Component> getComponentOrNull(
    entityId: EntityId,
    type: ComponentType<T>
  ): T? {
    TODO("Not yet implemented")
  }

  override fun <T : Component> getComponent(
    entityId: EntityId,
    type: ComponentType<T>
  ): T {
    TODO("Not yet implemented")
  }

  override fun <T : Component> setComponent(
    entityId: EntityId,
    type: ComponentType<T>,
    component: T
  ) {
    TODO("Not yet implemented")
  }
}
