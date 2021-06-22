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
 * When using a shared component, entities with the same shared component
 * will be grouped together. Use shared components when many entities share
 * the same data and it is more efficient to process all the entities with a
 * specific value together.
 */
interface SharedComponent : Component
