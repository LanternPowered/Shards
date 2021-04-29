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
  if (!validateZeroArgConstructor(componentClass.js))
    failNoDefaultConstructor(componentClass)
  // Construct an instantiator from the js constructor
  val actualInstantiator = resolveInstantiator<T>(constructor.unsafeCast<Any>())
  val id = idCounter++
  componentType = InternalComponentType(id, componentClass, actualInstantiator,
    ::SimpleComponentType)
  constructor.`$compType$` = componentType
  return componentType
}

// TODO: Replace this with kotlin reflection.
// TODO: Keep this up-to-date
private fun validateZeroArgConstructor(constructor: Any): Boolean {
  val value = constructor.toString()

  // We will try to determine the default parameters from function which will
  // look similar to this:
  /*
  function Name(name) {
    if (name === void 0)
      name = 'defaultName';
    this.name = name;
  }
  */

  // Get parameter region
  var start = value.indexOf('(') + 1
  val end = value.indexOf(')', start)

  val parametersString = value.substring(start, end)
  val parameters = parametersString
    .splitToSequence(',')
    .map { it.trim() }

  start = end + 1

  return parameters
    .all { parameter ->
      val param = Regex.escape(parameter)
      val regex = Regex("if\\s?\\($param\\s?===\\s?void\\s0\\)")
      val match = regex.find(input = value, startIndex = start)
        ?: return@all false // No match, so no default value
      start = match.range.last
      true
    }
}

private class SimpleComponentType<T : Component>(
  internalType: InternalComponentType<T>, accessMode: AccessMode
) : ComponentType<T>(internalType, accessMode)

private fun <T> resolveInstantiator(constructor: Any): () -> T =
  { instantiate(constructor) }

@Suppress("UNUSED_PARAMETER") // `constructor` is actually used
private fun <T> instantiate(constructor: Any): T =
  js("new constructor()").unsafeCast<T>()
