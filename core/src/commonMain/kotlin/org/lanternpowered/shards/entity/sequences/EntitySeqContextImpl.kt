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
import org.lanternpowered.shards.component.ComponentType
import org.lanternpowered.shards.entity.Entity
import org.lanternpowered.shards.util.unsafeCast
import org.lanternpowered.shards.entity.set as defaultSet
import org.lanternpowered.shards.entity.contains as defaultContains
import org.lanternpowered.shards.entity.remove as defaultRemove
import org.lanternpowered.shards.entity.get as defaultGet
import org.lanternpowered.shards.entity.getOrNull as defaultGetOrNull

internal class DefaultEntitySeqContext<
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
  private val type1: ComponentType<R1>,
  private val type2: ComponentType<R2>,
  private val type3: ComponentType<R3>,
  private val type4: ComponentType<R4>,
  private val type5: ComponentType<R5>,
) : EntitySeqContext5<R1, W1, R2, W2, R3, W3, R4, W4, R5, W5>() {

  companion object {

    private fun <T> List<T>.getOrNull(index: Int): T? =
      if (index in indices) get(index) else null

    fun create(
      types: List<ComponentType<*>>
    ): DefaultEntitySeqContext<*, *, *, *, *, *, *, *, *, *> {
      return DefaultEntitySeqContext<
        Component, Component, Component, Component, Component, Component,
        Component, Component, Component, Component
      >(
        types.getOrNull(0).unsafeCast(),
        types.getOrNull(1).unsafeCast(),
        types.getOrNull(2).unsafeCast(),
        types.getOrNull(3).unsafeCast(),
        types.getOrNull(4).unsafeCast(),
      )
    }
  }

  override fun Entity.set1(value: W1) = defaultSet(type1, value.unsafeCast())
  override fun Entity.set2(value: W2) = defaultSet(type2, value.unsafeCast())
  override fun Entity.set3(value: W3) = defaultSet(type3, value.unsafeCast())
  override fun Entity.set4(value: W4) = defaultSet(type4, value.unsafeCast())
  override fun Entity.set5(value: W5) = defaultSet(type5, value.unsafeCast())

  override fun Entity.contains1(): Boolean = defaultContains(type1)
  override fun Entity.contains2(): Boolean = defaultContains(type2)
  override fun Entity.contains3(): Boolean = defaultContains(type3)
  override fun Entity.contains4(): Boolean = defaultContains(type4)
  override fun Entity.contains5(): Boolean = defaultContains(type5)

  override fun Entity.remove1(): Boolean = defaultRemove(type1)
  override fun Entity.remove2(): Boolean = defaultRemove(type2)
  override fun Entity.remove3(): Boolean = defaultRemove(type3)
  override fun Entity.remove4(): Boolean = defaultRemove(type4)
  override fun Entity.remove5(): Boolean = defaultRemove(type5)

  override fun Entity.get1(): R1 = defaultGet(type1)
  override fun Entity.get2(): R2 = defaultGet(type2)
  override fun Entity.get3(): R3 = defaultGet(type3)
  override fun Entity.get4(): R4 = defaultGet(type4)
  override fun Entity.get5(): R5 = defaultGet(type5)

  override fun Entity.getOrNull1(): R1? = defaultGetOrNull(type1)
  override fun Entity.getOrNull2(): R2? = defaultGetOrNull(type2)
  override fun Entity.getOrNull3(): R3? = defaultGetOrNull(type3)
  override fun Entity.getOrNull4(): R4? = defaultGetOrNull(type4)
  override fun Entity.getOrNull5(): R5? = defaultGetOrNull(type5)
}
