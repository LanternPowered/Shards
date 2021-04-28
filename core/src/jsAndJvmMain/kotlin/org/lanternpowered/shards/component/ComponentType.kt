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
expect inline fun <reified T : Component> type(): ComponentType<T>

/**
 * Returns the [ComponentType] for the specified type component class.
 */
expect fun <T : Component> type(componentClass: KClass<T>): ComponentType<T>

/**
 * Returns the [ComponentType] for the specified type [T] with read-only access.
 */
expect inline fun <reified T : Component> readOnly(): ComponentType<T>

/**
 * Returns the [ComponentType] for the specified type [T] with read-write
 * access.
 */
expect inline fun <reified T : Component> readWrite(): ComponentType<T>
