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

internal actual class IntList {
  val backing = ArrayList<Int>()

  actual val isEmpty: Boolean get() = backing.isEmpty()
  actual fun removeAt(index: Int): Int = backing.removeAt(index)
  actual fun add(value: Int): Boolean = backing.add(value)
}
