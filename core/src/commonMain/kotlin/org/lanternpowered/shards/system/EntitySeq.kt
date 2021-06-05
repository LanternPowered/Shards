/*
 * Shards
 *
 * Copyright (c) LanternPowered <https://www.lanternpowered.org>
 * Copyright (c) contributors
 *
 * This work is licensed under the terms of the MIT License (MIT). For
 * a copy, see 'LICENSE.txt' or <https://opensource.org/licenses/MIT>.
 */
@file:Suppress("UNUSED_PARAMETER", "NOTHING_TO_INLINE", "unused")

package org.lanternpowered.shards.system

import org.lanternpowered.shards.component.Component
import org.lanternpowered.shards.entity.Entity
import org.lanternpowered.shards.entity.EntityList
import org.lanternpowered.shards.util.unsafeCast
import kotlin.js.JsName
import kotlin.jvm.JvmName
import kotlin.reflect.KClass

typealias EntitySeqContext0 = EntitySeqContext1<Nothing>

typealias EntitySeqContext1<
  T1
> = EntitySeqContext2<T1, Nothing>

typealias EntitySeqContext2<
  T1, T2
> = EntitySeqContext3<T1, T2, Nothing>

typealias EntitySeqContext3<
  T1, T2, T3
> = EntitySeqContext4<T1, T2, T3, Nothing>

typealias EntitySeqContext4<
  T1, T2, T3, T4
> = EntitySeqContext5<T1, T2, T3, T4, Nothing>

abstract class EntitySeqContext5<
  T1, T2, T3, T4, T5
> {

  @PublishedApi
  internal abstract fun Entity.set1(value: T1)
  @PublishedApi
  internal abstract fun Entity.set2(value: T2)
  @PublishedApi
  internal abstract fun Entity.set3(value: T3)
  @PublishedApi
  internal abstract fun Entity.set4(value: T4)
  @PublishedApi
  internal abstract fun Entity.set5(value: T5)

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
  internal abstract fun Entity.remove1()
  @PublishedApi
  internal abstract fun Entity.remove2()
  @PublishedApi
  internal abstract fun Entity.remove3()
  @PublishedApi
  internal abstract fun Entity.remove4()
  @PublishedApi
  internal abstract fun Entity.remove5()

  @PublishedApi
  internal abstract fun Entity.get1(): T1
  @PublishedApi
  internal abstract fun Entity.get2(): T2
  @PublishedApi
  internal abstract fun Entity.get3(): T3
  @PublishedApi
  internal abstract fun Entity.get4(): T4
  @PublishedApi
  internal abstract fun Entity.get5(): T5

  @PublishedApi
  internal abstract fun Entity.getOrNull1(): T1
  @PublishedApi
  internal abstract fun Entity.getOrNull2(): T2
  @PublishedApi
  internal abstract fun Entity.getOrNull3(): T3
  @PublishedApi
  internal abstract fun Entity.getOrNull4(): T4
  @PublishedApi
  internal abstract fun Entity.getOrNull5(): T5

  @JvmName("_set1")
  inline fun Entity.set(value: T1) = set1(value)
  @JvmName("_set2")
  inline fun Entity.set(value: T2) = set2(value)
  @JvmName("_set3")
  inline fun Entity.set(value: T3) = set3(value)
  @JvmName("_set4")
  inline fun Entity.set(value: T4) = set4(value)
  @JvmName("_set5")
  inline fun Entity.set(value: T5) = set5(value)

  @JvmName("_contains1")
  inline fun <T : T1> Entity.contains(
    ` `: ` `<T1>? = null
  ): Boolean = contains1()

  @JvmName("_contains2")
  inline fun <T : T2> Entity.contains(
    ` `: ` `<T2>? = null
  ): Boolean = contains2()

  @JvmName("_contains3")
  inline fun <T : T3> Entity.contains(
    ` `: ` `<T3>? = null
  ): Boolean = contains3()

  @JvmName("_contains4")
  inline fun <T : T4> Entity.contains(
    ` `: ` `<T4>? = null
  ): Boolean = contains4()

  @JvmName("_contains5")
  inline fun <T : T5> Entity.contains(
    ` `: ` `<T5>? = null
  ): Boolean = contains5()

  @JvmName("_remove1")
  inline fun <T : T1> Entity.remove(` `: ` `<T1>? = null) = remove1()
  @JvmName("_remove2")
  inline fun <T : T2> Entity.remove(` `: ` `<T2>? = null) = remove2()
  @JvmName("_remove3")
  inline fun <T : T3> Entity.remove(` `: ` `<T3>? = null) = remove3()
  @JvmName("_remove4")
  inline fun <T : T4> Entity.remove(` `: ` `<T4>? = null) = remove4()
  @JvmName("_remove5")
  inline fun <T : T5> Entity.remove(` `: ` `<T5>? = null) = remove5()

  @JvmName("_get1")
  inline fun <T : T1> Entity.get(` `: ` `<T1>? = null): T1 = get1()
  @JvmName("_get2")
  inline fun <T : T2> Entity.get(` `: ` `<T2>? = null): T2 = get2()
  @JvmName("_get3")
  inline fun <T : T3> Entity.get(` `: ` `<T3>? = null): T3 = get3()
  @JvmName("_get4")
  inline fun <T : T4> Entity.get(` `: ` `<T4>? = null): T4 = get4()
  @JvmName("_get5")
  inline fun <T : T5> Entity.get(` `: ` `<T5>? = null): T5 = get5()

  @JvmName("_getOrNull1")
  inline fun <T : T1> Entity.getOrNull(
    ` `: ` `<T1>? = null
  ): T1? = getOrNull1()

  @JvmName("_getOrNull2")
  inline fun <T : T2> Entity.getOrNull(
    ` `: ` `<T2>? = null
  ): T2? = getOrNull2()

  @JvmName("_getOrNull3")
  inline fun <T : T3> Entity.getOrNull(
    ` `: ` `<T3>? = null
  ): T3? = getOrNull3()

  @JvmName("_getOrNull4")
  inline fun <T : T4> Entity.getOrNull(
    ` `: ` `<T4>? = null
  ): T4? = getOrNull4()

  @JvmName("_getOrNull5")
  inline fun <T : T5> Entity.getOrNull(
    ` `: ` `<T5>? = null
  ): T5? = getOrNull5()

  @JvmName("_transform1")
  inline fun <T : T1> Entity.transform(operation: (T1) -> T1): T1 =
    operation(get1()).also { set1(it) }

  @JvmName("_transform2")
  inline fun <T : T2> Entity.transform(operation: (T2) -> T2): T2 =
    operation(get2()).also { set2(it) }

  @JvmName("_transform3")
  inline fun <T : T3> Entity.transform(operation: (T3) -> T3): T3 =
    operation(get3()).also { set3(it) }

  @JvmName("_transform4")
  inline fun <T : T4> Entity.transform(operation: (T4) -> T4): T4 =
    operation(get4()).also { set4(it) }

  @JvmName("_transform5")
  inline fun <T : T5> Entity.transform(operation: (T5) -> T5): T5 =
    operation(get5()).also { set5(it) }
}

