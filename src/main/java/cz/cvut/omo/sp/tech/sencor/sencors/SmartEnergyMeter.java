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

public class SmartEnergyMeter extends Sensor {

    private final Set<Observer> observers = new HashSet<>();
    private double energyConsumption; // Energy consumption in kilowatt-hours (kWh)
    private double waterFlow; // Water flow in liters per minute (l/min)
    private double gasFlow; // Gas flow in liters per minute (l/min)

    public SmartEnergyMeter(int id, String name, Room room, TypeSensor type) {
        super(id, name, room, type);
        this.energyConsumption = 0.0;
        this.waterFlow = 0.0;
        this.gasFlow = 0.0;
    }

    public void setEnergyConsumption(double energyConsumption) {
        this.energyConsumption = energyConsumption;
        notifySubscribers(TypeEvent.ENERGY_CONSUMPTION_CHANGE, LocalDateTime.now(), null); // Assuming you have a ENERGY_CONSUMPTION_CHANGE event type
    }

    public double getEnergyConsumption() {
        return energyConsumption;
    }

    public void setWaterFlow(double waterFlow) {
        this.waterFlow = waterFlow;
        notifySubscribers(TypeEvent.WATER_FLOW_CHANGE, LocalDateTime.now(), null); // Assuming you have a WATER_FLOW_CHANGE event type
    }

    public double getWaterFlow() {
        return waterFlow;
    }

    public void setGasFlow(double gasFlow) {
        this.gasFlow = gasFlow;
        notifySubscribers(TypeEvent.GAS_FLOW_CHANGE, LocalDateTime.now(), null); // Assuming you have a GAS_FLOW_CHANGE event type
    }

    public double getGasFlow() {
        return gasFlow;
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
