/*
 * Shards
 *
 * Copyright (c) LanternPowered <https://www.lanternpowered.org>
 * Copyright (c) contributors
 *
 * This work is licensed under the terms of the MIT License (MIT). For
 * a copy, see 'LICENSE.txt' or <https://opensource.org/licenses/MIT>.
 */
package org.lanternpowered.shards.internal.util;

/**
 * An abstract class which "hides" the finalize and clone methods. Mainly so
 * they don't show up during class generation of component type in java.
 */
public abstract class AbstractObject {

  @Override
  protected final void finalize() {
    // Empty, so considered trivial
  }

  @Override
  protected final Object clone() throws CloneNotSupportedException {
    return super.clone();
  }
}
