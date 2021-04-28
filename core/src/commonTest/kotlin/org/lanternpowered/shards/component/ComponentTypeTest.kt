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
  fun testInstantiator() {
    val healthType = Health
    val health = healthType.instantiator()
    assertEquals(Health::class, health::class)
    assertEquals(20.0, health.value)
    health.value = 10.0
    assertEquals(10.0, health.value)
  }

  @Test
  fun testAccessMode() {
    assertEquals(AccessMode.Undefined, Health.accessMode)
    assertEquals(AccessMode.ReadOnly, readOnly(Health).accessMode)
    assertEquals(AccessMode.ReadWrite, readWrite(Health).accessMode)
  }
}

data class Health(var value: Double = 20.0) : Component {
  companion object Type : ComponentType<Health>(::Health)
}
data class Food(var value: Double = 30.0) : Component {
  companion object Type : ComponentType<Food>(::Food)
}
