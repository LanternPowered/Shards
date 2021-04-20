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
    assertEquals(Health.componentClass, Health::class)
  }

  @Test
  fun testInstantiator() {
    val healthType = Health
    val health = healthType.instantiator()
    assertEquals(health::class, Health::class)
    assertEquals(20.0, health.value)
    health.value = 10.0
    assertEquals(10.0, health.value)
  }
}

data class Health(var value: Double = 20.0) : Component {
  companion object Type : ComponentType<Health>(::Health)
}
data class Food(var value: Double = 30.0) : Component {
  companion object Type : ComponentType<Food>(::Food)
}
