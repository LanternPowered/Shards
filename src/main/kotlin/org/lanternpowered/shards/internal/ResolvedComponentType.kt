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

import org.lanternpowered.lmbda.kt.createLambda
import org.lanternpowered.lmbda.kt.lambdaType
import org.lanternpowered.lmbda.kt.privateLookupIn
import org.lanternpowered.shards.component.ComponentType
import java.lang.invoke.MethodHandles
import java.lang.reflect.ParameterizedType
import java.util.concurrent.atomic.AtomicInteger
import kotlin.reflect.KClass

class ResolvedComponentType<T : Any>(
  val id: Int,
  val componentClass: KClass<T>
) {

  companion object {

    private val idCounter = AtomicInteger()

    fun <T : Any> resolve(type: KClass<*>): ResolvedComponentType<T> {
      val theClass = type.java
      val superClass = theClass.superclass
      check(superClass == ComponentType::class.java) {
        "Only direct subclasses of ComponentType are allowed." }
      val superType = theClass.genericSuperclass
      check(superType is ParameterizedType) {
        "Direct subclasses of ComponentType must be a parameterized type." }
      val id = this.idCounter.getAndIncrement()
      @Suppress("UNCHECKED_CAST")
      val componentClass = (superType.rawType as Class<T>).kotlin
      return ResolvedComponentType(id, componentClass)
    }

    private val instantiatorType = lambdaType<() -> Any>()

    /**
     * Resolves the default instantiator for the given [KClass], throws an
     * [IllegalStateException] if no default constructor is found.
     */
    fun <T : Any> resolveDefaultInstantiator(type: KClass<T>): () -> T {
      // Find the non-arg constructor, this is either manually defined,
      // default when having no constructors specified, or synthetically
      // generated by kotlin for constructors that have all default parameter
      // values (this can be private)
      val constructor = type.java.declaredConstructors
        .firstOrNull { constructor ->
          constructor.parameterCount == 0
        } ?: error("Class '$type' is missing a default constructor.")
      val lookup = MethodHandles.lookup().privateLookupIn(type)
      val handle = lookup.unreflectConstructor(constructor)
      @Suppress("UNCHECKED_CAST")
      return handle.createLambda(instantiatorType) as () -> T
    }
  }
}