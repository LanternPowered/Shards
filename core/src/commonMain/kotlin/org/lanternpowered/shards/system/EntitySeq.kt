/*
 * Shards
 *
 * Copyright (c) LanternPowered <https://www.lanternpowered.org>
 * Copyright (c) contributors
 *
 * This work is licensed under the terms of the MIT License (MIT). For
 * a copy, see 'LICENSE.txt' or <https://opensource.org/licenses/MIT>.
 */
@file:Suppress("NOTHING_TO_INLINE", "UNUSED_PARAMETER", "unused",
  "RedundantNullableReturnType")

package org.lanternpowered.shards.system

import org.lanternpowered.shards.component.Component
import org.lanternpowered.shards.component.ComponentType
import org.lanternpowered.shards.entity.Entity
import org.lanternpowered.shards.entity.EntityDsl
import org.lanternpowered.shards.entity.EntityList
import org.lanternpowered.shards.util.unsafeCast
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.jvm.JvmName

typealias EntitySeqContext0 = EntitySeqContext1<Nothing, Nothing>

typealias EntitySeqContext1<
  R1, W1
> = EntitySeqContext2<R1, W1, Nothing, Nothing>

typealias EntitySeqContext2<
  R1, W1, R2, W2
> = EntitySeqContext3<R1, W1, R2, W2, Nothing, Nothing>

typealias EntitySeqContext3<
  R1, W1, R2, W2, R3, W3
> = EntitySeqContext4<R1, W1, R2, W2, R3, W3, Nothing, Nothing>

typealias EntitySeqContext4<
  R1, W1, R2, W2, R3, W3, R4, W4
> = EntitySeqContext5<R1, W1, R2, W2, R3, W3, R4, W4, Nothing, Nothing>

