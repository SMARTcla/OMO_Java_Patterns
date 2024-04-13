package cz.cvut.omo.sp.tech.device.devices;

import cz.cvut.omo.sp.places.Room;
import cz.cvut.omo.sp.tech.device.energy.Energy;
import cz.cvut.omo.sp.tech.device.*;
import cz.cvut.omo.sp.tech.device.content.*;
import cz.cvut.omo.sp.tech.device.energy.*;
import cz.cvut.omo.sp.tech.device.state.OffState;
import cz.cvut.omo.sp.events.*;
import cz.cvut.omo.sp.facade.Facade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.*;

public class Teapot extends Device {
    private static final Logger LOGGER = LogManager.getLogger(Lamp.class.getName());
    private List<DeviceContent> contents;

    public Teapot(int id, String name, Room room, DeviceType type, int basePower) {
        super(id, name, room, type, basePower);
        this.contents = createDeviceContent();
        setEnergy(new Energy(TypeEnergy.ELECTRIC, basePower));
    }
    @Override
    public void update(TypeEvent type, LocalDateTime dateTime, Facade facade) {
        if (type == TypeEvent.DANGER) {
            facade.stopUseDevice(this, dateTime);
            setState(new OffState(this));
        }
    }

    private List<DeviceContent> createDeviceContent() {
        List<DeviceContent> contents = new ArrayList<>();
        contents.add(new DeviceContent(TypeDeviceContent.WATER_COFFEE, "This thing make coffee", 10, LocalDateTime.now(),LocalDateTime.now(),"USA", "EN"));
        return contents;
    }
}
