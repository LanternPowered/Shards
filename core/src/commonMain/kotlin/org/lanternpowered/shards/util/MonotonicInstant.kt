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

import kotlin.time.Duration
import kotlin.time.nanoseconds

/**
 * Represents a specific instant in monotonic time.
 */
@Suppress("NON_PUBLIC_PRIMARY_CONSTRUCTOR_OF_INLINE_CLASS")
inline class MonotonicInstant internal constructor(
  private val value: Long
) : Comparable<MonotonicInstant> {

  operator fun plus(duration: Duration): MonotonicInstant =
    MonotonicInstant(value + duration.toLongNanoseconds())

  operator fun minus(duration: Duration): MonotonicInstant =
    MonotonicInstant(value - duration.toLongNanoseconds())

  operator fun minus(instant: MonotonicInstant): Duration =
    (value - instant.value).nanoseconds

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
