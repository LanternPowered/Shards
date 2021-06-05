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

import kotlinx.coroutines.Deferred
import org.lanternpowered.shards.Engine
import org.lanternpowered.shards.component.Component
import org.lanternpowered.shards.component.ComponentType
import org.lanternpowered.shards.component.modify
import org.lanternpowered.shards.entity.Entity
import org.lanternpowered.shards.entity.EntityArray
import org.lanternpowered.shards.entity.EntityCollection
import org.lanternpowered.shards.entity.EntityIterator
import org.lanternpowered.shards.entity.EntityReference
import org.lanternpowered.shards.entity.EntitySequence
import org.lanternpowered.shards.entity.add
import org.lanternpowered.shards.entity.modify
import org.lanternpowered.shards.system.System
import kotlin.jvm.JvmName
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty
import kotlin.time.Duration

fun init() {
  val engine = Engine()
  val entity0 = engine.createEntity()
  val testComponent = entity0.add(Health)
  testComponent.health = 1000.0

  val entities = engine.use {
    EntityArray(1000) {
      val entity = createEntity()
      val health = entity.add(Health)
      health.health = 1000.0
      entity
    }
  }
  val entities1 = engine.use {
    EntityArray(1000) {
      createEntity().modify {
        add(Health) {
          health = 125.0
        }
      }
    }
  }

  engine.createEntity().modify {
    add(Health) {
      health = 0.3
      lastAttacker = EntityReference.Empty
    }
  }
}


data class Other(var value: String = "") : Component {
  companion object Type : ComponentType<Other>(Other::class)
}

data class Health(
  var health: Double = 10.0,
  var lastAttacker: EntityReference = EntityReference.Empty
) : Component {
  companion object Type : ComponentType<Health>(Health::class)
}

class Excluded : Component {
  companion object Type : ComponentType<Excluded>(Excluded::class)
}

data class Name(var name: String = "defaultName") : Component {
  companion object Type : ComponentType<Name>(Name::class)
}

data class Food(var food: Double = 10.0) : Component {
  companion object Type : ComponentType<Food>(Food::class)
}

/**
 * A test system targeting [Health] ignoring all entities holding
 * [Excluded].
 */
class TestSystem2 : System() {

  // TODO: Figure out how dependencies on certain component types can be
  //  determined, so we know which systems depend on eachother and which
  //  systems can be processed in parallel
  //  Possibly the only solution is to manually define the dependencies, we
  //  don't even know what methods may be used outside of the system class.

  /**
   * Is called when an entity was added to the engine or components were
   * added so the entity is now valid to be processed using this system.
   */
  private fun added(entity: Entity) {
  }

  /**
   * Is called when an entity was deleted from the engine or components were
   * removed so the entity is no longer valid to be processed using this system.
   */
  private fun deleted(entity: Entity) {
  }

  /**
   * Allowed `onProcess` parameters:
   * - Duration ~ deltaTime
   * - Entity ~ the target entity
   * - Subclasses of Component ~ components that are needed for the process, can
   * be nullable if they are considered optional
   */

  fun process(deltaTime: Duration, entity: Entity) {
  /*
    val test = entity.get(Health)
    test.health += 100.0
    entity.modify {
      get(Health).modify {

      }
      // OR
      modify(Health) {
        health -= 0.1
      }
    }
    // Process
    */
  }

  fun bulkProcess(deltaTime: Duration, entities: EntityCollection) {
    // Process all the applicable entities in bulk, instead of calling a
    // single process function for every entity
    for (entity in entities) {
      // Do things
      entity.modify {
        get(Health).modify {
          health -= 2.0
        }
      }
    }
    // OR better
    /*
    entities.modifyAll {
      modify(Health) {
        health -= 2.0
      }
    }*/
  }

  fun process(deltaTime: Duration, component: Health) {
    // Process with required components
  }

  fun process(deltaTime: Duration, entity: Entity, component: Health) {
    // Process with required components
  }

