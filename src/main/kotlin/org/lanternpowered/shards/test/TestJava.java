/*
 * Shards
 *
 * Copyright (c) LanternPowered <https://www.lanternpowered.org>
 * Copyright (c) contributors
 *
 * This work is licensed under the terms of the MIT License (MIT). For
 * a copy, see 'LICENSE.txt' or <https://opensource.org/licenses/MIT>.
 */
package org.lanternpowered.shards.test;

import org.lanternpowered.shards.component.Component;
import org.lanternpowered.shards.component.ComponentType;
import org.lanternpowered.shards.system.OnAdd;
import org.lanternpowered.shards.system.OnProcess;
import org.lanternpowered.shards.system.System;
import org.lanternpowered.shards.aspect.Aspect;
import org.lanternpowered.shards.jvm.JvmEntityId;

import java.time.Duration;

public class TestJava {

  static class JTestComponent implements Component {

    public static final ComponentType<JTestComponent> TYPE =
      new ComponentType<>() {};

    public double health;
  }

  static class JTestExcludedComponent implements Component {

    public static final ComponentType<JTestExcludedComponent> TYPE =
      new ComponentType<>() {};
  }

  static class JTestSystem extends System {

    JTestSystem() {
      super(Aspect.require(JTestComponent.TYPE)
        .and(Aspect.exclude(JTestExcludedComponent.TYPE)));
    }

    @OnAdd
    void add(@JvmEntityId final int entityId) {
      final JTestComponent component =
        get(entityId, JTestComponent.TYPE);
      component.health = 255.0;
    }

    @OnProcess
    void process(final Duration deltaTime, @JvmEntityId final int entityId) {
      final JTestComponent component =
        get(entityId, JTestComponent.TYPE);
      component.health += 100.0;
    }
  }
}
