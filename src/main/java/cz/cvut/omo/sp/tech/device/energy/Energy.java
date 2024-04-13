package cz.cvut.omo.sp.tech.device.energy;
import lombok.Getter;
import lombok.Setter;

/**
 * The `Energy` class represents the energy characteristics of a device, including its energy type
 * and power consumption.
 */
@Getter
@Setter
public class Energy {

    private TypeEnergy type;
    private int power;

    /**
     * Constructs an `Energy` object with the specified energy type and power consumption.
     *
     * @param type  The type of energy associated with the device.
     * @param power The power consumption of the device.
     */
    public Energy(TypeEnergy type, int power) {
        this.type = type;
        this.power = power;
    }

    /**
     * Returns a string representation of the `Energy` object.
     *
     * @return A string containing information about the energy type and power consumption of the device.
     */
    @Override
    public String toString() {
        return "Energy{" +
                "type=" + type +
                ", power=" + power +
                '}';
    }
}