/*
 * Shards
 *
 * Copyright (c) LanternPowered <https://www.lanternpowered.org>
 * Copyright (c) contributors
 *
 * This work is licensed under the terms of the MIT License (MIT). For
 * a copy, see 'LICENSE.txt' or <https://opensource.org/licenses/MIT>.
 */
package org.lanternpowered.shards.system

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import org.lanternpowered.shards.entity.EntityContext

/**
 * Represents a context in which a [System] will be executed.
 */
abstract class SystemContext : EntityContext(), CoroutineScope {

  /**
   * A sequence of all the entities in the current universe.
   */
  abstract fun entityQuery(): EntitySeq0

  /**
   * Executes an async task within the scope of the current [System] execution.
   *
   * All launched async tasks will be waited for after [System.execute]. If
   * the async task returns a result that is needed for execution, then it can
   * be awaited for using [Deferred.await].
   */
  abstract fun <R> async(
    operation: suspend CoroutineScope.() -> R
  ): Deferred<R>
}
