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

import kotlin.system.getTimeNanos

// TODO: Is not time since epoch, but since the program startup
actual fun MonotonicTime.now(): MonotonicInstant =
  MonotonicInstant(getTimeNanos())
