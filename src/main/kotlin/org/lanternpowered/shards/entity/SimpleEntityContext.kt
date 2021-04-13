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
import org.lanternpowered.shards.internal.EngineManager

object SimpleEntityContext : EntityContext {

  override fun <T : Component> Entity.get(type: ComponentType<T>): T =
    EngineManager.getComponent(ref, type)

  override fun <T : Component> Entity.getOrNull(type: ComponentType<T>): T? =
    EngineManager.getComponentOrNull(ref, type)

  override fun <T : Component> Entity.add(type: ComponentType<T>): T =
    EngineManager.addComponent(ref, type)
}
