package cz.cvut.omo.sp.tech.device.energy;

import java.time.LocalDateTime;

/**
 * The `EventEnergy` class represents an event related to energy consumption for a device.
 * It includes information such as the start and end timestamps of the event and the power consumption.
 */
public class EventEnergy {

    private LocalDateTime start;
    private LocalDateTime end;
    private int power;

    /**
     * Constructs an `EventEnergy` object with the specified start and end timestamps and power consumption.
     *
     * @param start The timestamp when the energy consumption event started.
     * @param end   The timestamp when the energy consumption event ended.
     * @param power The power consumption during the event.
     */
    public EventEnergy(LocalDateTime start, LocalDateTime end, int power) {
        this.start = start;
        this.end = end;
        this.power = power;
    }

    /**
     * Gets the timestamp when the energy consumption event started.
     *
     * @return The start timestamp of the event.
     */
    public LocalDateTime getStart() {
        return start;
    }

    /**
     * Sets the timestamp when the energy consumption event started.
     *
     * @param start The new start timestamp of the event.
     */
    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    /**
     * Gets the timestamp when the energy consumption event ended.
     *
     * @return The end timestamp of the event.
     */
    public LocalDateTime getEnd() {
        return end;
    }

    /**
     * Sets the timestamp when the energy consumption event ended.
     *
     * @param end The new end timestamp of the event.
     */
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    /**
     * Gets the power consumption during the event.
     *
     * @return The power consumption value.
     */
    public int getPower() {
        return power;
    }

    /**
     * Sets the power consumption during the event.
     *
     * @param power The new power consumption value.
     */
    public void setPower(int power) {
        this.power = power;
    }
}
