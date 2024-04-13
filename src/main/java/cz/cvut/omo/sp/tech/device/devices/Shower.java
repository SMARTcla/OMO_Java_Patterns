package cz.cvut.omo.sp.tech.device.devices;

import cz.cvut.omo.sp.places.Room;
import cz.cvut.omo.sp.tech.device.energy.Energy;
import cz.cvut.omo.sp.tech.device.*;
import cz.cvut.omo.sp.tech.device.energy.*;
import cz.cvut.omo.sp.tech.device.state.OffState;
import cz.cvut.omo.sp.events.*;
import cz.cvut.omo.sp.facade.Facade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;

public class Shower extends Device {
    private static final Logger LOGGER = LogManager.getLogger(Lamp.class.getName());
    public Shower(int id, String name, Room room, DeviceType type, int basePower) {
        super(id, name, room, type, basePower);
        setEnergy(new Energy(TypeEnergy.WATER, basePower));
    }


    @Override
    public void update(TypeEvent type, LocalDateTime dateTime, Facade facade) {
        switch (type) {
            case WATER_FLOW_CHANGE:
                LOGGER.info(this.getName() + " shower started at " + dateTime + ".");
                break;
            case WATER_LEAK_RESOLVED:
                LOGGER.info(this.getName() + " shower stopped at " + dateTime + ".");
                break;
            case WATER_LEAK_DETECTED:
                LOGGER.info(this.getName() + " temperature adjusted at " + dateTime + ".");
                break;
            case SOFTWARE_UPDATE:
                LOGGER.info(this.getName() + " undergoing maintenance at " + dateTime + ".");
                break;
            case DANGER:
                facade.stopUseDevice(this, dateTime);
                setState(new OffState(this));
                LOGGER.warn(this.getName() + " turned off due to a danger alert at " + dateTime + ".");
                break;
            default:
                LOGGER.warn("Unhandled event type for " + this.getName() + ": " + type + " at " + dateTime + ".");
                break;
        }
    }
}
