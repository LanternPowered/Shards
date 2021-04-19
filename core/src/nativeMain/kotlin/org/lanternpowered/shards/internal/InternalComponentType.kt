/*
 * Shards
 *
 * Copyright (c) LanternPowered <https://www.lanternpowered.org>
 * Copyright (c) contributors
 *
 * This work is licensed under the terms of the MIT License (MIT). For
 * a copy, see 'LICENSE.txt' or <https://opensource.org/licenses/MIT>.
 */
package org.lanternpowered.shards.internal

import org.lanternpowered.shards.component.Component
import kotlin.reflect.KClass

private var idCounter = 0
private val lookup = HashMap<KClass<*>, InternalComponentType<*>>()

// TODO: Thread safety?

@Suppress("UNCHECKED_CAST")
internal actual fun <T : Component> resolveInternalComponentType(
  componentClass: KClass<T>
): InternalComponentType<T> {
  // Construct an instantiator from the js constructor
  return lookup.getOrPut(componentClass) {
    val id = idCounter++
    val instantiator: () -> T = { throw UnsupportedOperationException() }
    InternalComponentType(id, componentClass, instantiator)
  } as InternalComponentType<T>
}
