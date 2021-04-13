/*
 * Shards
 *
 * Copyright (c) LanternPowered <https://www.lanternpowered.org>
 * Copyright (c) contributors
 *
 * This work is licensed under the terms of the MIT License (MIT). For
 * a copy, see 'LICENSE.txt' or <https://opensource.org/licenses/MIT>.
 */
@file:Suppress("FunctionName")

package org.lanternpowered.shards.entity

@PublishedApi
internal inline fun <R> EntityFunction0(
  crossinline fn: (Entity) -> R
): EntityFunction0<R> = object : EntityFunction0<R> {
  override fun invoke(entity: Entity): R = fn(entity)
}

@PublishedApi
internal interface EntityFunction0<R> {
  operator fun invoke(entity: Entity): R
}

@PublishedApi
internal inline fun <A, R> EntityFunction1(
  crossinline fn: (Entity, A) -> R
): EntityFunction1<A, R> = object : EntityFunction1<A, R> {
  override fun invoke(entity: Entity, a: A): R = fn(entity, a)
}

@PublishedApi
internal interface EntityFunction1<A, R> {
  operator fun invoke(entity: Entity, a: A): R
}

@PublishedApi
internal inline fun <A, B, R> EntityFunction2(
  crossinline fn: (Entity, A, B) -> R
): EntityFunction2<A, B, R> = object : EntityFunction2<A, B, R> {
  override fun invoke(entity: Entity, a: A, b: B): R = fn(entity, a, b)
}

@PublishedApi
internal interface EntityFunction2<A, B, R> {
  operator fun invoke(entity: Entity, a: A, b: B): R
}

@PublishedApi
internal inline fun EntityPredicate(
  crossinline fn: (Entity) -> Boolean
): EntityPredicate = object : EntityPredicate {
  override fun invoke(entity: Entity): Boolean = fn(entity)
}

@PublishedApi
internal interface EntityPredicate {
  operator fun invoke(entity: Entity): Boolean
}
