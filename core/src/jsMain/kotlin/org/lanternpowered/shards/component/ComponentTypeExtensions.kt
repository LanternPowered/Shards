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

package org.lanternpowered.shards.component

import kotlin.reflect.KClass

actual fun <T : Component> ComponentType(
  componentClass: KClass<T>
): ComponentType<T> = SimpleComponentType(componentClass)

private class SimpleComponentType<T : Component>(componentClass: KClass<T>) :
  ComponentType<T>(componentClass, null)
