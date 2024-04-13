package cz.cvut.omo.sp.tech.device.devices;

import cz.cvut.omo.sp.places.Room;
import cz.cvut.omo.sp.tech.device.energy.Energy;
import cz.cvut.omo.sp.tech.device.*;
import cz.cvut.omo.sp.tech.device.energy.*;
import cz.cvut.omo.sp.tech.device.state.ActiveState;
import cz.cvut.omo.sp.tech.device.state.OffState;
import cz.cvut.omo.sp.events.*;
import cz.cvut.omo.sp.facade.Facade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;

public class Speaker extends Device {
    private static final Logger LOGGER = LogManager.getLogger(Lamp.class.getName());
    public Speaker(int id, String name, Room room, DeviceType type, int basePower) {
        super(id, name, room, type, basePower);
        setEnergy(new Energy(TypeEnergy.ELECTRIC, basePower));
    }

    @Override
    public void update(TypeEvent type, LocalDateTime dateTime, Facade facade) {
        switch (type) {
            case LOWER_TEMPERATE:
                LOGGER.info(this.getName() + " volume adjusted at " + dateTime + ".");
                break;
            case AIR_QUALITY_CHANGE:
                setState(new ActiveState(this));
                LOGGER.info(this.getName() + " Change adjusted at " + dateTime + ".");
                break;
            case DANGER:
                facade.stopUseDevice(this, dateTime);
                setState(new OffState(this));
                LOGGER.warn(this.getName() + " turned off due to a danger alert at " + dateTime + ".");
                break;
            default:
                // Log an unhandled event
                LOGGER.warn("Unhandled event type for " + this.getName() + ": " + type + " at " + dateTime + ".");
                break;
        }
    }
}
