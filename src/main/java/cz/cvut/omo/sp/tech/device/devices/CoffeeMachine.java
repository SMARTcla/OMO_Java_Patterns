package cz.cvut.omo.sp.tech.device.devices;

import cz.cvut.omo.sp.places.Room;
import cz.cvut.omo.sp.tech.device.energy.Energy;
import cz.cvut.omo.sp.tech.device.*;
import cz.cvut.omo.sp.tech.device.content.*;
import cz.cvut.omo.sp.tech.device.energy.*;
import cz.cvut.omo.sp.tech.device.state.ActiveState;
import cz.cvut.omo.sp.tech.device.state.BlockState;
import cz.cvut.omo.sp.tech.device.state.OffState;
import cz.cvut.omo.sp.events.*;
import cz.cvut.omo.sp.facade.Facade;

import java.time.LocalDateTime;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.*;

public class CoffeeMachine extends Device {

    private static final Logger LOGGER = LogManager.getLogger(CoffeeMachine.class.getName());

    private List<DeviceContent> contents;

    public CoffeeMachine(int id, String name, Room room, DeviceType type, int basePower) {
        super(id, name, room, type, basePower);
        this.contents = createDeviceContent();
        setEnergy(new Energy(TypeEnergy.ELECTRIC, basePower));
    }


    @Override
    public void update(TypeEvent type, LocalDateTime dateTime, Facade facade) {
        switch (type) {
            case ACTION:
                if (getState().toString().equals("OFF")) {
                    this.setState(new ActiveState(this));
                    LOGGER.info("Coffee machine activated to make coffee at " + dateTime + ".");
                }
                makeCoffee();
                LOGGER.info("Coffee is being made by " + this.getName() + " at " + dateTime + ".");
                break;
            case STRUCTURAL_VIBRATION_DETECTED:
                LOGGER.info(this.getName() + " has been refilled with content at " + dateTime + ".");
                break;
            case WATER_FLOW_CHANGE:
                LOGGER.info(this.getName() + " is undergoing maintenance at " + dateTime + ".");
                break;
            case BLOCK_DEVICE:
                facade.stopUseDevice(this, dateTime);
                setState(new BlockState(this));
                LOGGER.info(this.getName() + " turned off at " + dateTime + ".");
                break;
            case USAGE_DEVICE:
                this.setState(new ActiveState(this));
                LOGGER.info(this.getName() + " turned on at " + dateTime + ".");
                break;
            case DANGER:
                facade.stopUseDevice(this, dateTime);
                setState(new OffState(this));
                LOGGER.info(this.getName() + " turned off due to a danger alert at " + dateTime + ".");
                break;
            default:
                LOGGER.warn("Unhandled event type for " + this.getName() + ": " + type + " at " + dateTime + ".");
                break;
        }
    }

    private List<DeviceContent> createDeviceContent() {
        List<DeviceContent> contents = new ArrayList<>();
        contents.add(new DeviceContent(TypeDeviceContent.WATER_COFFEE, "This thing make coffee", 10, LocalDateTime.now(),LocalDateTime.now(),"USA", "EN"));
        return contents;
    }


    public void makeCoffee() {

        // Check if there is enough water and coffee
        if (getWaterLevel() < 300 || getCoffeeLevel() < 100) {
            System.out.println("Not enough water or coffee to make a cup.");
            return;
        }

        // Simulate making a cup of coffee
        reduceContent(TypeDeviceContent.WATER_COFFEE, 300);
        reduceContent(TypeDeviceContent.COFFEE, 100);

        System.out.println("Coffee made successfully!");
    }

    private void reduceContent(TypeDeviceContent contentType, int amount) {
        for (DeviceContent content : contents) {
            if (content.getTypeContent() == contentType) {
                int currentQuantity = content.getCount();
                int newQuantity = Math.max(currentQuantity - amount, 0);
                content.setCount(newQuantity);
            }
        }
    }

    /**
     * Retrieves the current level of coffee in the machine.
     * @return The current coffee level.
     */
    private int getCoffeeLevel() {
        return contents.stream()
                .filter(content -> content.getTypeContent() == TypeDeviceContent.COFFEE)
                .findFirst()
                .map(DeviceContent::getCount)
                .orElse(0); // Return 0 if coffee content is not found
    }

    /**
     * Retrieves the current level of water in the machine.
     * @return The current water level.
     */
    private int getWaterLevel() {
        return contents.stream()
                .filter(content -> content.getTypeContent() == TypeDeviceContent.WATER_COFFEE)
                .findFirst()
                .map(DeviceContent::getCount)
                .orElse(0); // Return 0 if water content is not found
    }

    /**
     * Displays the current status of the coffee machine.
     */
    public void displayStatus() {
        LOGGER.info("Coffee Machine Status:");
        LOGGER.info("Water Level: {} units", getWaterLevel());
        LOGGER.info("Coffee Level: {} units", getCoffeeLevel());
        LOGGER.info("Current State: {}", getState().toString());
    }
}
