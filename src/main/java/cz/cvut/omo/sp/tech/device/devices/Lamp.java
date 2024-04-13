package cz.cvut.omo.sp.tech.device.devices;

import cz.cvut.omo.sp.places.Room;
import cz.cvut.omo.sp.tech.device.energy.Energy;
import cz.cvut.omo.sp.tech.device.*;
import cz.cvut.omo.sp.tech.device.energy.*;
import cz.cvut.omo.sp.tech.device.state.ActiveState;
import cz.cvut.omo.sp.events.*;
import cz.cvut.omo.sp.facade.Facade;
import java.time.LocalDateTime;
import cz.cvut.omo.sp.tech.device.state.OffState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



public class Lamp extends Device {

    private static final Logger LOGGER = LogManager.getLogger(Lamp.class.getName());

    public Lamp(int id, String name, Room room, DeviceType type, int basePower) {
        super(id, name, room, type, basePower);
        setEnergy(new Energy(TypeEnergy.ELECTRIC, basePower));
    }

    @Override
    public void update(TypeEvent type, LocalDateTime dateTime, Facade facade) {
        switch (type) {
            case LIGHT_DAY:
                if (getState().toString().equals("ACTIVE")) {
                    facade.stopUseDevice(this, dateTime);
                    facade.addEventEnergy(this, dateTime, this.getState().setPower());

                }
            case LIGHT_NIGHT:
                if (getState().toString().equals("IDLE") || this.getState().toString().equals("OFF")) {
                    this.setState(new ActiveState(this));
                    Event event = new Event(null, null, dateTime, null,type);
                    facade.addEventEnergy(this, dateTime, this.getState().setPower());
                    this.addEvent(event);
                    LOGGER.info("Start " + event + ".");
                }
                break;
            case DANGER:
                facade.stopUseDevice(this, dateTime);
                setState(new OffState(this));
        }
    }
}
