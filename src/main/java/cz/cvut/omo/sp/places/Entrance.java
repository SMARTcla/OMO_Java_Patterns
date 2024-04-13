package cz.cvut.omo.sp.places;

/**
 * The `Entrance` class represents an entrance or access point in a location.
 * It is identified by a unique ID and provides methods to get and set its ID.
 */
public class Entrance {
    private int id;

    /**
     * Constructs an `Entrance` object with the specified ID.
     *
     * @param id The unique identifier for the entrance.
     */
    public Entrance(int id) {
        this.id = id;
    }

    /**
     * Returns a string representation of the `Entrance` object.
     *
     * @return A string containing the entrance's ID.
     */
    @Override
    public String toString() {
        return "Entrance{" +
                "id=" + id +
                '}';
    }

    /**
     * Gets the ID of the entrance.
     *
     * @return The unique identifier for the entrance.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the entrance.
     *
     * @param id The new unique identifier for the entrance.
     */
    public void setId(int id) {
        this.id = id;
    }
}