@Suppress("ClassName", "unused")
@JsName("Dummy")
class ` `<T> private constructor()

abstract class EntitySeq<S, C>
  where S : EntitySeq<S, C>,
        C : EntitySeqContext5<*, *, *, *, *> {

  abstract fun <R> map(operation: C.(Entity) -> R): Sequence<R>

  abstract fun filter(predicate: C.(Entity) -> Boolean): S

  abstract fun without(type: KClass<out Component>): S

  inline fun <reified T1 : Component> without(): S = without(T1::class)

  abstract fun without(
    type1: KClass<out Component>,
    type2: KClass<out Component>,
  ): S

  inline fun <
    reified T1 : Component,
    reified T2 : Component,
  > without(
    `1`: ` `<T1>? = null,
    `2`: ` `<T2>? = null,
  ): S = without(T1::class, T2::class)

  abstract fun without(
    type1: KClass<out Component>,
    type2: KClass<out Component>,
    type3: KClass<out Component>,
  ): S

  inline fun <
    reified T1 : Component,
    reified T2 : Component,
    reified T3 : Component,
  > without(
    `1`: ` `<T1>? = null,
    `2`: ` `<T2>? = null,
    `3`: ` `<T3>? = null,
  ): S = without(T1::class, T2::class, T3::class)

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

  abstract fun <T1 : Component> with(
    type1: KClass<T1>
  ): EntitySeq1<T1>

  inline fun <reified T1 : Component> with(): EntitySeq1<T1> =
    with(T1::class)

  abstract fun <
    T1 : Component,
    T2 : Component,
  > with(
    type1: KClass<T1>,
    type2: KClass<T2>,
  ): EntitySeq2<T1, T2>

  inline fun <
    reified T1 : Component,
    reified T2 : Component,
  > with(
    `1`: ` `<T1>? = null,
    `2`: ` `<T2>? = null,
  ): EntitySeq2<T1, T2> =
    with(T1::class, T2::class)

  abstract fun <
    T1 : Component,
    T2 : Component,
    T3 : Component,
  > with(
    type1: KClass<T1>,
    type2: KClass<T2>,
    type3: KClass<T3>,
  ): EntitySeq3<T1, T2, T3>

  inline fun <
    reified T1 : Component,
    reified T2 : Component,
    reified T3 : Component,
  > with(
    `1`: ` `<T1>? = null,
    `2`: ` `<T2>? = null,
    `3`: ` `<T2>? = null,
  ): EntitySeq3<T1, T2, T3> =
    with(T1::class, T2::class, T3::class)
}

