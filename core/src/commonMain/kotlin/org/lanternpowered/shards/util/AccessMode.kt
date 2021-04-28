/*
 * Shards
 *
 * Copyright (c) LanternPowered <https://www.lanternpowered.org>
 * Copyright (c) contributors
 *
 * This work is licensed under the terms of the MIT License (MIT). For
 * a copy, see 'LICENSE.txt' or <https://opensource.org/licenses/MIT>.
 */
package org.lanternpowered.shards.util

import org.lanternpowered.shards.component.Component

/**
 * Represents an access mode, specifically used when accessing [Component]s.
 */
internal enum class AccessMode {
  ReadWrite,
  ReadOnly,
  Undefined,
}
