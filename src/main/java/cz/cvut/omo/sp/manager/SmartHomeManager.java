package cz.cvut.omo.sp.manager;

import cz.cvut.omo.sp.data.HouseData;
import cz.cvut.omo.sp.exception.RoomNotFoundException;
import cz.cvut.omo.sp.reports.ReportVisitor;
import cz.cvut.omo.sp.places.Floor;
import cz.cvut.omo.sp.places.Home;
import cz.cvut.omo.sp.places.Room;
import cz.cvut.omo.sp.places.Window;
import cz.cvut.omo.sp.tech.auto.Transport;
import cz.cvut.omo.sp.tech.device.Device;
import cz.cvut.omo.sp.alive.person.Person;
import cz.cvut.omo.sp.alive.pet.Pet;
import cz.cvut.omo.sp.tech.sencor.sencors.*;
import cz.cvut.omo.sp.tech.device.devices.*;
import java.util.*;
/**
 * The `SmartHomeManager` class manages various aspects of a smart home environment,
 * including sensors, devices, persons, pets, and transports.
 * It provides methods to add and retrieve these components and keeps track of the home's temperature.
 * This class also implements the `HomeVisitor` interface to accept visitor patterns for reporting purposes.
 */
public class SmartHomeManager implements HomeVisitor {
    private int temperature;

    private List<AirQualitySensor> airQualitySensors;
    private List<LightSensor> lightSensors;
    private List<MotionSensor> motionSensors;
    private List<SmartEnergyMeter> smartEnergyMeters;
    private List<SoundLevelSensor> soundLevelSensors;
    private List<StructuralHealthSensor> structuralHealthSensors;
    private List<TemperatureSensor> temperatureSensors;
    private List<WaterLeakSensor> waterLeakSensors;

    private List<Alarm> alarms;
    private List<CoffeeMachine> coffeeMachines;
    private List<Fridge> fridges;
    private List<Lamp> lamps;
    private List<PC> pcs;
    private List<PlayStation> playStations;
    private List<Shower> showers;
    private List<Speaker> speakers;
    private List<TV> tvs;
    private List<Teapot> teapots;
    private List<VacuumCleaner> vacuumCleaners;
    private List<Pet> pets;
    private List<Person> persons;
    private List<Transport> transports;
    private final Home home;
    private List<Window> windows;

    public Home getHome() {
        return home;
    }

    /**
     * Initializes a new instance of the `SmartHomeManager` class.
     * It sets up the smart home environment and initializes lists for persons, pets, and transports.
     */
    public SmartHomeManager() {
            this.home = Home.getHome();
            this.persons =  new ArrayList<>();
            this.pets =  new ArrayList<>();
            this.transports = new ArrayList<>();
    }
    @Override
    public void acceptVisitor(ReportVisitor visitor) {
        visitor.visit(this);
    }


    public void addAirQualitySensor(AirQualitySensor sensor) {
        Objects.requireNonNull(sensor, "AirQualitySensor cannot be null");
        if (airQualitySensors == null) {
            airQualitySensors = new ArrayList<>();
        }
        airQualitySensors.add(sensor);
    }

    public void addLightSensor(LightSensor sensor) {
        Objects.requireNonNull(sensor, "LightSensor cannot be null");
        if (lightSensors == null) {
            lightSensors = new ArrayList<>();
        }
        lightSensors.add(sensor);
    }
    public void addWindow(Window window) {
        Objects.requireNonNull(window);
        if (windows == null) windows = new ArrayList<>();
        windows.add(window);
    }

    public List<Window> getWindows() {
        return windows;
    }

    public void setWindows(List<Window> windows) {
        this.windows = windows;
    }

    public void addMotionSensor(MotionSensor sensor) {
        Objects.requireNonNull(sensor, "MotionSensor cannot be null");
        if (motionSensors == null) {
            motionSensors = new ArrayList<>();
        }
        motionSensors.add(sensor);
    }

    public void addSmartEnergyMeter(SmartEnergyMeter sensor) {
        Objects.requireNonNull(sensor, "SmartEnergyMeter cannot be null");
        if (smartEnergyMeters == null) {
            smartEnergyMeters = new ArrayList<>();
        }
        smartEnergyMeters.add(sensor);
    }

    public Room getRoomByID(int id) {
        return home.getFloors().stream()
                .flatMap(floor -> floor.getRooms().stream())
                .filter(room -> room != null && room.getId() == id)
                .findFirst()
                .orElseThrow(() -> new RoomNotFoundException("Room with ID " + id + " not found"));
    }


