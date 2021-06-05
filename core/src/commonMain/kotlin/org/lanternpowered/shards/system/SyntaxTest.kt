/*
 * Shards
 *
 * Copyright (c) LanternPowered <https://www.lanternpowered.org>
 * Copyright (c) contributors
 *
 * This work is licensed under the terms of the MIT License (MIT). For
 * a copy, see 'LICENSE.txt' or <https://opensource.org/licenses/MIT>.
 */
package org.lanternpowered.shards.system

import org.lanternpowered.shards.component.Component
import org.lanternpowered.shards.component.ComponentType
import org.lanternpowered.shards.component.InvalidatableComponent
import org.lanternpowered.shards.entity.EntityReference

private val TestSystem = System {
  val task1 = async {
    entities
      .with<Health, Food>()
      .without<Excluded>()
      .forEach { entity ->
        val (health) = entity.get<Health>()
        val (food) = entity.get<Food>()
        entity.set(Food(food + 1.0))
        entity.set(Health(health * 1.2))
      }

    entities
      .with<Health>()
      .with<Food>()
      .filter<Health> { (health) -> health > 0 }
      .filter<Food> { (food) -> food == 0.0 }
      .filter { entity -> !entity.contains<Tag>() }
      .forEach { entity ->
        val (health) = entity.get<Health>()
        entity.set(Health(health - 1.0))
        entity.set(Tag("Tag"))
        entity.transform<Food> { (food) -> Food(food + 1.0) }
      }

    1
  }

  val task2 = async {
    1
  }

  // Wait if you want to use the resulting data afterwards, at the end of
  // this block await will be done automatically for all started "async" tasks

  val result1 = task1.await()
  val result2 = task2.await()

  println(result1 + result2)
}

inline class Tag(val tag: String) : Component

data class Health(
  var health: Double = 10.0,
  var lastAttacker: EntityReference = EntityReference.Empty
) : InvalidatableComponent() {
  companion object Type : ComponentType<Health>(::Health)
}

class Excluded : Component {
  companion object Type : ComponentType<Excluded>(::Excluded)
}

data class Name(var name: String = "defaultName") : Component {
  companion object Type : ComponentType<Name>(::Name)
}

data class Food(var food: Double = 10.0) : InvalidatableComponent() {
  companion object Type : ComponentType<Food>(::Food)
}
