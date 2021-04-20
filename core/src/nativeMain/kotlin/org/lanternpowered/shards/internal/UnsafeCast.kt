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

/**
 * Performs an unsafe cast.
 */
@Suppress("UNCHECKED_CAST")
internal actual inline fun <T> Any?.unsafeCast(): T = this as T
