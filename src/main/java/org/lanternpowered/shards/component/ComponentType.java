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
package org.lanternpowered.shards.component;

import static com.google.common.base.Preconditions.checkNotNull;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;
import org.lanternpowered.shards.Component;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Nullable;

public final class ComponentType<T extends Component> {

    public static <T extends Component> ComponentType<T> get(Class<T> componentType) {
        checkNotNull(componentType, "componentType");
        //noinspection unchecked
        return cache.get(componentType);
    }

    private static final LoadingCache<Class<? extends Component>, ComponentType> cache =
            Caffeine.newBuilder().build(ComponentType::load);

    private static ComponentType load(Class<? extends Component> type) {
        /*
        // STEP 1:
        // - Collect all the required dependencies of the current component.
        // - Collect all the object types that will be dynamically registered
        //   to the Guice Module.
        final Set<ObjectMapping> typeMappings = new HashSet<>();
        final Multimap<Class<? extends Component>, Dependency> requirements = HashMultimap.create();
        // First check requirements of the type
        final Requirements anno = type.getAnnotation(Requirements.class);
        if (anno != null) {
            final Requirement[] annos1 = anno.value();
            for (Requirement anno1 : annos1) {
                requirements.put(anno1.type(), new Dependency(anno1.type(), anno1.autoAttach()));
            }
        }
        // Scan all the fields for required components and mappings that should be registered
        for (Field field : type.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.getAnnotation(Inject.class) == null &&
                    field.getAnnotation(javax.inject.Inject.class) == null) {
                continue;
            }
            final Class<?> fieldType = field.getType();
            typeMappings.add(new ObjectMapping(fieldType, field));
            if (Component.class.isAssignableFrom(fieldType)) {
                //noinspection unchecked
                final Class<? extends Component> fieldType1 = (Class<? extends Component>) fieldType;
                final AutoAttach autoAttach = field.getAnnotation(AutoAttach.class);
                requirements.put(fieldType1, new Dependency(fieldType1, autoAttach != null));
            } else if (Opt.class.isAssignableFrom(fieldType)) {
                final TypeToken typeToken = TypeToken.of(field.getGenericType());
                final Class<?> element = typeToken.resolveType(Opt.class.getTypeParameters()[0]).getRawType();
                System.out.println(typeToken + " -> " + element.getName());
            }
        }
        // Scan all the methods and constructors for required components and mappings that should be registered
        for (Executable executable : Iterables.concat(Arrays.asList(type.getDeclaredMethods()), Arrays.asList(type.getDeclaredConstructors()))) {
            executable.setAccessible(true);
            if (executable.getAnnotation(Inject.class) == null &&
                    executable.getAnnotation(javax.inject.Inject.class) == null) {
                continue;
            }
            final Class<?>[] parameterTypes = executable.getParameterTypes();
            final Type[] genericParameterTypes = executable.getGenericParameterTypes();
            final AnnotatedType[] parameterAnnotations = executable.getAnnotatedParameterTypes();
            for (int i = 0; i < parameterTypes.length; i++) {
                typeMappings.add(new ObjectMapping(parameterTypes[i], parameterAnnotations[i]));
                if (Component.class.isAssignableFrom(parameterTypes[i])) {
                    final AutoAttach autoAttach = parameterAnnotations[i].getAnnotation(AutoAttach.class);
                    //noinspection unchecked
                    final Class<? extends Component> fieldType1 = (Class<? extends Component>) parameterTypes[i];
                    requirements.put(fieldType1, new Dependency(fieldType1, autoAttach != null));
                } else if (Opt.class.isAssignableFrom(parameterTypes[i])) {
                    final TypeToken typeToken = TypeToken.of(genericParameterTypes[i]);
                    final Class<?> element = typeToken.resolveType(Opt.class.getTypeParameters()[0]).getRawType();
                    System.out.println(typeToken + " -> " + element.getName());
                }
            }
        }
        // Scan the superclasses and interfaces
        final Class<?> superClass = type.getSuperclass();
        if (superClass != null && Component.class.isAssignableFrom(superClass)) {
            // We CANNOT use the cache here, that blocks the thread for some reason, so don't do it
            //noinspection unchecked
            final ComponentType<?> type1 = load((Class<? extends Component>) superClass);
            type1.requirements.entrySet().forEach(e -> requirements.put(e.getKey(), e.getValue()));
        }
        final Class<?>[] interfaces = type.getInterfaces();
        for (Class<?> interf : interfaces) {
            if (Component.class.isAssignableFrom(interf)) {
                // We CANNOT use the cache here, that blocks the thread for some reason, so don't do it
                //noinspection unchecked
                final ComponentType<?> type1 = load((Class<? extends Component>) interf);
                type1.requirements.entrySet().forEach(e -> requirements.put(e.getKey(), e.getValue()));
            }
        }
        // Merge the DependencyWrappers for every component type
        final Map<Class<? extends Component>, Dependency> mergedWrappers = new HashMap<>();
        for (Map.Entry<Class<? extends Component>, Collection<Dependency>> entry : requirements.asMap().entrySet()) {
            boolean autoAttach = false;
            for (Dependency wrapper : entry.getValue()) {
                if (wrapper.getAutoAttach()) {
                    autoAttach = true;
                    break;
                }
            }
            mergedWrappers.put(entry.getKey(), new Dependency(entry.getKey(), autoAttach));
        }
        // Now we have to merge the requirements for the most exact dependency requirements
        final Set<Class<? extends Component>> classes = new HashSet<>(mergedWrappers.keySet());
        for (Class<? extends Component> clazz : classes) {
            // Ignore classes that are already removed
            if (!mergedWrappers.containsKey(clazz)) {
                continue;
            }
            final List<Class<? extends Component>> subClasses = new ArrayList<>();
            scanSubClasses(clazz, subClasses);
            subClasses.forEach(mergedWrappers::remove);
        }
        //noinspection unchecked
        return new ComponentType(type, ImmutableMap.copyOf(mergedWrappers), null);*/
        return new ComponentType(type, ImmutableMap.of());
    }

