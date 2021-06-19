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
import org.lanternpowered.shards.entity.collections.EntityList
import kotlin.jvm.JvmName

/**
 * Constructs an [EntitySeq0] from the given [iterator] supplier.
 */
fun EntitySequence(iterator: () -> EntityIterator): EntitySeq0 =
  EntitySeq0Impl(QueryableEntityIterableContext(iterator))

/**
 * Constructs an [EntitySeq0] from the given [iterable].
 */
fun EntitySequence(iterable: EntityIterable): EntitySeq0 =
  EntitySequence { iterable.iterator() }

abstract class EntitySeq<S, C>
  where S : EntitySeq<S, C>,
        C : EntitySeqContext5<*, *, *, *, *, *, *, *, *, *> {

  abstract fun <R> map(operation: C.(Entity) -> R): Sequence<R>

  abstract fun filter(predicate: C.(Entity) -> Boolean): S

  abstract fun without(type: ComponentType<*>): S

  fun without(
    firstType: ComponentType<*>,
    vararg moreTypes: ComponentType<*>,
  ): S = without(firstType)
    .let { moreTypes.fold(it) { seq, type -> seq.without(type) } }

  abstract fun forEach(operation: C.(Entity) -> Unit)

  abstract fun forAll(operation: C.(entities: EntityList) -> Unit)

  abstract fun first(): Entity

  fun first(predicate: C.(Entity) -> Boolean): Entity =
    filter(predicate).first()

  abstract fun firstOrNull(): Entity?

  fun firstOrNull(predicate: C.(Entity) -> Boolean): Entity? =
    filter(predicate).firstOrNull()

  abstract fun last(): Entity

  fun last(predicate: C.(Entity) -> Boolean): Entity =
    filter(predicate).last()

  abstract fun lastOrNull(): Entity?

  fun lastOrNull(predicate: C.(Entity) -> Boolean): Entity? =
    filter(predicate).lastOrNull()
}

abstract class EntitySeq0 : EntitySeq<EntitySeq0, EntitySeqContext0>() {

  abstract fun <R1 : Component> reads(
    type1: ComponentType<R1>
  ): EntitySeq1<R1, Nothing>

  fun <
    R1 : Component,
    R2 : Component,
  > reads(
    type1: ComponentType<R1>,
    type2: ComponentType<R2>,
  ): EntitySeq2<R1, Nothing, R2, Nothing> =
    reads(type1).reads(type2)

  fun <
    R1 : Component,
    R2 : Component,
    R3 : Component,
  > reads(
    type1: ComponentType<R1>,
    type2: ComponentType<R2>,
    type3: ComponentType<R3>,
  ): EntitySeq3<R1, Nothing, R2, Nothing, R3, Nothing> =
    reads(type1).reads(type2).reads(type3)

  abstract fun <W1 : Component> modifies(
    type1: ComponentType<W1>
  ): EntitySeq1<W1, W1>

  fun <
    W1 : Component,
    W2 : Component,
  > modifies(
    type1: ComponentType<W1>,
    type2: ComponentType<W2>,
  ): EntitySeq2<W1, W1, W2, W2> =
    modifies(type1).modifies(type2)

  fun <
    W1 : Component,
    W2 : Component,
    W3 : Component,
  > modifies(
    type1: ComponentType<W1>,
    type2: ComponentType<W2>,
    type3: ComponentType<W3>,
  ): EntitySeq3<W1, W1, W2, W2, W3, W3> =
    modifies(type1).modifies(type2).modifies(type3)
}

abstract class EntitySeq1<R1, W1> :
  EntitySeq<
    EntitySeq1<R1, W1>,
    EntitySeqContext1<R1, W1>>()
  where R1 : Component,
        W1 : Component
{

  abstract fun <R2 : Component> reads(
    type2: ComponentType<R2>
  ): EntitySeq2<R1, W1, R2, Nothing>

  fun <
    R2 : Component,
    R3 : Component,
  > reads(
    type2: ComponentType<R2>,
    type3: ComponentType<R3>
  ): EntitySeq3<R1, W1, R2, Nothing, R3, Nothing> =
    reads(type2).reads(type3)

  abstract fun <W2 : Component> modifies(
    type2: ComponentType<W2>
  ): EntitySeq2<R1, W1, W2, W2>

  fun <
    W2 : Component,
    W3 : Component,
  > modifies(
    type2: ComponentType<W2>,
    type3: ComponentType<W3>
  ): EntitySeq3<R1, W1, W2, W2, W3, W3> =
    modifies(type2).modifies(type3)

  @PublishedApi
  internal abstract fun with1(
    type1: ComponentType<R1>
  ): EntitySeq1<R1, W1>

  @JvmName("with_1")
  fun with(
    type1: ComponentType<R1>
  ): EntitySeq1<R1, W1> =
    with1(type1)

  @PublishedApi
  internal abstract fun withFiltered1(
    type1: ComponentType<W1>,
    predicate: (R1) -> Boolean
  ): EntitySeq1<R1, W1>

  @JvmName("withFiltered_1")
  fun withFiltered(
    type1: ComponentType<W1>,
    predicate: (R1) -> Boolean
  ): EntitySeq1<R1, W1> =
    withFiltered1(type1, predicate)

  inline fun forEachWith(
    type1: ComponentType<R1>,
    crossinline operation: EntitySeqContext1<R1, W1>.(Entity, R1) -> Unit
  ) = with1(type1)
    .forEach { entity ->
      operation(entity, entity.get1())
    }
}

