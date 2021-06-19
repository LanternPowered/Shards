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
import org.lanternpowered.shards.entity.Entity
import org.lanternpowered.shards.entity.EntityList
import org.lanternpowered.shards.internal.util.Bag
import org.lanternpowered.shards.util.unsafeCast

internal class EntitySeqContext5Impl<
  R1 : Component,
  W1 : Component,
  R2 : Component,
  W2 : Component,
  R3 : Component,
  W3 : Component,
  R4 : Component,
  W4 : Component,
  R5 : Component,
  W5 : Component,
>(
  private val bag1: Bag<R1>,
  private val bag2: Bag<R2>,
  private val bag3: Bag<R3>,
  private val bag4: Bag<R4>,
  private val bag5: Bag<R5>,
) : EntitySeqContext5<R1, W1, R2, W2, R3, W3, R4, W4, R5, W5>() {

  override fun Entity.set1(value: W1) {
    bag1[id.value] = value.unsafeCast()
  }

  override fun Entity.set2(value: W2) {
    bag2[id.value] = value.unsafeCast()
  }

  override fun Entity.set3(value: W3) {
    bag3[id.value] = value.unsafeCast()
  }

  override fun Entity.set4(value: W4) {
    bag4[id.value] = value.unsafeCast()
  }

  override fun Entity.set5(value: W5) {
    bag5[id.value] = value.unsafeCast()
  }

  override fun Entity.contains1(): Boolean =
    bag1.getOrNull(id.value) != null

  override fun Entity.contains2(): Boolean =
    bag2.getOrNull(id.value) != null

  override fun Entity.contains3(): Boolean =
    bag3.getOrNull(id.value) != null

  override fun Entity.contains4(): Boolean =
    bag4.getOrNull(id.value) != null

  override fun Entity.contains5(): Boolean =
    bag5.getOrNull(id.value) != null

  override fun Entity.remove1(): Boolean =
    bag1.removeAtOrNull(id.value) != null

  override fun Entity.remove2(): Boolean =
    bag1.removeAtOrNull(id.value) != null

  override fun Entity.remove3(): Boolean =
    bag1.removeAtOrNull(id.value) != null

  override fun Entity.remove4(): Boolean =
    bag1.removeAtOrNull(id.value) != null

  override fun Entity.remove5(): Boolean =
    bag1.removeAtOrNull(id.value) != null

  override fun Entity.get1(): R1 = bag1[id.value]

  override fun Entity.get2(): R2 = bag2[id.value]

  override fun Entity.get3(): R3 = bag3[id.value]

  override fun Entity.get4(): R4 = bag4[id.value]

  override fun Entity.get5(): R5 = bag5[id.value]

  override fun Entity.getOrNull1(): R1? = bag1.getOrNull(id.value)

  override fun Entity.getOrNull2(): R2? = bag2.getOrNull(id.value)

  override fun Entity.getOrNull3(): R3? = bag3.getOrNull(id.value)

  override fun Entity.getOrNull4(): R4? = bag4.getOrNull(id.value)

  override fun Entity.getOrNull5(): R5? = bag5.getOrNull(id.value)
}

internal interface EntitySeqImpl {
  val internalSeq: InternalEntitySeq
}

internal typealias EntitySeqContext = EntitySeqContext5<*,*,*,*,*,*,*,*,*,*>

internal class InternalEntitySeq(
  val reads: List<ComponentType<*>>,
  val writes: List<ComponentType<*>>
) {

  fun <R> map(
    operation: EntitySeqContext.(Entity) -> R
  ): Sequence<R> {
    TODO("Not yet implemented")
  }

  fun filter(
    predicate: EntitySeqContext.(Entity) -> Boolean
  ): InternalEntitySeq {
    TODO("Not yet implemented")
  }

  fun without(type: ComponentType<*>): InternalEntitySeq {
    TODO("Not yet implemented")
  }

  fun forEach(operation: EntitySeqContext.(Entity) -> Unit) {
    TODO("Not yet implemented")
  }

  fun forAll(operation: EntitySeqContext.(EntityList) -> Unit) {
    TODO("Not yet implemented")
  }

  fun first(): Entity {
    TODO("Not yet implemented")
  }

  fun first(predicate: EntitySeqContext.(Entity) -> Boolean): Entity {
    TODO("Not yet implemented")
  }

  fun firstOrNull(): Entity? {
    TODO("Not yet implemented")
  }

  fun firstOrNull(predicate: EntitySeqContext.(Entity) -> Boolean): Entity? {
    TODO("Not yet implemented")
  }

  fun last(): Entity {
    TODO("Not yet implemented")
  }

  fun last(predicate: EntitySeqContext.(Entity) -> Boolean): Entity {
    TODO("Not yet implemented")
  }

  fun lastOrNull(): Entity? {
    TODO("Not yet implemented")
  }

  fun lastOrNull(predicate: EntitySeqContext.(Entity) -> Boolean): Entity? {
    TODO("Not yet implemented")
  }

  fun reads(type: ComponentType<*>): InternalEntitySeq {
    TODO("Not yet implemented")
  }

  fun modifies(type: ComponentType<*>): InternalEntitySeq {
    TODO("Not yet implemented")
  }

  fun with(type: ComponentType<*>): InternalEntitySeq {
    TODO("Not yet implemented")
  }

  fun <T : Component> withFiltered(
    type: ComponentType<T>,
    predicate: EntitySeqContext.(T) -> Boolean
  ): InternalEntitySeq {
    TODO("Not yet implemented")
  }
}

