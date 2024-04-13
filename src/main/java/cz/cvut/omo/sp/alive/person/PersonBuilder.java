package cz.cvut.omo.sp.alive.person;

import cz.cvut.omo.sp.alive.inhabitant.StateInhabitant;
import cz.cvut.omo.sp.alive.inhabitant.TypeInhabitant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A builder class for creating instances of Person objects.
 *
 * This class provides a convenient way to construct Person objects with various attributes, including name,
 * state, age group, and driving license status.
 */
public class PersonBuilder {

    private static final Logger LOGGER = LogManager.getLogger(PersonBuilder.class.getName());

    /** The name of the person. */
    private String name;

    /** The state of the person. */
    private StateInhabitant state;

    /** The age group of the person. */
    private AgeGroup ageGroup;

    /** The driving license status of the person. */
    private boolean drivingLicense;

    /**
     * Creates a new instance of PersonBuilder.
     */
    public PersonBuilder() {
    }

    /**
     * Sets the name of the person.
     *
     * @param name The name of the person.
     * @return This PersonBuilder instance.
     */
    public PersonBuilder withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Sets the state of the person.
     *
     * @param state The state of the person.
     * @return This PersonBuilder instance.
     */
    public PersonBuilder withState(StateInhabitant state) {
        this.state = state;
        return this;
    }

    /**
     * Sets the age group of the person based on their age.
     *
     * @param age The age of the person.
     * @return This PersonBuilder instance.
     */
    public PersonBuilder withAgeGroup(long age) {
        this.ageGroup = getAgeGroup(age);
        return this;
    }

    /**
     * Sets the driving license status of the person.
     *
     * @param drivingLicense The driving license status of the person.
     * @return This PersonBuilder instance.
     */
    public PersonBuilder withDrivingLicense(boolean drivingLicense) {
        this.drivingLicense = drivingLicense;
        return this;
    }

    /**
     * Determines the age group of the person based on their age.
     *
     * @param age The age of the person.
     * @return The corresponding age group.
     */
    private AgeGroup getAgeGroup(long age) {
        if (age <= 5) return AgeGroup.BABY;
        else if (age <= 18) return AgeGroup.CHILD;
        return AgeGroup.ADULT;
    }

    /**
     * Gets the state of the person.
     *
     * @return The state of the person.
     */
    public StateInhabitant getState() {
        return state;
    }

    /**
     * Builds a Person object with the specified attributes.
     *
     * @return The constructed Person object, or null if required arguments are not provided.
     */
    public Person build() {
        if (this.name == null || this.state == null || this.ageGroup == null) {
            LOGGER.warn("Error in Person");
            return null;
        }
        Person person = new Person(this.name, TypeInhabitant.PERSON, this.ageGroup);
        person.setState(this.state);
        person.setActions(setActionTypePerson(person, this.drivingLicense));
        return person;
    }

    /**
     * Sets the action types for the person based on age and driving license status.
     *
     * @param person           The person for whom action types are set.
     * @param haveDrivingLicense The driving license status of the person.
     * @return A list of action types for the person.
     */
    private List<ActionTypePerson> setActionTypePerson(Person person, Boolean haveDrivingLicense) {
        List<ActionTypePerson> actions = new ArrayList<>();
        Collections.addAll(actions, ActionTypePerson.SLEEP, ActionTypePerson.PLAY, ActionTypePerson.EAT, ActionTypePerson.WALK);
        if (person.getAgeGroup() != AgeGroup.BABY) {
            Collections.addAll(actions, ActionTypePerson.LEAVE_HOME, ActionTypePerson.USAGE_AUTO,
                    ActionTypePerson.ON_DEVICE, ActionTypePerson.OFF_DEVICE, ActionTypePerson.USAGE_DEVICE);
        }
        if (person.getAgeGroup() == AgeGroup.ADULT) {
            actions.add(ActionTypePerson.FIX_DEVICE);
            if (haveDrivingLicense)
                actions.add(ActionTypePerson.USAGE_AUTO);
        }
        return actions;
    }
}
