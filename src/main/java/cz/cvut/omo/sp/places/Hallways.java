package cz.cvut.omo.sp.places;

/**
 * The `Hallways` class represents a hallway or corridor within a building.
 * It is identified by a unique ID and provides methods to get and set its ID.
 */
public class Hallways {
    private int id;

    /**
     * Constructs a `Hallways` object with the specified ID.
     *
     * @param id The unique identifier for the hallway or corridor.
     */
    public Hallways(int id) {
        this.id = id;
    }

    /**
     * Returns a string representation of the `Hallways` object.
     *
     * @return A string containing the hallway's ID.
     */
    @Override
    public String toString() {
        return "Hallways{" +
                "id=" + id +
                '}';
    }

    /**
     * Gets the ID of the hallway or corridor.
     *
     * @return The unique identifier for the hallway or corridor.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the hallway or corridor.
     *
     * @param id The new unique identifier for the hallway or corridor.
     */
    public void setId(int id) {
        this.id = id;
    }
}
