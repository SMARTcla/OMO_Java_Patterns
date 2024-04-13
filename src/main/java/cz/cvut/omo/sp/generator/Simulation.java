package cz.cvut.omo.sp.generator;

import cz.cvut.omo.sp.alive.person.ActionTypePerson;
import cz.cvut.omo.sp.alive.pet.ActionTypePet;
import cz.cvut.omo.sp.alive.pet.Pet;
import cz.cvut.omo.sp.data.DeviceData;
import cz.cvut.omo.sp.data.PersonData;
import cz.cvut.omo.sp.data.PetData;
import cz.cvut.omo.sp.events.Event;
import cz.cvut.omo.sp.events.TypeEvent;
import cz.cvut.omo.sp.load.LoadJson;
import cz.cvut.omo.sp.manager.SmartHomeManager;
import cz.cvut.omo.sp.reports.ReportConsumption;
import cz.cvut.omo.sp.tech.auto.Transport;
import cz.cvut.omo.sp.tech.device.Device;
import cz.cvut.omo.sp.alive.inhabitant.StateInhabitant;
import cz.cvut.omo.sp.alive.person.AgeGroup;
import cz.cvut.omo.sp.alive.person.Person;
import cz.cvut.omo.sp.tech.device.devices.*;
import cz.cvut.omo.sp.tech.sencor.sencors.*;
import cz.cvut.omo.sp.facade.Facade;
import cz.cvut.omo.sp.facade.FacadeImplementation;
import cz.cvut.omo.sp.manager.FactorySmartHomeManager;
import cz.cvut.omo.sp.reports.ReportActivityAndUsage;
import cz.cvut.omo.sp.reports.ReportEvent;
import cz.cvut.omo.sp.reports.ReportHouseConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
/**
 * The {@code Simulation} class represents a smart home simulation that models various events and actions in a smart home environment.
 * It simulates the behavior of inhabitants, devices, and sensors within the home.
 */
public class Simulation {

    private static final Logger LOGGER = LogManager.getLogger(Simulation.class.getName());

    private final int numberCase;
    private final String startDateAndTime;
    private LocalDateTime currentTime;
    private long duration;
    private int airHumidity;
    private int airSmoke;
    private int temperature;
    private boolean isDay;
    private LoadJson loadConfiguration;


