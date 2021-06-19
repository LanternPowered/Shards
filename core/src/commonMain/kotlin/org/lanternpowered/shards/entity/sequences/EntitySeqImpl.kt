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
import org.lanternpowered.shards.entity.collections.EntityList
import org.lanternpowered.shards.util.unsafeCast

internal fun EntitySeq0Impl(context: QueryableEntityContext): EntitySeq0Impl =
  EntitySeq0Impl(InternalQueryImpl(context))

internal class EntitySeq0Impl(
  private val internalQuery: InternalQueryImpl
) : EntitySeq0() {

  override fun <R> map(
    operation: EntitySeqContext0.(Entity) -> R
  ): Sequence<R> =
    internalQuery.map(operation.unsafeCast())

  override fun filter(
    predicate: EntitySeqContext0.(Entity) -> Boolean
  ): EntitySeq0 =
    EntitySeq0Impl(internalQuery.filter(predicate.unsafeCast()))

  override fun without(type: ComponentType<*>): EntitySeq0 =
    EntitySeq0Impl(internalQuery.without(type))

  override fun forEach(
    operation: EntitySeqContext0.(Entity) -> Unit
  ) {
    internalQuery.forEach(operation.unsafeCast())
  }

  override fun forAll(
    operation: EntitySeqContext0.(EntityList) -> Unit
  ) {
    internalQuery.forAll(operation.unsafeCast())
  }

  override fun first(): Entity =
    internalQuery.first()

  override fun firstOrNull(): Entity? =
    internalQuery.firstOrNull()

  override fun last(): Entity =
    internalQuery.last()

  override fun lastOrNull(): Entity? =
    internalQuery.lastOrNull()

  override fun <R1 : Component> reads(
    type1: ComponentType<R1>
  ): EntitySeq1<R1, Nothing> =
    EntitySeq1Impl(internalQuery.reads(type1))

  override fun <W1 : Component> modifies(
    type1: ComponentType<W1>
  ): EntitySeq1<W1, W1> =
    EntitySeq1Impl(internalQuery.modifies(type1))
}

internal class EntitySeq1Impl<
  R1 : Component,
  W1 : Component,
>(
  private val internalQuery: InternalQueryImpl
): EntitySeq1<R1, W1>() {

  override fun <R> map(
    operation: EntitySeqContext1<R1, W1>.(Entity) -> R
  ): Sequence<R> =
    internalQuery.map(operation.unsafeCast())

  override fun filter(
    predicate: EntitySeqContext1<R1, W1>.(Entity) -> Boolean
  ): EntitySeq1<R1, W1> =
    EntitySeq1Impl(internalQuery.filter(predicate.unsafeCast()))

  override fun without(type: ComponentType<*>): EntitySeq1<R1, W1> =
    EntitySeq1Impl(internalQuery.without(type))

  override fun forEach(
    operation: EntitySeqContext1<R1, W1>.(Entity) -> Unit
  ) {
    internalQuery.forEach(operation.unsafeCast())
  }

  override fun forAll(
    operation: EntitySeqContext1<R1, W1>.(EntityList) -> Unit
  ) {
    internalQuery.forAll(operation.unsafeCast())
  }

  override fun first(): Entity =
    internalQuery.first()

  override fun firstOrNull(): Entity? =
    internalQuery.firstOrNull()

  override fun last(): Entity =
    internalQuery.last()

  override fun lastOrNull(): Entity? =
    internalQuery.lastOrNull()

  override fun <R2 : Component> reads(
    type2: ComponentType<R2>
  ): EntitySeq2<R1, W1, R2, Nothing> =
    EntitySeq2Impl(internalQuery.reads(type2))

  override fun <W2 : Component> modifies(
    type2: ComponentType<W2>
  ): EntitySeq2<R1, W1, W2, W2> =
    EntitySeq2Impl(internalQuery.modifies(type2))

  override fun with1(type1: ComponentType<R1>): EntitySeq1<R1, W1> =
    EntitySeq1Impl(internalQuery.with(type1))

  override fun withFiltered1(
    type1: ComponentType<W1>,
    predicate: (R1) -> Boolean
  ): EntitySeq1<R1, W1> =
    EntitySeq1Impl(internalQuery.withFiltered(type1, predicate.unsafeCast()))
}

internal class EntitySeq2Impl<
  R1 : Component,
  W1 : Component,
  R2 : Component,
  W2 : Component,
