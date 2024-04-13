package cz.cvut.omo.sp.places;

/**
 * The `Garden` class represents a garden area or outdoor space.
 * It is identified by a unique ID and provides methods to get and set its ID.
 */
public class Garden {
    private int id;

    /**
     * Constructs a `Garden` object with the specified ID.
     *
     * @param id The unique identifier for the garden area.
     */
    public Garden(int id) {
        this.id = id;
    }

    /**
     * Returns a string representation of the `Garden` object.
     *
     * @return A string containing the garden's ID.
     */
    @Override
    public String toString() {
        return "Garden{" +
                "id=" + id +
                '}';
    }

    /**
     * Gets the ID of the garden.
     *
     * @return The unique identifier for the garden area.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the garden.
     *
     * @param id The new unique identifier for the garden area.
     */
    public void setId(int id) {
        this.id = id;
    }
}
