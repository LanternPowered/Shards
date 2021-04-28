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

actual inline fun <reified T : Component> type(): ComponentType<T> =
  type(T::class)

actual inline fun <reified T : Component> readOnly(): ComponentType<T> =
  readOnly(T::class)

actual inline fun <reified T : Component> readWrite(): ComponentType<T> =
  readWrite(T::class)

actual fun <T : Component> type(
  componentClass: KClass<T>
): ComponentType<T> =
  resolveInternalComponentType(componentClass).componentType

@PublishedApi
internal fun <T : Component> readOnly(
  componentClass: KClass<T>
): ComponentType<T> =
  resolveInternalComponentType(componentClass).componentTypeReadOnly

@PublishedApi
internal fun <T : Component> readWrite(
  componentClass: KClass<T>
): ComponentType<T> =
  resolveInternalComponentType(componentClass).componentTypeReadWrite