abstract class EntitySeq1<T1> :
  EntitySeq<
    EntitySeq1<T1>,
    EntitySeqContext1<T1>>()
  where T1 : Component
{

  abstract fun <T2 : Component> with(
    type2: KClass<T2>
  ): EntitySeq2<T1, T2>

  inline fun <reified T2 : Component> with(): EntitySeq2<T1, T2> =
    with(T2::class)

  abstract fun filter(
    type1: KClass<T1>,
    predicate: EntitySeq1<T1>.(T1) -> Boolean
  ): EntitySeq1<T1>

  abstract fun filter(
    type1: KClass<T1>,
    predicate: EntitySeq1<T1>.(Entity, T1) -> Boolean
  ): EntitySeq1<T1>

  inline fun <reified T : T1> filter(
    noinline predicate: EntitySeq1<T1>.(T1) -> Boolean
  ): EntitySeq1<T1> = filter(T::class.unsafeCast(), predicate)

  inline fun <reified T : T1> filter(
    noinline predicate: EntitySeq1<T1>.(Entity, T1) -> Boolean
  ): EntitySeq1<T1> = filter(T::class.unsafeCast(), predicate)
}

abstract class EntitySeq2<T1, T2> :
  EntitySeq<
    EntitySeq2<T1, T2>,
    EntitySeqContext2<T1, T2>>()
  where T1 : Component,
        T2 : Component
{

  abstract fun <T3 : Component> with(
    type3: KClass<T3>
  ): EntitySeq3<T1, T2, T3>

  inline fun <reified T3 : Component> with(): EntitySeq3<T1, T2, T3> =
    with(T3::class)

  @PublishedApi
  internal abstract fun filter1(
    type1: KClass<T1>,
    predicate: EntitySeq2<T1, T2>.(T1) -> Boolean
  ): EntitySeq2<T1, T2>

  @PublishedApi
  internal abstract fun filter1(
    type1: KClass<T1>,
    predicate: EntitySeq2<T1, T2>.(Entity, T1) -> Boolean
  ): EntitySeq2<T1, T2>

  @PublishedApi
  internal abstract fun filter2(
    type1: KClass<T2>,
    predicate: EntitySeq2<T1, T2>.(T2) -> Boolean
  ): EntitySeq2<T1, T2>

  @PublishedApi
  internal abstract fun filter2(
    type1: KClass<T2>,
    predicate: EntitySeq2<T1, T2>.(Entity, T2) -> Boolean
  ): EntitySeq2<T1, T2>

  @JvmName("_filter1")
  fun filter(
    type1: KClass<T1>,
    predicate: EntitySeq2<T1, T2>.(T1) -> Boolean
  ): EntitySeq2<T1, T2> = filter1(type1, predicate)

  @JvmName("_filter1")
  fun filter(
    type1: KClass<T1>,
    predicate: EntitySeq2<T1, T2>.(Entity, T1) -> Boolean
  ): EntitySeq2<T1, T2> = filter1(type1.unsafeCast(), predicate)

  @JvmName("_filter1")
  inline fun <reified T : T1> filter(
    noinline predicate: EntitySeq2<T1, T2>.(T1) -> Boolean
  ): EntitySeq2<T1, T2> = filter1(T::class.unsafeCast(), predicate)

  @JvmName("_filter1")
  inline fun <reified T : T1> filter(
    noinline predicate: EntitySeq2<T1, T2>.(Entity, T1) -> Boolean
  ): EntitySeq2<T1, T2> = filter1(T::class.unsafeCast(), predicate)

  @JvmName("_filter2")
  fun filter(
    type2: KClass<T2>,
    predicate: EntitySeq2<T1, T2>.(T2) -> Boolean
  ): EntitySeq2<T1, T2> = filter2(type2, predicate)

  @JvmName("_filter2")
  fun filter(
    type2: KClass<T2>,
    predicate: EntitySeq2<T1, T2>.(Entity, T2) -> Boolean
  ): EntitySeq2<T1, T2> = filter2(type2, predicate)

  @JvmName("_filter2")
  inline fun <reified T : T2> filter(
    noinline predicate: EntitySeq2<T1, T2>.(T2) -> Boolean
  ): EntitySeq2<T1, T2> = filter2(T::class.unsafeCast(), predicate)

  @JvmName("_filter2")
  inline fun <reified T : T2> filter(
    noinline predicate: EntitySeq2<T1, T2>.(Entity, T2) -> Boolean
  ): EntitySeq2<T1, T2> = filter2(T::class.unsafeCast(), predicate)
}

abstract class EntitySeq3<T1, T2, T3> :
  EntitySeq<
    EntitySeq3<T1, T2, T3>,
    EntitySeqContext3<T1, T2, T3>>()
  where T1 : Component,
        T2 : Component,
        T3 : Component
{

}
