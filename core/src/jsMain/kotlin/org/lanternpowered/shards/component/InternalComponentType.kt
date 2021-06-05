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

import kotlin.reflect.KClass

private var idCounter = 0

internal actual fun <T : Component> resolveInternalComponentType(
  componentClass: KClass<T>
): InternalComponentType<T> {
  val constructor = componentClass.js.asDynamic()
  // The component type is stored directly on the JS object, to eliminate map
  // lookups
  var componentType = constructor.`$compType$`
  if (componentType != null)
    return componentType.unsafeCast<InternalComponentType<T>>()
  val id = idCounter++
  componentType = InternalComponentType(id, componentClass,
    ::SimpleComponentType)
  constructor.`$compType$` = componentType
  return componentType
}

private class SimpleComponentType<T : Component>(
  internalType: InternalComponentType<T>
) : ComponentType<T>(internalType)