    public void addSoundLevelSensor(SoundLevelSensor sensor) {
        Objects.requireNonNull(sensor, "SoundLevelSensor cannot be null");
        if (soundLevelSensors == null) {
            soundLevelSensors = new ArrayList<>();
        }
        soundLevelSensors.add(sensor);
    }

    public void addStructuralHealthSensor(StructuralHealthSensor sensor) {
        Objects.requireNonNull(sensor, "StructuralHealthSensor cannot be null");
        if (structuralHealthSensors == null) {
            structuralHealthSensors = new ArrayList<>();
        }
        structuralHealthSensors.add(sensor);
    }

    public void addTemperatureSensor(TemperatureSensor sensor) {
        Objects.requireNonNull(sensor, "TemperatureSensor cannot be null");
        if (temperatureSensors == null) {
            temperatureSensors = new ArrayList<>();
        }
        temperatureSensors.add(sensor);
    }

    public void addWaterLeakSensor(WaterLeakSensor sensor) {
        Objects.requireNonNull(sensor, "WaterLeakSensor cannot be null");
        if (waterLeakSensors == null) {
            waterLeakSensors = new ArrayList<>();
        }
        waterLeakSensors.add(sensor);
    }



    public List<LightSensor> getLightSensors() {
        return lightSensors;
    }

    public void setLightSensors(List<LightSensor> lightSensors) {
        this.lightSensors = lightSensors;
    }


    public List<TemperatureSensor> getTemperatureSensors() {
        return temperatureSensors;
    }

    public void setTemperatureSensors(List<TemperatureSensor> temperatureSensors) {
        this.temperatureSensors = temperatureSensors;
    }

    public List<Alarm> getAlarms() {
        return alarms;
    }

    public void setAlarms(List<Alarm> alarms) {
        this.alarms = alarms;
    }

    public List<CoffeeMachine> getCoffeeMachines() {
        return coffeeMachines;
    }

    public void setCoffeeMachines(List<CoffeeMachine> coffeeMachines) {
        this.coffeeMachines = coffeeMachines;
    }

    public List<Fridge> getFridges() {
        return fridges;
    }

    public void setFridges(List<Fridge> fridges) {
        this.fridges = fridges;
    }

    public List<Lamp> getLamps() {
        return lamps;
    }

    public void setLamps(List<Lamp> lamps) {
        this.lamps = lamps;
    }

    public List<PC> getPcs() {
        return pcs;
    }

    public void setPcs(List<PC> pcs) {
        this.pcs = pcs;
    }

    public List<PlayStation> getPlayStations() {
        return playStations;
    }

    public void setPlayStations(List<PlayStation> playStations) {
        this.playStations = playStations;
    }

    public List<Shower> getShowers() {
        return showers;
    }

    public void setShowers(List<Shower> showers) {
        this.showers = showers;
    }

    public List<Speaker> getSpeakers() {
        return speakers;
    }

    public void setSpeakers(List<Speaker> speakers) {
        this.speakers = speakers;
    }

    public List<TV> getTvs() {
        return tvs;
    }

    public void setTvs(List<TV> tvs) {
        this.tvs = tvs;
    }

    public List<Teapot> getTeapots() {
        return teapots;
    }

    public void setTeapots(List<Teapot> teapots) {
        this.teapots = teapots;
    }

    public List<VacuumCleaner> getVacuumCleaners() {
        return vacuumCleaners;
    }

