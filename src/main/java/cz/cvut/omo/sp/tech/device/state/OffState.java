package cz.cvut.omo.sp.tech.device.state;

import cz.cvut.omo.sp.tech.device.Device;

/**
 * The {@code OffState} class represents the "OFF" state of a device in a state machine.
 * It implements the {@link State} interface.
 *
 * In this state, the device's power is set to zero, and the state is represented as "OFF."
 */
public class OffState implements State {

    private Device device;

    /**
     * Constructs an {@code OffState} object with the given device.
     *
     * @param device The device associated with this state.
     */
    public OffState(Device device) {
        this.device = device;
    }

    /**
     * Sets the power of the device to zero and updates the energy level accordingly.
     *
     * @return The power level set for the device, which is always 0 in this state.
     */
    @Override
    public int setPower() {
        device.getEnergy().setPower(0);
        return 0;
    }

    /**
     * Returns a string representation of this state, which is "OFF."
     *
     * @return The string "OFF" representing this state.
     */
    @Override
    public String toString() {
        return "OFF";
    }
}
