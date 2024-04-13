package cz.cvut.omo.sp.facade;

import cz.cvut.omo.sp.alive.inhabitant.Inhabitant;
import cz.cvut.omo.sp.alive.person.Person;
import cz.cvut.omo.sp.alive.pet.Pet;
import cz.cvut.omo.sp.tech.device.Device;

import java.time.LocalDateTime;

/**
 * A facade interface that provides simplified access to a complex system of smart home management.
 *
 * The Facade pattern is used to provide a high-level, simplified interface to a complex system, making it easier
 * for clients to interact with the system. This interface defines methods for various actions related to devices,
 * events, and inhabitants in a smart home.
 */
public interface Facade {
    /**
     * Fix a device using a person at a specific date and time.
     *
     * @param device    The device to be fixed.
     * @param person    The person fixing the device.
     * @param dateTime  The date and time of the device fixing.
     */
    void fixDevice(Device device, Person person, LocalDateTime dateTime);

    /**
     * Start using a device by an inhabitant at a specific date and time.
     *
     * @param device      The device to start using.
     * @param inhabitant  The inhabitant using the device.
     * @param dateTime    The date and time of starting to use the device.
     */
    void startUseDevice(Device device, Inhabitant inhabitant, LocalDateTime dateTime);

    /**
     * Stop using a device by an inhabitant at a specific date and time.
     *
     * @param inhabitant  The inhabitant stopping to use the device.
     * @param dateTime    The date and time of stopping to use the device.
     */
    void stopUseDevice(Inhabitant inhabitant, LocalDateTime dateTime);

    /**
     * Stop using a specific device at a specific date and time.
     *
     * @param device    The device to stop using.
     * @param dateTime  The date and time of stopping to use the device.
     */
    void stopUseDevice(Device device, LocalDateTime dateTime);

    /**
     * Turn off a device using an inhabitant at a specific date and time.
     *
     * @param device      The device to turn off.
     * @param inhabitant  The inhabitant turning off the device.
     * @param dateTime    The date and time of turning off the device.
     */
    void offDevice(Device device, Inhabitant inhabitant, LocalDateTime dateTime);

    /**
     * Turn on a device using an inhabitant at a specific date and time.
     *
     * @param device      The device to turn on.
     * @param inhabitant  The inhabitant turning on the device.
     * @param dateTime    The date and time of turning on the device.
     */
    void onDevice(Device device, Inhabitant inhabitant, LocalDateTime dateTime);

    /**
     * Block a device at a specific date and time.
     *
     * @param device    The device to be blocked.
     * @param dateTime  The date and time of blocking the device.
     */
    void blockDevice(Device device, LocalDateTime dateTime);

    /**
     * Add an energy-related event for a device at a specific date and time.
     *
     * @param device    The device for which the energy event is added.
     * @param dateTime  The date and time of the energy event.
     * @param power     The power value associated with the event.
     */
    void addEventEnergy(Device device, LocalDateTime dateTime, int power);

    /**
     * Handle the end of the last event for a pet at the current time.
     *
     * @param pet         The pet for which the event is ending.
     * @param currentTime The current time when the event is ending.
     */
    void theEndLastEvent(Pet pet, LocalDateTime currentTime);

    /**
     * Handle the end of the last event for a person at the current time.
     *
     * @param person      The person for which the event is ending.
     * @param currentTime The current time when the event is ending.
     */
    void theEndLastEvent(Person person, LocalDateTime currentTime);
}
