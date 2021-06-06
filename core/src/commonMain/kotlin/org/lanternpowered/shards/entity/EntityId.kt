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

import kotlin.jvm.JvmInline

/**
 * Represents the id of an entity.
 */
@JvmInline
value class EntityId internal constructor(
  internal val value: Int
)
