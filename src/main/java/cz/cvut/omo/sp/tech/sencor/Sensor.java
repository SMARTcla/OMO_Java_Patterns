package cz.cvut.omo.sp.tech.sencor;

import cz.cvut.omo.sp.places.Room;

/**
 * The {@code Sensor} abstract class represents a generic sensor in a smart home system.
 * It implements the {@link Subject} interface.
 *
 * This class provides common attributes and methods for sensors, such as identification, name, type, and readings.
 * Specific sensor types should extend this class to define their behavior.
 */
public abstract class Sensor implements Subject {

    private int id;
    private String name;
    private TypeSensor type;
    private Room room;
    private int readings;

    /**
     * Constructs a {@code Sensor} object with the specified parameters.
     *
     * @param id   The unique identifier of the sensor.
     * @param name The name of the sensor.
     * @param type The type of the sensor.
     */
    public Sensor(int id, String name, Room room, TypeSensor type) {
        this.id = id;
        this.name = name;
        this.room = room;
        this.type = type;
        this.readings = 0;
    }


    /**
     * Gets the number of readings taken by the sensor.
     *
     * @return The number of readings taken.
     */
    public int getReadings() {
        return readings;
    }

    /**
     * Sets the number of readings taken by the sensor.
     *
     * @param readings The number of readings to set.
     */
    public void setReadings(int readings) {
        this.readings = readings;
    }

    /**
     * Gets the unique identifier of the sensor.
     *
     * @return The unique identifier of the sensor.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the sensor.
     *
     * @param id The unique identifier to set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the name of the sensor.
     *
     * @return The name of the sensor.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the sensor.
     *
     * @param name The name to set for the sensor.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the type of the sensor.
     *
     * @return The type of the sensor.
     */
    public TypeSensor getType() {
        return type;
    }

    /**
     * Sets the type of the sensor.
     *
     * @param type The type to set for the sensor.
     */
    public void setType(TypeSensor type) {
        this.type = type;
    }

    /**
     * Returns a string representation of the sensor, including its ID, name, and type.
     *
     * @return A string representation of the sensor.
     */
    @Override
    public String toString() {
        return "Sensor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                '}';
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