abstract class EntitySeq2<R1, W1, R2, W2> :
  EntitySeq<
    EntitySeq2<R1, W1, R2, W2>,
    EntitySeqContext2<R1, W1, R2, W2>>()
  where R1 : Component,
        W1 : Component,
        R2 : Component,
        W2 : Component
{

  abstract fun <R3 : Component> reads(
    type3: ComponentType<R3>
  ): EntitySeq3<R1, W1, R2, W2, R3, Nothing>

  abstract fun <W3 : Component> modifies(
    type3: ComponentType<W3>
  ): EntitySeq3<R1, W1, R2, W2, W3, W3>

  @PublishedApi
  internal abstract fun with1(
    type1: ComponentType<R1>
  ): EntitySeq2<R1, W1, R2, W2>

  @PublishedApi
  internal abstract fun with2(
    type2: ComponentType<R2>
  ): EntitySeq2<R1, W1, R2, W2>

  @JvmName("with_1")
  fun with(
    type1: ComponentType<R1>,
  ): EntitySeq2<R1, W1, R2, W2> =
    with1(type1)

  @JvmName("with_2")
  fun with(
    type2: ComponentType<R2>,
  ): EntitySeq2<R1, W1, R2, W2> =
    with2(type2)

  @JvmName("with_1_2")
  fun with(
    type1: ComponentType<R1>,
    type2: ComponentType<R2>,
  ): EntitySeq2<R1, W1, R2, W2> =
    with1(type1).with2(type2)

  @JvmName("with_2_1")
  fun with(
    type2: ComponentType<R2>,
    type1: ComponentType<R1>,
  ): EntitySeq2<R1, W1, R2, W2> =
    with1(type1).with2(type2)

  @PublishedApi
  internal abstract fun withFiltered1(
    type1: ComponentType<R1>,
    predicate: (R1) -> Boolean
  ): EntitySeq2<R1, W1, R2, W2>

  @PublishedApi
  internal abstract fun withFiltered2(
    type2: ComponentType<R2>,
    predicate: (R2) -> Boolean
  ): EntitySeq2<R1, W1, R2, W2>

  @JvmName("withFiltered_1")
  fun withFiltered(
    type1: ComponentType<R1>,
    predicate: (R1) -> Boolean
  ): EntitySeq2<R1, W1, R2, W2> = withFiltered1(type1, predicate)

  @JvmName("withFiltered_2")
  fun withFiltered(
    type2: ComponentType<R2>,
    predicate: (R2) -> Boolean
  ): EntitySeq2<R1, W1, R2, W2> = withFiltered2(type2, predicate)

  @JvmName("forEach_1")
  inline fun forEachWith(
    type1: ComponentType<R1>,
    crossinline operation: EntitySeqContext2<R1, W1, R2, W2>.(Entity, R1) -> Unit
  ) = with1(type1)
    .forEach { entity ->
      operation(entity, entity.get1())
    }

  @JvmName("forEach_2")
  inline fun forEachWith(
    type2: ComponentType<R2>,
    crossinline operation: EntitySeqContext2<R1, W1, R2, W2>.(Entity, R2) -> Unit
  ) = with2(type2)
    .forEach { entity ->
      operation(entity, entity.get2())
    }

  @JvmName("forEach_1_2")
  inline fun forEachWith(
    type1: ComponentType<R1>,
    type2: ComponentType<R2>,
    crossinline operation: EntitySeqContext2<R1, W1, R2, W2>.(Entity, R1, R2) -> Unit
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
    crossinline operation: EntitySeqContext2<R1, W1, R2, W2>.(Entity, R2, R1) -> Unit
  ) = with1(type1)
    .with2(type2)
    .forEach { entity ->
      operation(entity,
        entity.get2(),
        entity.get1())
    }
}

