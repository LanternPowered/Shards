/*
 * Shards
 *
 * Copyright (c) LanternPowered <https://www.lanternpowered.org>
 * Copyright (c) contributors
 *
 * This work is licensed under the terms of the MIT License (MIT). For
 * a copy, see 'LICENSE.txt' or <https://opensource.org/licenses/MIT>.
 */
package org.lanternpowered.shards.util

/**
 * Performs an unsafe cast.
 */
@PublishedApi
@Suppress("UNCHECKED_CAST", "NOTHING_TO_INLINE")
internal actual inline fun <T> Any?.unsafeCast(): T = this as T