internal class EntitySeq0Impl(
  override val internalSeq: InternalEntitySeq
) : EntitySeq0(), EntitySeqImpl {

  override fun <R> map(
    operation: EntitySeqContext0.(Entity) -> R
  ): Sequence<R> =
    internalSeq.map(operation.unsafeCast())

  override fun filter(
    predicate: EntitySeqContext0.(Entity) -> Boolean
  ): EntitySeq0 =
    EntitySeq0Impl(internalSeq.filter(predicate.unsafeCast()))

  override fun without(type: ComponentType<*>): EntitySeq0 =
    EntitySeq0Impl(internalSeq.without(type))

  override fun forEach(
    operation: EntitySeqContext0.(Entity) -> Unit
  ) {
    internalSeq.forEach(operation.unsafeCast())
  }

  override fun forAll(
    operation: EntitySeqContext0.(EntityList) -> Unit
  ) {
    internalSeq.forAll(operation.unsafeCast())
  }

  override fun first(): Entity =
    internalSeq.first()

  override fun first(
    predicate: EntitySeqContext0.(Entity) -> Boolean
  ): Entity =
    internalSeq.first(predicate.unsafeCast())

  override fun firstOrNull(): Entity? =
    internalSeq.firstOrNull()

  override fun firstOrNull(
    predicate: EntitySeqContext0.(Entity) -> Boolean
  ): Entity? =
    internalSeq.firstOrNull(predicate.unsafeCast())

  override fun last(): Entity =
    internalSeq.last()

  override fun last(
    predicate: EntitySeqContext0.(Entity) -> Boolean
  ): Entity =
    internalSeq.last(predicate.unsafeCast())

  override fun lastOrNull(): Entity? =
    internalSeq.lastOrNull()

  override fun lastOrNull(
    predicate: EntitySeqContext0.(Entity) -> Boolean
  ): Entity? =
    internalSeq.lastOrNull()

  override fun <R1 : Component> reads(
    type1: ComponentType<R1>
  ): EntitySeq1<R1, Nothing> =
    EntitySeq1Impl(internalSeq.reads(type1))

  override fun <W1 : Component> modifies(
    type1: ComponentType<W1>
  ): EntitySeq1<W1, W1> =
    EntitySeq1Impl(internalSeq.modifies(type1))
}

internal class EntitySeq1Impl<
  R1 : Component,
  W1 : Component,
>(
  override val internalSeq: InternalEntitySeq
): EntitySeq1<R1, W1>(), EntitySeqImpl {

  override fun <R> map(
    operation: EntitySeqContext1<R1, W1>.(Entity) -> R
  ): Sequence<R> =
    internalSeq.map(operation.unsafeCast())

  override fun filter(
    predicate: EntitySeqContext1<R1, W1>.(Entity) -> Boolean
  ): EntitySeq1<R1, W1> =
    EntitySeq1Impl(internalSeq.filter(predicate.unsafeCast()))

  override fun without(type: ComponentType<*>): EntitySeq1<R1, W1> =
    EntitySeq1Impl(internalSeq.without(type))

  override fun forEach(
    operation: EntitySeqContext1<R1, W1>.(Entity) -> Unit
  ) {
    internalSeq.forEach(operation.unsafeCast())
  }

  override fun forAll(
    operation: EntitySeqContext1<R1, W1>.(EntityList) -> Unit
  ) {
    internalSeq.forAll(operation.unsafeCast())
  }

  override fun first(): Entity =
    internalSeq.first()

  override fun first(
    predicate: EntitySeqContext1<R1, W1>.(Entity) -> Boolean
  ): Entity =
    internalSeq.first(predicate.unsafeCast())

  override fun firstOrNull(): Entity? =
    internalSeq.firstOrNull()

  override fun firstOrNull(
    predicate: EntitySeqContext1<R1, W1>.(Entity) -> Boolean
  ): Entity? =
    internalSeq.firstOrNull(predicate.unsafeCast())

  override fun last(): Entity =
    internalSeq.last()

  override fun last(
    predicate: EntitySeqContext1<R1, W1>.(Entity) -> Boolean
  ): Entity =
    internalSeq.last(predicate.unsafeCast())

  override fun lastOrNull(): Entity? =
    internalSeq.lastOrNull()

  override fun lastOrNull(
    predicate: EntitySeqContext1<R1, W1>.(Entity) -> Boolean
  ): Entity? =
    internalSeq.lastOrNull()

  override fun <R2 : Component> reads(
    type2: ComponentType<R2>
  ): EntitySeq2<R1, W1, R2, Nothing> =
    EntitySeq2Impl(internalSeq.reads(type2))

  override fun <W2 : Component> modifies(
    type2: ComponentType<W2>
  ): EntitySeq2<R1, W1, W2, W2> =
    EntitySeq2Impl(internalSeq.modifies(type2))

  override fun with1(type1: ComponentType<R1>): EntitySeq1<R1, W1> =
    EntitySeq1Impl(internalSeq.with(type1))

  override fun withFiltered1(
    type1: ComponentType<W1>,
    predicate: EntitySeqContext1<R1, W1>.(R1) -> Boolean
  ): EntitySeq1<R1, W1> =
    EntitySeq1Impl(internalSeq.withFiltered(type1, predicate.unsafeCast()))
}

