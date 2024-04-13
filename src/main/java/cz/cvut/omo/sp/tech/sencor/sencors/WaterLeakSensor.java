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

public class WaterLeakSensor extends Sensor {

    private final Set<Observer> observers = new HashSet<>();
    private boolean leakDetected;

    public WaterLeakSensor(int id, String name, Room room, TypeSensor type) {
        super(id, name, room, type);
    }

    public void detectLeak() {
        if (!leakDetected) {
            leakDetected = true;
            notifySubscribers(TypeEvent.WATER_LEAK_DETECTED, LocalDateTime.now(), null); // Assuming you have a WATER_LEAK_DETECTED event type
        }
    }

    public void resetLeakDetection() {
        if (leakDetected) {
            leakDetected = false;
            notifySubscribers(TypeEvent.WATER_LEAK_RESOLVED, LocalDateTime.now(), null); // Assuming you have a WATER_LEAK_RESOLVED event type
        }
    }

    public boolean isLeakDetected() {
        return leakDetected;
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
