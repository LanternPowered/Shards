/*
 * Shards
 *
 * Copyright (c) LanternPowered <https://www.lanternpowered.org>
 * Copyright (c) contributors
 *
 * This work is licensed under the terms of the MIT License (MIT). For
 * a copy, see 'LICENSE.txt' or <https://opensource.org/licenses/MIT>.
 */
package org.lanternpowered.shards.test

import org.lanternpowered.shards.Engine
import org.lanternpowered.shards.aspect.exclude
import org.lanternpowered.shards.aspect.plus
import org.lanternpowered.shards.component.Component
import org.lanternpowered.shards.component.ComponentType
import org.lanternpowered.shards.component.InvalidatableComponent
import org.lanternpowered.shards.component.modify
import org.lanternpowered.shards.entity.Entity
import org.lanternpowered.shards.entity.EntityArray
import org.lanternpowered.shards.entity.EntityCollection
import org.lanternpowered.shards.entity.EntitySequence
import org.lanternpowered.shards.entity.OptionalEntity
import org.lanternpowered.shards.entity.add
import org.lanternpowered.shards.entity.forEach
import org.lanternpowered.shards.entity.modify
import org.lanternpowered.shards.entity.with
import org.lanternpowered.shards.system.IfModified
import org.lanternpowered.shards.system.OnAdd
import org.lanternpowered.shards.system.OnDelete
import org.lanternpowered.shards.system.OnProcess
import org.lanternpowered.shards.system.System
import kotlin.time.Duration

fun init() {
  val engine = Engine()
  val entity0 = engine.createEntity()
  val testComponent = entity0.add(HealthComponent)
  testComponent.health = 1000.0

  val entities = engine.use {
    EntityArray(1000) {
      val entity = createEntity()
      val health = entity.add(HealthComponent)
      health.health = 1000.0
      entity
    }
  }
  val entities1 = engine.use {
    EntityArray(1000) {
      createEntity().modify {
        add(HealthComponent) {
          health = 125.0
        }
      }
    }
  }

  engine.createEntity().modify {
    add(HealthComponent) {
      health = 0.3
      lastAttacker = OptionalEntity.empty()
    }
  }
}


data class OtherComponent(
  var value: String = ""
) : InvalidatableComponent() {

  companion object Type : ComponentType<OtherComponent>()
}

data class HealthComponent(
  var health: Double = 10.0,
  var lastAttacker: OptionalEntity = OptionalEntity.empty()
) : InvalidatableComponent() {

  companion object Type : ComponentType<HealthComponent>()
}

class ExcludedComponent : Component {

  companion object Type : ComponentType<ExcludedComponent>()
}

/**
 * A test system targeting [HealthComponent] ignoring all entities holding
 * [ExcludedComponent].
 */
class TestSystem : System(HealthComponent + exclude(ExcludedComponent)) {

  // TODO: Figure out how dependencies on certain component types can be
  //  determined, so we know which systems depend on eachother and which
  //  systems can be processed in parallel
  //  Possibly the only solution is to manually define the dependencies, we
  //  don't even know what methods may be used outside of the system class.

  /**
   * Is called when an entity was added to the engine or components were
   * added so the entity is now valid to be processed using this system.
   */
  @OnAdd
  private fun added(entity: Entity) {
  }

  /**
   * Is called when an entity was deleted from the engine or components were
   * removed so the entity is no longer valid to be processed using this system.
   */
  @OnDelete
  private fun deleted(entity: Entity) {
  }

  /**
   * Allowed `onProcess` parameters:
   * - Duration ~ deltaTime
   * - Entity ~ the target entity
   * - Subclasses of Component ~ components that are needed for the process, can
   * be nullable if they are considered optional
   */

  @OnProcess
  fun process(deltaTime: Duration, entity: Entity) {
    val test = entity.get(HealthComponent)
    test.health += 100.0
    entity.modify {
      get(HealthComponent).modify {

      }
      // OR
      modify(HealthComponent) {
        health -= 0.1
      }
    }
    // Process
  }

  @OnProcess
  fun bulkProcess(deltaTime: Duration, entities: EntityCollection) {
    // Process all the applicable entities in bulk, instead of calling a
    // single process function for every entity
    for (entity in entities) {
      // Do things
      entity.modify {
        get(HealthComponent).modify {
          health -= 2.0
        }
      }
    }
    // OR better
    entities.modifyAll {
      modify(HealthComponent) {
        health -= 2.0
      }
    }
  }

  @OnProcess
  fun process(deltaTime: Duration, component: HealthComponent) {
    // Process with required components
  }

  @OnProcess
  fun process(deltaTime: Duration, entity: Entity, component: HealthComponent) {
    // Process with required components
  }

  @OnProcess
  fun processIfModified(deltaTime: Duration, component: @IfModified HealthComponent) {
    // Process with required components, if the target component type was
    // modified, this filter only works for Components of subtype
    // InvalidatableComponent, the filter will be ignored for other types
  }

  @OnProcess
  fun process() {
    // Process ignoring delta time
  }

  @OnProcess
  fun alternativeFiltering(entities: EntitySequence) {
    // Exploring alternative solutions to the annotation based system
    // TODO: how can dependencies on component types be determined through this?

    entities
      // .filter { entity -> entity.contains(HealthComponent) }
      .with(HealthComponent)
      .forEach { entity ->
        entity.modify(HealthComponent) {
          health += 2
        }
      }

    val fn: (Entity, HealthComponent) -> Unit = { entity, health ->
      health.health += 2
    }
  }
}
