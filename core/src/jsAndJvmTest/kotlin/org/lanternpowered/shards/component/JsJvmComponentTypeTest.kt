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

import org.lanternpowered.shards.util.AccessMode
import kotlin.test.Test
import kotlin.test.assertEquals

class JsJvmComponentTypeTest {

  @Test
  fun testGenericType() {
    assertEquals(Tag::class, type<Tag>().componentClass)
  }

  @Test
  fun testGenericTypeWithModeAccess() {
    assertEquals(AccessMode.Undefined, type<Tag>().accessMode)
    assertEquals(AccessMode.ReadOnly, readOnly<Tag>().accessMode)
    assertEquals(AccessMode.ReadWrite, readWrite<Tag>().accessMode)
  }
}

data class Tag(var tag: String = "") : Component {
  companion object Type : ComponentType<Tag>(::Tag)
}
