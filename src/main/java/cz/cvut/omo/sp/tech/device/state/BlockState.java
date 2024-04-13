package cz.cvut.omo.sp.tech.device.state;

import cz.cvut.omo.sp.tech.device.Device;

/**
 * The `BlockState` class represents the blocked or inactive state of a device. In this state,
 * the device consumes no power, and its energy state is set to zero.
 */
public class BlockState implements State {

    private Device device;

    /**
     * Constructs a `BlockState` object associated with the given device.
     *
     * @param device The device in the blocked state.
     */
    public BlockState(Device device) {
        this.device = device;
    }

    /**
     * Sets the power consumption of the device to zero and updates its energy state accordingly.
     *
     * @return The power consumption value set for the device, which is always 0 in the blocked state.
     */
    @Override
    public int setPower() {
        device.getEnergy().setPower(0);
        return 0;
    }

    /**
     * Returns a string representation of the `BlockState`, which is "BLOCK".
     *
     * @return The string "BLOCK" to represent the blocked state.
     */
    @Override
    public String toString() {
        return "BLOCK";
    }
}
