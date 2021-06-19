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

package org.lanternpowered.shards.util

import kotlin.reflect.KClass
import kotlin.reflect.KClassifier
import kotlin.reflect.KType
import kotlin.reflect.typeOf

/**
 * Constructs a [GenericType] for the given type [T].
 */
inline fun <reified T> genericTypeOf(): GenericType<T> {
  val type = typeOf<T>()
  return GenericType(type.classifier!!, type.isMarkedNullable, type)
}

/**
 * Constructs a new [GenericType] from the given [KClass].
 */
fun <T : Any> GenericType(kClass: KClass<T>): GenericType<T> =
  GenericType(kClass, isNullable = false)

/**
 * Constructs a new [GenericType] from the given [KType].
 */
fun <T> GenericType(kType: KType): GenericType<T> =
  GenericType(kType.classifier!!, kType.isMarkedNullable, kType)

/**
 * Represents a [KType] with a generic type parameter
 * representing the type.
 *
 * @property T The generic type of the [KType]
 * @property classifier The classifier of this generic type
 * @property isNullable Whether this type is nullable
 */
class GenericType<T> @PublishedApi internal constructor(
  val classifier: KClassifier,
  val isNullable: Boolean,
  private val kType: KType? = null
) {

  /**
   * Constructs a new [GenericType] with the given [classifier].
   */
  constructor(classifier: KClassifier, isNullable: Boolean = false) :
    this(classifier, isNullable, null)

  override fun equals(other: Any?): Boolean =
    other is GenericType<*> &&
      other.classifier == classifier &&
      other.isNullable == isNullable

  override fun hashCode(): Int =
    classifier.hashCode() * 31 + isNullable.hashCode()

  override fun toString(): String =
    kType?.toString() ?: classifier.toString()
}
