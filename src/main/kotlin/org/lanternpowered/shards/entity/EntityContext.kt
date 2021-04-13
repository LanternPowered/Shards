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

import org.lanternpowered.shards.component.Component
import org.lanternpowered.shards.component.ComponentType

interface EntityContext {

  /**
   * Gets the component instance of the given [type] and fails
   * if the component wasn't found.
   */
  @JvmSynthetic
  fun <T : Component> Entity.get(type: ComponentType<T>): T

  /**
   * Gets the component instance of the given [type] and returns `null`
   * if the component wasn't found.
   */
  @JvmSynthetic
  fun <T : Component> Entity.getOrNull(type: ComponentType<T>): T?

  /**
   * Adds the component of the given [type] to the entity and gets
   * the instance.
   */
  @JvmSynthetic
  fun <T : Component> Entity.add(type: ComponentType<T>): T
}
