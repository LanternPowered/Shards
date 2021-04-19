/*
 * Shards
 *
 * Copyright (c) LanternPowered <https://www.lanternpowered.org>
 * Copyright (c) contributors
 *
 * This work is licensed under the terms of the MIT License (MIT). For
 * a copy, see 'LICENSE.txt' or <https://opensource.org/licenses/MIT>.
 */
package org.lanternpowered.shards.entity

import it.unimi.dsi.fastutil.longs.LongArrayList
import it.unimi.dsi.fastutil.longs.LongList

actual class EntityArrayList internal constructor(
  internal val backing: LongList
) : MutableEntityList {

  actual constructor() :
    this(LongArrayList())

  actual constructor(capacity: Int) :
    this(LongArrayList(capacity))

  actual constructor(collection: EntityCollection) :
    this(collection.toReferenceList())

  override val size: Int
    get() = backing.size

  override fun get(index: Int): Entity =
    Entity(backing.getLong(index))

  override fun set(index: Int, entity: Entity): Entity =
    Entity(backing.set(index, entity.ref.value))

  override fun add(index: Int, entity: Entity) {
    backing.add(index, entity.ref.value)
  }

  override fun add(entity: Entity): Boolean =
    backing.add(entity.ref.value)

  override fun removeAt(index: Int): Entity =
    Entity(backing.removeLong(index))

  override fun remove(entity: Entity): Boolean =
    backing.rem(entity.ref.value)

  override fun clear() {
    backing.clear()
  }

  override fun isEmpty(): Boolean = backing.isEmpty()

  override fun contains(entity: Entity): Boolean =
    backing.contains(entity.ref.value)

  override fun containsAll(entities: Collection<Entity>): Boolean =
    entities.all { contains(it) }

  override fun containsAll(entities: EntityCollection): Boolean =
    if (entities is EntityArrayList) backing.containsAll(entities.backing)
    else entities.all { contains(it) }

  override fun indexOf(entity: Entity): Int =
    backing.indexOf(entity.ref.value)

  override fun lastIndexOf(entity: Entity): Int =
    backing.lastIndexOf(entity.ref.value)

  override fun iterator(): EntityIterator {
    val itr = backing.iterator()
    return object : EntityIterator {
      override fun next(): Entity = Entity(itr.nextLong())
      override fun hasNext(): Boolean = itr.hasNext()
    }
  }
}

private fun EntityCollection.toReferenceList(): LongList {
  if (this is EntityArrayList)
    return LongArrayList(this.backing)
  val list = LongArrayList(size)
  for (entity in this)
    list.add(entity.ref.value)
  return list
}
