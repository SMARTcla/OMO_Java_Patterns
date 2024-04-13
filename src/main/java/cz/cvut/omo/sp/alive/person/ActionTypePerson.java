package cz.cvut.omo.sp.alive.person;

/**
 * Enum representing the possible actions that a person can perform in a simulation.
 *
 * This enum defines the various actions that a person can take during the simulation,
 * including SLEEP, EAT, TIME_DEVICE, WALK, LEAVE_HOME, ATTEND_HOME, FIX_DEVICE, OFF_DEVICE,
 * ON_DEVICE, UPDATE_DEVICE, USAGE_DEVICE, USAGE_AUTO, PLAY, and WAIT.
 */
public enum ActionTypePerson {
    /** The person is sleeping. */
    SLEEP,

    /** The person is eating. */
    EAT,

    /** The person is interacting with a time device. */
    TIME_DEVICE,

    /** The person is walking. */
    WALK,

    /** The person is leaving their home. */
    LEAVE_HOME,

    /** The person is attending their home. */
    ATTEND_HOME,

    /** The person is fixing a device. */
    FIX_DEVICE,

    /** The person is turning off a device. */
    OFF_DEVICE,

    /** The person is turning on a device. */
    ON_DEVICE,

    /** The person is updating a device. */
    UPDATE_DEVICE,

    /** The person is using a device. */
    USAGE_DEVICE,

    /** The person is using an automobile. */
    USAGE_AUTO,

    /** The person is playing. */
    PLAY,

    /** The person is waiting. */
    WAIT;
}
