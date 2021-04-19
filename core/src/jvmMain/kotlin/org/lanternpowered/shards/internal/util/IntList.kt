/*
 * Shards
 *
 * Copyright (c) LanternPowered <https://www.lanternpowered.org>
 * Copyright (c) contributors
 *
 * This work is licensed under the terms of the MIT License (MIT). For
 * a copy, see 'LICENSE.txt' or <https://opensource.org/licenses/MIT>.
 */
package org.lanternpowered.shards.internal.util

import it.unimi.dsi.fastutil.ints.IntArrayList

internal actual class IntList {
  val backing = IntArrayList()

  actual val isEmpty: Boolean get() = backing.isEmpty
  actual fun removeAt(index: Int): Int = backing.removeInt(index)
  actual fun add(value: Int): Boolean = backing.add(value)
}
