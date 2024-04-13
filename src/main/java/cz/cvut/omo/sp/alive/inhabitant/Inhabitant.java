package cz.cvut.omo.sp.alive.inhabitant;

import cz.cvut.omo.sp.events.Event;
import cz.cvut.omo.sp.Entity;
import cz.cvut.omo.sp.places.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * An abstract class representing an inhabitant in a simulation.
 *
 * This class defines common attributes and behaviors for all inhabitants in the simulation.
 */
public abstract class Inhabitant implements Entity, Observer {

    /** The name of the inhabitant. */
    protected String name;

    /** The type of the inhabitant. */
    private TypeInhabitant type;

    /** The current state of the inhabitant. */
    protected StateInhabitant state;

    /** The list of events associated with the inhabitant. */
    protected List<Event> events;

    /**
     * Creates a new inhabitant with the specified name and type.
     *
     * @param name The name of the inhabitant.
     * @param type The type of the inhabitant.
     */
    public Inhabitant(String name, TypeInhabitant type) {
        this.name = name;
        this.type = type;
    }

    /**
     * Gets the current state of the inhabitant.
     *
     * @return The current state of the inhabitant.
     */
    public StateInhabitant getState() {
        return state;
    }

    /**
     * Gets the name of the inhabitant.
     *
     * @return The name of the inhabitant.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the inhabitant.
     *
     * @param name The new name of the inhabitant.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the type of the inhabitant.
     *
     * @return The type of the inhabitant.
     */
    public TypeInhabitant getType() {
        return type;
    }

    /**
     * Sets the type of the inhabitant.
     *
     * @param type The new type of the inhabitant.
     */
    public void setType(TypeInhabitant type) {
        this.type = type;
    }

    /**
     * Gets the list of events associated with the inhabitant.
     *
     * @return The list of events associated with the inhabitant.
     */
    public List<Event> getEvents() {
        return events;
    }

    /**
     * Sets the list of events associated with the inhabitant.
     *
     * @param events The new list of events associated with the inhabitant.
     */
    public void setEvents(List<Event> events) {
        this.events = events;
    }

    /**
     * Sets the state of the inhabitant.
     *
     * @param state The new state of the inhabitant.
     */
    public void setState(StateInhabitant state) {
        this.state = state;
    }

    /**
     * Adds an event to the list of events associated with the inhabitant.
     *
     * @param event The event to be added.
     * @throws NullPointerException if the provided event is null.
     */
    public void addEvent(Event event) {
        Objects.requireNonNull(event, "Event cannot be null");
        if (events == null) {
            events = new ArrayList<>();
        }
        events.add(event);
    }
}
