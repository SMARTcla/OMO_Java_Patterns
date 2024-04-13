package cz.cvut.omo.sp.tech.device.state;

import cz.cvut.omo.sp.tech.device.Device;

/**
 * The {@code IdleState} class represents the "IDLE" state of a device in a state machine.
 * It implements the {@link State} interface.
 *
 * In this state, the device's power is set to half of its base power, and the state is represented as "IDLE."
 */
public class IdleState implements State {

    private Device device;

    /**
     * Constructs an {@code IdleState} object with the given device.
     *
     * @param device The device associated with this state.
     */
    public IdleState(Device device) {
        this.device = device;
    }

    /**
     * Sets the power of the device to half of its base power and updates the energy level accordingly.
     *
     * @return The power level set for the device.
     */
    @Override
    public int setPower() {
        int power = (int) Math.round(device.getBasePower() * 0.5);
        device.getEnergy().setPower(power);
        return power;
    }

    /**
     * Returns a string representation of this state, which is "IDLE."
     *
     * @return The string "IDLE" representing this state.
     */
    @Override
    public String toString() {
        return "IDLE";
    }
}
