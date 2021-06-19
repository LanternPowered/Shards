/*
 * Shards
 *
 * Copyright (c) LanternPowered <https://www.lanternpowered.org>
 * Copyright (c) contributors
 *
 * This work is licensed under the terms of the MIT License (MIT). For
 * a copy, see 'LICENSE.txt' or <https://opensource.org/licenses/MIT>.
 */
package org.lanternpowered.shards.entity.sequences

import org.lanternpowered.shards.component.Component
import org.lanternpowered.shards.entity.Entity
import org.lanternpowered.shards.internal.util.Bag
import org.lanternpowered.shards.util.unsafeCast

internal class BagEntitySequenceContextImpl<
  R1 : Component,
  W1 : Component,
  R2 : Component,
  W2 : Component,
  R3 : Component,
  W3 : Component,
  R4 : Component,
  W4 : Component,
  R5 : Component,
  W5 : Component,
>(
  private val bag1: Bag<R1>,
  private val bag2: Bag<R2>,
  private val bag3: Bag<R3>,
  private val bag4: Bag<R4>,
  private val bag5: Bag<R5>,
) : EntitySequenceContext5<R1, W1, R2, W2, R3, W3, R4, W4, R5, W5>() {

  override fun Entity.set1(value: W1) {
    bag1[id.value] = value.unsafeCast()
  }

  override fun Entity.set2(value: W2) {
    bag2[id.value] = value.unsafeCast()
  }

  override fun Entity.set3(value: W3) {
    bag3[id.value] = value.unsafeCast()
  }

  override fun Entity.set4(value: W4) {
    bag4[id.value] = value.unsafeCast()
  }

  override fun Entity.set5(value: W5) {
    bag5[id.value] = value.unsafeCast()
  }

  override fun Entity.contains1(): Boolean =
    bag1.getOrNull(id.value) != null

  override fun Entity.contains2(): Boolean =
    bag2.getOrNull(id.value) != null

  override fun Entity.contains3(): Boolean =
    bag3.getOrNull(id.value) != null

  override fun Entity.contains4(): Boolean =
    bag4.getOrNull(id.value) != null

  override fun Entity.contains5(): Boolean =
    bag5.getOrNull(id.value) != null

  override fun Entity.remove1(): Boolean =
    bag1.removeAtOrNull(id.value) != null

  override fun Entity.remove2(): Boolean =
    bag1.removeAtOrNull(id.value) != null

  override fun Entity.remove3(): Boolean =
    bag1.removeAtOrNull(id.value) != null

  override fun Entity.remove4(): Boolean =
    bag1.removeAtOrNull(id.value) != null

  override fun Entity.remove5(): Boolean =
    bag1.removeAtOrNull(id.value) != null

  override fun Entity.get1(): R1 = bag1[id.value]

  override fun Entity.get2(): R2 = bag2[id.value]

  override fun Entity.get3(): R3 = bag3[id.value]

  override fun Entity.get4(): R4 = bag4[id.value]

  override fun Entity.get5(): R5 = bag5[id.value]

  override fun Entity.getOrNull1(): R1? = bag1.getOrNull(id.value)

  override fun Entity.getOrNull2(): R2? = bag2.getOrNull(id.value)

  override fun Entity.getOrNull3(): R3? = bag3.getOrNull(id.value)

  override fun Entity.getOrNull4(): R4? = bag4.getOrNull(id.value)

  override fun Entity.getOrNull5(): R5? = bag5.getOrNull(id.value)
}