internal class EntitySeq2Impl<
  R1 : Component,
  W1 : Component,
  R2 : Component,
  W2 : Component,
>(
  override val internalSeq: InternalEntitySeq
): EntitySeq2<R1, W1, R2, W2>(), EntitySeqImpl {

  override fun <R> map(
    operation: EntitySeqContext2<R1, W1, R2, W2>.(Entity) -> R
  ): Sequence<R> =
    internalSeq.map(operation.unsafeCast())

  override fun filter(
    predicate: EntitySeqContext2<R1, W1, R2, W2>.(Entity) -> Boolean
  ): EntitySeq2<R1, W1, R2, W2> =
    EntitySeq2Impl(internalSeq.filter(predicate.unsafeCast()))

  override fun without(type: ComponentType<*>): EntitySeq2<R1, W1, R2, W2> =
    EntitySeq2Impl(internalSeq.without(type))

  override fun forEach(
    operation: EntitySeqContext2<R1, W1, R2, W2>.(Entity) -> Unit
  ) {
    internalSeq.forEach(operation.unsafeCast())
  }

  override fun forAll(
    operation: EntitySeqContext2<R1, W1, R2, W2>.(EntityList) -> Unit
  ) {
    internalSeq.forAll(operation.unsafeCast())
  }

  override fun first(): Entity =
    internalSeq.first()

  override fun first(
    predicate: EntitySeqContext2<R1, W1, R2, W2>.(Entity) -> Boolean
  ): Entity =
    internalSeq.first(predicate.unsafeCast())

  override fun firstOrNull(): Entity? =
    internalSeq.firstOrNull()

  override fun firstOrNull(
    predicate: EntitySeqContext2<R1, W1, R2, W2>.(Entity) -> Boolean
  ): Entity? =
    internalSeq.firstOrNull(predicate.unsafeCast())

  override fun last(): Entity =
    internalSeq.last()

  override fun last(
    predicate: EntitySeqContext2<R1, W1, R2, W2>.(Entity) -> Boolean
  ): Entity =
    internalSeq.last(predicate.unsafeCast())

  override fun lastOrNull(): Entity? =
    internalSeq.lastOrNull()

  override fun lastOrNull(
    predicate: EntitySeqContext2<R1, W1, R2, W2>.(Entity) -> Boolean
  ): Entity? =
    internalSeq.lastOrNull()

  override fun <R3 : Component> reads(
    type3: ComponentType<R3>
  ): EntitySeq3<R1, W1, R2, W2, R3, Nothing> =
    EntitySeq3Impl(internalSeq.reads(type3))

  override fun <W3 : Component> modifies(
    type3: ComponentType<W3>
  ): EntitySeq3<R1, W1, R2, W2, W3, W3> =
    EntitySeq3Impl(internalSeq.modifies(type3.unsafeCast()))

  override fun with1(type1: ComponentType<R1>): EntitySeq2<R1, W1, R2, W2> =
    EntitySeq2Impl(internalSeq.with(type1))

  override fun with2(type2: ComponentType<R2>): EntitySeq2<R1, W1, R2, W2> =
    EntitySeq2Impl(internalSeq.with(type2))

  override fun withFiltered1(
    type1: ComponentType<R1>,
    predicate: EntitySeqContext2<R1, W1, R2, W2>.(R1) -> Boolean
  ): EntitySeq2<R1, W1, R2, W2> =
    EntitySeq2Impl(internalSeq.withFiltered(type1, predicate.unsafeCast()))

  override fun withFiltered2(
    type2: ComponentType<R2>,
    predicate: EntitySeqContext2<R1, W1, R2, W2>.(R2) -> Boolean
  ): EntitySeq2<R1, W1, R2, W2> =
    EntitySeq2Impl(internalSeq.withFiltered(type2, predicate.unsafeCast()))
}

