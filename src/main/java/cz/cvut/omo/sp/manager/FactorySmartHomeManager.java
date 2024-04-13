package cz.cvut.omo.sp.manager;

import cz.cvut.omo.sp.manager.SmartHomeManager;
import cz.cvut.omo.sp.alive.inhabitant.Inhabitant;
import cz.cvut.omo.sp.tech.auto.Transport;
import cz.cvut.omo.sp.tech.device.Device;
import cz.cvut.omo.sp.tech.sencor.Sensor;

import java.util.List;
public class FactorySmartHomeManager {

    private SmartHomeManager smarthome;

    /**
     * Constructs a `FactorySmartHomeManager` with a reference to the `SmartHomeManager` instance.
     *
     * @param smarthome The `SmartHomeManager` instance that manages the smart home configuration.
     */
    public FactorySmartHomeManager(SmartHomeManager smarthome) {
        this.smarthome = smarthome;
    }

    /**
     * Generates associations between sensors and various subjects within the smart home configuration.
     * It links sensors with devices, inhabitants, and transports based on their types and available subject lists.
     */
    public void generateSubjects() {
        addDevice(smarthome.getLightSensors(), smarthome.getPcs());
        addDevice(smarthome.getLightSensors(), smarthome.getPlayStations());
        addDevice(smarthome.getLightSensors(), smarthome.getLamps());
        addDevice(smarthome.getLightSensors(), smarthome.getTvs());
        addDevice(smarthome.getWaterLeakSensors(), smarthome.getShowers());
        addDevice(smarthome.getStructuralHealthSensors(), smarthome.getShowers());
        addDevice(smarthome.getSmartEnergyMeters(), smarthome.getShowers());
        addDevice(smarthome.getSoundLevelSensors(), smarthome.getAlarms());
        addDevice(smarthome.getSoundLevelSensors(), smarthome.getTvs());
        addDevice(smarthome.getAirQualitySensors(), smarthome.getShowers());
        addDevice(smarthome.getSmartEnergyMeters(), smarthome.getTeapots());
        addDevice(smarthome.getMotionSensors(), smarthome.getFridges());
        addDevice(smarthome.getWaterLeakSensors(), smarthome.getFridges());
        addDevice(smarthome.getStructuralHealthSensors(), smarthome.getSpeakers());
        addDevice(smarthome.getWaterLeakSensors(), smarthome.getVacuumCleaners());
        addInhabitant(smarthome.getStructuralHealthSensors(), smarthome.getPersons());
        addInhabitant(smarthome.getStructuralHealthSensors(), smarthome.getPets());
        addTransport(smarthome.getStructuralHealthSensors(), smarthome.getTransports());
        addInhabitant(smarthome.getWaterLeakSensors(), smarthome.getPersons());
        addInhabitant(smarthome.getWaterLeakSensors(), smarthome.getPets());
        addTransport(smarthome.getWaterLeakSensors(), smarthome.getTransports());
        addInhabitant(smarthome.getAirQualitySensors(), smarthome.getPersons());
        addInhabitant(smarthome.getAirQualitySensors(), smarthome.getPets());
    }

    /**
     * Helper method to associate sensors with devices.
     *
     * @param sensors  The list of sensors.
     * @param devices  The list of devices.
     */
    private void addDevice(List<? extends Sensor> sensors, List<? extends Device> devices) {
        if (sensors != null) {
            for (Sensor sensor : sensors) {
                if (devices != null) {
                    for (Device device : devices) {
                        sensor.addSubscriber(device);
                    }
                }
            }
        }
    }

    /**
     * Helper method to associate sensors with inhabitants.
     *
     * @param sensors     The list of sensors.
     * @param inhabitants The list of inhabitants.
     */
    private void addInhabitant(List<? extends Sensor> sensors, List<? extends Inhabitant> inhabitants) {
        if (sensors != null) {
            for (Sensor sensor : sensors) {
                if (inhabitants != null) {
                    for (Inhabitant inhabitant : inhabitants) {
                        sensor.addSubscriber(inhabitant);
                    }
                }
            }
        }
    }

    /**
     * Helper method to associate sensors with transports.
     *
     * @param sensors    The list of sensors.
     * @param transports The list of transports.
     */
    private void addTransport(List<? extends Sensor> sensors, List<Transport> transports) {
        if (sensors != null) {
            for (Sensor sensor : sensors) {
                if (transports != null) {
                    for (Transport transport : transports) {
                        sensor.addSubscriber(transport);
                    }
                }
            }
        }
    }
}