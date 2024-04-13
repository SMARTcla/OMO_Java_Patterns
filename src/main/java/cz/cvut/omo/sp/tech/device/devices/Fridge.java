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

public class Fridge extends Device {
    private static final Logger LOGGER = LogManager.getLogger(Fridge.class.getName());

    public Fridge(int id, String name, Room room, DeviceType type, int basePower) {
        super(id, name, room, type, basePower);
        setEnergy(new Energy(TypeEnergy.ELECTRIC, basePower));
    }

    @Override
    public void update(TypeEvent type, LocalDateTime dateTime, Facade facade) {
        switch (type) {
            case RAISE_TEMPERATE:
                LOGGER.info(this.getName() + " temperature adjusted at " + dateTime + ".");
                break;
            case ENERGY_CONSUMPTION_CHANGE:
                LOGGER.info(this.getName() + " entered power saving mode at " + dateTime + ".");
                break;
            case MOTION_DETECTED:
                LOGGER.info(this.getName() + " is being restocked at " + dateTime + ".");
                break;
            case LOWER_TEMPERATE:
                LOGGER.info(this.getName() + " is defrosting at " + dateTime + ".");
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
