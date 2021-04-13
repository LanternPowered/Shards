/*
 * Shards
 *
 * Copyright (c) LanternPowered <https://www.lanternpowered.org>
 * Copyright (c) contributors
 *
 * This work is licensed under the terms of the MIT License (MIT). For
 * a copy, see 'LICENSE.txt' or <https://opensource.org/licenses/MIT>.
 */
package org.lanternpowered.shards.component

import org.lanternpowered.shards.internal.ResolvedComponentType
import org.lanternpowered.shards.internal.util.AbstractObject

/**
 * Represents the type of a [Component].
 */
abstract class ComponentType<T : Component> : AbstractObject {

  /**
   * Information resolved about the component type.
   */
  @JvmSynthetic
  internal val resolved: ResolvedComponentType<T>

  /**
   * The instantiator that's used to create component instances.
   */
  @JvmSynthetic
  internal val instantiator: () -> T

  /**
   * Constructs a new [ComponentType] with the given type [T] and a specific
   * [instantiator].
   */
  constructor(instantiator: () -> T) {
    resolved = ResolvedComponentType.resolve(this::class)
    this.instantiator = instantiator
  }

  /**
   * Constructs a new [ComponentType] with the given type [T] and a default
   * instantiator. The target component class must either have an empty
   * constructor or a constructor with parameters that all have default values.
   */
  constructor() {
    resolved = ResolvedComponentType.resolve(this::class)
    instantiator = ResolvedComponentType
      .resolveDefaultInstantiator(resolved.componentClass)
  }

  final override fun equals(other: Any?): Boolean =
    other is ComponentType<*> && other.resolved == resolved

  final override fun hashCode(): Int =
    resolved.hashCode()

  final override fun toString(): String =
    resolved.toString()
}
