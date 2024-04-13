package cz.cvut.omo.sp.tech.device.devices;

import cz.cvut.omo.sp.data.InhabitantData;
import cz.cvut.omo.sp.places.Room;
import cz.cvut.omo.sp.tech.device.energy.Energy;
import cz.cvut.omo.sp.tech.device.*;
import cz.cvut.omo.sp.tech.device.energy.*;
import cz.cvut.omo.sp.tech.device.state.ActiveState;
import cz.cvut.omo.sp.tech.device.state.BlockState;
import cz.cvut.omo.sp.tech.device.state.OffState;
import cz.cvut.omo.sp.events.*;
import cz.cvut.omo.sp.facade.Facade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;

public class Alarm extends Device {
    private static final Logger LOGGER = LogManager.getLogger(Lamp.class.getName());
    public Alarm(int id, String name, Room room, DeviceType type, int basePower) {
        super(id, name, room, type, basePower);
        setEnergy(new Energy(TypeEnergy.ELECTRIC, basePower));
    }

    @Override
    public void update(TypeEvent type, LocalDateTime dateTime, Facade facade) {
        switch (type) {
            case LIGHT_DAY:
                if (getState().toString().equals("OFF")) {
                    this.setState(new ActiveState(this));
                    LOGGER.info("Intrusion detected. Alarm activated at " + dateTime + ".");
                }
                break;
            case WATER_LEAK_DETECTED:
                if (getState().toString().equals("OFF")) {
                    Event event = new Event(InhabitantData.INHABITANT_USE_ELECTRONIC_DEVICE, null, dateTime, null,TypeEvent.OFF_DEVICE);
                    this.addEvent(event);
                    facade.addEventEnergy(this, dateTime, this.getState().setPower());
                    LOGGER.info("Start " + event + ".");
                    this.setState(new ActiveState(this));
                    LOGGER.info("Water detected. Alarm activated at " + dateTime + ".");
                }
                break;
            case MOTION_DETECTED:
                this.setState(new ActiveState(this));
                LOGGER.info("Alarm manually activated at " + dateTime + ".");
                break;
            case STRUCTURAL_CRACK_DETECTED:
                facade.stopUseDevice(this, dateTime);
                setState(new BlockState(this));
                LOGGER.info("Alarm deactivated at " + dateTime + ".");
                break;
            case DANGER:
                facade.stopUseDevice(this, dateTime);
                setState(new OffState(this));
                LOGGER.info("Alarm responded to danger and turned off at " + dateTime + ".");
                break;
            default:
                LOGGER.warn("Received unhandled event type: " + type + " at " + dateTime + ".");
                break;
        }
    }
}
