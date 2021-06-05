/*
 * Shards
 *
 * Copyright (c) LanternPowered <https://www.lanternpowered.org>
 * Copyright (c) contributors
 *
 * This work is licensed under the terms of the MIT License (MIT). For
 * a copy, see 'LICENSE.txt' or <https://opensource.org/licenses/MIT>.
 */
@file:Suppress("UNCHECKED_CAST", "NOTHING_TO_INLINE")

package org.lanternpowered.shards.util

import kotlin.jvm.JvmInline

/**
 * Represents a typed either. The actual left or right value type depends on
 * the value type.
 */
@JvmInline
value class TypedEither<A, B> @PublishedApi internal constructor(
  @PublishedApi internal val value: Any?
) {

  companion object {

    /**
     * Returns a left [TypedEither].
     */
    inline fun <A, B> left(value: A): TypedEither<A, B> =
      TypedEither(value)

    /**
     * Returns a right [TypedEither].
     */
    inline fun <A, B> right(value: B): TypedEither<A, B> =
      TypedEither(value)
  }
}

/**
 * Returns if this [TypedEither] is left.
 */
inline fun <reified A, B> TypedEither<A, B>.isLeft(): Boolean = value is A

/**
 * Returns if this [TypedEither] is right.
 */
inline fun <reified B, A> TypedEither<A, B>.isRight(): Boolean = value is B

/**
 * Applies [ifLeft] if this is a left or [ifRight] if this is a right.
 */
inline fun <reified A, B, C> TypedEither<A, B>.fold(
  ifLeft: (A) -> C,
  ifRight: (B) -> C,
): C =
  if (value is A) ifLeft(value)
  else ifRight(value as B)

/**
 * Applies the function if this is a left.
 */
inline fun <reified A> TypedEither<A, *>.ifLeft(
  f: (A) -> Unit
) = if (value is A) f(value) else Unit

/**
 * Applies the function if this is a right.
 */
inline fun <reified B> TypedEither<*, B>.ifRight(
  f: (B) -> Unit
) = if (value is B) f(value) else Unit

/**
 * Binds the given function across either right.
 */
inline fun <reified A, B, C> TypedEither<A, B>.flatMap(
  f: (B) -> TypedEither<A, C>
): TypedEither<A, C> =
  if (value is A) TypedEither.left(value)
  else f(value as B)

inline fun <reified A, B, C> TypedEither<A, B>.map(
  f: (B) -> C
): TypedEither<A, C> = flatMap { value -> TypedEither.right(f(value)) }
