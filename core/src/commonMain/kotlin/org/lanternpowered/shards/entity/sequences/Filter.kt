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
import org.lanternpowered.shards.entity.contains
import org.lanternpowered.shards.entity.getOrNull

internal interface Filter {

  fun testEntity(context: InternalQueryContext, entity: Entity): Boolean
}

internal data class WithFilter<T : Component>(
  val type: ComponentType<T>,
  val predicate: ((T) -> Boolean)? = null,
) : Filter {

  override fun testEntity(
    context: InternalQueryContext, entity: Entity
  ): Boolean {
    val component = entity.getOrNull(type)
    val predicate = this.predicate
    return component != null && (predicate == null || predicate(component))
  }
}

internal data class WithoutFilter(
  val type: ComponentType<*>
) : Filter {

  override fun testEntity(
    context: InternalQueryContext,
    entity: Entity
  ): Boolean = !entity.contains(type)
}

internal data class EntityFilter(
  val predicate: InternalQueryContext.(Entity) -> Boolean
) : Filter {

  override fun testEntity(
    context: InternalQueryContext,
    entity: Entity
  ): Boolean = context.predicate(entity)
}
