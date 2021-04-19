/*
 * Shards
 *
 * Copyright (c) LanternPowered <https://www.lanternpowered.org>
 * Copyright (c) contributors
 *
 * This work is licensed under the terms of the MIT License (MIT). For
 * a copy, see 'LICENSE.txt' or <https://opensource.org/licenses/MIT>.
 */
package org.lanternpowered.shards.internal.util

import kotlin.math.max

class IntBag(capacity: Int) {

  constructor() : this(64)

  private var _data = IntArray(capacity)
  private var _size = 0

  val isEmpty: Boolean
    get() = _size == 0

  val size: Int
    get() = _size

  fun add(value: Int) {
    if (_size == _data.size)
      grow(_data.size * 2)
    _data[_size++] = value
  }

  operator fun get(index: Int): Int {
    if (index >= _size)
      throw NoSuchElementException()
    return _data[index]
  }

  fun getOrDefault(index: Int, default: Int): Int {
    if (index >= _size)
      return default
    return _data[index]
  }

  operator fun set(index: Int, value: Int) {
    if (index >= _data.size)
      grow(max(2 * _data.size, index + 1))
    _data[index] = value
    _size = max(index + 1, _size)
  }

  fun indexOf(value: Int): Int {
    for (i in 0 until _size) {
      if (_data[i] == value)
        return i
    }
    return -1
  }

  fun contains(value: Int): Boolean = indexOf(value) != -1

  fun remove(value: Int): Boolean {
    val index = indexOf(value)
    if (index == -1)
      return false
    _data[index] = _data[--_size]
    return true
  }

  fun removeAt(index: Int): Int {
    if (index >= _size)
      throw IndexOutOfBoundsException()
    val value = _data[index]
    _data[index] = _data[--_size]
    return value
  }

  private var _iterator: Iterator? = null

  /**
   * Gets an iterator to iterate over the elements of this [IntBag].
   *
   * The returned
   */
  operator fun iterator(): IntIterator {
    var iterator = _iterator
    if (iterator != null) {
      _iterator = null
    } else {
      iterator = Iterator()
    }
    return iterator
  }

  private fun grow(capacity: Int) {
    _data = _data.copyOf(capacity)
  }

  private inner class Iterator : IntIterator() {
    var index = 0

    override fun hasNext(): Boolean = index < _size

    override fun nextInt(): Int {
      val offset = index - _size
      if (offset == 0)
        throw NoSuchElementException()
      // Recycle the iterator
      if (offset == 1) // Is last element
        _iterator = this
      return _data[index++]
    }
  }
}
