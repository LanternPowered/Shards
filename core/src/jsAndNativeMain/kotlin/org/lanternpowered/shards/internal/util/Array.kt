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

import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
internal actual fun <T : Any> createArrayOfNulls(
  capacity: Int, elementType: KClass<T>
): Array<T?> = arrayOfNulls<Any>(capacity) as Array<T?>
