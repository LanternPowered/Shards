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

import org.lanternpowered.shards.system.System
import org.lanternpowered.shards.util.MonotonicInstant
import org.lanternpowered.shards.util.MonotonicTime
import org.lanternpowered.shards.util.now

/**
 * Represents a [Component] of which its modifications can be tracked. This
 * allows [System]s to perform actions only when the component was modified
 * in combination with the [IfModified] system filter annotation.
 *
 * It's recommend that modifications to [InvalidatableComponent]s are handled
 * inside a [modify] block, the component will be invalidated after the block
 * is finished. It's also possible to manually call the [invalidate] function
 * after doing modifications.
 */
abstract class InvalidatableComponent : Component {

  /**
   * The last time this component was modified.
   */
  var lastModified: MonotonicInstant = MonotonicTime.now()
}

/**
 * Invalidates the [InvalidatableComponent].
 */
fun InvalidatableComponent.invalidate() {
  lastModified = MonotonicTime.now()
}
