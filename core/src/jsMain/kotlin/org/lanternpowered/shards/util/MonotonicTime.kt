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

actual fun MonotonicTime.now(): MonotonicInstant {
  // https://stackoverflow.com/questions/6002808/is-there-any-way-to-get-current-time-in-nanoseconds-using-javascript

  val window = js("window")
  return if (window != undefined) {
    val perf = window.performance
    val millis = (perf.now() + perf.timing.navigationStart) as Double
    MonotonicInstant((millis * 1_000_000.0).toLong())
  } else {
    val hrTime = js("process").hrtime() as Array<Int>
    MonotonicInstant(hrTime[0].toLong() * 1_000_000_000 + hrTime[1])
  }
}
