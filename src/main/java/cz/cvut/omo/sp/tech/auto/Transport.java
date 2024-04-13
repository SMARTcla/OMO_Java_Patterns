package cz.cvut.omo.sp.tech.auto;

import cz.cvut.omo.sp.events.*;
import cz.cvut.omo.sp.alive.person.Person;
import cz.cvut.omo.sp.Entity;
import cz.cvut.omo.sp.facade.Facade;
import cz.cvut.omo.sp.places.Observer;

import java.time.*;
import java.util.*;

/**
 * The `Transport` class represents a mode of transportation, such as a car, within the system.
 * It provides information about the transport's name, type, current user, expected passengers,
 * and a list of events associated with it.
 */
public class Transport implements Entity, Observer {

    private String name;
    private TransportType type;

    private Person currentUser;
    private Set<Person> expects;
    private List<Event> events;

    /**
     * Constructs a `Transport` object with the specified name and type.
     *
     * @param name The name of the transport.
     * @param type The type of the transport (e.g., car).
     */
    public Transport(String name, TransportType type) {
        this.name = name;
        this.type = type;
    }

    /**
     * Returns a string representation of the `Transport` object.
     *
     * @return A string containing the transport's name.
     */
    @Override
    public String toString() {
        return "name={" + name + "} ";
    }

    /**
     * Updates the transport in response to an event.
     *
     * @param type    The type of event that occurred.
     * @param dateTime The timestamp indicating when the event occurred.
     * @param facade  The `Facade` object that provides a simplified interface to the underlying system.
     */
    @Override
    public void update(TypeEvent type, LocalDateTime dateTime, Facade facade) {
        if (type == TypeEvent.DANGER) {
            this.setExpects(null);
        }
    }

    /**
     * Checks if the transport is of type "car."
     *
     * @return `true` if the transport is a car, `false` otherwise.
     */
    public boolean isAuto() {
        return type == TransportType.CAR;
    }

    /**
     * Adds a person to the list of expected passengers.
     *
     * @param person The person to be added to the expected passengers.
     * @throws NullPointerException if the provided person is null.
     */
    public void addPerson(Person person) {
        Objects.requireNonNull(person);
        if (expects == null)
            expects = new HashSet<>();
        expects.add(person);
    }

    /**
     * Removes a person from the list of expected passengers.
     *
     * @param person The person to be removed from the expected passengers.
     * @throws NullPointerException if the provided person is null.
     */
    public void removePerson(Person person) {
        Objects.requireNonNull(person);
        if (expects == null) return;
        expects.remove(person);
    }

    /**
     * Adds an event to the list of events associated with the transport.
     *
     * @param event The event to be added.
     * @throws NullPointerException if the provided event is null.
     */
    public void addEvent(Event event) {
        Objects.requireNonNull(event);
        if (events == null)
            events = new ArrayList<>();
        events.add(event);
    }

    /**
     * Gets the set of expected passengers.
     *
     * @return The set of expected passengers.
     */
    public Set<Person> getExpects() {
        return expects;
    }

    /**
     * Sets the set of expected passengers.
     *
     * @param expects The set of expected passengers.
     */
    public void setExpects(Set<Person> expects) {
        this.expects = expects;
    }

    /**
     * Gets the current user of the transport.
     *
     * @return The current user of the transport.
     */
    public Person getCurrentUser() {
        return currentUser;
    }

    /**
     * Sets the current user of the transport.
     *
     * @param currentUser The current user of the transport.
     */
    public void setCurrentUser(Person currentUser) {
        this.currentUser = currentUser;
    }

    /**
     * Generates a string representation of the `Transport` object for logging purposes.
     *
     * @return A string containing detailed information about the transport.
     */
    public String toStringForLog() {
        return "Transport{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", currentUser=" + currentUser +
                ", expects=" + expects +
                ", events=" + events +
                '}';
    }

    /**
     * Gets the name of the transport.
     *
     * @return The name of the transport.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the transport.
     *
     * @param name The new name for the transport.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the type of the transport.
     *
     * @return The type of the transport.
     */
    public TransportType getType() {
        return type;
    }

    /**
     * Sets the type of the transport.
     *
     * @param type The new type for the transport.
     */
    public void setType(TransportType type) {
        this.type = type;
    }

    /**
     * Gets the list of events associated with the transport.
     *
     * @return The list of events associated with the transport.
     */
    public List<Event> getEvents() {
        return events;
    }

    /**
     * Sets the list of events associated with the transport.
     *
     * @param events The new list of events associated with the transport.
     */
    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
