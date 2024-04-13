package cz.cvut.omo.sp.alive.pet;

/**
 * Enum representing the possible actions that a pet can perform in a simulation.
 *
 * This enum defines the various actions that a pet can take during the simulation,
 * including FEED, DRINK, WALK, PLAY, SLEEP, and FLY (if applicable to the pet type).
 */
public enum ActionTypePet {
    /** The pet is being fed. */
    FEED,

    /** The pet is drinking. */
    DRINK,

    /** The pet is going for a walk. */
    WALK,

    /** The pet is playing. */
    PLAY,

    /** The pet is sleeping. */
    SLEEP,

    /** The pet is flying (if applicable to the pet type). */
    FLY;
}
