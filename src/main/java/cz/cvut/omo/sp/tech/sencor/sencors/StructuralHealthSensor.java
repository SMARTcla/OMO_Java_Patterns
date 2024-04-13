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
public class StructuralHealthSensor extends Sensor {

    private final Set<Observer> observers = new HashSet<>();
    private double vibrationLevel; // Level of vibration detected
    private boolean crackDetected; // Whether a crack has been detected

    public StructuralHealthSensor(int id, String name, Room room, TypeSensor type) {
        super(id, name, room, type);
    }

    public void setVibrationLevel(double vibrationLevel) {
        this.vibrationLevel = vibrationLevel;
        notifySubscribers(TypeEvent.STRUCTURAL_VIBRATION_DETECTED, LocalDateTime.now(), null); // Assuming you have a STRUCTURAL_VIBRATION_DETECTED event type
    }

    public double getVibrationLevel() {
        return vibrationLevel;
    }

    public void detectCrack() {
        this.crackDetected = true;
        notifySubscribers(TypeEvent.STRUCTURAL_CRACK_DETECTED, LocalDateTime.now(), null); // Assuming you have a STRUCTURAL_CRACK_DETECTED event type
    }

    public boolean isCrackDetected() {
        return crackDetected;
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