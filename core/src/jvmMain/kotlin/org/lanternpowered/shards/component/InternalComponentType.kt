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

import org.lanternpowered.shards.util.unsafeCast
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger
import kotlin.reflect.KClass

private val idCounter = AtomicInteger()
private val lookup = ConcurrentHashMap<Class<*>, InternalComponentType<*>>()

internal actual fun <T : Component> resolveInternalComponentType(
  componentClass: KClass<T>
): InternalComponentType<T> = resolveInternalComponentType(componentClass.java)

internal fun <T : Component> resolveInternalComponentType(
  componentClass: Class<T>
): InternalComponentType<T> = lookup.computeIfAbsent(componentClass) {
  createInternalComponentType(componentClass.kotlin)
}.unsafeCast()

private fun <T : Component> createInternalComponentType(
  componentClass: KClass<T>
): InternalComponentType<T> {
  val id = idCounter.getAndIncrement()
  return InternalComponentType(id, componentClass, ::SimpleComponentType)
}

private class SimpleComponentType<T : Component>(
  internalType: InternalComponentType<T>
) : ComponentType<T>(internalType)