  fun process() {
    // Process ignoring delta time
  }

  override suspend fun org.lanternpowered.shards.system.SystemContext.execute() {
    TODO("Not yet implemented")
  }
}

// Exploring an alternative system that's not dependent on annotations
class AlternativeSystem : System() {

  private val withHealth = entityQuery()
    .with(Health)

  fun process(entities: EntitySequence) {
    entities
      .execute(withHealth)
      .forEach { healthComponent ->
        healthComponent.health += 1.0
      }
  }

  // OR

  fun SystemContext.execute() {
    // Execute is only called once, to setup the system context, after that
    // only `process` is called

    // Using a EntityQuery allows a system to automatically track
    // dependencies that are being used on combination with `readOnly` and
    // `readWrite`

    // Other dependencies that aren't defined in a query, a processed entity
    // isn't required to contain the component type, but the system depends
    // on it so other systems will not simultaneously try to access them
    dependsOn(Food) // By default read-write here
    //dependsOn(readOnly(Food)) // Read-only dependency

    val withHealth by entityQuery()
      // .with(Health, readOnly<Name>())
      //.with(Health, readOnly(Name))
      // `readWrite` is necessary here if you want to modify/read a
      // FoodComponent and if you didn't specify them in a normal `with`
      .filterWith(Food) // By default no dependency here
      //.filterWith(readWrite(Food)) // Read-write dependency
      // Only entities that don't have the specified component will be processed
      .filterWithout(Excluded)

    val withHealthWithoutFood by entityQuery()
      .filterWith(Health)
      .filterWithout(Food)

    // Process will be called for every system update
    process {
      var counter = 0

      /*
      withHealth
        .filter { entity, health, name -> true }
        .forEach { entity, health, name ->
          health.health += 10.0
          if (name.name.isBlank()) {
            health.health += 2.0
          } else if (name.name == "WantsFood") {
            entity.modify(Food) {
              food += 100.0
            }
            counter++
          }
        }

      withHealth
        .drop(Name) // Drop the NameComponent from the sequence
        .filter { health -> health.health <= 2.0 }
        .forEach { health ->
          health.lastAttacker = EntityReference.Empty
        }*/

      println("$counter entities wanted food.")

      // Also possible, but less optimal
      for (entity in withHealthWithoutFood) {
        entity.modify(Health) {
          health -= 1.0
        }
      }
    }
  }

  override suspend fun org.lanternpowered.shards.system.SystemContext.execute() {
    TODO("Not yet implemented")
  }
}

@Target(
  AnnotationTarget.TYPE,
  AnnotationTarget.FUNCTION,
  AnnotationTarget.CLASS
)
@Retention(AnnotationRetention.BINARY)
@DslMarker
annotation class SystemDsl

@SystemDsl
interface SystemContext {

  fun dependsOn(type: ComponentType<*>)

  fun entityQuery(): EntityQuery.With0

  operator fun <T : EntityQuery> T.provideDelegate(
    target: Any?, prop: KProperty<*>
  ): ReadOnlyProperty<Any?, T>

  fun EntityQuery.filterWith(type: ComponentType<*>): EntityQuery
  fun EntityQuery.filterWithout(type: ComponentType<*>): EntityQuery

  fun EntityQuery.With0.filterWith(type: ComponentType<*>): EntityQuery.With0
  fun EntityQuery.With0.filterWithout(type: ComponentType<*>): EntityQuery.With0

  fun <T1> EntityQuery.With1<T1>.filterWith(type: ComponentType<*>): EntityQuery.With1<T1>
  fun <T1> EntityQuery.With1<T1>.filterWithout(type: ComponentType<*>): EntityQuery.With1<T1>

  fun <T1, T2> EntityQuery.With2<T1, T2>.filterWith(type: ComponentType<*>): EntityQuery.With2<T1, T2>
  fun <T1, T2> EntityQuery.With2<T1, T2>.filterWithout(type: ComponentType<*>): EntityQuery.With2<T1, T2>

