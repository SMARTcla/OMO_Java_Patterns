package cz.cvut.omo.sp.facade;

import cz.cvut.omo.sp.manager.SmartHomeManager;
import cz.cvut.omo.sp.alive.pet.Pet;
import cz.cvut.omo.sp.data.InhabitantData;
import cz.cvut.omo.sp.data.PersonData;
import cz.cvut.omo.sp.data.PetData;
import cz.cvut.omo.sp.Entity;
import cz.cvut.omo.sp.tech.device.state.State;
import cz.cvut.omo.sp.tech.device.*;
import cz.cvut.omo.sp.tech.device.devices.*;
import cz.cvut.omo.sp.tech.device.energy.EventEnergy;
import cz.cvut.omo.sp.tech.device.state.ActiveState;
import cz.cvut.omo.sp.tech.device.state.BlockState;
import cz.cvut.omo.sp.tech.device.state.IdleState;
import cz.cvut.omo.sp.events.Event;
import cz.cvut.omo.sp.events.TypeEvent;
import cz.cvut.omo.sp.alive.person.*;
import cz.cvut.omo.sp.alive.inhabitant.*;
import cz.cvut.omo.sp.tech.device.state.OffState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.List;
/**
 * A facade class that provides simplified access to a complex system of smart home management.
 *
 * The Facade pattern is used to provide a high-level, simplified interface to a complex system, making it easier
 * for clients to interact with the system. This interface defines methods for various actions related to devices,
 * events, and inhabitants in a smart home.
 */
public class FacadeImplementation implements Facade {

    private static final Logger LOGGER = LogManager.getLogger(FacadeImplementation.class.getName());

    private SmartHomeManager configuration;

    public FacadeImplementation(SmartHomeManager configuration) {
        this.configuration = configuration;
    }
    private Device getDeviceLastEvent(Event event) {
        Entity target = event.getTarget();
        List<CoffeeMachine> machines = configuration.getCoffeeMachines();
        if (machines != null) {
            for (CoffeeMachine machine : machines)
                if (machine.equals(target)) return machine;
        }
        List<PC> pcs = configuration.getPcs();
        if (pcs != null) {
            for (PC pc : pcs)
                if (pc.equals(target)) return pc;
        }
        List<Shower> showers = configuration.getShowers();
        if (showers != null) {
            for (Shower shower : showers)
                if (shower.equals(target)) return shower;
        }
        List<VacuumCleaner> vacuumCleaners = configuration.getVacuumCleaners();
        if (vacuumCleaners != null) {
            for (VacuumCleaner vacuumCleaner : vacuumCleaners)
                if (vacuumCleaner.equals(target)) return vacuumCleaner;
        }
        List<Fridge> fridges = configuration.getFridges();
        if (fridges != null) {
            for (Fridge fridge : fridges)
                if (fridge.equals(target)) return fridge;
        }
        List<Lamp> lamps = configuration.getLamps();
        if (lamps != null) {
            for (Lamp lamp : lamps)
                if (lamp.equals(target)) return lamp;
        }
        List<PlayStation> playStations = configuration.getPlayStations();
        if (playStations != null) {
            for (PlayStation playStation : playStations)
                if (playStation.equals(target)) return playStation;
        }

        List<Speaker> speakers = configuration.getSpeakers();
        if (speakers != null) {
            for (Speaker speaker : speakers)
                if (speaker.equals(target)) return speaker;
        }

        List<Teapot> teapots = configuration.getTeapots();
        if (teapots != null) {
            for (Teapot teapot : teapots)
                if (teapot.equals(target)) return teapot;
        }

        List<TV> tvs = configuration.getTvs();
        if (tvs != null) {
            for (TV tv : tvs)
                if (tv.equals(target)) return tv;
        }
        return null;
    }
    @Override
    public void blockDevice(Device device, LocalDateTime dateTime) {
        String deviceName = device.getName();
        State currentState = device.getState();
        LOGGER.info("Attempting to lock device: " + deviceName + " at " + dateTime + ".");

        if (currentState instanceof BlockState) {
            LOGGER.info("No action taken. The device '" + deviceName + "' is already locked.");
        } else if (currentState instanceof OffState) {
            LOGGER.warn("The device '" + deviceName + "' cannot be locked because it is turned off.");
        } else {
            try {
                LOGGER.info("Stopping the device '" + deviceName + "' before locking.");
                stopUseDevice(device, dateTime);

                List<Inhabitant> expects = device.getExpects();
                if (expects != null && !expects.isEmpty()) {
                    LOGGER.info("Updating state for inhabitants expecting to use the device '" + deviceName + "'.");
                    for (Inhabitant inhabitant : expects) {
                        inhabitant.setState(StateInhabitant.ACTIVITY);
                    }
                }
                device.setExpects(null);

                device.setState(new BlockState(device));
                Event blockEvent = new Event(PersonData.PERSON_BLOCK_DEVICE, null, dateTime, device, TypeEvent.BLOCK_DEVICE);
                device.addEvent(blockEvent);
                blockEvent.setEndDateTime(dateTime);

                addEventEnergy(device, dateTime, currentState.setPower());
                LOGGER.info("Device '" + deviceName + "' has been successfully locked at " + dateTime + ".");
            } catch (Exception e) {
                LOGGER.error("Error occurred while trying to lock device '" + deviceName + "': " + e.getMessage(), e);
            }
        }
    }
    @Override
    public void theEndLastEvent(Pet pet, LocalDateTime currentTime) {
        List<Event> events = pet.getEvents();
        if (events != null && pet.getState() != StateInhabitant.WAIT) {
            Event lastEvent = events.get(events.size() - 1);
            if (lastEvent.getContext().equals(PetData.PET_ACTIVITY_PLAY_WITH_OWNER) ) {
                Entity entity = lastEvent.getTarget();
                Person person1 = getPersonByEntity(entity);
                if (person1 != null)
                    person1.setState(StateInhabitant.ACTIVITY);
            } else if (lastEvent.getContext().equals(InhabitantData.INHABITANT_WAIT_FOR_ELECTRONIC_DEVICE)) {
                stopUseDevice(pet, currentTime);
            } else {
                lastEvent.setEndDateTime(currentTime);
                pet.setState(StateInhabitant.ACTIVITY);
                LOGGER.info("The end " + lastEvent + " for Pet=" + pet.getName() + "}.");
            }
        }
    }
    private Person getPersonByEntity(Entity entity) {
        List<Person> persons = configuration.getPersons();
        if (persons != null) {
            for (Person person : persons) {
                if (entity.equals(person))
                    return person;
            }
        }
        return null;
    }


