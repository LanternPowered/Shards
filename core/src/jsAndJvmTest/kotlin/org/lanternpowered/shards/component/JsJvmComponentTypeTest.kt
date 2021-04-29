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
import kotlin.test.assertFailsWith

class JsJvmComponentTypeTest {

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

  @Test
  fun testInvalidComponentClass() {
    assertFailsWith<InvalidComponentException> { type<InvalidTag>() }
    assertFailsWith<InvalidComponentException> { type<InvalidDoubleTag>() }
    assertFailsWith<InvalidComponentException> { type<InvalidTripleTag>() }
  }
}

data class Tag(var tag: String = "") : Component {
  companion object Type : ComponentType<Tag>(::Tag)
}

data class DoubleTag(
  var tag: String = "first",
  val tag2: String = "second"
) : Component {
  companion object Type : ComponentType<DoubleTag>(::DoubleTag)
}

// No default value for tag, this is not allowed
data class InvalidTag(var tag: String) : Component

data class InvalidDoubleTag(
  var tag1: String, var tag2: String
) : Component

data class InvalidTripleTag(
  var tag1: String, var tag2: String, var tag3: String = "third"
) : Component
