package cz.cvut.omo.sp.alive.inhabitant;

/**
 * Enum representing the possible states of an inhabitant in a simulation.
 *
 * This enum defines the various states that an inhabitant can be in during the simulation,
 * including OUTSIDE, WAIT, SLEEP, and ACTIVITY.
 */
public enum StateInhabitant {
    /** The inhabitant is outside. */
    OUTSIDE,

    /** The inhabitant is waiting. */
    WAIT,

    /** The inhabitant is sleeping. */
    SLEEP,

    /** The inhabitant is engaged in some activity. */
    ACTIVITY;
}