  fun process(fn: SystemProcessContext.() -> Unit)
}

@SystemDsl
abstract class SystemProcessContext {

  abstract fun <R> async(fn: () -> R): Deferred<R>

  abstract operator fun EntityQuery.iterator(): EntityIterator

  abstract fun EntityQuery.With0.filter(predicate: (Entity) -> Boolean):
    EntitySequence

  fun <T1 : Component> EntityQuery.With1<T1>.drop(
    type: ComponentType<T1>
  ): EntitySequence {
    TODO()
  }

  @JvmName("drop2")
  fun <T1 : Component, T2> EntityQuery.With2<T1, T2>.drop(
    type: ComponentType<T1>
  ): EntitySeq1<T2> {
    TODO()
  }

  @JvmName("drop1")
  fun <T1, T2 : Component> EntityQuery.With2<T1, T2>.drop(
    type: ComponentType<T2>
  ): EntitySeq1<T1> {
    TODO()
  }

  abstract fun <T1> EntitySeq1<T1>.filter(predicate: (T1) -> Boolean): EntitySeq1<T1>
  abstract fun <T1> EntitySeq1<T1>.filter(predicate: (Entity, T1) -> Boolean): EntitySeq1<T1>

  abstract fun <T1> EntityQuery.With1<T1>.filter(predicate: (T1) -> Boolean): EntitySeq1<T1>
  abstract fun <T1> EntityQuery.With1<T1>.filter(predicate: (Entity, T1) -> Boolean): EntitySeq1<T1>

  abstract fun <T1, R> EntityQuery.With1<T1>.map(transform: (T1) -> R): Sequence<R>
  abstract fun <T1, R> EntityQuery.With1<T1>.map(transform: (Entity, T1) -> R): Sequence<R>

  abstract fun <T1, T2> EntityQuery.With2<T1, T2>.filter(
    predicate: (T1, T2) -> Boolean
  ): EntitySeq2<T1, T2>

  abstract fun <T1, T2> EntityQuery.With2<T1, T2>.filter(
    predicate: (Entity, T1, T2) -> Boolean
  ): EntitySeq2<T1, T2>

  abstract fun EntityQuery.map(predicate: (Entity) -> Boolean): EntitySequence

  abstract fun EntityQuery.With0.sequence(): EntitySequence
  abstract fun EntityQuery.With0.forEach(fn: (Entity) -> Unit)

  abstract fun <T1> EntityQuery.With1<T1>.forEach(fn: (Entity, T1) -> Unit)

  abstract fun <T1, T2> EntityQuery.With2<T1, T2>.forEach(
    fn: (Entity, T1, T2) -> Unit
  )
}

fun System.entityQuery(): EntityQuery.With0 {
  TODO()
}

fun EntitySequence.execute(query: EntityQuery.With0): EntitySequence {
  TODO()
}

fun <T1> EntitySequence.execute(query: EntityQuery.With1<T1>): EntitySeq1<T1> {
  TODO()
}

@SystemDsl
interface EntityQuery {

  interface With0 : EntityQuery {

    fun <T1 : Component> with(type1: ComponentType<T1>): With1<T1>

    fun <T1 : Component, T2 : Component> with(
      type1: ComponentType<T1>,
      type2: ComponentType<T2>
    ): With2<T1, T2>
  }

  interface With1<T1> : EntityQuery {

    fun <T2 : Component> with(type: ComponentType<T2>): With2<T1, T2>
  }

  interface With2<T1, T2> : EntityQuery {
  }
}

interface EntitySeq1<T1> {

  fun forEach(fn: (T1) -> Unit)
  fun forEach(fn: (Entity, T1) -> Unit)
}

interface EntitySeq2<T1, T2> {

  fun forEach(fn: (T1, T2) -> Unit)
  fun forEach(fn: (Entity, T1, T2) -> Unit)
}

@Target(AnnotationTarget.TYPE)
@Retention(AnnotationRetention.RUNTIME)
annotation class ReadOnly