abstract class EntitySeqContext5<
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
> {

  @PublishedApi
  internal abstract fun Entity.set1(value: W1)
  @PublishedApi
  internal abstract fun Entity.set2(value: W2)
  @PublishedApi
  internal abstract fun Entity.set3(value: W3)
  @PublishedApi
  internal abstract fun Entity.set4(value: W4)
  @PublishedApi
  internal abstract fun Entity.set5(value: W5)

  @PublishedApi
  internal abstract fun Entity.contains1(): Boolean
  @PublishedApi
  internal abstract fun Entity.contains2(): Boolean
  @PublishedApi
  internal abstract fun Entity.contains3(): Boolean
  @PublishedApi
  internal abstract fun Entity.contains4(): Boolean
  @PublishedApi
  internal abstract fun Entity.contains5(): Boolean

  @PublishedApi
  internal abstract fun Entity.remove1(): Boolean
  @PublishedApi
  internal abstract fun Entity.remove2(): Boolean
  @PublishedApi
  internal abstract fun Entity.remove3(): Boolean
  @PublishedApi
  internal abstract fun Entity.remove4(): Boolean
  @PublishedApi
  internal abstract fun Entity.remove5(): Boolean

  @PublishedApi
  internal abstract fun Entity.get1(): R1
  @PublishedApi
  internal abstract fun Entity.get2(): R2
  @PublishedApi
  internal abstract fun Entity.get3(): R3
  @PublishedApi
  internal abstract fun Entity.get4(): R4
  @PublishedApi
  internal abstract fun Entity.get5(): R5

  @PublishedApi
  internal abstract fun Entity.getOrNull1(): R1?
  @PublishedApi
  internal abstract fun Entity.getOrNull2(): R2?
  @PublishedApi
  internal abstract fun Entity.getOrNull3(): R3?
  @PublishedApi
  internal abstract fun Entity.getOrNull4(): R4?
  @PublishedApi
  internal abstract fun Entity.getOrNull5(): R5?

  @JvmName("set_1")
  inline fun Entity.set(value: W1) = set1(value)
  @JvmName("set_2")
  inline fun Entity.set(value: W2) = set2(value)
  @JvmName("set_3")
  inline fun Entity.set(value: W3) = set3(value)
  @JvmName("set_4")
  inline fun Entity.set(value: W4) = set4(value)
  @JvmName("set_5")
  inline fun Entity.set(value: W5) = set5(value)

  @Deprecated(
    message = "Component types that are accessed must be available in the " +
      "context with the proper access mode. The can be done using reads() " +
      "and modifies().",
    level = DeprecationLevel.ERROR,
    replaceWith = ReplaceWith("")
  )
  inline fun Entity.set(value: Component): Unit =
    throw UnsupportedOperationException()

  @JvmName("contains_1")
  inline fun Entity.contains(type: ComponentType<R1>): Boolean = contains1()
  @JvmName("contains_2")
  inline fun Entity.contains(type: ComponentType<R2>): Boolean = contains2()
  @JvmName("contains_3")
  inline fun Entity.contains(type: ComponentType<R3>): Boolean = contains3()
  @JvmName("contains_4")
  inline fun Entity.contains(type: ComponentType<R4>): Boolean = contains4()
  @JvmName("contains_5")
  inline fun Entity.contains(type: ComponentType<R5>): Boolean = contains5()

  @Deprecated(
    message = "Component types that are accessed must be available in the " +
      "context with the proper access mode. The can be done using reads() " +
      "and modifies().",
    level = DeprecationLevel.ERROR,
    replaceWith = ReplaceWith("")
  )
  inline fun Entity.contains(type: ComponentType<*>): Boolean =
    throw UnsupportedOperationException()

  @JvmName("remove_1")
  inline fun Entity.remove(type: ComponentType<W1>): Boolean = remove1()
  @JvmName("remove_2")
  inline fun Entity.remove(type: ComponentType<W2>): Boolean = remove2()
  @JvmName("remove_3")
  inline fun Entity.remove(type: ComponentType<W3>): Boolean = remove3()
  @JvmName("remove_4")
  inline fun Entity.remove(type: ComponentType<W4>): Boolean = remove4()
  @JvmName("remove_5")
  inline fun Entity.remove(type: ComponentType<W5>): Boolean = remove5()

  @Deprecated(
    message = "Component types that are accessed must be available in the " +
      "context with the proper access mode. The can be done using reads() " +
      "and modifies().",
    level = DeprecationLevel.ERROR,
    replaceWith = ReplaceWith("")
  )
  inline fun Entity.remove(type: ComponentType<*>): Boolean =
    throw UnsupportedOperationException()

  @JvmName("get_1")
  inline fun Entity.get(type: ComponentType<R1>): R1 = get1()
  @JvmName("get_2")
  inline fun Entity.get(type: ComponentType<R2>): R2 = get2()
  @JvmName("get_3")
  inline fun Entity.get(type: ComponentType<R3>): R3 = get3()
  @JvmName("get_4")
  inline fun Entity.get(type: ComponentType<R4>): R4 = get4()
  @JvmName("get_5")
  inline fun Entity.get(type: ComponentType<R5>): R5 = get5()

  @Deprecated(
    message = "Component types that are accessed must be available in the " +
      "context with the proper access mode. The can be done using reads() " +
      "and modifies().",
    level = DeprecationLevel.ERROR,
    replaceWith = ReplaceWith("")
  )
  inline fun <R : Component> Entity.get(type: ComponentType<R>): R =
    throw UnsupportedOperationException()

  @JvmName("getOr_1")
  inline fun Entity.getOr(type: ComponentType<R1>, or: () -> R1): R1 {
    contract { callsInPlace(or, InvocationKind.AT_MOST_ONCE) }
    return getOrNull1() ?: or()
  }

  @JvmName("getOr_2")
  inline fun Entity.getOr(type: ComponentType<R2>, or: () -> R2): R2 {
    contract { callsInPlace(or, InvocationKind.AT_MOST_ONCE) }
    return getOrNull2() ?: or()
  }

  @JvmName("getOr_3")
  inline fun Entity.getOr(type: ComponentType<R3>, or: () -> R3): R3 {
    contract { callsInPlace(or, InvocationKind.AT_MOST_ONCE) }
    return getOrNull3() ?: or()
  }

  @JvmName("getOr_4")
  inline fun Entity.getOr(type: ComponentType<R4>, or: () -> R4): R4 {
    contract { callsInPlace(or, InvocationKind.AT_MOST_ONCE) }
    return getOrNull4() ?: or()
  }

  @JvmName("getOr_5")
  inline fun Entity.getOr(type: ComponentType<R5>, or: () -> R5): R5 {
    contract { callsInPlace(or, InvocationKind.AT_MOST_ONCE) }
    return getOrNull5() ?: or()
  }

  @Deprecated(
    message = "Component types that are accessed must be available in the " +
      "context with the proper access mode. The can be done using reads() " +
      "and modifies().",
    level = DeprecationLevel.ERROR,
    replaceWith = ReplaceWith("")
  )
  inline fun <R : Component> Entity.getOr(
    type: ComponentType<R>, or: () -> R
  ): R {
    contract { callsInPlace(or, InvocationKind.AT_MOST_ONCE) }
    throw UnsupportedOperationException()
  }

  @JvmName("getOr_1")
  inline fun Entity.getOr(or: () -> R1): R1 {
    contract { callsInPlace(or, InvocationKind.AT_MOST_ONCE) }
    return getOrNull1() ?: or()
  }

  @JvmName("getOr_2")
  inline fun Entity.getOr(or: () -> R2): R2 {
    contract { callsInPlace(or, InvocationKind.AT_MOST_ONCE) }
    return getOrNull2() ?: or()
  }

  @JvmName("getOr_3")
  inline fun Entity.getOr(or: () -> R3): R3 {
    contract { callsInPlace(or, InvocationKind.AT_MOST_ONCE) }
    return getOrNull3() ?: or()
  }

  @JvmName("getOr_4")
  inline fun Entity.getOr(or: () -> R4): R4 {
    contract { callsInPlace(or, InvocationKind.AT_MOST_ONCE) }
    return getOrNull4() ?: or()
  }

  @JvmName("getOr_5")
  inline fun Entity.getOr(or: () -> R5): R5 {
    contract { callsInPlace(or, InvocationKind.AT_MOST_ONCE) }
    return getOrNull5() ?: or()
  }

  @Deprecated(
    message = "Component types that are accessed must be available in the " +
      "context with the proper access mode. The can be done using reads() " +
      "and modifies().",
    level = DeprecationLevel.ERROR,
    replaceWith = ReplaceWith("")
  )
  inline fun <R : Component> Entity.getOr(
    or: () -> R
  ): R {
    contract { callsInPlace(or, InvocationKind.AT_MOST_ONCE) }
    throw UnsupportedOperationException()
  }

  @JvmName("getOrNull_1")
  inline fun Entity.getOrNull(type: ComponentType<R1>): R1? = getOrNull1()
  @JvmName("getOrNull_2")
  inline fun Entity.getOrNull(type: ComponentType<R2>): R2? = getOrNull2()
  @JvmName("getOrNull_3")
  inline fun Entity.getOrNull(type: ComponentType<R3>): R3? = getOrNull3()
  @JvmName("getOrNull_4")
  inline fun Entity.getOrNull(type: ComponentType<R4>): R4? = getOrNull4()
  @JvmName("getOrNull_5")
  inline fun Entity.getOrNull(type: ComponentType<R5>): R5? = getOrNull5()

  @Deprecated(
    message = "Component types that are accessed must be available in the " +
      "context with the proper access mode. The can be done using reads() " +
      "and modifies().",
    level = DeprecationLevel.ERROR,
    replaceWith = ReplaceWith("")
  )
  inline fun <R : Component> Entity.getOrNull(type: ComponentType<R>): R? =
    throw UnsupportedOperationException()

  @JvmName("transform_1")
  inline fun Entity.transform(
    type: ComponentType<W1>,
    operation: (W1) -> W1
  ): W1 {
    contract { callsInPlace(operation, InvocationKind.EXACTLY_ONCE) }
    return operation(get1().unsafeCast()).also { set1(it) }
  }

  @JvmName("transform_2")
  inline fun Entity.transform(
    type: ComponentType<W2>,
    operation: (W2) -> W2
  ): W2 {
    contract { callsInPlace(operation, InvocationKind.EXACTLY_ONCE) }
    return operation(get2().unsafeCast()).also { set2(it) }
  }

  @JvmName("transform_3")
  inline fun Entity.transform(
    type: ComponentType<W3>,
    operation: (W3) -> W3
  ): W3 {
    contract { callsInPlace(operation, InvocationKind.EXACTLY_ONCE) }
    return operation(get3().unsafeCast()).also { set3(it) }
  }

  @JvmName("transform_4")
  inline fun Entity.transform(
    type: ComponentType<W4>,
    operation: (W4) -> W4
  ): W4 {
    contract { callsInPlace(operation, InvocationKind.EXACTLY_ONCE) }
    return operation(get4().unsafeCast()).also { set4(it) }
  }

  @JvmName("transform_5")
  inline fun Entity.transform(
    type: ComponentType<W5>,
    operation: (W5) -> W5
  ): W5 {
    contract { callsInPlace(operation, InvocationKind.EXACTLY_ONCE) }
    return operation(get5().unsafeCast()).also { set5(it) }
  }

  @Deprecated(
    message = "Component types that are accessed must be available in the " +
      "context with the proper access mode. The can be done using reads() " +
      "and modifies().",
    level = DeprecationLevel.ERROR,
    replaceWith = ReplaceWith("")
  )
  inline fun <W : Component> Entity.transform(
    type: ComponentType<W>,
    operation: (W) -> W
  ): W {
    contract { callsInPlace(operation, InvocationKind.EXACTLY_ONCE) }
    throw UnsupportedOperationException()
  }

  @JvmName("transform_1")
  inline fun Entity.transform(
    type: ComponentType<W1>,
    default: () -> W1,
    operation: (W1) -> W1
  ): W1 {
    contract { callsInPlace(operation, InvocationKind.EXACTLY_ONCE) }
    val value = getOrNull1() ?: default()
    return operation(value.unsafeCast()).also { set1(it) }
  }

  @JvmName("transform_2")
  inline fun Entity.transform(
    type: ComponentType<W2>,
    default: () -> W2,
    operation: (W2) -> W2
  ): W2 {
    contract { callsInPlace(operation, InvocationKind.EXACTLY_ONCE) }
    val value = getOrNull2() ?: default()
    return operation(value.unsafeCast()).also { set2(it) }
  }

  @JvmName("transform_3")
  inline fun Entity.transform(
    type: ComponentType<W3>,
    default: () -> W3,
    operation: (W3) -> W3
  ): W3 {
    contract { callsInPlace(operation, InvocationKind.EXACTLY_ONCE) }
    val value = getOrNull3() ?: default()
    return operation(value.unsafeCast()).also { set3(it) }
  }

  @JvmName("transform_4")
  inline fun Entity.transform(
    type: ComponentType<W4>,
    default: () -> W4,
    operation: (W4) -> W4
  ): W4 {
    contract { callsInPlace(operation, InvocationKind.EXACTLY_ONCE) }
    val value = getOrNull4() ?: default()
    return operation(value.unsafeCast()).also { set4(it) }
  }

  @JvmName("transform_5")
  inline fun Entity.transform(
    type: ComponentType<W5>,
    default: () -> W5,
    operation: (W5) -> W5
  ): W5 {
    contract { callsInPlace(operation, InvocationKind.EXACTLY_ONCE) }
    val value = getOrNull5() ?: default()
    return operation(value.unsafeCast()).also { set5(it) }
  }

  @Deprecated(
    message = "Component types that are accessed must be available in the " +
      "context with the proper access mode. The can be done using reads() " +
      "and modifies().",
    level = DeprecationLevel.ERROR,
    replaceWith = ReplaceWith("")
  )
  inline fun <T : Component> Entity.transform(
    type: ComponentType<T>,
    default: () -> T,
    operation: (T) -> T
  ): T {
    contract { callsInPlace(operation, InvocationKind.EXACTLY_ONCE) }
    throw UnsupportedOperationException()
  }

  @JvmName("transform_1")
  inline fun Entity.transform(
    default: () -> W1,
    operation: (W1) -> W1
  ): W1 {
    contract { callsInPlace(operation, InvocationKind.EXACTLY_ONCE) }
    val value = getOrNull1() ?: default()
    return operation(value.unsafeCast()).also { set1(it) }
  }

  @JvmName("transform_2")
  inline fun Entity.transform(
    default: () -> W2,
    operation: (W2) -> W2
  ): W2 {
    contract { callsInPlace(operation, InvocationKind.EXACTLY_ONCE) }
    val value = getOrNull2() ?: default()
    return operation(value.unsafeCast()).also { set2(it) }
  }

  @JvmName("transform_3")
  inline fun Entity.transform(
    default: () -> W3,
    operation: (W3) -> W3
  ): W3 {
    contract { callsInPlace(operation, InvocationKind.EXACTLY_ONCE) }
    val value = getOrNull3() ?: default()
    return operation(value.unsafeCast()).also { set3(it) }
  }

  @JvmName("transform_4")
  inline fun Entity.transform(
    default: () -> W4,
    operation: (W4) -> W4
  ): W4 {
    contract { callsInPlace(operation, InvocationKind.EXACTLY_ONCE) }
    val value = getOrNull4() ?: default()
    return operation(value.unsafeCast()).also { set4(it) }
  }

  @JvmName("transform_5")
  inline fun Entity.transform(
    default: () -> W5,
    operation: (W5) -> W5
  ): W5 {
    contract { callsInPlace(operation, InvocationKind.EXACTLY_ONCE) }
    val value = getOrNull5() ?: default()
    return operation(value.unsafeCast()).also { set5(it) }
  }

  @Deprecated(
    message = "Component types that are accessed must be available in the " +
      "context with the proper access mode. The can be done using reads() " +
      "and modifies().",
    level = DeprecationLevel.ERROR,
    replaceWith = ReplaceWith("")
  )
  inline fun <T : Component> Entity.transform(
    default: () -> T,
    operation: (T) -> T
  ): T {
    contract { callsInPlace(operation, InvocationKind.EXACTLY_ONCE) }
    throw UnsupportedOperationException()
  }

  @JvmName("getOrSet_1")
  inline fun Entity.getOrSet(supplier: () -> W1): W1 {
    contract { callsInPlace(supplier, InvocationKind.AT_MOST_ONCE) }
    return getOrSet(supplier, { getOrNull1() }, { set1(it) })
  }

  @JvmName("getOrSet_2")
  inline fun Entity.getOrSet(supplier: () -> W2): W2 {
    contract { callsInPlace(supplier, InvocationKind.AT_MOST_ONCE) }
    return getOrSet(supplier, { getOrNull2() }, { set2(it) })
  }

  @JvmName("getOrSet_3")
  inline fun Entity.getOrSet(supplier: () -> W3): W3 {
    contract { callsInPlace(supplier, InvocationKind.AT_MOST_ONCE) }
    return getOrSet(supplier, { getOrNull3() }, { set3(it) })
  }

  @JvmName("getOrSet_4")
  inline fun Entity.getOrSet(supplier: () -> W4): W4 {
    contract { callsInPlace(supplier, InvocationKind.AT_MOST_ONCE) }
    return getOrSet(supplier, { getOrNull4() }, { set4(it) })
  }

  @JvmName("getOrSet_5")
  inline fun Entity.getOrSet(supplier: () -> W5): W5 {
    contract { callsInPlace(supplier, InvocationKind.AT_MOST_ONCE) }
    return getOrSet(supplier, { getOrNull5() }, { set5(it) })
  }

  @JvmName("getOrSet_1")
  inline fun Entity.getOrSet(
    type: ComponentType<W1>, supplier: () -> W1
  ): W1 {
    contract { callsInPlace(supplier, InvocationKind.AT_MOST_ONCE) }
    return getOrSet(supplier)
  }

  @JvmName("getOrSet_2")
  inline fun Entity.getOrSet(
    type: ComponentType<W2>, supplier: () -> W2
  ): W2 {
    contract { callsInPlace(supplier, InvocationKind.AT_MOST_ONCE) }
    return getOrSet(supplier)
  }

  @JvmName("getOrSet_3")
  inline fun Entity.getOrSet(
    type: ComponentType<W3>, supplier: () -> W3
  ): W3 {
    contract { callsInPlace(supplier, InvocationKind.AT_MOST_ONCE) }
    return getOrSet(supplier)
  }

  @JvmName("getOrSet_4")
  inline fun Entity.getOrSet(
    type: ComponentType<W4>, supplier: () -> W4
  ): W4 {
    contract { callsInPlace(supplier, InvocationKind.AT_MOST_ONCE) }
    return getOrSet(supplier)
  }

  @JvmName("getOrSet_5")
  inline fun Entity.getOrSet(
    type: ComponentType<W5>, supplier: () -> W5
  ): W5 {
    contract { callsInPlace(supplier, InvocationKind.AT_MOST_ONCE) }
    return getOrSet(supplier)
  }

  @PublishedApi
  internal inline fun <R : Component> Entity.getOrSet(
    supplier: () -> R, get: Entity.() -> Component?, set: Entity.(R) -> Unit
  ): R {
    val previous = get()
    if (previous != null)
      return previous.unsafeCast()
    val component = supplier()
    set(component)
    return component
  }

  @Deprecated(
    message = "Component types that are accessed must be available in the " +
      "context with the proper access mode. The can be done using reads() " +
      "and modifies().",
    level = DeprecationLevel.ERROR,
    replaceWith = ReplaceWith("")
  )
  @OverloadResolutionByLambdaReturnType
  inline fun <reified T : Component> Entity.getOrSet(supplier: () -> T): T =
    throw UnsupportedOperationException()

  @Deprecated(
    message = "Component types that are accessed must be available in the " +
      "context with the proper access mode. The can be done using reads() " +
      "and modifies().",
    level = DeprecationLevel.ERROR,
    replaceWith = ReplaceWith("")
  )
  inline fun <reified T : Component> Entity.getOrSet(
    type: ComponentType<T>, supplier: () -> T
  ): T = throw UnsupportedOperationException()

  @Deprecated(
    message = "All processed entities are active.",
    level = DeprecationLevel.ERROR,
    replaceWith = ReplaceWith("true")
  )
  inline val Entity.isActive: Boolean
    get() = true

  /**
   * Modifies the entity with the given [operation].
   */
  inline fun Entity.modify(operation: @EntityDsl Entity.() -> Unit): Entity {
    contract { callsInPlace(operation, InvocationKind.EXACTLY_ONCE) }
    return apply(operation)
  }
}

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

  abstract fun first(predicate: C.(Entity) -> Boolean): Entity

  abstract fun firstOrNull(): Entity?

  abstract fun firstOrNull(predicate: C.(Entity) -> Boolean): Entity?

  abstract fun last(): Entity

  abstract fun last(predicate: C.(Entity) -> Boolean): Entity

  abstract fun lastOrNull(): Entity?

  abstract fun lastOrNull(predicate: C.(Entity) -> Boolean): Entity?
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
    predicate: EntitySeqContext1<R1, W1>.(R1) -> Boolean
  ): EntitySeq1<R1, W1>

  @JvmName("withFiltered_1")
  fun withFiltered(
    type1: ComponentType<W1>,
    predicate: EntitySeqContext1<R1, W1>.(R1) -> Boolean
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
    predicate: EntitySeqContext2<R1, W1, R2, W2>.(R1) -> Boolean
  ): EntitySeq2<R1, W1, R2, W2>

  @PublishedApi
  internal abstract fun withFiltered2(
    type2: ComponentType<R2>,
    predicate: EntitySeqContext2<R1, W1, R2, W2>.(R2) -> Boolean
  ): EntitySeq2<R1, W1, R2, W2>

  @JvmName("withFiltered_1")
  fun withFiltered(
    type1: ComponentType<R1>,
    predicate: EntitySeqContext2<R1, W1, R2, W2>.(R1) -> Boolean
  ): EntitySeq2<R1, W1, R2, W2> = withFiltered1(type1, predicate)

  @JvmName("withFiltered_2")
  fun withFiltered(
    type2: ComponentType<R2>,
    predicate: EntitySeqContext2<R1, W1, R2, W2>.(R2) -> Boolean
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
    predicate: EntitySeqContext3<R1, W1, R2, W2, R3, W3>.(R1) -> Boolean
  ): EntitySeq3<R1, W1, R2, W2, R3, W3>

  @PublishedApi
  internal abstract fun withFiltered2(
    type2: ComponentType<R2>,
    predicate: EntitySeqContext3<R1, W1, R2, W2, R3, W3>.(R2) -> Boolean
  ): EntitySeq3<R1, W1, R2, W2, R3, W3>

  @PublishedApi
  internal abstract fun withFiltered3(
    type3: ComponentType<R3>,
    predicate: EntitySeqContext3<R1, W1, R2, W2, R3, W3>.(R3) -> Boolean
  ): EntitySeq3<R1, W1, R2, W2, R3, W3>

  @JvmName("withFiltered_1")
  fun withFiltered(
    type1: ComponentType<R1>,
    predicate: EntitySeqContext3<R1, W1, R2, W2, R3, W3>.(R1) -> Boolean
  ): EntitySeq3<R1, W1, R2, W2, R3, W3> = withFiltered1(type1, predicate)

  @JvmName("withFiltered_2")
  fun withFiltered(
    type2: ComponentType<R2>,
    predicate: EntitySeqContext3<R1, W1, R2, W2, R3, W3>.(R2) -> Boolean
  ): EntitySeq3<R1, W1, R2, W2, R3, W3> = withFiltered2(type2, predicate)

  @JvmName("withFiltered_3")
  fun withFiltered(
    type3: ComponentType<R3>,
    predicate: EntitySeqContext3<R1, W1, R2, W2, R3, W3>.(R3) -> Boolean
  ): EntitySeq3<R1, W1, R2, W2, R3, W3> = withFiltered3(type3, predicate)
}
