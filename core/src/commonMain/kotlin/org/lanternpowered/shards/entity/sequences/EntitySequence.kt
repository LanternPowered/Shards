/*
 * Shards
 *
 * Copyright (c) LanternPowered <https://www.lanternpowered.org>
 * Copyright (c) contributors
 *
 * This work is licensed under the terms of the MIT License (MIT). For
 * a copy, see 'LICENSE.txt' or <https://opensource.org/licenses/MIT>.
 */
package org.lanternpowered.shards.entity.sequences

import org.lanternpowered.shards.component.Component
import org.lanternpowered.shards.component.ComponentType
import org.lanternpowered.shards.entity.Entity
import org.lanternpowered.shards.entity.collections.EntityIterable
import org.lanternpowered.shards.entity.collections.EntityIterator
import kotlin.jvm.JvmName

/**
 * Constructs an [EntitySequence] from the given [iterator] supplier.
 */
fun EntitySequence(iterator: () -> EntityIterator): EntitySequence =
  EntitySequenceImpl(QueryableEntityIterableContext(iterator))

/**
 * Constructs an [EntitySequence] from the given [iterable].
 */
fun EntitySequence(iterable: EntityIterable): EntitySequence =
  EntitySequence { iterable.iterator() }

/**
 * Represents the base class for all the entity sequence types.
 */
abstract class EntitySequenceBase<S, C> internal constructor()
  where S : EntitySequenceBase<S, C>,
        C : EntitySequenceContext5<*, *, *, *, *, *, *, *, *, *> {

  /**
   * Returns a sequence containing the results of applying the given transform
   * [operation] to each entity in the original sequence.
   */
  abstract fun <R> map(operation: C.(Entity) -> R): Sequence<R>

  /**
   * Returns a sequence containing only entities matching the given [predicate].
   */
  abstract fun filter(predicate: C.(Entity) -> Boolean): S

  /**
   * Returns a sequence containing only entities which do *not* contain the
   * given component type.
   */
  abstract fun without(type: ComponentType<*>): S

  /**
   * Returns a sequence containing only entities which do *not* contain the
   * given component types.
   */
  fun without(
    firstType: ComponentType<*>,
    vararg moreTypes: ComponentType<*>,
  ): S = moreTypes.fold(without(firstType)) { seq, type -> seq.without(type) }

  /**
   * Performs the given [operation] on each [Entity]s.
   */
  abstract fun forEach(operation: C.(Entity) -> Unit)

  /**
   * Returns the first [Entity].
   */
  abstract fun first(): Entity

  /**
   * Returns the first [Entity] that matches the given [predicate].
   */
  fun first(predicate: C.(Entity) -> Boolean): Entity =
    filter(predicate).first()

  /**
   * Returns the first [Entity], or null if the sequence is empty.
   */
  abstract fun firstOrNull(): Entity?

  /**
   * Returns the first [Entity] that matches the given [predicate], or null if
   * the sequence is empty or there were no matches.
   */
  fun firstOrNull(predicate: C.(Entity) -> Boolean): Entity? =
    filter(predicate).firstOrNull()

  /**
   * Returns the last [Entity].
   */
  abstract fun last(): Entity

  /**
   * Returns the last [Entity] that matches the given [predicate].
   */
  fun last(predicate: C.(Entity) -> Boolean): Entity =
    filter(predicate).last()

  /**
   * Returns the last [Entity], or null if the sequence is empty.
   */
  abstract fun lastOrNull(): Entity?

  /**
   * Returns the last [Entity] that matches the given [predicate], or null if
   * the sequence is empty or there were no matches.
   */
  fun lastOrNull(predicate: C.(Entity) -> Boolean): Entity? =
    filter(predicate).lastOrNull()
}

abstract class EntitySequence : EntitySequenceBase<EntitySequence, EntitySequenceContext0>() {

  abstract fun <R1 : Component> reads(
    type1: ComponentType<R1>
  ): EntitySequence1<R1, Nothing>

  fun <
    R1 : Component,
    R2 : Component,
  > reads(
    type1: ComponentType<R1>,
    type2: ComponentType<R2>,
  ): EntitySequence2<R1, Nothing, R2, Nothing> =
    reads(type1).reads(type2)

