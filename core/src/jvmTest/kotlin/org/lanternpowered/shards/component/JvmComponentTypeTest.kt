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

import org.junit.Test
import kotlin.test.assertEquals

class JvmComponentTypeTest {

  @Test
  fun testGenericTypeExtraction() {
    assertEquals(Saturation.componentClass, Saturation::class)
  }
}

data class Saturation(val value: Double = 5.0) : Component {
  companion object Type : ComponentType<Saturation>()
}
