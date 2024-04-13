package cz.cvut.omo.sp.alive.person;

import cz.cvut.omo.sp.alive.inhabitant.Inhabitant;
import cz.cvut.omo.sp.alive.inhabitant.StateInhabitant;
import cz.cvut.omo.sp.alive.inhabitant.TypeInhabitant;
import cz.cvut.omo.sp.events.TypeEvent;
import cz.cvut.omo.sp.facade.Facade;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Class representing a person in a simulation.
 *
 * This class extends the Inhabitant class and adds specific attributes and behaviors for a person
 * in the simulation, including their age group, available actions, and transport/device usage.
 */
@Getter
@Setter
public class Person extends Inhabitant {

    /** The age group of the person (BABY, CHILD, ADULT, etc.). */
    private AgeGroup ageGroup;

    /** The list of possible actions that a person can perform. */
    private List<ActionTypePerson> actions;

    /**
     * Creates a new person with the specified name, type, and age group.
     *
     * @param name     The name of the person.
     * @param type     The type of the person (TypeInhabitant = PERSON).
     * @param ageGroup The age group of the person (0-5 BABY, 5-18 CHILD, 18+ ADULT).
     */
    public Person(String name, TypeInhabitant type, AgeGroup ageGroup) {
        super(name, type);
        this.ageGroup = ageGroup;
    }

    /**
     * Returns a string representation of the person.
     *
     * @return A string containing the person's name.
     */
    @Override
    public String toString() {
        return " name={" + name + "} ";
    }

    /**
     * Updates the person's state based on the given event type.
     *
     * @param type     The type of the event.
     * @param dateTime The date and time of the event.
     * @param facade   The facade object for coordinating events.
     */
    @Override
    public void update(TypeEvent type, LocalDateTime dateTime, Facade facade) {
        if (type == TypeEvent.DANGER) {
            facade.theEndLastEvent(this, dateTime);
            this.setState(StateInhabitant.OUTSIDE);
        }
    }

    /**
     * Returns a string representation of the person, including their name, events, state, age group, and actions.
     *
     * @return A string representation of the person.
     */
    public String toStringForLog() {
        return "Person{" +
                "name='" + name + '\'' +
                ", events=" + events +
                ", state=" + state +
                ", ageGroup=" + ageGroup +
                ", actions=" + actions +
                '}';
    }

    /**
     * Checks if the person can use transportation.
     *
     * @return true if the person can use transportation; otherwise, false.
     */
    public boolean canUseTransport() {
        return actions.contains(ActionTypePerson.USAGE_AUTO);
    }

    /**
     * Checks if the person can use devices.
     *
     * @return true if the person can use devices; otherwise, false.
     */
    public boolean canUseDevice() {
        return actions.contains(ActionTypePerson.USAGE_DEVICE);
    }
}