  fun <
    R1 : Component,
    R2 : Component,
    R3 : Component,
  > reads(
    type1: ComponentType<R1>,
    type2: ComponentType<R2>,
    type3: ComponentType<R3>,
  ): EntitySequence3<R1, Nothing, R2, Nothing, R3, Nothing> =
    reads(type1).reads(type2).reads(type3)

  abstract fun <W1 : Component> modifies(
    type1: ComponentType<W1>
  ): EntitySequence1<W1, W1>

  fun <
    W1 : Component,
    W2 : Component,
  > modifies(
    type1: ComponentType<W1>,
    type2: ComponentType<W2>,
  ): EntitySequence2<W1, W1, W2, W2> =
    modifies(type1).modifies(type2)

  fun <
    W1 : Component,
    W2 : Component,
    W3 : Component,
  > modifies(
    type1: ComponentType<W1>,
    type2: ComponentType<W2>,
    type3: ComponentType<W3>,
  ): EntitySequence3<W1, W1, W2, W2, W3, W3> =
    modifies(type1).modifies(type2).modifies(type3)
}

abstract class EntitySequence1<R1, W1> :
  EntitySequenceBase<
    EntitySequence1<R1, W1>,
    EntitySequenceContext1<R1, W1>>()
  where R1 : Component,
        W1 : Component
{

  abstract fun <R2 : Component> reads(
    type2: ComponentType<R2>
  ): EntitySequence2<R1, W1, R2, Nothing>

  fun <
    R2 : Component,
    R3 : Component,
  > reads(
    type2: ComponentType<R2>,
    type3: ComponentType<R3>
  ): EntitySequence3<R1, W1, R2, Nothing, R3, Nothing> =
    reads(type2).reads(type3)

  abstract fun <W2 : Component> modifies(
    type2: ComponentType<W2>
  ): EntitySequence2<R1, W1, W2, W2>

  fun <
    W2 : Component,
    W3 : Component,
  > modifies(
    type2: ComponentType<W2>,
    type3: ComponentType<W3>
  ): EntitySequence3<R1, W1, W2, W2, W3, W3> =
    modifies(type2).modifies(type3)

  @PublishedApi
  internal abstract fun with1(
    type1: ComponentType<R1>
  ): EntitySequence1<R1, W1>

  @JvmName("with_1")
  fun with(
    type1: ComponentType<R1>
  ): EntitySequence1<R1, W1> =
    with1(type1)

  @PublishedApi
  internal abstract fun withFiltered1(
    type1: ComponentType<W1>,
    predicate: (R1) -> Boolean
  ): EntitySequence1<R1, W1>

  @JvmName("withFiltered_1")
  fun withFiltered(
    type1: ComponentType<W1>,
    predicate: (R1) -> Boolean
  ): EntitySequence1<R1, W1> =
    withFiltered1(type1, predicate)

  inline fun forEachWith(
    type1: ComponentType<R1>,
    crossinline operation: EntitySequenceContext1<R1, W1>.(Entity, R1) -> Unit
  ) = with1(type1)
    .forEach { entity ->
      operation(entity, entity.get1())
    }
}

