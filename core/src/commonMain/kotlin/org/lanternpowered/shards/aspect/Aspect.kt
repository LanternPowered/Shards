/*
 * Shards
 *
 * Copyright (c) LanternPowered <https://www.lanternpowered.org>
 * Copyright (c) contributors
 *
 * This work is licensed under the terms of the MIT License (MIT). For
 * a copy, see 'LICENSE.txt' or <https://opensource.org/licenses/MIT>.
 */
package org.lanternpowered.shards.aspect

import org.lanternpowered.shards.component.ComponentType
import kotlin.jvm.JvmName
import kotlin.jvm.JvmStatic

/**
 * Creates an [Aspect] that excludes the given [ComponentType].
 */
fun exclude(type: ComponentType<*>): Aspect =
  Aspect.exclude(type)

operator fun ComponentType<*>.plus(type: ComponentType<*>): Aspect =
  Aspect.require(this).plus(type)

operator fun ComponentType<*>.plus(aspect: Aspect): Aspect =
  Aspect.require(this).plus(aspect)

class Aspect {

  @JvmName("and")
  operator fun plus(aspect: Aspect): Aspect {
    TODO()
  }

  @JvmName("and")
  operator fun plus(type: ComponentType<*>): Aspect =
    plus(require(type))

  companion object {

    val Empty = Aspect()

    @JvmStatic
    fun require(type: ComponentType<*>): Aspect {
      TODO()
    }

    @JvmStatic
    fun exclude(type: ComponentType<*>): Aspect {
      TODO()
    }
  }
}
