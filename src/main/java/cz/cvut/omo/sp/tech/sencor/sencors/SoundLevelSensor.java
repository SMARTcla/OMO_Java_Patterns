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

public class SoundLevelSensor extends Sensor {

    private final Set<Observer> observers = new HashSet<>();
    private double soundLevel; // Sound level in decibels (dB)

    public SoundLevelSensor(int id, String name, Room room, TypeSensor type) {
        super(id, name, room, type);
        this.soundLevel = 0.0;
    }

    public void setSoundLevel(double soundLevel) {
        this.soundLevel = soundLevel;
        notifySubscribers(TypeEvent.SOUND_LEVEL_CHANGE, LocalDateTime.now(), null); // Assuming you have a SOUND_LEVEL_CHANGE event type
    }

    public double getSoundLevel() {
        return soundLevel;
    }

    @Override
    public void addSubscriber(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void notifySubscribers(TypeEvent type, LocalDateTime dateTime, Facade facade) {
        for (Observer observer : observers) {
            observer.update(type, dateTime, facade);
        }
    }
}
