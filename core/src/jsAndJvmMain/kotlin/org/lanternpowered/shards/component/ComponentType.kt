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

/**
 * Constructs a new [ComponentType] with the specified type [T].
 */
inline fun <reified T : Component> componentType(): ComponentType<T> =
  ComponentType(T::class)

/**
 * Constructs a new [ComponentType] with the specified type component class.
 */
fun <T : Component> ComponentType(componentClass: KClass<T>): ComponentType<T> =
  ComponentType(componentClass, null)
