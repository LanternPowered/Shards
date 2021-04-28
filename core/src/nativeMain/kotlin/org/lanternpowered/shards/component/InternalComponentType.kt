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

import org.lanternpowered.shards.util.AccessMode
import kotlin.reflect.KClass

private var idCounter = 0
private val lookup = HashMap<KClass<*>, InternalComponentType<*>>()

// TODO: Thread safety?

@Suppress("UNCHECKED_CAST")
internal actual fun <T : Component> resolveInternalComponentType(
  componentClass: KClass<T>, instantiator: (() -> T)?
): InternalComponentType<T> {
  // Construct an instantiator from the js constructor
  return lookup.getOrPut(componentClass) {
    val id = idCounter++
    InternalComponentType(id, componentClass, instantiator!!,
      ::SimpleComponentType)
  } as InternalComponentType<T>
}

private class SimpleComponentType<T : Component>(
  internalType: InternalComponentType<T>, accessMode: AccessMode
) : ComponentType<T>(internalType, accessMode)