abstract class EntitySeq3<R1, W1, R2, W2, R3, W3> :
  EntitySeq<
    EntitySeq3<R1, W1, R2, W2, R3, W3>,
    EntitySeqContext3<R1, W1, R2, W2, R3, W3>>()
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
  ): EntitySeq3<R1, W1, R2, W2, R3, W3>

  @PublishedApi
  internal abstract fun with2(
    type2: ComponentType<R2>
  ): EntitySeq3<R1, W1, R2, W2, R3, W3>

  @PublishedApi
  internal abstract fun with3(
    type3: ComponentType<R3>
  ): EntitySeq3<R1, W1, R2, W2, R3, W3>

  @JvmName("with_1")
  fun with(
    type1: ComponentType<R1>,
  ): EntitySeq3<R1, W1, R2, W2, R3, W3> =
    with1(type1)

  @JvmName("with_2")
  fun with(
    type2: ComponentType<R2>,
  ): EntitySeq3<R1, W1, R2, W2, R3, W3> =
    with2(type2)

  @JvmName("with_3")
  fun with(
    type2: ComponentType<R3>,
  ): EntitySeq3<R1, W1, R2, W2, R3, W3> =
    with3(type2)

  @JvmName("with_1_2")
  fun with(
    type1: ComponentType<R1>,
    type2: ComponentType<R2>,
  ): EntitySeq3<R1, W1, R2, W2, R3, W3> =
    with1(type1).with2(type2)

  @JvmName("with_2_1")
  fun with(
    type2: ComponentType<R2>,
    type1: ComponentType<R1>,
  ): EntitySeq3<R1, W1, R2, W2, R3, W3> =
    with1(type1).with2(type2)

  @JvmName("with_1_3")
  fun with(
    type1: ComponentType<R1>,
    type3: ComponentType<R3>,
  ): EntitySeq3<R1, W1, R2, W2, R3, W3> =
    with1(type1).with3(type3)

  @JvmName("with_3_1")
  fun with(
    type3: ComponentType<R3>,
    type1: ComponentType<R1>,
  ): EntitySeq3<R1, W1, R2, W2, R3, W3> =
    with1(type1).with3(type3)

  @JvmName("with_2_3")
  fun with(
    type2: ComponentType<R2>,
    type3: ComponentType<R3>,
  ): EntitySeq3<R1, W1, R2, W2, R3, W3> =
    with2(type2).with3(type3)

  @JvmName("with_3_2")
  fun with(
    type3: ComponentType<R3>,
    type2: ComponentType<R2>,
  ): EntitySeq3<R1, W1, R2, W2, R3, W3> =
    with2(type2).with3(type3)

  @JvmName("with_1_2_3")
  fun with(
    type1: ComponentType<R1>,
    type2: ComponentType<R2>,
    type3: ComponentType<R3>,
  ): EntitySeq3<R1, W1, R2, W2, R3, W3> =
    with1(type1).with2(type2).with3(type3)

  @JvmName("with_1_3_2")
  fun with(
    type1: ComponentType<R1>,
    type3: ComponentType<R3>,
    type2: ComponentType<R2>,
  ): EntitySeq3<R1, W1, R2, W2, R3, W3> =
    with1(type1).with2(type2).with3(type3)

  @JvmName("with_3_2_1")
  fun with(
    type3: ComponentType<R3>,
    type2: ComponentType<R2>,
    type1: ComponentType<R1>,
  ): EntitySeq3<R1, W1, R2, W2, R3, W3> =
    with1(type1).with2(type2).with3(type3)

  @JvmName("with_3_1_2")
  fun with(
    type3: ComponentType<R3>,
    type1: ComponentType<R1>,
    type2: ComponentType<R2>,
  ): EntitySeq3<R1, W1, R2, W2, R3, W3> =
    with1(type1).with2(type2).with3(type3)

  @JvmName("with_2_3_1")
  fun with(
    type2: ComponentType<R2>,
    type3: ComponentType<R3>,
    type1: ComponentType<R1>,
  ): EntitySeq3<R1, W1, R2, W2, R3, W3> =
    with1(type1).with2(type2).with3(type3)

  @JvmName("with_2_1_3")
  fun with(
    type2: ComponentType<R2>,
    type1: ComponentType<R1>,
    type3: ComponentType<R3>,
  ): EntitySeq3<R1, W1, R2, W2, R3, W3> =
    with1(type1).with2(type2).with3(type3)

  @PublishedApi
  internal abstract fun withFiltered1(
    type1: ComponentType<R1>,
    predicate: (R1) -> Boolean
  ): EntitySeq3<R1, W1, R2, W2, R3, W3>

  @PublishedApi
  internal abstract fun withFiltered2(
    type2: ComponentType<R2>,
    predicate: (R2) -> Boolean
  ): EntitySeq3<R1, W1, R2, W2, R3, W3>

  @PublishedApi
  internal abstract fun withFiltered3(
    type3: ComponentType<R3>,
    predicate: (R3) -> Boolean
  ): EntitySeq3<R1, W1, R2, W2, R3, W3>

  @JvmName("withFiltered_1")
  fun withFiltered(
    type1: ComponentType<R1>,
    predicate: (R1) -> Boolean
  ): EntitySeq3<R1, W1, R2, W2, R3, W3> = withFiltered1(type1, predicate)

  @JvmName("withFiltered_2")
  fun withFiltered(
    type2: ComponentType<R2>,
    predicate: (R2) -> Boolean
  ): EntitySeq3<R1, W1, R2, W2, R3, W3> = withFiltered2(type2, predicate)

  @JvmName("withFiltered_3")
  fun withFiltered(
    type3: ComponentType<R3>,
    predicate: (R3) -> Boolean
  ): EntitySeq3<R1, W1, R2, W2, R3, W3> = withFiltered3(type3, predicate)
}
