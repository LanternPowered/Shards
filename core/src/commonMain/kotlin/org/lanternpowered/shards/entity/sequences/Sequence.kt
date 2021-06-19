/*
 * Shards
 *
 * Copyright (c) LanternPowered <https://www.lanternpowered.org>
 * Copyright (c) contributors
 *
 * This work is licensed under the terms of the MIT License (MIT). For
 * a copy, see 'LICENSE.txt' or <https://opensource.org/licenses/MIT>.
 */
package org.lanternpowered.shards.entity.sequences

import org.lanternpowered.shards.entity.Entity
import org.lanternpowered.shards.entity.collections.EntityArrayList
import org.lanternpowered.shards.entity.collections.EntityList

fun Sequence<Entity>.toEntityList(): EntityList {
  val list = EntityArrayList()
  for (entity in this)
    list.add(entity)
  return list
}
