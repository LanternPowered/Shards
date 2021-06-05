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

import org.lanternpowered.shards.internal.EngineManager
import org.lanternpowered.shards.util.unsafeCast
import kotlin.jvm.JvmInline

/**
 * Constructs an [EntityReference] for the specified [Entity].
 */
fun EntityReference(entity: Entity): EntityReference =
  EntityReference(entity.ref)

/**
 * Constructs an [EntityReference] for the specified [Entity].
 */
fun EntityReference(entity: Entity?): EntityReference =
  if (entity == null) EntityReference.Empty else EntityReference(entity)

/**
 * Represents a reference to an entity.
 */
@Suppress("NON_PUBLIC_PRIMARY_CONSTRUCTOR_OF_INLINE_CLASS")
@JvmInline
value class EntityReference internal constructor(
  internal val ref: InternalEntityRef
) {

  /**
   * Gets the entity if it exists, otherwise throws a [NoSuchElementException].
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
   * Gets the entity if present, otherwise returns the entity provided by the
   * supplier.
   *
   * @param fallback The fallback entity to be used
   * @return The entity
   */
  inline fun <T : Entity?> orElseGet(fallback: () -> T): T =
    if (isPresent()) asEntity().unsafeCast() else fallback()

  /**
   * Gets the entity if present, otherwise throws the exception provided by the
   * supplier.
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
  fun isPresent(): Boolean =
    !ref.isEmpty && EngineManager.isActive(ref)

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
  inline fun <T> map(fn: (Entity) -> T): T? =
    if (isPresent()) fn(asEntity()) else null

  /**
   * Filters this optional entity with the given function.
   */
  inline fun filter(fn: (Entity) -> Boolean): EntityReference =
    if (isPresent() && fn(asEntity())) this else Empty

  /**
   * Gets the reference as an entity, without any checks.
   */
  @PublishedApi
  internal fun asEntity(): Entity = Entity(ref)

  override fun toString(): String =
    if (isPresent()) "EntityReference(${asEntity()})"
    else "EntityReference.Empty"

  companion object {

    /**
     * An empty entity reference.
     */
    val Empty: EntityReference = EntityReference(InternalEntityRef.Empty)
  }
}
