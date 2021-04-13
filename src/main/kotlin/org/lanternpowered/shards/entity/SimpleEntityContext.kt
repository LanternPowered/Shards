package org.lanternpowered.shards.entity

import org.lanternpowered.shards.component.Component
import org.lanternpowered.shards.component.ComponentType
import org.lanternpowered.shards.internal.EngineManager

object SimpleEntityContext : EntityContext {

  override fun <T : Component> Entity.get(type: ComponentType<T>): T =
    EngineManager.getComponent(reference, type)

  override fun <T : Component> Entity.getOrNull(type: ComponentType<T>): T? =
    EngineManager.getComponentOrNull(reference, type)

  override fun <T : Component> Entity.add(type: ComponentType<T>): T =
    EngineManager.addComponent(reference, type)
}
