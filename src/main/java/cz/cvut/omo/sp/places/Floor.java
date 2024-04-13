package cz.cvut.omo.sp.places;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The `Floor` class represents a floor within a building. It contains a unique identifier (ID)
 * and a list of rooms on the floor.
 */
@Getter
@Setter
public class Floor {

    private int id;
    private List<Room> rooms;

    /**
     * @param id unique id floor.
     */
    public Floor(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Floor{" +
                "id=" + id +
                ", rooms=" + rooms +
                '}';
    }

    public void addRoom(Room room) {
        Objects.requireNonNull(room);
        if (rooms == null)
            rooms = new ArrayList<>();
        rooms.add(room);
    }
}