    private SmartHomeManager homeConfiguration;
    private Facade facade;
    private Random rand;
    /**
     * Constructs a new simulation with the specified configuration number, start date and time, and duration.
     *
     * @param numberCase    The configuration number.
     * @param startDateAndTime The start date and time in ISO 8601 format.
     * @param duration         The duration of the simulation in minutes.
     */
    public Simulation(int numberCase, String startDateAndTime, long duration) {
        this.numberCase = numberCase;
        this.startDateAndTime = startDateAndTime;
        this.duration = duration;
        this.loadConfiguration =  new LoadJson();
        this.airHumidity = 0;
        this.airSmoke = 0;
        this.temperature = 20;
        this.rand = new Random();
    }
    /**
     * Starts the smart home simulation, initializing the environment and running the simulation loop.
     */
    public void start() {
        LOGGER.info("Start simulation.");

        currentTime = LocalDateTime.parse(startDateAndTime);
        loadConfiguration.loadAllJson(numberCase);
        homeConfiguration = loadConfiguration.getHomeConfiguration();
        facade = new FacadeImplementation(homeConfiguration);

        FactorySmartHomeManager managerObserver = new FactorySmartHomeManager(homeConfiguration);
        managerObserver.generateSubjects();

        run();
    }
    /**
     * Runs the simulation loop, generating events and actions for the smart home environment.
     */
    public void run() {
        LOGGER.info("Run simulation.");

        int tick = 0;
        while (duration > 0) {
            if (tick == 2) {
                generateRandomPetEvent();
                generatePersonBaseEvent();
            } else if (tick == 4) {
                generateRandomTransportEvent();
                generateRandomDeviceEvent();
                generateRandomDeviceEvent();
                tick = 0;
            }
            checkDeviceQuality();
            changeWeather();
            tick++;
            duration--;
            currentTime = currentTime.plusMinutes(1);
        }
        stop();
    }
    /**
     * Generates random events related to the activities of pets in the smart home.
     * It randomly selects a pet and an action for the pet to perform, such as walking, playing, or sleeping,
     * and updates the pet's state and logs the event.
     */
    private void generateRandomPetEvent() {
        Pet pet = homeConfiguration.getPets().get(rand.nextInt(homeConfiguration.getPets().size()));
        facade.theEndLastEvent(pet, currentTime);
        if (pet.getState() == StateInhabitant.ACTIVITY) {
            List<ActionTypePet> actions = pet.getActions();
            switch (actions.get(rand.nextInt(actions.size()))) {
                case WALK -> {
                    Person person1 = getFreePerson();
                    if (person1 != null) {
                        pet.setState(StateInhabitant.OUTSIDE);
                        person1.setState(StateInhabitant.OUTSIDE);
                        Event event = new Event(PetData.PET_ACTIVITY_WALK_WITH_OWNER, pet, currentTime, person1, TypeEvent.ACTION);
                        person1.addEvent(event);
                        pet.addEvent(event);

                        LOGGER.info("Start " + event + " Pet=" + pet + ", Person=" + person1);
                    } else
                        LOGGER.info("Not free person. Pet cannot walk! Pet=" + pet);
                }
                case PLAY -> {
                    Event event;
                    Person person2 = getFreePerson();
                    if (person2 != null) {
                        person2.setState(StateInhabitant.ACTIVITY);
                        event = new Event(PetData.PET_ACTIVITY_WALK_WITH_OWNER, pet, currentTime, person2, TypeEvent.ACTION);
                        pet.addEvent(event);
                    } else
                        event = new Event(PetData.PET_ACTIVITY_WALK_WITH_OWNER, pet, currentTime, null, TypeEvent.ACTION);
                    pet.addEvent(event);
                    pet.setState(StateInhabitant.ACTIVITY);
                    LOGGER.info("Start " + event + " Pet=" + pet);
                }
                case SLEEP -> {
                    pet.setState(StateInhabitant.ACTIVITY);
                    Event event1 = new Event(PetData.PET_ACTIVITY_SLEEP, pet, currentTime, null, TypeEvent.ACTION);
                    pet.addEvent(event1);
                    LOGGER.info("Start " + event1 + " Pet=" + pet);
                }
            }
        }
    }
    /**
     * Generates random events related to the activities of persons in the smart home.
     * It randomly selects a person and an action for the person to perform, such as sleeping, eating, playing, leaving home,
     * using an automobile, or walking, and updates the person's state and logs the event.
     */
    private void generatePersonBaseEvent() {
        Person person = homeConfiguration.getPersons().get(rand.nextInt(homeConfiguration.getPersons().size()));
        facade.theEndLastEvent(person, currentTime);
        if (person.getState() == StateInhabitant.ACTIVITY) {
            List<ActionTypePerson> actions = person.getActions();
            List<ActionTypePerson> actionFromGen = new ArrayList<>();
            for (ActionTypePerson action : actions) {
                if (action.equals(ActionTypePerson.SLEEP) || action.equals(ActionTypePerson.EAT) ||
                        action.equals(ActionTypePerson.PLAY) || action.equals(ActionTypePerson.LEAVE_HOME) ||
                        action.equals(ActionTypePerson.USAGE_AUTO) || action.equals(ActionTypePerson.WALK))
                    actionFromGen.add(action);
            }
            switch (actionFromGen.get(rand.nextInt(actionFromGen.size()))) {
                case SLEEP:
                    person.setState(StateInhabitant.ACTIVITY);
                    Event event = new Event(PersonData.PERSON_SLEEP, person, currentTime, null, TypeEvent.ACTION);
                    person.addEvent(event);

                    LOGGER.info("Start " + event + " Person=" + person.getName());
                    break;
                case EAT:
                    List<Fridge> refrigerators = homeConfiguration.getFridges();
                    if (refrigerators != null) {
                        facade.startUseDevice(refrigerators.get(rand.nextInt(refrigerators.size())), person, currentTime);
                    } else
                        LOGGER.info("Not found refrigerators.");
                    break;
                case PLAY:
                    if (person.getAgeGroup() == AgeGroup.BABY) {
                        Person person1 = getFreePerson();
                        if (person1 != null) {
                            person.setState(StateInhabitant.ACTIVITY);
                            person1.setState(StateInhabitant.ACTIVITY);
                            event = new Event(PersonData.PERSON_PLAY_WITH_CHILD, person, currentTime, person1, TypeEvent.ACTION);
                            person1.addEvent(event);
                            person.addEvent(event);

                            LOGGER.info("Start " + event + " Baby=" + person + " Person=" + person1);
                        } else
                            LOGGER.info("Not found free person.");
                    } else {
                        PC pc = homeConfiguration.getPcs().get(rand.nextInt(homeConfiguration.getPcs().size()));
                        facade.startUseDevice(pc, person, currentTime);
                    }
                    break;
                case LEAVE_HOME:
                    person.setState(StateInhabitant.OUTSIDE);
                    event = new Event(PersonData.PERSON_LEAVE_HOME, person, currentTime, null, TypeEvent.ACTION);
                    person.addEvent(event);

                    LOGGER.info("Start " + event + " Person=" + person);
                    break;
                case USAGE_AUTO:
                    Transport freeAuto = getFreeAuto();
                    freeAuto.setCurrentUser(person);
                    person.setState(StateInhabitant.OUTSIDE);
                    event = new Event(PersonData.PERSON_USE_CAR, person, currentTime, null, TypeEvent.ACTION);
                    person.addEvent(event);
                    freeAuto.addEvent(event);

                    LOGGER.info("Start " + event + " Person=" + person + " Auto=" + freeAuto);
                    break;
                case WALK:
                    if (person.getAgeGroup() == AgeGroup.BABY) {
                        Person person1 = getFreePerson();
                        if (person1 != null) {
                            person.setState(StateInhabitant.OUTSIDE);
                            person1.setState(StateInhabitant.OUTSIDE);
                            event = new Event(PersonData.PERSON_WALK_WITH_CHILD, person, currentTime, null, TypeEvent.ACTION);
                            person.addEvent(event);
                            person1.addEvent(event);

                            LOGGER.info("Start " + event + " Baby=" + person + " Person=" + person1);
                        } else
                            LOGGER.info("Not found free person.");

                    }
                    break;
            }
        }
    }
    /**
     * Finds a free automobile transport that can be used by a person.
     *
     * @return A free automobile transport if available, or null if none are available.
     */
    private Transport getFreeAuto() {
        for (Transport transport: homeConfiguration.getTransports())
            if (transport.getCurrentUser() == null && transport.isAuto())
                return transport;
        return null;
    }
    /**
     * Generates random events related to the usage of transport by persons in the smart home.
     * It randomly selects a person and a transport for the person to use, such as a car or bicycle,
     * and updates the person's state, assigns the transport to the person, and logs the event.
     */
    private void generateRandomTransportEvent() {
        List<Person> persons = homeConfiguration.getPersons().stream()
                .filter(Person::canUseTransport)
                .collect(Collectors.toList());
        Person person = persons.get(rand.nextInt(persons.size()));
        facade.theEndLastEvent(person, currentTime);
        if (person.getState() == StateInhabitant.ACTIVITY) {
            List<Transport> transports = homeConfiguration.getTransports().stream()
                    .filter(transport -> !transport.isAuto())
                    .collect(Collectors.toList());
            Transport transport = transports.get(rand.nextInt(transports.size()));
            if (transport.getCurrentUser() == null) {
                transport.setCurrentUser(person);
                person.setState(StateInhabitant.OUTSIDE);
                Event event = new Event(PersonData.PERSON_USE_CAR, person, currentTime, transport, TypeEvent.ACTION);
                person.addEvent(event);
                transport.addEvent(event);

                LOGGER.info("Start " + event + " Person=" + person + ", Transport=" + transport);
            } else {
                if (person.equals(transport.getCurrentUser()))
                    return;
                transport.addPerson(person);
                person.setState(StateInhabitant.WAIT);
                Event event = new Event(PersonData.PERSON_WAIT_FOR_CAR, person, currentTime, transport, TypeEvent.ACTION);
                person.addEvent(event);

                LOGGER.info("Start " + event + " Person=" + person + ", Transport=" + transport);
            }
        }
    }

