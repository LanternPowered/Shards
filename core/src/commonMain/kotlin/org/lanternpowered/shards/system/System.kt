/*
 * Shards
 *
 * Copyright (c) LanternPowered <https://www.lanternpowered.org>
 * Copyright (c) contributors
 *
 * This work is licensed under the terms of the MIT License (MIT). For
 * a copy, see 'LICENSE.txt' or <https://opensource.org/licenses/MIT>.
 */
package org.lanternpowered.shards.system

import org.lanternpowered.shards.component.Component
import org.lanternpowered.shards.entity.Entity
import org.lanternpowered.shards.entity.EntityDsl

/**
 * Constructs a new [System] with an [executor] function.
 */
inline fun System(
  crossinline executor: suspend SystemContext.() -> Unit
): System {
  return object : System() {
    override suspend fun SystemContext.execute() {
      executor()
    }
  }
}

/**
 * Represents a system that can process [Component] related to [Entity]s.
 */
@EntityDsl
abstract class System {

  /**
   * Sets the system up to process entities and their components.
   */
  abstract suspend fun SystemContext.execute()
}
