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

@Target(
  AnnotationTarget.TYPE,
  AnnotationTarget.FUNCTION,
  AnnotationTarget.CLASS
)
@Retention(AnnotationRetention.BINARY)
@DslMarker
annotation class EntityDsl
