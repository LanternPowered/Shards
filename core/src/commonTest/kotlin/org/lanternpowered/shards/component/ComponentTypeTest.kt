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

class ComponentTypeTest {

  @Test
  fun testType() {
    assertEquals(Health::class, Health.componentClass)
  }

  @Test
  fun testAccessMode() {
    assertEquals(AccessMode.Undefined, Health.accessMode)
    assertEquals(AccessMode.ReadOnly, readOnly(Health).accessMode)
    assertEquals(AccessMode.ReadWrite, readWrite(Health).accessMode)
  }

  @Test
  fun testGenericType() {
    assertEquals(Tag::class, type<Tag>().componentClass)
    assertEquals(DoubleTag::class, type<DoubleTag>().componentClass)
  }

  @Test
  fun testGenericTypeWithModeAccess() {
    assertEquals(AccessMode.Undefined, type<Tag>().accessMode)
    assertEquals(AccessMode.ReadOnly, readOnly<Tag>().accessMode)
    assertEquals(AccessMode.ReadWrite, readWrite<Tag>().accessMode)
  }
}

data class Health(var value: Double = 20.0) : Component {
  companion object Type : ComponentType<Health>(Health::class)
}

data class Food(var value: Double = 30.0) : Component {
  companion object Type : ComponentType<Food>(Food::class)
}

data class Tag(var tag: String = "") : Component {
  companion object Type : ComponentType<Tag>(Tag::class)
}

data class DoubleTag(
  var tag: String = "first",
  val tag2: String = "second"
) : Component {
  companion object Type : ComponentType<DoubleTag>(DoubleTag::class)
}
