/*
 * Shards
 *
 * Copyright (c) LanternPowered <https://www.lanternpowered.org>
 * Copyright (c) contributors
 *
 * This work is licensed under the terms of the MIT License (MIT). For
 * a copy, see 'LICENSE.txt' or <https://opensource.org/licenses/MIT>.
 */
@file:Suppress("FunctionName")

package org.lanternpowered.shards.entity

import org.lanternpowered.shards.component.Component

/**
 * Constructs a new [EntityArchetype].
 */
fun EntityArchetype(
  operation: EntityArchetypeBuilder.() -> Unit
): EntityArchetype {
  TODO()
}

/**
 * Represents an archetype of [Entity]s. Entities can be instantiated using
 * the archetype.
 */
interface EntityArchetype {

}

/**
 * A builder for [EntityArchetype]s.
 */
interface EntityArchetypeBuilder {

  /**
   * Copies all the [Component]s from the target [EntityArchetype] into this
   * builder.
   */
  fun setFrom(archetype: EntityArchetype)

  /**
   * Sets the [Component].
   */
  fun set(component: Component)
}