    @Override
    public void onDevice(Device device, Inhabitant inhabitant, LocalDateTime dateTime) {
        LOGGER.info("Attempting to turn on device: " + device.getName() + " by inhabitant: " + inhabitant.getName() + " at " + dateTime + ".");

        if (inhabitant.getType() != TypeInhabitant.PERSON) {
            LOGGER.warn("Non-person inhabitant cannot operate devices. Inhabitant=" + inhabitant);
            return;
        }

        List<Person> persons = configuration.getPersons();
        if (persons == null || persons.isEmpty()) {
            LOGGER.warn("No persons available in the configuration to operate the device.");
            return;
        }

        for (Person person : persons) {
            if (!inhabitant.equals(person)) {
                continue;
            }

            if (person.getAgeGroup() == AgeGroup.BABY) {
                LOGGER.warn("Person of age group BABY cannot operate the device. Person=" + person);
                continue;
            }

            if (person.getState() != StateInhabitant.ACTIVITY) {
                LOGGER.info("Person is not in an active state to operate the device. Person=" + person);
                continue;
            }

            if (device.getState() instanceof OffState) {
                try {
                    device.setState(new IdleState(device));
                    addEventEnergy(device, dateTime, device.getState().setPower());

                    Event event = new Event(InhabitantData.INHABITANT_USE_ELECTRONIC_DEVICE, person, dateTime, device, TypeEvent.ON_DEVICE);
                    person.addEvent(event);
                    device.addEvent(event);
                    event.setEndDateTime(dateTime);
                    person.setState(StateInhabitant.ACTIVITY);

                    LOGGER.info("Device turned on by person. Event: " + event + ", Person: " + person + ", Device: " + device);
                } catch (Exception e) {
                    LOGGER.error("Error occurred while trying to turn on device '" + device.getName() + "': " + e.getMessage(), e);
                }
            } else {
                LOGGER.info("The device is not in the OFF state and cannot be turned on. Device: " + device);
            }
            break;
        }
    }



