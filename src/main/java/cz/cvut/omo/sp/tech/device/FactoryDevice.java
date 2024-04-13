package cz.cvut.omo.sp.tech.device;
import cz.cvut.omo.sp.places.Room;
import cz.cvut.omo.sp.tech.device.Device;
import cz.cvut.omo.sp.tech.device.DeviceType;
import cz.cvut.omo.sp.tech.device.devices.*;
import cz.cvut.omo.sp.manager.SmartHomeManager;
import cz.cvut.omo.sp.data.DeviceData;
/**
 * A factory class for creating instances of different types of devices.
 *
 * This class provides a convenient way to create instances of various devices based on their type, such as Alarm,
 * Speaker, TV, Teapot, and more. It is used to initialize devices within a smart home configuration managed by a
 * SmartHomeManager.
 */
public class FactoryDevice {

    /**
     * Create a device of the specified type and configure it within the smart home configuration.
     *
     * @param id             The ID of the device.
     * @param deviceType     The string representation of the device type.
     * @param room           The room where the device is located.
     * @param name           The name of the device.
     * @param configuration  The SmartHomeManager managing the smart home configuration.
     * @return An instance of the specified device type, configured within the smart home configuration, or null if
     *         the device type is not recognized.
     */

    public Device createDevice(int id, String deviceType, Room room, String name, SmartHomeManager configuration) {
        DeviceType type = DeviceType.valueOf(deviceType);
        switch (type){
            case ALARM:
                Alarm alarm = new Alarm(id, name, room, type, DeviceData.POWER_ALARM);
                configuration.addAlarm(alarm);
                return alarm;
            case SPEAKER:
                Speaker speaker = new Speaker(id, name, room, type, DeviceData.POWER_ALARM);
                configuration.addSpeaker(speaker);
                return speaker;
            case TV:
                TV tv = new TV(id, name, room, type, DeviceData.POWER_ALARM);
                configuration.addTV(tv);
                return tv;
            case TEAPOT:
                Teapot teapot = new Teapot(id, name, room, type, DeviceData.POWER_ALARM);
                configuration.addTeapot(teapot);
                return teapot;
            case VACUUM_CLEANER:
                VacuumCleaner vacuumCleaner = new VacuumCleaner(id, name, room, type, DeviceData.POWER_ALARM);
                configuration.addVacuumCleaner(vacuumCleaner);
                return vacuumCleaner;
            case COFFEE_MACHINE:
                CoffeeMachine coffeeMachine = new CoffeeMachine(id, name, room, type, DeviceData.POWER_COFFEMACHINE);
                configuration.addCoffeeMachine(coffeeMachine);
                return coffeeMachine;
            case FRIDGE:
                Fridge fridge = new Fridge(id, name, room, type, DeviceData.POWER_LAPM);
                configuration.addFridge(fridge);
                return fridge;
            case LAMP:
                Lamp lamp = new Lamp(id, name, room, type, DeviceData.POWER_LAPM);
                configuration.addLamp(lamp);
                return lamp;
            case PC:
                PC pc = new PC(id, name, room, type, DeviceData.POWER_PC);
                configuration.addPC(pc);
                return pc;
            case PLAY_STATION:
                PlayStation playStation = new PlayStation(id, name, room, type, DeviceData.POWER_PLAYSTATION);
                configuration.addPlayStation(playStation);
                return playStation;
            case SHOWER:
                Shower shower = new Shower(id, name, room, type, DeviceData.POWER_SHOWER);
                configuration.addShower(shower);
                return shower;
            default: return null;
        }
    }
}