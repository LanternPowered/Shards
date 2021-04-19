/*
 * Shards
 *
 * Copyright (c) LanternPowered <https://www.lanternpowered.org>
 * Copyright (c) contributors
 *
 * This work is licensed under the terms of the MIT License (MIT). For
 * a copy, see 'LICENSE.txt' or <https://opensource.org/licenses/MIT>.
 */
package org.lanternpowered.shards.internal

import org.lanternpowered.shards.component.Component
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
  val instantiator: () -> T
)

internal expect fun <T : Component> resolveInternalComponentType(
  componentClass: KClass<T>
): InternalComponentType<T>