internal class EntitySeq3Impl<
  R1 : Component,
  W1 : Component,
  R2 : Component,
  W2 : Component,
  R3 : Component,
  W3 : Component,
>(
  override val internalSeq: InternalEntitySeq
): EntitySeq3<R1, W1, R2, W2, R3, W3>(), EntitySeqImpl {

  override fun <R> map(
    operation: EntitySeqContext3<R1, W1, R2, W2, R3, W3>.(Entity) -> R
  ): Sequence<R> =
    internalSeq.map(operation.unsafeCast())

  override fun filter(
    predicate: EntitySeqContext3<R1, W1, R2, W2, R3, W3>.(Entity) -> Boolean
  ): EntitySeq3<R1, W1, R2, W2, R3, W3> =
    EntitySeq3Impl(internalSeq.filter(predicate.unsafeCast()))

  override fun without(
    type: ComponentType<*>
  ): EntitySeq3<R1, W1, R2, W2, R3, W3> =
    EntitySeq3Impl(internalSeq.without(type))

  override fun forEach(
    operation: EntitySeqContext3<R1, W1, R2, W2, R3, W3>.(Entity) -> Unit
  ) {
    internalSeq.forEach(operation.unsafeCast())
  }

  override fun forAll(
    operation: EntitySeqContext3<R1, W1, R2, W2, R3, W3>.(EntityList) -> Unit
  ) {
    internalSeq.forAll(operation.unsafeCast())
  }

  override fun first(): Entity =
    internalSeq.first()

  override fun first(
    predicate: EntitySeqContext3<R1, W1, R2, W2, R3, W3>.(Entity) -> Boolean
  ): Entity =
    internalSeq.first(predicate.unsafeCast())

  override fun firstOrNull(): Entity? =
    internalSeq.firstOrNull()

  override fun firstOrNull(
    predicate: EntitySeqContext3<R1, W1, R2, W2, R3, W3>.(Entity) -> Boolean
  ): Entity? =
    internalSeq.firstOrNull(predicate.unsafeCast())

  override fun last(): Entity =
    internalSeq.last()

  override fun last(
    predicate: EntitySeqContext3<R1, W1, R2, W2, R3, W3>.(Entity) -> Boolean
  ): Entity =
    internalSeq.last(predicate.unsafeCast())

  override fun lastOrNull(): Entity? =
    internalSeq.lastOrNull()

  override fun lastOrNull(
    predicate: EntitySeqContext3<R1, W1, R2, W2, R3, W3>.(Entity) -> Boolean
  ): Entity? =
    internalSeq.lastOrNull()

  override fun with1(
    type1: ComponentType<R1>
  ): EntitySeq3<R1, W1, R2, W2, R3, W3> =
    EntitySeq3Impl(internalSeq.with(type1))

  override fun with2(
    type2: ComponentType<R2>
  ): EntitySeq3<R1, W1, R2, W2, R3, W3> =
    EntitySeq3Impl(internalSeq.with(type2))

  override fun with3(
    type3: ComponentType<R3>
  ): EntitySeq3<R1, W1, R2, W2, R3, W3> =
    EntitySeq3Impl(internalSeq.with(type3))

  override fun withFiltered1(
    type1: ComponentType<R1>,
    predicate: EntitySeqContext3<R1, W1, R2, W2, R3, W3>.(R1) -> Boolean
  ): EntitySeq3<R1, W1, R2, W2, R3, W3> =
    EntitySeq3Impl(internalSeq.withFiltered(type1, predicate.unsafeCast()))

  override fun withFiltered2(
    type2: ComponentType<R2>,
    predicate: EntitySeqContext3<R1, W1, R2, W2, R3, W3>.(R2) -> Boolean
  ): EntitySeq3<R1, W1, R2, W2, R3, W3> =
    EntitySeq3Impl(internalSeq.withFiltered(type2, predicate.unsafeCast()))

  override fun withFiltered3(
    type3: ComponentType<R3>,
    predicate: EntitySeqContext3<R1, W1, R2, W2, R3, W3>.(R3) -> Boolean
  ): EntitySeq3<R1, W1, R2, W2, R3, W3> =
    EntitySeq3Impl(internalSeq.withFiltered(type3, predicate.unsafeCast()))
}
