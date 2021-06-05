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

import kotlin.test.Test
import kotlin.test.assertEquals

class ComponentTypeTest {

  @Test
  fun testType() {
    assertEquals(Health::class, Health.componentClass)
  }

  @Test
  fun testGenericType() {
    assertEquals(Tag::class, componentType<Tag>().componentClass)
    assertEquals(DoubleTag::class, componentType<DoubleTag>().componentClass)
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
