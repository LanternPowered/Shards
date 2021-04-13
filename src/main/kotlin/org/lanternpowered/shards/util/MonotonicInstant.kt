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
import kotlin.time.TimeSource
import kotlin.time.nanoseconds

/**
 * The monotonic time source.
 */
typealias MonotonicTime = TimeSource.Monotonic

/**
 * Gets the [MonotonicInstant] of the [TimeSource.Monotonic] time source.
 */
@Suppress("unused")
fun MonotonicTime.now(): MonotonicInstant =
  MonotonicInstant(System.nanoTime())

/**
 * Represents a specific instant in monotonic time.
 */
@Suppress("NON_PUBLIC_PRIMARY_CONSTRUCTOR_OF_INLINE_CLASS")
inline class MonotonicInstant internal constructor(
  private val value: Long
) : Comparable<MonotonicInstant> {

  operator fun plus(duration: Duration): MonotonicInstant =
    MonotonicInstant(this.value + duration.toLongNanoseconds())

  operator fun minus(duration: Duration): MonotonicInstant =
    MonotonicInstant(this.value - duration.toLongNanoseconds())

  operator fun minus(instant: MonotonicInstant): Duration =
    (this.value - instant.value).nanoseconds

  override fun compareTo(other: MonotonicInstant): Int =
    this.value.compareTo(other.value)

  fun toLongNanoseconds(): Long = value

  companion object {

    /**
     * Constructs a [MonotonicInstant] from the specified nanoseconds.
     */
    fun ofNanoseconds(value: Long): MonotonicInstant = MonotonicInstant(value)
  }
}
