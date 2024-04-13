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

public class MotionSensor extends Sensor {

    private final Set<Observer> observers = new HashSet<>();
    private boolean motionDetected;

    public MotionSensor(int id, String name, Room room, TypeSensor type) {
        super(id, name, room, type);
        this.motionDetected = false;
    }

    public void detectMotion() {
        if (!motionDetected) {
            motionDetected = true;
            notifySubscribers(TypeEvent.MOTION_DETECTED, LocalDateTime.now(), null); // Assuming you have a MOTION_DETECTED event type
        }
    }

    public void resetMotionDetection() {
        if (motionDetected) {
            motionDetected = false;
            notifySubscribers(TypeEvent.MOTION_RESOLVED, LocalDateTime.now(), null); // Assuming you have a MOTION_RESOLVED event type
        }
    }

    public boolean isMotionDetected() {
        return motionDetected;
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
