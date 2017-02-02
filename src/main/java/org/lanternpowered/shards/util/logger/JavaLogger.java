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

import java.util.Arrays;

final class JavaLogger implements Logger {

    private final java.util.logging.Logger logger;

    JavaLogger(java.util.logging.Logger logger) {
        this.logger = logger;
    }

    @Override
    public void error(String message) {
        this.logger.severe(message);
    }

    @Override
    public void error(String message, Throwable throwable) {
        this.logger.severe(message);
        throwable.printStackTrace();
    }

    @Override
    public void error(String message, Object... args) {
        if (args.length > 0 && args[args.length - 1] instanceof Throwable) {
            error(format(message, Arrays.copyOf(args, args.length - 1)), (Throwable) args[args.length - 1]);
        } else {
            error(format(message, args));
        }
    }

    @Override
    public void warn(String message) {
        this.logger.warning(message);
    }

    @Override
    public void warn(String message, Object... args) {
        warn(format(message, args));
    }

    @Override
    public void info(String message) {
        this.logger.info(message);
    }

    @Override
    public void info(String message, Object... args) {
        info(format(message, args));
    }

    private String format(String message, Object... args) {
        return String.format(message.replaceAll("\\{\\}", "%s"), args);
    }
}
