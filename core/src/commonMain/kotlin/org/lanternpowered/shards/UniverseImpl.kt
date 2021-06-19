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
import org.lanternpowered.shards.entity.sequences.EntitySeq0
import org.lanternpowered.shards.entity.sequences.EntitySeq0Impl
import org.lanternpowered.shards.system.EntitySeqContext

internal class UniverseImpl(id: Int) : Universe(id) {

  private val queryContext = object : QueryableEntityContext {

    override fun <R> map(
      query: InternalQuery,
      operation: EntitySeqContext.(Entity) -> R
    ): Sequence<R> {
      TODO("Not yet implemented")
    }

    override fun forEach(
      query: InternalQuery,
      operation: EntitySeqContext.(Entity) -> Unit
    ) {
      TODO("Not yet implemented")
    }

    override fun forAll(
      query: InternalQuery,
      operation: EntitySeqContext.(EntityList) -> Unit
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

  override fun entityQuery(): EntitySeq0 = EntitySeq0Impl(queryContext)

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
