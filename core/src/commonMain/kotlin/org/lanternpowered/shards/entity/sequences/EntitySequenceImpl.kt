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

internal fun EntitySequenceImpl(
  context: QueryableEntityContext
): EntitySequenceImpl =
  EntitySequenceImpl(InternalQueryImpl(context))

internal class EntitySequenceImpl(
  private val internalQuery: InternalQueryImpl
) : EntitySequence() {

  override fun <R> map(
    operation: EntitySequenceContext0.(Entity) -> R
  ): Sequence<R> =
    internalQuery.map(operation.unsafeCast())

  override fun filter(
    predicate: EntitySequenceContext0.(Entity) -> Boolean
  ): EntitySequence =
    EntitySequenceImpl(internalQuery.filter(predicate.unsafeCast()))

  override fun without(type: ComponentType<*>): EntitySequence =
    EntitySequenceImpl(internalQuery.without(type))

  override fun forEach(
    operation: EntitySequenceContext0.(Entity) -> Unit
  ) {
    internalQuery.forEach(operation.unsafeCast())
  }

  override fun forAll(
    operation: EntitySequenceContext0.(EntityList) -> Unit
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
  ): EntitySequence1<R1, Nothing> =
    EntitySequence1Impl(internalQuery.reads(type1))

  override fun <W1 : Component> modifies(
    type1: ComponentType<W1>
  ): EntitySequence1<W1, W1> =
    EntitySequence1Impl(internalQuery.modifies(type1))
}

internal class EntitySequence1Impl<
  R1 : Component,
  W1 : Component,
>(
  private val internalQuery: InternalQueryImpl
): EntitySequence1<R1, W1>() {

  override fun <R> map(
    operation: EntitySequenceContext1<R1, W1>.(Entity) -> R
  ): Sequence<R> =
    internalQuery.map(operation.unsafeCast())

  override fun filter(
    predicate: EntitySequenceContext1<R1, W1>.(Entity) -> Boolean
  ): EntitySequence1<R1, W1> =
    EntitySequence1Impl(internalQuery.filter(predicate.unsafeCast()))

  override fun without(type: ComponentType<*>): EntitySequence1<R1, W1> =
    EntitySequence1Impl(internalQuery.without(type))

  override fun forEach(
    operation: EntitySequenceContext1<R1, W1>.(Entity) -> Unit
  ) {
    internalQuery.forEach(operation.unsafeCast())
  }

  override fun forAll(
    operation: EntitySequenceContext1<R1, W1>.(EntityList) -> Unit
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
  ): EntitySequence2<R1, W1, R2, Nothing> =
    EntitySequence2Impl(internalQuery.reads(type2))

  override fun <W2 : Component> modifies(
    type2: ComponentType<W2>
  ): EntitySequence2<R1, W1, W2, W2> =
    EntitySequence2Impl(internalQuery.modifies(type2))

  override fun with1(type1: ComponentType<R1>): EntitySequence1<R1, W1> =
    EntitySequence1Impl(internalQuery.with(type1))

  override fun withFiltered1(
    type1: ComponentType<W1>,
    predicate: (R1) -> Boolean
  ): EntitySequence1<R1, W1> =
    EntitySequence1Impl(internalQuery.withFiltered(type1, predicate.unsafeCast()))
}

internal class EntitySequence2Impl<
  R1 : Component,
  W1 : Component,
  R2 : Component,
  W2 : Component,
