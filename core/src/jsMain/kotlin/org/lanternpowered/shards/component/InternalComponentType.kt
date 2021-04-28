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

internal actual fun <T : Component> resolveInternalComponentType(
  componentClass: KClass<T>, instantiator: (() -> T)?
): InternalComponentType<T> {
  val constructor = componentClass.js.asDynamic()
  // The component type is stored directly on the JS object, to eliminate map
  // lookups
  var componentType = constructor.`$compType$`
  if (componentType != null)
    return componentType.unsafeCast<InternalComponentType<T>>()
  // Construct an instantiator from the js constructor
  val actualInstantiator = resolveInstantiator<T>(constructor.unsafeCast<Any>())
  val id = idCounter++
  componentType = InternalComponentType(id, componentClass, actualInstantiator,
    ::SimpleComponentType)
  constructor.`$compType$` = componentType
  return componentType
}

private class SimpleComponentType<T : Component>(
  internalType: InternalComponentType<T>, accessMode: AccessMode
) : ComponentType<T>(internalType, accessMode)

private fun <T> resolveInstantiator(constructor: Any): () -> T =
  { instantiate(constructor) }

@Suppress("UNUSED_PARAMETER") // `constructor` is actually used
private fun <T> instantiate(constructor: Any): T =
  js("new constructor()").unsafeCast<T>()