>(
  private val internalQuery: InternalQueryImpl
): EntitySeq2<R1, W1, R2, W2>() {

  override fun <R> map(
    operation: EntitySeqContext2<R1, W1, R2, W2>.(Entity) -> R
  ): Sequence<R> =
    internalQuery.map(operation.unsafeCast())

  override fun filter(
    predicate: EntitySeqContext2<R1, W1, R2, W2>.(Entity) -> Boolean
  ): EntitySeq2<R1, W1, R2, W2> =
    EntitySeq2Impl(internalQuery.filter(predicate.unsafeCast()))

  override fun without(type: ComponentType<*>): EntitySeq2<R1, W1, R2, W2> =
    EntitySeq2Impl(internalQuery.without(type))

  override fun forEach(
    operation: EntitySeqContext2<R1, W1, R2, W2>.(Entity) -> Unit
  ) {
    internalQuery.forEach(operation.unsafeCast())
  }

  override fun forAll(
    operation: EntitySeqContext2<R1, W1, R2, W2>.(EntityList) -> Unit
  ) {
    internalQuery.forAll(operation.unsafeCast())
  }

  override fun first(): Entity =
    internalQuery.first()

  override fun firstOrNull(): Entity? =
    internalQuery.firstOrNull()

  override fun last(): Entity =
    internalQuery.last()

  override fun lastOrNull(): Entity? =
    internalQuery.lastOrNull()

  override fun <R3 : Component> reads(
    type3: ComponentType<R3>
  ): EntitySeq3<R1, W1, R2, W2, R3, Nothing> =
    EntitySeq3Impl(internalQuery.reads(type3))

  override fun <W3 : Component> modifies(
    type3: ComponentType<W3>
  ): EntitySeq3<R1, W1, R2, W2, W3, W3> =
    EntitySeq3Impl(internalQuery.modifies(type3.unsafeCast()))

  override fun with1(type1: ComponentType<R1>): EntitySeq2<R1, W1, R2, W2> =
    EntitySeq2Impl(internalQuery.with(type1))

  override fun with2(type2: ComponentType<R2>): EntitySeq2<R1, W1, R2, W2> =
    EntitySeq2Impl(internalQuery.with(type2))

  override fun withFiltered1(
    type1: ComponentType<R1>,
    predicate: (R1) -> Boolean
  ): EntitySeq2<R1, W1, R2, W2> =
    EntitySeq2Impl(internalQuery.withFiltered(type1, predicate.unsafeCast()))

  override fun withFiltered2(
    type2: ComponentType<R2>,
    predicate: (R2) -> Boolean
  ): EntitySeq2<R1, W1, R2, W2> =
    EntitySeq2Impl(internalQuery.withFiltered(type2, predicate.unsafeCast()))
}

internal class EntitySeq3Impl<
  R1 : Component,
  W1 : Component,
  R2 : Component,
  W2 : Component,
  R3 : Component,
  W3 : Component,
>(
  private val internalQuery: InternalQueryImpl
): EntitySeq3<R1, W1, R2, W2, R3, W3>() {

  override fun <R> map(
    operation: EntitySeqContext3<R1, W1, R2, W2, R3, W3>.(Entity) -> R
  ): Sequence<R> =
    internalQuery.map(operation.unsafeCast())

  override fun filter(
    predicate: EntitySeqContext3<R1, W1, R2, W2, R3, W3>.(Entity) -> Boolean
  ): EntitySeq3<R1, W1, R2, W2, R3, W3> =
    EntitySeq3Impl(internalQuery.filter(predicate.unsafeCast()))

  override fun without(
    type: ComponentType<*>
  ): EntitySeq3<R1, W1, R2, W2, R3, W3> =
    EntitySeq3Impl(internalQuery.without(type))

  override fun forEach(
    operation: EntitySeqContext3<R1, W1, R2, W2, R3, W3>.(Entity) -> Unit
  ) {
    internalQuery.forEach(operation.unsafeCast())
  }

  override fun forAll(
    operation: EntitySeqContext3<R1, W1, R2, W2, R3, W3>.(EntityList) -> Unit
  ) {
    internalQuery.forAll(operation.unsafeCast())
  }

  override fun first(): Entity =
    internalQuery.first()

  override fun firstOrNull(): Entity? =
    internalQuery.firstOrNull()

  override fun last(): Entity =
    internalQuery.last()

  override fun lastOrNull(): Entity? =
    internalQuery.lastOrNull()

  override fun with1(
    type1: ComponentType<R1>
  ): EntitySeq3<R1, W1, R2, W2, R3, W3> =
    EntitySeq3Impl(internalQuery.with(type1))

  override fun with2(
    type2: ComponentType<R2>
  ): EntitySeq3<R1, W1, R2, W2, R3, W3> =
    EntitySeq3Impl(internalQuery.with(type2))

  override fun with3(
    type3: ComponentType<R3>
  ): EntitySeq3<R1, W1, R2, W2, R3, W3> =
    EntitySeq3Impl(internalQuery.with(type3))

  override fun withFiltered1(
    type1: ComponentType<R1>,
    predicate: (R1) -> Boolean
  ): EntitySeq3<R1, W1, R2, W2, R3, W3> =
    EntitySeq3Impl(internalQuery.withFiltered(type1, predicate.unsafeCast()))

  override fun withFiltered2(
    type2: ComponentType<R2>,
    predicate: (R2) -> Boolean
  ): EntitySeq3<R1, W1, R2, W2, R3, W3> =
    EntitySeq3Impl(internalQuery.withFiltered(type2, predicate.unsafeCast()))

  override fun withFiltered3(
    type3: ComponentType<R3>,
    predicate: (R3) -> Boolean
  ): EntitySeq3<R1, W1, R2, W2, R3, W3> =
    EntitySeq3Impl(internalQuery.withFiltered(type3, predicate.unsafeCast()))
}
