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

/**
 * Can be applied to a [Component] type to mark it as read-only. This is the
 * equivalent of calling a `readOnly()` on a [ComponentType] in a [System].
 */
@Target(AnnotationTarget.TYPE)
@Retention(AnnotationRetention.RUNTIME)
annotation class ReadOnly
