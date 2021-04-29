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
 * Returns the [ComponentType] for the specified type [T].
 */
inline fun <reified T : Component> type(): ComponentType<T> = type(T::class)

/**
 * Returns the [ComponentType] for the specified type component class.
 */
fun <T : Component> type(componentClass: KClass<T>): ComponentType<T> =
  resolveInternalComponentType(componentClass).componentType

/**
 * Returns the [ComponentType] for the specified type [T] with read-only access.
 */
inline fun <reified T : Component> readOnly(): ComponentType<T> =
  readOnly(T::class)

/**
 * Returns the [ComponentType] for the specified type [T] with read-write
 * access.
 */
inline fun <reified T : Component> readWrite(): ComponentType<T> =
  readWrite(T::class)

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

internal fun failNoDefaultConstructor(type: KClass<*>): Nothing =
  throw InvalidComponentException(
    "Class '$type' is missing a default constructor.")
