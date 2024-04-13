package cz.cvut.omo.sp.tech.device.state;

/**
 * The `State` interface defines the contract for objects representing different states
 * within a context in a state pattern. Implementing classes should provide a method
 * to set the power level associated with the state.
 */
public interface State {

    /**
     * Sets the power level associated with the state.
     *
     * @return The power level value.
     */
    int setPower();
}