abstract class EntitySequence2<R1, W1, R2, W2> :
  EntitySequenceBase<
    EntitySequence2<R1, W1, R2, W2>,
    EntitySequenceContext2<R1, W1, R2, W2>>()
  where R1 : Component,
        W1 : Component,
        R2 : Component,
        W2 : Component
{

  abstract fun <R3 : Component> reads(
    type3: ComponentType<R3>
  ): EntitySequence3<R1, W1, R2, W2, R3, Nothing>

  abstract fun <W3 : Component> modifies(
    type3: ComponentType<W3>
  ): EntitySequence3<R1, W1, R2, W2, W3, W3>

  @PublishedApi
  internal abstract fun with1(
    type1: ComponentType<R1>
  ): EntitySequence2<R1, W1, R2, W2>

  @PublishedApi
  internal abstract fun with2(
    type2: ComponentType<R2>
  ): EntitySequence2<R1, W1, R2, W2>

  @JvmName("with_1")
  fun with(
    type1: ComponentType<R1>,
  ): EntitySequence2<R1, W1, R2, W2> =
    with1(type1)

  @JvmName("with_2")
  fun with(
    type2: ComponentType<R2>,
  ): EntitySequence2<R1, W1, R2, W2> =
    with2(type2)

  @JvmName("with_1_2")
  fun with(
    type1: ComponentType<R1>,
    type2: ComponentType<R2>,
  ): EntitySequence2<R1, W1, R2, W2> =
    with1(type1).with2(type2)

  @JvmName("with_2_1")
  fun with(
    type2: ComponentType<R2>,
    type1: ComponentType<R1>,
  ): EntitySequence2<R1, W1, R2, W2> =
    with1(type1).with2(type2)

  @PublishedApi
  internal abstract fun withFiltered1(
    type1: ComponentType<R1>,
    predicate: (R1) -> Boolean
  ): EntitySequence2<R1, W1, R2, W2>

  @PublishedApi
  internal abstract fun withFiltered2(
    type2: ComponentType<R2>,
    predicate: (R2) -> Boolean
  ): EntitySequence2<R1, W1, R2, W2>

  @JvmName("withFiltered_1")
  fun withFiltered(
    type1: ComponentType<R1>,
    predicate: (R1) -> Boolean
  ): EntitySequence2<R1, W1, R2, W2> = withFiltered1(type1, predicate)

  @JvmName("withFiltered_2")
  fun withFiltered(
    type2: ComponentType<R2>,
    predicate: (R2) -> Boolean
  ): EntitySequence2<R1, W1, R2, W2> = withFiltered2(type2, predicate)

  @JvmName("forEach_1")
  inline fun forEachWith(
    type1: ComponentType<R1>,
    crossinline operation: EntitySequenceContext2<R1, W1, R2, W2>.(Entity, R1) -> Unit
  ) = with1(type1)
    .forEach { entity ->
      operation(entity, entity.get1())
    }

  @JvmName("forEach_2")
  inline fun forEachWith(
    type2: ComponentType<R2>,
    crossinline operation: EntitySequenceContext2<R1, W1, R2, W2>.(Entity, R2) -> Unit
  ) = with2(type2)
    .forEach { entity ->
      operation(entity, entity.get2())
    }

  @JvmName("forEach_1_2")
  inline fun forEachWith(
    type1: ComponentType<R1>,
    type2: ComponentType<R2>,
    crossinline operation: EntitySequenceContext2<R1, W1, R2, W2>.(Entity, R1, R2) -> Unit
  ) = with1(type1)
    .with2(type2)
    .forEach { entity ->
      operation(entity,
        entity.get1(),
        entity.get2())
    }

  @JvmName("forEach_2_1")
  inline fun forEachWith(
    type2: ComponentType<R2>,
    type1: ComponentType<R1>,
    crossinline operation: EntitySequenceContext2<R1, W1, R2, W2>.(Entity, R2, R1) -> Unit
  ) = with1(type1)
    .with2(type2)
    .forEach { entity ->
      operation(entity,
        entity.get2(),
        entity.get1())
    }
}

