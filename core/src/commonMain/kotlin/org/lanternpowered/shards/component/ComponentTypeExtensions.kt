/*
 * Shards
 *
 * Copyright (c) LanternPowered <https://www.lanternpowered.org>
 * Copyright (c) contributors
 *
 * This work is licensed under the terms of the MIT License (MIT). For
 * a copy, see 'LICENSE.txt' or <https://opensource.org/licenses/MIT>.
 */
@file:JvmName("ComponentTypeExtensions1")

package org.lanternpowered.shards.component

import kotlin.jvm.JvmName
import kotlin.reflect.KClass

/**
 * The [Component] class that is represented by this [ComponentType].
 */
val <T : Component> ComponentType<T>.componentClass: KClass<T>
  get() = internalType.componentClass

internal val <T : Component> ComponentType<T>.instantiator: () -> T
  get() = internalType.instantiator
