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

import kotlin.jvm.JvmInline
import kotlin.time.Duration

/**
 * Represents a specific instant in monotonic time.
 */
@JvmInline
value class MonotonicInstant internal constructor(
  private val value: Long
) : Comparable<MonotonicInstant> {

  operator fun plus(duration: Duration): MonotonicInstant =
    MonotonicInstant(value + duration.inWholeNanoseconds)

  operator fun minus(duration: Duration): MonotonicInstant =
    MonotonicInstant(value - duration.inWholeNanoseconds)

  operator fun minus(instant: MonotonicInstant): Duration =
    Duration.nanoseconds(value - instant.value)

  override fun compareTo(other: MonotonicInstant): Int =
    value.compareTo(other.value)

  fun toLongNanoseconds(): Long = value

  companion object {

    /**
     * Constructs a [MonotonicInstant] from the specified nanoseconds.
     */
    fun ofNanoseconds(value: Long): MonotonicInstant = MonotonicInstant(value)
  }
}
