package cz.cvut.omo.sp.events;

/**
 * Enum representing the possible types of events in a simulation.
 *
 * This enum defines various types of events that can occur during the simulation,
 * including ACTION, WAIT, DANGER, AIR_QUALITY_CHANGE, WATER_LEAK_DETECTED, WATER_LEAK_RESOLVED,
 * SOUND_LEVEL_CHANGE, STRUCTURAL_CRACK_DETECTED, STRUCTURAL_VIBRATION_DETECTED, GAS_FLOW_CHANGE,
 * WATER_FLOW_CHANGE, ENERGY_CONSUMPTION_CHANGE, MOTION_RESOLVED, MOTION_DETECTED, LIGHT_DAY,
 * LIGHT_NIGHT, SMART_ENERGY_METER, LOWER_TEMPERATURE, RAISE_TEMPERATURE, BLOCK_DEVICE, ON_DEVICE,
 * OFF_DEVICE, TIME_DEVICE, ADD_INFO, USAGE_DEVICE, FIX_DEVICE, USE_CAR, and SOFTWARE_UPDATE.
 */
public enum TypeEvent {
    /** An action event. */
    ACTION,

    /** A wait event. */
    WAIT,

    /** A danger event. */
    DANGER,

    /** An event representing changes in air quality. */
    AIR_QUALITY_CHANGE,

    /** An event representing the detection of a water leak. */
    WATER_LEAK_DETECTED,

    /** An event representing the resolution of a water leak. */
    WATER_LEAK_RESOLVED,

    /** An event representing changes in sound levels. */
    SOUND_LEVEL_CHANGE,

    /** An event representing the detection of a structural crack. */
    STRUCTURAL_CRACK_DETECTED,

    /** An event representing the detection of structural vibrations. */
    STRUCTURAL_VIBRATION_DETECTED,

    /** An event representing changes in gas flow. */
    GAS_FLOW_CHANGE,

    /** An event representing changes in water flow. */
    WATER_FLOW_CHANGE,

    /** An event representing changes in energy consumption. */
    ENERGY_CONSUMPTION_CHANGE,

    /** An event representing the resolution of motion. */
    MOTION_RESOLVED,

    /** An event representing the detection of motion. */
    MOTION_DETECTED,

    /** An event representing daylight. */
    LIGHT_DAY,

    /** An event representing nighttime. */
    LIGHT_NIGHT,

    /** An event related to smart energy meter data. */
    SMART_ENERGY_METER,

    /** An event to lower temperature. */
    LOWER_TEMPERATE,

    /** An event to raise temperature. */
    RAISE_TEMPERATE,

    /** An event to block a device. */
    BLOCK_DEVICE,

    /** An event to turn on a device. */
    ON_DEVICE,

    /** An event to turn off a device. */
    OFF_DEVICE,

    /** An event related to time devices. */
    TIME_DEVICE,

    /** An event to add additional information. */
    ADD_INFO,

    /** An event related to device usage. */
    USAGE_DEVICE,

    /** An event related to device repair. */
    FIX_DEVICE,

    /** An event related to using a car. */
    USE_CAR,

    /** An event related to software updates. */
    SOFTWARE_UPDATE;
}
