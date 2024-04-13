package cz.cvut.omo.sp.tech.device;

import java.util.List;

/**
 * An interface representing a composite component in the composite pattern.
 *
 * This interface defines methods for adding and removing child components, accessing child components, and getting
 * information about the composite component, such as its name and type.
 */
public interface Composite {
    /**
     * Add a child component to the composite.
     *
     * @param component The child component to add.
     */
    void add(Composite component);

    /**
     * Remove a child component from the composite.
     *
     * @param component The child component to remove.
     */
    void remove(Composite component);

    /**
     * Get a specific child component by its index.
     *
     * @param index The index of the child component.
     * @return The child component at the specified index.
     */
    Composite getChild(int index);

    /**
     * Get a list of all child components.
     *
     * @return A list of child components.
     */
    List<Composite> getChildren();

    /**
     * Get the name of the composite component.
     *
     * @return The name of the composite component.
     */
    String getName();

    /**
     * Get the type of the composite component.
     *
     * @return The type of the composite component.
     */
    String getType();
}
