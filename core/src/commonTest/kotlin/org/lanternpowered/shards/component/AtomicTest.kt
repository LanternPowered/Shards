/*
 * Shards
 *
 * Copyright (c) LanternPowered <https://www.lanternpowered.org>
 * Copyright (c) contributors
 *
 * This work is licensed under the terms of the MIT License (MIT). For
 * a copy, see 'LICENSE.txt' or <https://opensource.org/licenses/MIT>.
 */
package org.lanternpowered.shards.component

import kotlinx.atomicfu.atomic
import kotlin.test.Test
import kotlin.test.assertTrue

class AtomicTest {

  private val atomic = atomic(10)

  @Test
  fun test() {
    assertTrue(atomic.compareAndSet(10, 11))
  }
}