    @Override
    public void startUseDevice(Device device, Inhabitant inhabitant, LocalDateTime dateTime) {
        LOGGER.info("Attempting to start using device: " + device.getName() + " by inhabitant: " + inhabitant.getName() + " at " + dateTime + ".");

        if (inhabitant.getState() != StateInhabitant.ACTIVITY) {
            LOGGER.warn("Inhabitant is not active and cannot use the device. Inhabitant: " + inhabitant);
            return;
        }

        State currentState = device.getState();
        if (currentState instanceof BlockState) {
            LOGGER.warn("Device is blocked and cannot be used. Device: " + device);
            return;
        }

        if (currentState instanceof OffState) {
            LOGGER.info("Device is off. Turning on the device. Device: " + device);
            onDevice(device, inhabitant, dateTime);
            return;
        }

        if (currentState instanceof IdleState && device.getCurrentUser() == null) {
            LOGGER.info("Device is idle and available for use. Device: " + device);
            device.setCurrentUser(inhabitant);
            device.setState(new ActiveState(device));
            addEventEnergy(device, dateTime, device.getState().setPower());

            Event startEvent = new Event(InhabitantData.INHABITANT_USE_ELECTRONIC_DEVICE, inhabitant, dateTime, device, TypeEvent.USAGE_DEVICE);
            inhabitant.addEvent(startEvent);
            device.addEvent(startEvent);
            LOGGER.info("Inhabitant started using device. Event: " + startEvent);
        } else if (currentState instanceof ActiveState) {
            if (device.getCurrentUser() == null) {
                LOGGER.info("Device is active but no user found. Assigning new user. Device: " + device);
                device.setCurrentUser(inhabitant);
                addEventEnergy(device, dateTime, device.getState().setPower());
                startUseDevice(device, inhabitant, dateTime);
            } else if (!device.getCurrentUser().equals(inhabitant)) {
                LOGGER.info("Device is currently in use by another user. Adding inhabitant to wait list. Inhabitant: " + inhabitant);
                device.addInhabitant(inhabitant);
                inhabitant.setState(StateInhabitant.WAIT);

                Event waitEvent = new Event(InhabitantData.INHABITANT_WAIT_FOR_ELECTRONIC_DEVICE, inhabitant, dateTime, device, TypeEvent.WAIT);
                inhabitant.addEvent(waitEvent);
                device.addEvent(waitEvent);
                LOGGER.info("Inhabitant is waiting for device. Event: " + waitEvent);
            }
        }
    }



    @Override
    public void stopUseDevice(Inhabitant inhabitant, LocalDateTime dateTime) {
        LOGGER.info("Attempting to stop device used by inhabitant: " + inhabitant.getName() + " at " + dateTime + ".");

        if (inhabitant.getState() != StateInhabitant.ACTIVITY) {
            LOGGER.warn("Inhabitant is not active and hence not using any device. Inhabitant: " + inhabitant);
            return;
        }

        List<Event> events = inhabitant.getEvents();
        if (events == null || events.isEmpty()) {
            LOGGER.warn("No events found for inhabitant: " + inhabitant);
            return;
        }

        Event lastEvent = events.get(events.size() - 1);
        Device device = getDeviceLastEvent(lastEvent);
        if (device == null) {
            LOGGER.warn("No device associated with the last event of inhabitant: " + inhabitant);
            return;
        }

        State deviceState = device.getState();
        if (deviceState instanceof OffState) {
            LOGGER.info("Device is already turned off. Device: " + device);
        } else if (deviceState instanceof BlockState) {
            LOGGER.info("Device is blocked and cannot be stopped. Device: " + device);
        } else {
            lastEvent.setEndDateTime(dateTime);
            device.setCurrentUser(null);
            device.setState(new IdleState(device));
            addEventEnergy(device, dateTime, device.getState().setPower());
            LOGGER.info("Stopped using device. Event: " + lastEvent + ", Inhabitant: " + inhabitant + ", Device: " + device);

            List<Inhabitant> expects = device.getExpects();
            if (expects != null && !expects.isEmpty()) {
                Inhabitant nextInhabitant = expects.get(0);
                device.removeInhabitant(nextInhabitant);
                nextInhabitant.setState(StateInhabitant.ACTIVITY);
                startUseDevice(device, nextInhabitant, dateTime);
                LOGGER.info("Next inhabitant started using the device. Inhabitant: " + nextInhabitant + ", Device: " + device);
            }
        }
    }


