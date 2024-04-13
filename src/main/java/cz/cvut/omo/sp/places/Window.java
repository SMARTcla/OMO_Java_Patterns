package cz.cvut.omo.sp.places;

import cz.cvut.omo.sp.events.TypeEvent;
import cz.cvut.omo.sp.facade.Facade;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Window implements Observer {

    private int id;
    private Room room;

    /**
     * @param id   unique id within the room.
     * @param room the Room in which the window is located.
     */
    public Window(int id, Room room) {
        this.id = id;
        this.room = room;
    }

    @Override
    public String toString() {
        return "Window{" +
                "id=" + id +
                '}';
    }

    @Override
    public void update(TypeEvent type, LocalDateTime dateTime, Facade facade) {
    }
}
