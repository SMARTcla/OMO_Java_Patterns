package cz.cvut.omo.sp.tech.sencor.sencors;

import cz.cvut.omo.sp.events.TypeEvent;
import cz.cvut.omo.sp.places.Room;
import cz.cvut.omo.sp.tech.sencor.Sensor;
import cz.cvut.omo.sp.tech.sencor.TypeSensor;
import cz.cvut.omo.sp.facade.Facade;
import cz.cvut.omo.sp.places.Observer;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * The {@code AirQualitySensor} class represents an air quality sensor in a smart home system.
 * It extends the {@link cz.cvut.omo.sp.tech.sencor.Sensor} class and implements the {@link Observer} interface.
 *
 * This sensor measures and provides information about air quality, specifically using the Air Quality Index (AQI) as a standardized indicator.
 * It can be used to monitor and report changes in air quality to subscribers.
 */
public class AirQualitySensor extends Sensor {

    private final Set<Observer> observers = new HashSet<>();
    private int airQualityIndex;  // Air Quality Index (AQI) is a standardized indicator.

    /**
     * Constructs an {@code AirQualitySensor} object with the specified parameters.
     *
     * @param id   The unique identifier of the sensor.
     * @param name The name of the sensor.
     * @param type The type of the sensor.
     */
    public AirQualitySensor(int id, String name, Room room, TypeSensor type) {
        super(id, name, room, type);
    }

    /**
     * Sets the air quality index (AQI) for the sensor and notifies subscribers about the change.
     *
     * @param airQualityIndex The new air quality index value.
     */
    public void setAirQualityIndex(int airQualityIndex) {
        this.airQualityIndex = airQualityIndex;
        notifySubscribers(TypeEvent.AIR_QUALITY_CHANGE, LocalDateTime.now(), null);
    }

    /**
     * Adds an observer (subscriber) to the sensor's list of subscribers.
     *
     * @param observer The observer to add.
     */
    @Override
    public void addSubscriber(Observer observer) {
        observers.add(observer);
    }

    /**
     * Notifies all subscribers about an event, including the event type, date and time, and a facade reference.
     *
     * @param type     The type of event being notified.
     * @param dateTime The date and time of the event.
     * @param facade   The facade reference (can be null).
     */
    @Override
    public void notifySubscribers(TypeEvent type, LocalDateTime dateTime, Facade facade) {
        for (Observer observer : observers) {
            observer.update(type, dateTime, facade);
        }
    }
}