    /**
     * Generates random events related to the usage of various devices by persons in the smart home.
     * It randomly selects a person who can use devices and then selects a device type and starts its usage.
     * The selected devices include alarms, coffee machines, fridges, PCs, lamps, TVs, vacuum cleaners,
     * PlayStation consoles, and showers.
     */
    private void generateRandomDeviceEvent() {
        List<Person> persons = homeConfiguration.getPersons().stream().filter(Person::canUseDevice).collect(Collectors.toList());
        Person person = persons.get(rand.nextInt(persons.size()));
        facade.theEndLastEvent(person, currentTime);
        if (person.getState() == StateInhabitant.ACTIVITY) {
            switch (rand.nextInt(10)) {
                case 0 -> {
                    List<Alarm> alarms = homeConfiguration.getAlarms();
                    Alarm alarm = alarms.get(rand.nextInt(alarms.size()));
                    facade.theEndLastEvent(person, currentTime);
                    facade.startUseDevice(alarm, person, currentTime);
                }
                case 1 -> {
                    List<CoffeeMachine> coffeeMachines = homeConfiguration.getCoffeeMachines();
                    CoffeeMachine coffeeMachine = coffeeMachines.get(rand.nextInt(coffeeMachines.size()));
                    facade.theEndLastEvent(person, currentTime);
                    facade.startUseDevice(coffeeMachine, person, currentTime);
                }
                case 2 -> {
                    List<Fridge> fridges = homeConfiguration.getFridges();
                    Fridge fridge = fridges.get(rand.nextInt(fridges.size()));
                    facade.theEndLastEvent(person, currentTime);
                    facade.startUseDevice(fridge, person, currentTime);
                }
                case 3 -> {
                    List<PC> pcs = homeConfiguration.getPcs();
                    PC pc = pcs.get(rand.nextInt(pcs.size()));
                    facade.theEndLastEvent(person, currentTime);
                    facade.startUseDevice(pc, person, currentTime);
                }
                case 4 -> {
                    List<Lamp> lapms = homeConfiguration.getLamps();
                    Lamp lamp = lapms.get(rand.nextInt(lapms.size()));
                    facade.theEndLastEvent(person, currentTime);
                    facade.startUseDevice(lamp, person, currentTime);
                }
                case 5 -> {
                    List<TV> tvs = homeConfiguration.getTvs();
                    TV tv = tvs.get(rand.nextInt(tvs.size()));
                    facade.theEndLastEvent(person, currentTime);
                    facade.startUseDevice(tv, person, currentTime);
                }
                case 6 -> {
                    List<VacuumCleaner> cleaners = homeConfiguration.getVacuumCleaners();
                    VacuumCleaner cleaner = cleaners.get(rand.nextInt(cleaners.size()));
                    facade.theEndLastEvent(person, currentTime);
                    facade.startUseDevice(cleaner, person, currentTime);
                }
                case 7 -> {
                    List<PlayStation> playStations = homeConfiguration.getPlayStations();
                    PlayStation playStation = playStations.get(rand.nextInt(playStations.size()));
                    facade.theEndLastEvent(person, currentTime);
                    facade.startUseDevice(playStation, person, currentTime);
                }
                case 8 -> {
                    List<Shower> showers = homeConfiguration.getShowers();
                    Shower shower = showers.get(rand.nextInt(showers.size()));
                    facade.theEndLastEvent(person, currentTime);
                    facade.startUseDevice(shower, person, currentTime);
                }
            }
        }
    }