    /*
    private static void scanSubClasses(Class<? extends Component> target, List<Class<? extends Component>> subClasses) {
        final Class<?>[] interfaces = target.getInterfaces();
        for (Class<?> interf : interfaces) {
            if (Component.class.isAssignableFrom(interf)) {
                //noinspection unchecked
                final Class<? extends Component> interf1 = (Class<? extends Component>) interf;
                if (!subClasses.contains(interf1)) {
                    subClasses.add(interf1);
                    // Collect sub interfaces
                    scanSubClasses(interf1, subClasses);
                }
            }
        }
        final Class<?> superClass = target.getSuperclass();
        if (superClass != null && Component.class.isAssignableFrom(superClass)) {
            //noinspection unchecked
            final Class<? extends Component> superClass1 = (Class<? extends Component>) superClass;
            if (!subClasses.contains(superClass1)) {
                subClasses.add(superClass1);
                // Collect super class sub classes
                scanSubClasses(superClass1, subClasses);
            }
        }
    }*/

    private final Class<T> componentType;
    private final Map<Class<? extends Component>, DependencySpec> requirements;

    private ComponentType(Class<T> componentType, Map<Class<? extends Component>, DependencySpec> requirements) {
        this.componentType = componentType;
        this.requirements = requirements;
    }

    /**
     * Gets a {@link Collection} with all the {@link DependencySpec}s
     * in this collection.
     *
     * @return The requirements
     */
    public Collection<DependencySpec> getRequirements() {
        return this.requirements.values();
    }

    /**
     * Gets the {@link Class} of the component type.
     *
     * @return The class
     */
    public Class<T> getComponentType() {
        return this.componentType;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("type", this.componentType.getName())
                .toString();
    }
}
