/*
 * Shards
 *
 * Copyright (c) LanternPowered <https://www.lanternpowered.org>
 * Copyright (c) contributors
 *
 * This work is licensed under the terms of the MIT License (MIT). For
 * a copy, see 'LICENSE.txt' or <https://opensource.org/licenses/MIT>.
 */
package org.lanternpowered.shards.component

import org.lanternpowered.shards.util.AccessMode
import kotlin.reflect.KClass

/**
 * @property id An unique identifier which represents the component type
 * @property componentClass The type of the component
 * @property instantiator An instantiator used to create instances of the
 *                        component
 */
internal class InternalComponentType<T : Component>(
  val id: Int,
  val componentClass: KClass<T>,
  val instantiator: () -> T,
  componentType: (InternalComponentType<T>, AccessMode) -> ComponentType<T>
) {
  val componentType = componentType(this, AccessMode.Undefined)
  val componentTypeReadOnly = componentType(this, AccessMode.ReadOnly)
  val componentTypeReadWrite = componentType(this, AccessMode.ReadWrite)
}

internal expect fun <T : Component> resolveInternalComponentType(
  componentClass: KClass<T>, instantiator: (() -> T)? = null
): InternalComponentType<T>