    @Override
    public void stopUseDevice(Device device, LocalDateTime dateTime) {
        LOGGER.info("Attempting to stop using device: " + device.getName() + " at " + dateTime + ".");

        State currentState = device.getState();
        if (!(currentState instanceof ActiveState)) {
            LOGGER.warn("Device is not in an active state, so it cannot be stopped. Current state: " + currentState);
            return;
        }

        List<Event> events = device.getEvents();
        if (events != null && !events.isEmpty()) {
            Event lastEvent = events.get(events.size() - 1);
            lastEvent.setEndDateTime(dateTime);
            LOGGER.info("Updated last event's end time for device: " + device.getName());
        } else {
            LOGGER.warn("No events found for device: " + device.getName());
        }

        device.setCurrentUser(null);
        device.setState(new IdleState(device));
        addEventEnergy(device, dateTime, device.getState().setPower());

        List<Inhabitant> expects = device.getExpects();
        if (expects != null && !expects.isEmpty()) {
            LOGGER.info("Updating states for inhabitants expecting to use the device: " + device.getName());
            for (Inhabitant inhabitant : expects)
                inhabitant.setState(StateInhabitant.ACTIVITY);
        } else {
            LOGGER.info("No inhabitants are expecting to use the device: " + device.getName());
        }

        device.setExpects(null);
        LOGGER.info("Device usage stopped: " + device.getName());
    }
    @Override
    public void theEndLastEvent(Person person, LocalDateTime currentTime) {
        List<Event> events = person.getEvents();
        if (events != null && person.getState() != StateInhabitant.WAIT) {
            Event lastEvent = events.get(events.size() - 1);
            if (lastEvent.getEndDateTime() == null) {
                if (lastEvent.getContext().equals(InhabitantData.INHABITANT_USE_ELECTRONIC_DEVICE)) {
                    stopUseDevice(person, currentTime);
                    return;
                }
            }
        }
    }

    @Override
    public void offDevice(Device device, Inhabitant inhabitant, LocalDateTime dateTime) {
        String deviceName = device.getName();
        LOGGER.info("Attempting to turn off device: " + deviceName + " by inhabitant: " + inhabitant.getName() + " at " + dateTime + ".");

        State currentState = device.getState();
        if (currentState instanceof OffState) {
            LOGGER.info("The device '" + deviceName + "' is already turned off.");
            return;
        }

        if (currentState instanceof BlockState) {
            LOGGER.warn("The device '" + deviceName + "' cannot be turned off until it is unlocked.");
            return;
        }

        if (inhabitant.getType() != TypeInhabitant.PERSON) {
            LOGGER.warn("Only persons can turn off the device. Attempted by: " + inhabitant);
            return;
        }

        if (!(inhabitant instanceof Person)) {
            LOGGER.warn("Inhabitant is not a person. Cannot operate the device. Inhabitant: " + inhabitant);
            return;
        }

        Person person = (Person) inhabitant;
        if (person.getAgeGroup() == AgeGroup.BABY) {
            LOGGER.warn("A baby cannot turn off the device. Person: " + person);
            return;
        }

        if (currentState instanceof ActiveState) {
            LOGGER.info("Stopping device usage before turning off. Device: " + deviceName);
            stopUseDevice(inhabitant, dateTime);
        }

        device.setCurrentUser(null);
        device.setState(new OffState(device));
        Event offEvent = new Event(InhabitantData.INHABITANT_USE_ELECTRONIC_DEVICE, person, dateTime, device, TypeEvent.OFF_DEVICE);
        person.addEvent(offEvent);
        device.addEvent(offEvent);
        offEvent.setEndDateTime(dateTime);
        person.setState(StateInhabitant.ACTIVITY);

        LOGGER.info("Device '" + deviceName + "' has been turned off by person: " + person);
    }


    @Override
    public void fixDevice(Device device, Person person, LocalDateTime dateTime) {
        stopUseDevice(device, dateTime);
        device.setQuality(100);
        Event event = new Event(PersonData.PERSON_FIX_DEVICE, person, dateTime, device,TypeEvent.OFF_DEVICE);
        person.addEvent(event);
        device.addEvent(event);
        event.setEndDateTime(dateTime);
        LOGGER.info("Start " + event + " Person=" + person + ", Device=" + device);
    }



    @Override
    public void addEventEnergy(Device device, LocalDateTime dateTime, int power) {
        List<EventEnergy> events = device.getEventEnergy();
        if (events != null && events.get(events.size() - 1).getEnd() == null)
            events.get(events.size() - 1).setEnd(dateTime);
        EventEnergy event =  new EventEnergy(dateTime, null, power);
        device.addEventEnergy(event);
    }


}