package cz.cvut.omo.sp.tech.device.state;

import cz.cvut.omo.sp.tech.device.Device;

/**
 * The `ActiveState` class represents the active state of a device. In this state, the device
 * consumes power according to its base power consumption, and its energy state reflects this.
 */
public class ActiveState implements State {

    private Device device;

    /**
     * Constructs an `ActiveState` object associated with the given device.
     *
     * @param device The device in the active state.
     */
    public ActiveState(Device device) {
        this.device = device;
    }

    /**
     * Sets the power consumption of the device to its base power and updates its energy state.
     *
     * @return The power consumption value set for the device.
     */
    @Override
    public int setPower() {
        int power = device.getBasePower();
        device.getEnergy().setPower(power);
        return power;
    }

    /**
     * Returns a string representation of the `ActiveState`, which is "ACTIVE".
     *
     * @return The string "ACTIVE" to represent the active state.
     */
    @Override
    public String toString() {
        return "ACTIVE";
    }
}
