/**
 * The `FactorySensor` class is responsible for creating sensor objects based on the provided parameters.
 * It supports various types of sensors and adds them to the configuration managed by the `SmartHomeManager`.
 *
 * @author Your Name
 * @version 1.0
 * @since 2023-11-30
 */
package cz.cvut.omo.sp.tech.sencor;

import cz.cvut.omo.sp.manager.SmartHomeManager;
import cz.cvut.omo.sp.places.Room;
import cz.cvut.omo.sp.tech.sencor.Sensor;
import cz.cvut.omo.sp.tech.sencor.TypeSensor;
import cz.cvut.omo.sp.tech.sencor.sencors.*;

public class FactorySensor {

    /**
     * Creates a sensor based on the provided parameters and adds it to the configuration managed by the `SmartHomeManager`.
     *
     * @param id             The unique identifier for the sensor.
     * @param typeSensor     The type of sensor to create (e.g., "AIR_QUALITY_CHANGE", "LIGHT", etc.).
     * @param name           The name or description of the sensor.
     * @param configuration  The `SmartHomeManager` instance that manages the smart home configuration.
     * @return               A sensor object of the specified type, or `null` if the sensor type is unknown.
     * @throws IllegalArgumentException if the provided `typeSensor` is not a valid sensor type.
     */
    public Sensor createSensor(int id, String typeSensor, String name, Room room, SmartHomeManager configuration) {
        TypeSensor type = TypeSensor.valueOf(typeSensor);
        switch (type) {
            case AIR_QUALITY_CHANGE:
                AirQualitySensor airQualitySensor = new AirQualitySensor(id, name, room, type);
                configuration.addAirQualitySensor(airQualitySensor);
                return airQualitySensor;
            case LIGHT:
                LightSensor lightSensor = new LightSensor(id, name, room, type);
                configuration.addLightSensor(lightSensor);
                return lightSensor;
            case MOTION:
                MotionSensor motionSensor = new MotionSensor(id, name, room, type);
                configuration.addMotionSensor(motionSensor);
                return motionSensor;
            case SMART_ENERGY_METER:
                SmartEnergyMeter smartEnergyMeter = new SmartEnergyMeter(id, name, room, type);
                configuration.addSmartEnergyMeter(smartEnergyMeter);
                return smartEnergyMeter;
            case SOUND_LEVEL:
                SoundLevelSensor soundLevelSensor = new SoundLevelSensor(id, name, room, type);
                configuration.addSoundLevelSensor(soundLevelSensor);
                return soundLevelSensor;
            case STRUCTURAL_HEALTH:
                StructuralHealthSensor structuralHealthSensor = new StructuralHealthSensor(id, name, room, type);
                configuration.addStructuralHealthSensor(structuralHealthSensor);
                return structuralHealthSensor;
            case TEMPERATURE:
                TemperatureSensor temperatureSensor = new TemperatureSensor(id, name, room, type);
                configuration.addTemperatureSensor(temperatureSensor);
                return temperatureSensor;
            case WATER_LEAK:
                WaterLeakSensor waterLeakSensor = new WaterLeakSensor(id, name, room, type);
                configuration.addWaterLeakSensor(waterLeakSensor);
                return waterLeakSensor;
            default:
                return null; // Or throw an exception if the sensor type is unknown
        }
    }


}