    /**
     * Simulates changes in various environmental parameters like air quality, light levels, motion detection,
     * energy consumption, sound levels, structural integrity, temperature, and water presence.
     * These parameters are adjusted based on predefined rules or random fluctuations to simulate realistic
     * changes in the smart home environment.
     */
    private void changeWeather() {
        int airQuality = 100;
        boolean motionDetected = false;
        int energyConsumption = 100;
        int soundLevel = 100;
        int structuralIntegrity = 100;
        boolean waterPresence = false;

        List<AirQualitySensor> airQualitySensors = homeConfiguration.getAirQualitySensors();
        if (airQualitySensors != null) {
            for (AirQualitySensor sensor : airQualitySensors)
                sensor.setReadings(airQuality); // Assuming airQuality is a predefined variable
        }

        List<LightSensor> lightSensors = homeConfiguration.getLightSensors();
        if (lightSensors != null) {
            for (LightSensor sensor : lightSensors)
                sensor.setReadings(isDay ? 0 : 1); // Assuming isDay is a boolean indicating day or night
        }

        List<MotionSensor> motionSensors = homeConfiguration.getMotionSensors();
        if (motionSensors != null) {
            for (MotionSensor sensor : motionSensors)
                sensor.setReadings(motionDetected ? 1 : 0); // Assuming motionDetected is a boolean
        }

        List<SmartEnergyMeter> smartEnergyMeters = homeConfiguration.getSmartEnergyMeters();
        if (smartEnergyMeters != null) {
            for (SmartEnergyMeter sensor : smartEnergyMeters)
                sensor.setReadings(energyConsumption); // Assuming energyConsumption is a predefined variable
        }

        List<SoundLevelSensor> soundLevelSensors = homeConfiguration.getSoundLevelSensors();
        if (soundLevelSensors != null) {
            for (SoundLevelSensor sensor : soundLevelSensors)
                sensor.setReadings(soundLevel); // Assuming soundLevel is a predefined variable
        }
        List<StructuralHealthSensor> structuralHealthSensors = homeConfiguration.getStructuralHealthSensors();
        if (structuralHealthSensors != null) {
            for (StructuralHealthSensor sensor : structuralHealthSensors)
                sensor.setReadings(structuralIntegrity); // Assuming structuralIntegrity is a predefined variable
        }

        List<TemperatureSensor> temperatureSensors = homeConfiguration.getTemperatureSensors();
        if (temperatureSensors != null) {
            for (TemperatureSensor sensor : temperatureSensors)
                sensor.setReadings(temperature); // Assuming temperature is a predefined variable
        }

        List<WaterLeakSensor> waterLeakSensors = homeConfiguration.getWaterLeakSensors();
        if (waterLeakSensors != null) {
            for (WaterLeakSensor sensor : waterLeakSensors)
                sensor.setReadings(waterPresence ? 1 : 0); // Assuming waterPresence is a boolean
        }

    }
    /**
     * Simulates changes in a given parameter based on predefined rules or random fluctuations.
     * The method randomly adds or subtracts a small value from the parameter to create variability.
     *
     * @param parameter The parameter value to be modified.
     * @return The modified parameter value after simulating changes.
     */
    private int changeWeatherParameters(int parameter) {
        if (rand.nextInt(2) == 1) {
            int part = rand.nextInt(0, 5);
            if (rand.nextInt(2) == 1) {
                return parameter + part;
            } else
                return parameter - part;
        }
        return parameter;
    }
    /**
     * Checks the quality of various devices in the smart home and takes appropriate actions if their quality
     * deteriorates to a critical level. This method iterates through different types of devices and reduces
     * their quality based on predefined rules. If a device's quality reaches zero, it is either fixed by a free person
     * or blocked, depending on availability.
     */
    private void checkDeviceQuality() {
        qualityReduction(homeConfiguration.getPcs());
        qualityReduction(homeConfiguration.getSpeakers());
        qualityReduction(homeConfiguration.getTvs());
        qualityReduction(homeConfiguration.getTeapots());
        qualityReduction(homeConfiguration.getCoffeeMachines());
        qualityReduction(homeConfiguration.getAlarms());
        qualityReduction(homeConfiguration.getLamps());
        qualityReduction(homeConfiguration.getPlayStations());
        qualityReduction(homeConfiguration.getPcs());
        qualityReduction(homeConfiguration.getShowers());
    }

