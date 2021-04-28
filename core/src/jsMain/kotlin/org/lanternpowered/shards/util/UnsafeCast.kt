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

import kotlin.js.unsafeCast as unsafeCast0

/**
 * Performs an unsafe cast.
 */
@Suppress("NOTHING_TO_INLINE")
internal actual inline fun <T> Any?.unsafeCast(): T = unsafeCast0<T>()