>(
  private val internalQuery: InternalQueryImpl
): EntitySequence2<R1, W1, R2, W2>() {

  override fun <R> map(
    operation: EntitySequenceContext2<R1, W1, R2, W2>.(Entity) -> R
  ): Sequence<R> =
    internalQuery.map(operation.unsafeCast())

  override fun filter(
    predicate: EntitySequenceContext2<R1, W1, R2, W2>.(Entity) -> Boolean
  ): EntitySequence2<R1, W1, R2, W2> =
    EntitySequence2Impl(internalQuery.filter(predicate.unsafeCast()))

  override fun without(type: ComponentType<*>): EntitySequence2<R1, W1, R2, W2> =
    EntitySequence2Impl(internalQuery.without(type))

  override fun forEach(
    operation: EntitySequenceContext2<R1, W1, R2, W2>.(Entity) -> Unit
  ) {
    internalQuery.forEach(operation.unsafeCast())
  }

  override fun forAll(
    operation: EntitySequenceContext2<R1, W1, R2, W2>.(EntityList) -> Unit
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
  ): EntitySequence3<R1, W1, R2, W2, R3, Nothing> =
    EntitySequence3Impl(internalQuery.reads(type3))

  override fun <W3 : Component> modifies(
    type3: ComponentType<W3>
  ): EntitySequence3<R1, W1, R2, W2, W3, W3> =
    EntitySequence3Impl(internalQuery.modifies(type3.unsafeCast()))

  override fun with1(type1: ComponentType<R1>): EntitySequence2<R1, W1, R2, W2> =
    EntitySequence2Impl(internalQuery.with(type1))

  override fun with2(type2: ComponentType<R2>): EntitySequence2<R1, W1, R2, W2> =
    EntitySequence2Impl(internalQuery.with(type2))

  override fun withFiltered1(
    type1: ComponentType<R1>,
    predicate: (R1) -> Boolean
  ): EntitySequence2<R1, W1, R2, W2> =
    EntitySequence2Impl(internalQuery.withFiltered(type1, predicate.unsafeCast()))

  override fun withFiltered2(
    type2: ComponentType<R2>,
    predicate: (R2) -> Boolean
  ): EntitySequence2<R1, W1, R2, W2> =
    EntitySequence2Impl(internalQuery.withFiltered(type2, predicate.unsafeCast()))
}

internal class EntitySequence3Impl<
  R1 : Component,
  W1 : Component,
  R2 : Component,
  W2 : Component,
  R3 : Component,
  W3 : Component,
>(
  private val internalQuery: InternalQueryImpl
): EntitySequence3<R1, W1, R2, W2, R3, W3>() {

  override fun <R> map(
    operation: EntitySequenceContext3<R1, W1, R2, W2, R3, W3>.(Entity) -> R
  ): Sequence<R> =
    internalQuery.map(operation.unsafeCast())

  override fun filter(
    predicate: EntitySequenceContext3<R1, W1, R2, W2, R3, W3>.(Entity) -> Boolean
  ): EntitySequence3<R1, W1, R2, W2, R3, W3> =
    EntitySequence3Impl(internalQuery.filter(predicate.unsafeCast()))

  override fun without(
    type: ComponentType<*>
  ): EntitySequence3<R1, W1, R2, W2, R3, W3> =
    EntitySequence3Impl(internalQuery.without(type))

  override fun forEach(
    operation: EntitySequenceContext3<R1, W1, R2, W2, R3, W3>.(Entity) -> Unit
  ) {
    internalQuery.forEach(operation.unsafeCast())
  }

  override fun forAll(
    operation: EntitySequenceContext3<R1, W1, R2, W2, R3, W3>.(EntityList) -> Unit
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
  ): EntitySequence3<R1, W1, R2, W2, R3, W3> =
    EntitySequence3Impl(internalQuery.with(type1))

  override fun with2(
    type2: ComponentType<R2>
  ): EntitySequence3<R1, W1, R2, W2, R3, W3> =
    EntitySequence3Impl(internalQuery.with(type2))

  override fun with3(
    type3: ComponentType<R3>
  ): EntitySequence3<R1, W1, R2, W2, R3, W3> =
    EntitySequence3Impl(internalQuery.with(type3))

  override fun withFiltered1(
    type1: ComponentType<R1>,
    predicate: (R1) -> Boolean
  ): EntitySequence3<R1, W1, R2, W2, R3, W3> =
    EntitySequence3Impl(internalQuery.withFiltered(type1, predicate.unsafeCast()))

  override fun withFiltered2(
    type2: ComponentType<R2>,
    predicate: (R2) -> Boolean
  ): EntitySequence3<R1, W1, R2, W2, R3, W3> =
    EntitySequence3Impl(internalQuery.withFiltered(type2, predicate.unsafeCast()))

  override fun withFiltered3(
    type3: ComponentType<R3>,
    predicate: (R3) -> Boolean
  ): EntitySequence3<R1, W1, R2, W2, R3, W3> =
    EntitySequence3Impl(internalQuery.withFiltered(type3, predicate.unsafeCast()))
}