    /**
     * Reduces the quality of a list of devices based on predefined rules. If a device's quality reaches zero,
     * it takes appropriate actions like fixing it with a free person or blocking it, depending on availability.
     *
     * @param devices A list of devices to be checked and have their quality reduced.
     */
    private void qualityReduction(List<? extends Device> devices) {
        if (devices != null) {
            for (Device device : devices) {
                if (device.getState().toString().equals("ACTIVE")) {
                    device.setQuality(device.getQuality() - DeviceData.QUALITY_REDUCTION);
                    if (device.getQuality() == 0) {
                        Person person = getFreePerson();
                        if (person != null)
                            facade.fixDevice(device, person, currentTime);
                        else
                            facade.blockDevice(device, currentTime);
                    }
                }
            }
        }
    }

    /**
     * Finds a free person who can perform an action or provide assistance in fixing a device.
     *
     * @return A free person who is available to perform an action or assist with fixing a device.
     */
    private Person getFreePerson() {
        for (Person person : homeConfiguration.getPersons()) {
            if (person.getState() == StateInhabitant.ACTIVITY && person.getAgeGroup() != AgeGroup.BABY) {
                return person;
            }
        }
        return null;
    }


    /**
     * Stops the simulation and generates reports about the simulation results.
     */
    public void stop() {
        String time = currentTime.format(DateTimeFormatter.ofPattern("d-MM-yyyy_HH-mm"));
        ReportHouseConfiguration hr = new ReportHouseConfiguration(numberCase, time);
        hr.visit(homeConfiguration);

        ReportActivityAndUsage aur = new ReportActivityAndUsage(numberCase, time);
        aur.setCurrentTime(currentTime);
        aur.visit(homeConfiguration);

        ReportConsumption cr = new ReportConsumption(numberCase, time);
        cr.visit(homeConfiguration);

        ReportEvent er = new ReportEvent(numberCase, time);
        er.visit(homeConfiguration);

        LOGGER.info("The end simulation.");
        System.exit(1);
    }
}