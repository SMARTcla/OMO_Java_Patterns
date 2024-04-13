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

public class PC  extends Device {
    private static final Logger LOGGER = LogManager.getLogger(Lamp.class.getName());

    public PC(int id, String name, Room room, DeviceType type, int basePower) {
        super(id, name, room, type, basePower);
        setEnergy(new Energy(TypeEnergy.ELECTRIC, basePower));
    }


    @Override
    public void update(TypeEvent type, LocalDateTime dateTime, Facade facade) {
        switch (type) {
            case STRUCTURAL_VIBRATION_DETECTED:
                LOGGER.info(this.getName() + " is problem at " + dateTime + ".");
                break;
            case SOFTWARE_UPDATE:
                LOGGER.info(this.getName() + " is updating software at " + dateTime + ".");
                break;
            case SOUND_LEVEL_CHANGE:
                LOGGER.info(this.getName() + " Sound mode at " + dateTime + ".");
                break;
            case ACTION:
                LOGGER.info(this.getName() + " has been woken up by user activity at " + dateTime + ".");
                break;
            case LOWER_TEMPERATE:
                LOGGER.info(this.getName() + " entered power saving mode at " + dateTime + ".");
                break;
            case DANGER:
                facade.stopUseDevice(this, dateTime);
                setState(new OffState(this));
                LOGGER.warn(this.getName() + " shut down due to a danger alert at " + dateTime + ".");
                break;
            default:
                LOGGER.warn("Unhandled event type for " + this.getName() + ": " + type + " at " + dateTime + ".");
                break;
        }
    }
}
