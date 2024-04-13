package cz.cvut.omo.sp.tech.device;

import cz.cvut.omo.sp.places.Room;
import cz.cvut.omo.sp.manager.SmartHomeManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A builder class for creating instances of Device objects.
 *
 * This class provides a convenient way to construct Device objects with various attributes, including ID, name,
 * type, and the room where the device is located. It uses the FactoryDevice class to create the device instance.
 */
public class DeviceBuilder {

    private static final Logger LOGGER = LogManager.getLogger(DeviceBuilder.class.getName());

    /** The ID of the device. */
    private int id;

    /** The name of the device. */
    private String name;

    /** The type of the device. */
    private String type;

    /** The room where the device is located. */
    private Room room;

    /** The factory for creating device instances. */
    private final FactoryDevice factoryDevice;

    /** The SmartHomeManager responsible for managing devices. */
    private final SmartHomeManager manager;

    /**
     * Creates a new instance of DeviceBuilder.
     *
     * @param manager The SmartHomeManager responsible for managing devices.
     */
    public DeviceBuilder(SmartHomeManager manager) {
        this.manager = manager;
        this.factoryDevice = new FactoryDevice();
    }

    /**
     * Sets the ID of the device.
     *
     * @param id The ID of the device.
     * @return This DeviceBuilder instance.
     */
    public DeviceBuilder withId(int id) {
        this.id = id;
        return this;
    }

    /**
     * Sets the name of the device.
     *
     * @param name The name of the device.
     * @return This DeviceBuilder instance.
     */
    public DeviceBuilder withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Sets the type of the device.
     *
     * @param type The type of the device.
     * @return This DeviceBuilder instance.
     */
    public DeviceBuilder withType(String type) {
        this.type = type;
        return this;
    }

    /**
     * Sets the room where the device is located.
     *
     * @param room The room where the device is located.
     * @return This DeviceBuilder instance.
     */
    public DeviceBuilder inRoom(Room room) {
        this.room = room;
        return this;
    }

    /**
     * Builds a Device object with the specified attributes using the FactoryDevice.
     *
     * @return The constructed Device object.
     */
    public Device build() {
        return factoryDevice.createDevice(this.id, this.type, this.room, this.name, manager);
    }
}
