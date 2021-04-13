/*
 * Shards
 *
 * Copyright (c) LanternPowered <https://www.lanternpowered.org>
 * Copyright (c) contributors
 *
 * This work is licensed under the terms of the MIT License (MIT). For
 * a copy, see 'LICENSE.txt' or <https://opensource.org/licenses/MIT>.
 */
package org.lanternpowered.shards.entity

import org.lanternpowered.shards.internal.EntityReference
import java.util.Optional

/**
 * Represents an optional entity.
 */
@Suppress("NON_PUBLIC_PRIMARY_CONSTRUCTOR_OF_INLINE_CLASS")
inline class OptionalEntity internal constructor(
  internal val reference: EntityReference
) {

  /**
   * Gets the entity if present, otherwise throws a [NoSuchElementException].
   */
  fun get(): Entity =
    if (isPresent()) asEntity()
    else throw NoSuchElementException("No value present")

  /**
   * Gets the entity if present, otherwise returns the `other` entity.
   *
   * @param other The other entity to return
   * @return The entity
   */
  fun orElse(other: Entity): Entity =
    if (isPresent()) asEntity() else other

  /**
   * Gets the entity if present, otherwise returns the `other` entity.
   *
   * @param other The other entity to return
   * @return The entity
   */
  fun orElse(other: Entity?): Entity? =
    if (isPresent()) asEntity() else other

  /**
   * Gets the entity if present, otherwise returns the entity provided by the supplier.
   *
   * @param fallback The fallback entity to be used
   * @return The entity
   */
  inline fun orElseGet(fallback: () -> Entity?): Entity? =
    if (isPresent()) asEntity() else fallback()

  /**
   * Gets the entity if present, otherwise throws the exception provided by the supplier.
   *
   * @param ex The exception to thrown if the entity isn't present
   * @return The entity
   */
  inline fun orElseThrow(ex: () -> Exception): Entity =
    if (isPresent()) asEntity() else throw ex()

  /**
   * Gets the entity if present, otherwise returns `null`.
   *
   * @return The entity
   */
  fun orNull(): Entity? = orElse(null)

  /**
   * Gets whether the entity is present.
   */
  fun isPresent(): Boolean = !reference.isEmpty

  /**
   * Executes the given function if the entity is present.
   */
  inline fun ifPresent(fn: (Entity) -> Unit) {
    if (isPresent())
      fn(asEntity())
  }

  /**
   * Maps the entity to a value of type [T], if present.
   */
  inline fun <T : Any> map(fn: (Entity) -> T): Optional<T> =
    if (isPresent()) Optional.of(fn(asEntity())) else Optional.empty()

  /**
   * Maps the entity to a value of type [T], if present.
   */
  inline fun <T : Any> flatMap(fn: (Entity) -> Optional<T>): Optional<T> =
    if (isPresent()) fn(asEntity()) else Optional.empty()

  /**
   * Filters this optional entity with the given function.
   */
  inline fun filter(fn: (Entity) -> Boolean): OptionalEntity =
    if (isPresent() && fn(asEntity())) this else empty()

  /**
   * Gets the reference as an entity, without any checks.
   */
  @PublishedApi
  internal fun asEntity(): Entity = Entity(reference)

  override fun toString(): String =
    if (isPresent()) "OptionalEntity[${asEntity()}]"
    else "OptionalEntity.empty"

  companion object {

    /**
     * Gets an empty optional entity.
     */
    fun empty(): OptionalEntity =
      OptionalEntity(EntityReference.Empty)

    /**
     * Gets an optional entity from the given [entity].
     */
    fun of(entity: Entity): OptionalEntity =
      OptionalEntity(entity.reference)

    /**
     * Gets an optional entity from the given nullable [entity].
     */
    fun ofNullable(entity: Entity?): OptionalEntity =
      if (entity == null) empty() else of(entity)
  }
}
