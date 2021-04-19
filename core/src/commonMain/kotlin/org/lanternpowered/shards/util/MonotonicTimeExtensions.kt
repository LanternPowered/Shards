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

import kotlin.time.TimeSource

// In a separate file for now, see https://youtrack.jetbrains.com/issue/KT-21186

/**
 * Gets the [MonotonicInstant] of the [TimeSource.Monotonic] time source.
 */
expect fun MonotonicTime.now(): MonotonicInstant
