package cz.cvut.omo.sp.places;

/**
 * The `Room` class represents a room within a building or a living space. It is identified by a unique ID
 * and provides methods to get and set its ID.
 */
public class Room {

    private int id;

    /**
     * Constructs a `Room` object with the specified ID.
     *
     * @param id The unique identifier for the room.
     */
    public Room(int id) {
        this.id = id;
    }

    /**
     * Returns a string representation of the `Room` object.
     *
     * @return A string containing the room's ID.
     */
    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                '}';
    }

    /**
     * Gets the ID of the room.
     *
     * @return The unique identifier for the room.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the room.
     *
     * @param id The new unique identifier for the room.
     */
    public void setId(int id) {
        this.id = id;
    }



}
