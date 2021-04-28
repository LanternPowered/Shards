/*
 * Shards
 *
 * Copyright (c) LanternPowered <https://www.lanternpowered.org>
 * Copyright (c) contributors
 *
 * This work is licensed under the terms of the MIT License (MIT). For
 * a copy, see 'LICENSE.txt' or <https://opensource.org/licenses/MIT>.
 */
@file:JvmName("JsJvmEntities")

package org.lanternpowered.shards.entity

import org.lanternpowered.shards.component.Component
import org.lanternpowered.shards.component.type
import kotlin.jvm.JvmName

/**
 * Returns if the entity contains a component of the specified [type].
 */
inline fun <reified T : Component> Entity.contains(): Boolean =
  contains(type<T>())

/**
 * Gets the component instance of the given [type] and fails if the component
 * wasn't found.
 */
inline fun <reified T : Component> Entity.get(): T = get(type())

/**
 * Gets the component instance of the given [type] and returns `null` if the
 * component wasn't found.
 */
inline fun <reified T : Component> Entity.getOrNull(): T? = getOrNull(type())

/**
 * Adds the component of the specified type [T] to the entity and gets the
 * instance. An [IllegalArgumentException] will be thrown if the entity
 * already owns a component of the specified type [T].
 */
inline fun <reified T : Component> Entity.add(): T = add(type())

/**
 * Gets the component of the specified [type] if it exists, otherwise a new
 * component will be created.
 */
inline fun <reified T : Component> Entity.getOrAdd(): T = getOrAdd(type())

/**
 * Adds the component of the specified [type] to the entity and gets the
 * instance. An [IllegalArgumentException] will be thrown if the entity
 * already owns a component of the specified [type]. The [operation] will be
 * applied to the constructed component.
 */
inline fun <reified T : Component> Entity.add(
  operation: T.() -> Unit
): T = add(type(), operation)

/**
 * Gets the component of the specified [type] and applies the given
 * [operation] to it. An [IllegalArgumentException] will be thrown if the
 * entity doesn't own a component with the specified [type].
 */
inline fun <reified T : Component> Entity.modify(
  operation: T.() -> Unit
): T = modify(type(), operation)

/**
 * Gets the component of the specified [type] if it exists, otherwise a new
 * component will be created. The [operation] will be applied to the
 * retrieved or constructed component.
 */
inline fun <reified T : Component> Entity.modifyOrAdd(
  operation: T.() -> Unit
): T = modifyOrAdd(type(), operation)
