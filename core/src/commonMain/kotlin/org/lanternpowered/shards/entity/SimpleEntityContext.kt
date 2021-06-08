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
import org.lanternpowered.shards.internal.UniverseManager

object SimpleEntityContext : EntityContext() {

  override val Entity.isActive: Boolean
    get() = UniverseManager.isActive(ref)

  override fun Entity.contains(type: ComponentType<*>): Boolean =
    UniverseManager.containsComponent(ref, type)

  override fun <T : Component> Entity.get(type: ComponentType<T>): T =
    UniverseManager.getComponent(ref, type)

  override fun <T : Component> Entity.getOrNull(type: ComponentType<T>): T? =
    UniverseManager.getComponentOrNull(ref, type)

  override fun <T : Component> Entity.set(
    type: ComponentType<T>, component: T
  ) = UniverseManager.setComponent(ref, type, component)

  override fun Entity.set(component: Component) =
    UniverseManager.setComponent(ref, component)
}
