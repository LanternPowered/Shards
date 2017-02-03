package org.lanternpowered.shards.dependency;

import org.lanternpowered.shards.Component;
import org.lanternpowered.shards.ComponentHolder;
import org.lanternpowered.shards.Dyn;
import org.lanternpowered.shards.Opt;

/**
 * Represents how strict a dependency {@link Component} type should be present
 * on a {@link ComponentHolder} for the depending {@link Component} to function.
 */
public enum DependencyType {

    /**
     * The dependency {@link Component} is optionally available for
     * the target {@link ComponentHolder} in order for the depending
     * {@link Component} to function.
     * <p>
     * The equivalent of this by using annotations is specifying a
     * {@link Opt} with a specific {@link Component} type, for example:
     * <pre>
     * {@code
     *     @Inject Opt<FooComponent> optFooComponent;
     * }
     * </pre>
     */
    OPTIONAL,

    /**
     * The dependency {@link Component} must be available for the
     * the target {@link ComponentHolder} in order for the depending
     * {@link Component} to function.
     * <p>
     * The equivalent of this by using annotations is specifying a
     * a specific {@link Component} type, for example:
     * <pre>
     * {@code
     *     @Inject FooComponent fooComponent;
     * }
     * </pre>
     */
    REQUIRED,

    /**
     * The dependency {@link Component} must be available for the
     * the target {@link ComponentHolder} in order for the depending
     * {@link Component} to function. But allows swapping of the
     * implementation through the methods
     * {@link ComponentHolder#replaceComponent(Class, Class)} and
     * {@link ComponentHolder#replaceComponent(Class, Component)}.
     * <p>
     * The equivalent of this by using annotations is specifying a
     * {@link Dyn} with a specific {@link Component} type, for example:
     * <pre>
     * {@code
     *     @Inject Dyn<FooComponent> fooComponent;
     * }
     * </pre>
     */
    REQUIRED_DYNAMIC,
}