abstract class EntitySequence3<R1, W1, R2, W2, R3, W3> :
  EntitySequenceBase<
    EntitySequence3<R1, W1, R2, W2, R3, W3>,
    EntitySequenceContext3<R1, W1, R2, W2, R3, W3>>()
  where R1 : Component,
        W1 : Component,
        R2 : Component,
        W2 : Component,
        R3 : Component,
        W3 : Component
{

  @PublishedApi
  internal abstract fun with1(
    type1: ComponentType<R1>
  ): EntitySequence3<R1, W1, R2, W2, R3, W3>

  @PublishedApi
  internal abstract fun with2(
    type2: ComponentType<R2>
  ): EntitySequence3<R1, W1, R2, W2, R3, W3>

  @PublishedApi
  internal abstract fun with3(
    type3: ComponentType<R3>
  ): EntitySequence3<R1, W1, R2, W2, R3, W3>

  @JvmName("with_1")
  fun with(
    type1: ComponentType<R1>,
  ): EntitySequence3<R1, W1, R2, W2, R3, W3> =
    with1(type1)

  @JvmName("with_2")
  fun with(
    type2: ComponentType<R2>,
  ): EntitySequence3<R1, W1, R2, W2, R3, W3> =
    with2(type2)

  @JvmName("with_3")
  fun with(
    type2: ComponentType<R3>,
  ): EntitySequence3<R1, W1, R2, W2, R3, W3> =
    with3(type2)

  @JvmName("with_1_2")
  fun with(
    type1: ComponentType<R1>,
    type2: ComponentType<R2>,
  ): EntitySequence3<R1, W1, R2, W2, R3, W3> =
    with1(type1).with2(type2)

  @JvmName("with_2_1")
  fun with(
    type2: ComponentType<R2>,
    type1: ComponentType<R1>,
  ): EntitySequence3<R1, W1, R2, W2, R3, W3> =
    with1(type1).with2(type2)

  @JvmName("with_1_3")
  fun with(
    type1: ComponentType<R1>,
    type3: ComponentType<R3>,
  ): EntitySequence3<R1, W1, R2, W2, R3, W3> =
    with1(type1).with3(type3)

  @JvmName("with_3_1")
  fun with(
    type3: ComponentType<R3>,
    type1: ComponentType<R1>,
  ): EntitySequence3<R1, W1, R2, W2, R3, W3> =
    with1(type1).with3(type3)

  @JvmName("with_2_3")
  fun with(
    type2: ComponentType<R2>,
    type3: ComponentType<R3>,
  ): EntitySequence3<R1, W1, R2, W2, R3, W3> =
    with2(type2).with3(type3)

  @JvmName("with_3_2")
  fun with(
    type3: ComponentType<R3>,
    type2: ComponentType<R2>,
  ): EntitySequence3<R1, W1, R2, W2, R3, W3> =
    with2(type2).with3(type3)

  @JvmName("with_1_2_3")
  fun with(
    type1: ComponentType<R1>,
    type2: ComponentType<R2>,
    type3: ComponentType<R3>,
  ): EntitySequence3<R1, W1, R2, W2, R3, W3> =
    with1(type1).with2(type2).with3(type3)

  @JvmName("with_1_3_2")
  fun with(
    type1: ComponentType<R1>,
    type3: ComponentType<R3>,
    type2: ComponentType<R2>,
  ): EntitySequence3<R1, W1, R2, W2, R3, W3> =
    with1(type1).with2(type2).with3(type3)

  @JvmName("with_3_2_1")
  fun with(
    type3: ComponentType<R3>,
    type2: ComponentType<R2>,
    type1: ComponentType<R1>,
  ): EntitySequence3<R1, W1, R2, W2, R3, W3> =
    with1(type1).with2(type2).with3(type3)

  @JvmName("with_3_1_2")
  fun with(
    type3: ComponentType<R3>,
    type1: ComponentType<R1>,
    type2: ComponentType<R2>,
  ): EntitySequence3<R1, W1, R2, W2, R3, W3> =
    with1(type1).with2(type2).with3(type3)

  @JvmName("with_2_3_1")
  fun with(
    type2: ComponentType<R2>,
    type3: ComponentType<R3>,
    type1: ComponentType<R1>,
  ): EntitySequence3<R1, W1, R2, W2, R3, W3> =
    with1(type1).with2(type2).with3(type3)

  @JvmName("with_2_1_3")
  fun with(
    type2: ComponentType<R2>,
    type1: ComponentType<R1>,
    type3: ComponentType<R3>,
  ): EntitySequence3<R1, W1, R2, W2, R3, W3> =
    with1(type1).with2(type2).with3(type3)

  @PublishedApi
  internal abstract fun withFiltered1(
    type1: ComponentType<R1>,
    predicate: (R1) -> Boolean
  ): EntitySequence3<R1, W1, R2, W2, R3, W3>

  @PublishedApi
  internal abstract fun withFiltered2(
    type2: ComponentType<R2>,
    predicate: (R2) -> Boolean
  ): EntitySequence3<R1, W1, R2, W2, R3, W3>

  @PublishedApi
  internal abstract fun withFiltered3(
    type3: ComponentType<R3>,
    predicate: (R3) -> Boolean
  ): EntitySequence3<R1, W1, R2, W2, R3, W3>

  @JvmName("withFiltered_1")
  fun withFiltered(
    type1: ComponentType<R1>,
    predicate: (R1) -> Boolean
  ): EntitySequence3<R1, W1, R2, W2, R3, W3> = withFiltered1(type1, predicate)

  @JvmName("withFiltered_2")
  fun withFiltered(
    type2: ComponentType<R2>,
    predicate: (R2) -> Boolean
  ): EntitySequence3<R1, W1, R2, W2, R3, W3> = withFiltered2(type2, predicate)

  @JvmName("withFiltered_3")
  fun withFiltered(
    type3: ComponentType<R3>,
    predicate: (R3) -> Boolean
  ): EntitySequence3<R1, W1, R2, W2, R3, W3> = withFiltered3(type3, predicate)
}
