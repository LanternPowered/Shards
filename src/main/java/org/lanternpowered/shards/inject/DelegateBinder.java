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
package org.lanternpowered.shards.inject;

import com.google.inject.Binding;
import com.google.inject.Key;
import com.google.inject.MembersInjector;
import com.google.inject.Module;
import com.google.inject.PrivateBinder;
import com.google.inject.Provider;
import com.google.inject.Scope;
import com.google.inject.Stage;
import com.google.inject.TypeLiteral;
import com.google.inject.binder.AnnotatedBindingBuilder;
import com.google.inject.binder.AnnotatedConstantBindingBuilder;
import com.google.inject.binder.LinkedBindingBuilder;
import com.google.inject.matcher.Matcher;
import com.google.inject.spi.Dependency;
import com.google.inject.spi.Message;
import com.google.inject.spi.ModuleAnnotatedMethodScanner;
import com.google.inject.spi.ProvisionListener;
import com.google.inject.spi.TypeConverter;
import com.google.inject.spi.TypeListener;
import org.aopalliance.intercept.MethodInterceptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class DelegateBinder implements Binder {

    private final com.google.inject.Binder delegate;

    public DelegateBinder(com.google.inject.Binder delegate) {
        this.delegate = delegate;
    }

    @Override
    public void bindInterceptor(Matcher<? super Class<?>> classMatcher, Matcher<? super Method> methodMatcher, MethodInterceptor... interceptors) {
        this.delegate.bindInterceptor(classMatcher, methodMatcher, interceptors);
    }

    @Override
    public void bindScope(Class<? extends Annotation> annotationType, Scope scope) {
        this.delegate.bindScope(annotationType, scope);
    }

    @Override
    public <T> LinkedBindingBuilder<T> bind(Key<T> key) {
        return this.delegate.bind(key);
    }

    @Override
    public <T> AnnotatedBindingBuilder<T> bind(TypeLiteral<T> typeLiteral) {
        return this.delegate.bind(typeLiteral);
    }

    @Override
    public <T> AnnotatedBindingBuilder<T> bind(Class<T> type) {
        return this.delegate.bind(type);
    }

    @Override
    public AnnotatedConstantBindingBuilder bindConstant() {
        return this.delegate.bindConstant();
    }

    @Override
    public <T> void requestInjection(TypeLiteral<T> type, T instance) {
        this.delegate.requestInjection(type, instance);
    }

    @Override
    public void requestInjection(Object instance) {
        this.delegate.requestInjection(instance);
    }

    @Override
    public void requestStaticInjection(Class<?>... types) {
        this.delegate.requestStaticInjection(types);
    }

    @Override
    public void install(Module module) {
        this.delegate.install(module);
    }

    @Override
    public Stage currentStage() {
        return this.delegate.currentStage();
    }

    @Override
    public void addError(String message, Object... arguments) {
        this.delegate.addError(message, arguments);
    }

    @Override
    public void addError(Throwable t) {
        this.delegate.addError(t);
    }

    @Override
    public void addError(Message message) {
        this.delegate.addError(message);
    }

    @Override
    public <T> Provider<T> getProvider(Key<T> key) {
        return this.delegate.getProvider(key);
    }

    @Override
    public <T> Provider<T> getProvider(Dependency<T> dependency) {
        return this.delegate.getProvider(dependency);
    }

    @Override
    public <T> Provider<T> getProvider(Class<T> type) {
        return this.delegate.getProvider(type);
    }

    @Override
    public <T> MembersInjector<T> getMembersInjector(TypeLiteral<T> typeLiteral) {
        return this.delegate.getMembersInjector(typeLiteral);
    }

    @Override
    public <T> MembersInjector<T> getMembersInjector(Class<T> type) {
        return this.delegate.getMembersInjector(type);
    }

    @Override
    public void convertToTypes(Matcher<? super TypeLiteral<?>> typeMatcher, TypeConverter converter) {
        this.delegate.convertToTypes(typeMatcher, converter);
    }

    @Override
    public void bindListener(Matcher<? super TypeLiteral<?>> typeMatcher, TypeListener listener) {
        this.delegate.bindListener(typeMatcher, listener);
    }

    @Override
    public void bindListener(Matcher<? super Binding<?>> bindingMatcher, ProvisionListener... listeners) {
        this.delegate.bindListener(bindingMatcher, listeners);
    }

    @Override
    public Binder withSource(Object source) {
        this.delegate.withSource(source);
        return this;
    }

    @Override
    public Binder skipSources(Class... classesToSkip) {
        this.delegate.skipSources(classesToSkip);
        return this;
    }

    @Override
    public PrivateBinder newPrivateBinder() {
        return this.delegate.newPrivateBinder();
    }

    @Override
    public void requireExplicitBindings() {
        this.delegate.requireExplicitBindings();
    }

    @Override
    public void disableCircularProxies() {
        this.delegate.disableCircularProxies();
    }

    @Override
    public void requireAtInjectOnConstructors() {
        this.delegate.requireAtInjectOnConstructors();
    }

    @Override
    public void requireExactBindingAnnotations() {
        this.delegate.requireExactBindingAnnotations();
    }

    @Override
    public void scanModulesForAnnotatedMethods(ModuleAnnotatedMethodScanner scanner) {
        this.delegate.scanModulesForAnnotatedMethods(scanner);
    }
}
