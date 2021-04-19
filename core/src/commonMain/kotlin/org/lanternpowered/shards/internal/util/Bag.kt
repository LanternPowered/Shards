/*
 * Shards
 *
 * Copyright (c) LanternPowered <https://www.lanternpowered.org>
 * Copyright (c) contributors
 *
 * This work is licensed under the terms of the MIT License (MIT). For
 * a copy, see 'LICENSE.txt' or <https://opensource.org/licenses/MIT>.
 */
@file:Suppress("FunctionName")

package org.lanternpowered.shards.internal.util

import kotlin.math.max
import kotlin.reflect.KClass

fun <T : Any> Bag(
  type: KClass<T>, capacity: Int = 64, default: (Int) -> T
): Bag<T> {
  @Suppress("UNCHECKED_CAST")
  val data = createArrayOfNulls(capacity, type) as Array<T>
  return Bag(data, default)
}

class Bag<T> internal constructor(
  private var data: Array<T>,
  private val default: (Int) -> T
) {

  private var _size = 0

  val isEmpty: Boolean
    get() = _size == 0

  val size: Int
    get() = _size

  fun add(value: T) {
    if (_size == data.size)
      grow(data.size * 2)
    data[_size++] = value
  }

  operator fun get(index: Int): T {
    if (index >= _size)
      throw NoSuchElementException()
    @Suppress("UNCHECKED_CAST")
    return data[index]
  }

  fun getOrDefault(index: Int, default: T): T {
    if (index >= _size)
      return default
    @Suppress("UNCHECKED_CAST")
    return data[index]
  }

  operator fun set(index: Int, value: T) {
    if (index >= data.size)
      grow(max(2 * data.size, index + 1))
    val size = _size
    if (index >= size) {
      for (i in size until index)
        data[i] = default(i)
    }
    data[index] = value
    _size = max(index + 1, _size)
  }

  fun unsafeSet(index: Int, value: T) {
    data[index] = value
  }

  fun indexOf(value: Int): Int {
    for (i in 0 until _size) {
      if (data[i] == value)
        return i
    }
    return -1
  }

  fun contains(value: Int): Boolean = indexOf(value) != -1

  fun remove(value: Int): Boolean {
    val index = indexOf(value)
    if (index == -1)
      return false
    data[index] = data[--_size]
    return true
  }

  fun removeAt(index: Int): T {
    if (index >= _size)
      throw IndexOutOfBoundsException()
    val value = data[index]
    data[index] = data[--_size]
    return value
  }

  private var _iterator: BagIterator? = null

  /**
   * Gets an iterator to iterate over the elements of this [Bag].
   *
   * The returned
   */
  operator fun iterator(): Iterator<T> {
    var iterator = _iterator
    if (iterator != null) {
      _iterator = null
    } else {
      iterator = BagIterator()
    }
    return iterator
  }

  private fun grow(capacity: Int) {
    @Suppress("UNCHECKED_CAST")
    data = data.copyOf(capacity) as Array<T>
  }

  fun forEach(operation: (T) -> Unit) {
    for (i in 0 until _size)
      operation(data[i])
  }

  private inner class BagIterator : Iterator<T> {
    var index = 0

    override fun hasNext(): Boolean = index < _size

    override fun next(): T {
      val offset = index - _size
      if (offset == 0)
        throw NoSuchElementException()
      // Recycle the iterator
      if (offset == 1) // Is last element
        _iterator = this
      return data[index++]
    }
  }
}