    public void setVacuumCleaners(List<VacuumCleaner> vacuumCleaners) {
        this.vacuumCleaners = vacuumCleaners;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    public List<Person> getPersons() {
        return persons;
    }
    public void addPerson(Person person) {
        Objects.requireNonNull(person);
        persons.add(person);
    }
    public void addPet(Pet pet) {
        Objects.requireNonNull(pet);
        pets.add(pet);
    }
    public void addTransport(Transport transport) {
        Objects.requireNonNull(transport);
        transports.add(transport);
    }



    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    public List<Transport> getTransports() {
        return transports;
    }

    public void setTransports(List<Transport> transports) {
        this.transports = transports;
    }
    public List<AirQualitySensor> getAirQualitySensors() {
        return airQualitySensors;
    }

    public void setAirQualitySensors(List<AirQualitySensor> airQualitySensors) {
        this.airQualitySensors = airQualitySensors;
    }

    public List<MotionSensor> getMotionSensors() {
        return motionSensors;
    }

    public void setMotionSensors(List<MotionSensor> motionSensors) {
        this.motionSensors = motionSensors;
    }

    public List<SmartEnergyMeter> getSmartEnergyMeters() {
        return smartEnergyMeters;
    }

    public void setSmartEnergyMeters(List<SmartEnergyMeter> smartEnergyMeters) {
        this.smartEnergyMeters = smartEnergyMeters;
    }

    public List<SoundLevelSensor> getSoundLevelSensors() {
        return soundLevelSensors;
    }

    public void setSoundLevelSensors(List<SoundLevelSensor> soundLevelSensors) {
        this.soundLevelSensors = soundLevelSensors;
    }

    public List<StructuralHealthSensor> getStructuralHealthSensors() {
        return structuralHealthSensors;
    }

    public void setStructuralHealthSensors(List<StructuralHealthSensor> structuralHealthSensors) {
        this.structuralHealthSensors = structuralHealthSensors;
    }

    public List<WaterLeakSensor> getWaterLeakSensors() {
        return waterLeakSensors;
    }

    public void setWaterLeakSensors(List<WaterLeakSensor> waterLeakSensors) {
        this.waterLeakSensors = waterLeakSensors;
    }

    public void addAlarm(Alarm alarm) {
        Objects.requireNonNull(alarm, HouseData.ALARM_NAME);
        if (alarms == null) alarms = new ArrayList<>();
        alarms.add(alarm);
    }

    public void addCoffeeMachine(CoffeeMachine coffeeMachine) {
        Objects.requireNonNull(coffeeMachine, HouseData.COFFEE_MACHINE_NAME);
        if (coffeeMachines == null) coffeeMachines = new ArrayList<>();
        coffeeMachines.add(coffeeMachine);
    }

    public void addFridge(Fridge fridge) {
        Objects.requireNonNull(fridge, HouseData.FRIDGE_NAME);
        if (fridges == null) fridges = new ArrayList<>();
        fridges.add(fridge);
    }

    public void addLamp(Lamp lamp) {
        Objects.requireNonNull(lamp, HouseData.LAMP_NAME);
        if (lamps == null) lamps = new ArrayList<>();
        lamps.add(lamp);
    }

    public void addPC(PC pc) {
        Objects.requireNonNull(pc, HouseData.PC_NAME);
        if (pcs == null) pcs = new ArrayList<>();
        pcs.add(pc);
    }

    public void addPlayStation(PlayStation playStation) {
        Objects.requireNonNull(playStation, HouseData.PLAYSTATION_NAME);
        if (playStations == null) playStations = new ArrayList<>();
        playStations.add(playStation);
    }

    public void addShower(Shower shower) {
        Objects.requireNonNull(shower, HouseData.SHOWER_NAME);
        if (showers == null) showers = new ArrayList<>();
        showers.add(shower);
    }

    public void addSpeaker(Speaker speaker) {
        Objects.requireNonNull(speaker, HouseData.SPEAKER_NAME);
        if (speakers == null) speakers = new ArrayList<>();
        speakers.add(speaker);
    }

    public void addTV(TV tv) {
        Objects.requireNonNull(tv, HouseData.TV_NAME);
        if (tvs == null) tvs = new ArrayList<>();
        tvs.add(tv);
    }

    public void addTeapot(Teapot teapot) {
        Objects.requireNonNull(teapot, HouseData.TEAPOT_NAME);
        if (teapots == null) teapots = new ArrayList<>();
        teapots.add(teapot);
    }

    public void addVacuumCleaner(VacuumCleaner vacuumCleaner) {
        Objects.requireNonNull(vacuumCleaner, HouseData.VACUUM_CLEANER_NAME);
        if (vacuumCleaners == null) vacuumCleaners = new ArrayList<>();
        vacuumCleaners.add(vacuumCleaner);
    }


    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }


    public List<Device> getAllDevices() {
        List<Device> allDevices = new ArrayList<>();
        allDevices.addAll(alarms);
        allDevices.addAll(coffeeMachines);
        allDevices.addAll(fridges);
        allDevices.addAll(lamps);
        allDevices.addAll(pcs);
        allDevices.addAll(playStations);
        allDevices.addAll(showers);
        allDevices.addAll(speakers);
        allDevices.addAll(tvs);
        allDevices.addAll(teapots);
        allDevices.addAll(vacuumCleaners);
        return allDevices;
    }
}