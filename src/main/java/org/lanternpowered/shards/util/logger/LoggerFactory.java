/*
 * This file is part of Shards, licensed under the MIT License (MIT).
 *
 * Copyright (c) LanternPowered <https://www.lanternpowered.org>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the Software), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, andor sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED AS IS, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.lanternpowered.shards.util.logger;

import java.util.function.Function;

final class LoggerFactory {

    final static Function<String, Logger> FACTORY;

    static {
        boolean slf4j = false;
        try {
            Class.forName("org.slf4j.impl.StaticLoggerBinder");
            slf4j = true;
        } catch (ClassNotFoundException ignored) {
        }
        if (slf4j) {
            FACTORY = name -> {
                final org.slf4j.Logger logger;
                if (name.isEmpty()) {
                    logger = org.slf4j.LoggerFactory.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME);
                } else {
                    logger = org.slf4j.LoggerFactory.getLogger(name);
                }
                return new Slf4jLogger(logger);
            };
        } else {
            FACTORY = name -> {
                final java.util.logging.Logger logger;
                if (name.isEmpty()) {
                    logger = java.util.logging.Logger.getGlobal();
                } else {
                    logger = java.util.logging.Logger.getLogger(name);
                }
                return new JavaLogger(logger);
            };
        }
    }
}
