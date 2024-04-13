package cz.cvut.omo.sp.tech.device;

import cz.cvut.omo.sp.Entity;
import cz.cvut.omo.sp.tech.device.state.State;
import cz.cvut.omo.sp.places.Room;
import cz.cvut.omo.sp.tech.device.documentation.Documentation;
import cz.cvut.omo.sp.tech.device.energy.Energy;
import cz.cvut.omo.sp.tech.device.energy.EventEnergy;
import cz.cvut.omo.sp.events.Event;
import cz.cvut.omo.sp.alive.inhabitant.Inhabitant;
import cz.cvut.omo.sp.places.Observer;
import cz.cvut.omo.sp.tech.device.state.OffState;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Device implements Entity, Observer {

    private int id;

    private State state;

    private Documentation documentation;
    private String name;
    private DeviceType type;
    private int basePower;
    private Energy energy;

    private int quality;
    private Inhabitant currentUser;
    private List<Inhabitant> expects;
    private List<Event> events;
    private List<EventEnergy> eventEnergy;
    private Room room;

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Device(int id, String name, Room room, DeviceType type, int basePower) {
        this.id = id;
        this.name = name;
        this.room = room;
        this.type = type;
        this.basePower = basePower;
        this.energy = null;
        this.state = new OffState(this);
    }
    /**
     * Gets the unique identifier of the device.
     *
     * @return The device's unique identifier.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the device.
     *
     * @param id The unique identifier to set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the name of the device.
     *
     * @return The device's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the device.
     *
     * @param name The name to set for the device.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the type of the device.
     *
     * @return The device's type.
     */
    public DeviceType getType() {
        return type;
    }

    /**
     * Sets the type of the device.
     *
     * @param type The type to set for the device.
     */
    public void setType(DeviceType type) {
        this.type = type;
    }

    /**
     * Gets the base power consumption of the device.
     *
     * @return The base power consumption of the device.
     */
    public int getBasePower() {
        return basePower;
    }

    /**
     * Sets the base power consumption of the device.
     *
     * @param basePower The base power consumption to set.
     */
    public void setBasePower(int basePower) {
        this.basePower = basePower;
    }

    /**
     * Gets the energy instance associated with the device.
     *
     * @return The energy instance associated with the device.
     */
    public Energy getEnergy() {
        return energy;
    }

    /**
     * Sets the energy instance associated with the device.
     *
     * @param energy The energy instance to set.
     */
    public void setEnergy(Energy energy) {
        this.energy = energy;
    }

    /**
     * Gets the quality rating of the device.
     *
     * @return The quality rating of the device.
     */
    public int getQuality() {
        return quality;
    }

    /**
     * Sets the quality rating of the device.
     *
     * @param quality The quality rating to set for the device.
     */
    public void setQuality(int quality) {
        this.quality = quality;
    }

    /**
     * Gets the current user of the device.
     *
     * @return The current user of the device.
     */
    public Inhabitant getCurrentUser() {
        return currentUser;
    }

    /**
     * Sets the current user of the device.
     *
     * @param currentUser The current user to set for the device.
     */
    public void setCurrentUser(Inhabitant currentUser) {
        this.currentUser = currentUser;
    }

    /**
     * Gets the list of expected users for the device.
     *
     * @return The list of expected users for the device.
     */
    public List<Inhabitant> getExpects() {
        return expects;
    }

    /**
     * Sets the list of expected users for the device.
     *
     * @param expects The list of expected users to set for the device.
     */
    public void setExpects(List<Inhabitant> expects) {
        this.expects = expects;
    }

    /**
     * Gets the list of events associated with the device.
     *
     * @return The list of events associated with the device.
     */
    public List<Event> getEvents() {
        return events;
    }

    /**
     * Sets the list of events associated with the device.
     *
     * @param events The list of events to set for the device.
     */
    public void setEvents(List<Event> events) {
        this.events = events;
    }

    /**
     * Gets the list of event energies associated with the device.
     *
     * @return The list of event energies associated with the device.
     */
    public List<EventEnergy> getEventEnergy() {
        return eventEnergy;
    }

    /**
     * Sets the list of event energies associated with the device.
     *
     * @param eventEnergy The list of event energies to set for the device.
     */
    public void setEventEnergy(List<EventEnergy> eventEnergy) {
        this.eventEnergy = eventEnergy;
    }

    /**
     * Gets the current state of the device.
     *
     * @return The current state of the device.
     */
    public State getState() {
        return state;
    }

    /**
     * Sets the current state of the device.
     *
     * @param state The state to set for the device.
     */
    public void setState(State state) {
        this.state = state;
    }

    /**
     * Adds an inhabitant to the list of expected users for the device.
     *
     * @param inhabitant The inhabitant to add to the list of expected users.
     */
    public void addInhabitant(Inhabitant inhabitant) {
        Objects.requireNonNull(inhabitant);
        if (expects == null)
            expects = new ArrayList<>();
        expects.add(inhabitant);
    }

    /**
     * Removes an inhabitant from the list of expected users for the device.
     *
     * @param inhabitant The inhabitant to remove from the list of expected users.
     */
    public void removeInhabitant(Inhabitant inhabitant) {
        Objects.requireNonNull(inhabitant);
        if (expects == null) return;
        expects.remove(inhabitant);
    }

    /**
     * Adds an event to the list of events associated with the device.
     *
     * @param event The event to add to the list of events.
     */
    public void addEvent(Event event) {
        Objects.requireNonNull(event);
        if (events == null)
            events = new ArrayList<>();
        events.add(event);
    }

    /**
     * Adds an event energy to the list of event energies associated with the device.
     *
     * @param event The event energy to add to the list of event energies.
     */

    public void addEventEnergy(EventEnergy event) {
        Objects.requireNonNull(event);
        if (eventEnergy == null)
            eventEnergy = new ArrayList<>();
        eventEnergy.add(event);
    }

    /**
     * Gets the documentation associated with the device.
     *
     * @return The documentation associated with the device.
     */
    public Documentation getDocumentation() {
        return documentation;
    }

    /**
     * Sets the documentation for the device.
     *
     * @param documentation The documentation to set for the device.
     */
    public void setDocumentation(Documentation documentation) {
        this.documentation = documentation;
    }
}
