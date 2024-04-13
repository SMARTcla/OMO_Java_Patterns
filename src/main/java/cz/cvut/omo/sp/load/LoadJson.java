package cz.cvut.omo.sp.load;

import cz.cvut.omo.sp.manager.SmartHomeManager;
import cz.cvut.omo.sp.places.Floor;
import cz.cvut.omo.sp.places.Room;
import cz.cvut.omo.sp.places.Window;
import cz.cvut.omo.sp.tech.device.Device;
import cz.cvut.omo.sp.alive.inhabitant.StateInhabitant;
import cz.cvut.omo.sp.tech.sencor.Sensor;
import cz.cvut.omo.sp.tech.device.DeviceBuilder;
import cz.cvut.omo.sp.alive.person.PersonBuilder;
import cz.cvut.omo.sp.alive.pet.PetBuilder;
import cz.cvut.omo.sp.tech.auto.AutoBuilder;
import cz.cvut.omo.sp.tech.sencor.FactorySensor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
/**
 * The {@code LoadJson} class is responsible for loading and configuring the smart home system from JSON configuration files.
 * It reads JSON files containing information about devices, sensors, persons, pets, and transport within the smart home.
 *
 * The class uses builders and factories to create and configure various elements of the smart home.
 */
public class LoadJson {

    private static final Logger LOGGER = LogManager.getLogger(LoadJson.class.getName());

    private static final String PATH = Objects.requireNonNull(LoadJson.class.getResource("/")).getPath() + "home_settings",
            FIRST = "/case1",
            SECOND = "/case2",
            THIRD = "/case3",
            FOURTH = "/case4",
            FIFTH = "/case5";

    private final SmartHomeManager smartHomeManager;
    /**
     * Gets the smart home configuration.
     *
     * @return The smart home manager.
     */
    public SmartHomeManager getHomeConfiguration() {
        return smartHomeManager;
    }

    private final FactorySensor factorySensor;
    /**
     * Constructs a {@code LoadJson} object, initializing the smart home manager.
     */
    public LoadJson() {
        this.smartHomeManager = new SmartHomeManager();
        this.factorySensor = new FactorySensor();
    }
    /**
     * Loads the JSON configuration specified by the given configuration number.
     *
     * @param numberCase The number of the configuration to load (1 or 2).
     */
    public void loadAllJson(int numberCase) {
        String nameConfig;
        if(numberCase == 1) nameConfig = FIRST;
        else if(numberCase == 2) nameConfig = SECOND;
        else if(numberCase == 3) nameConfig = THIRD;
        else if(numberCase == 4) nameConfig = FOURTH;
        else if(numberCase == 5) nameConfig = FIFTH;
        else {
            LOGGER.warn("Configuration with this number \"" + numberCase +"\" does not exist.");
            return;
        }
        loadHome(nameConfig);
        loadDevices(nameConfig);
        loadSensors(nameConfig);
        loadPersons(nameConfig);
        loadPets(nameConfig);
        loadTransport(nameConfig);
    }
    private void loadHome(String nameConfig) {
        JSONArray array = load(nameConfig, "/home.json");
        int idRoom = 1;
        for (Object o: array) {
            JSONObject homeJson = (JSONObject) o;
            int idFloor = (int)(long)homeJson.get("floor");
            Floor floor = new Floor(idFloor);
            JSONArray roomArray = (JSONArray)homeJson.get("rooms");


            for (Object ob: roomArray) {
                JSONObject roomJson = (JSONObject) ob;
                int countWindows = (int)(long)roomJson.get("windowsCount");
                Room room = new Room(idRoom++);
                for (int i = 1; i <= countWindows; i++) {
                    Window window = new Window(i, room);
                    smartHomeManager.addWindow(window);
                }
                floor.addRoom(room);
            }
            smartHomeManager.getHome().addFloor(floor);

            LOGGER.info("Created " + floor);
        }
    }



    private void loadDevices(String nameConfig) {
        JSONArray array = load(nameConfig, "/devices.json");
        int id = 1;
        for (Object o: array) {
            JSONObject deviceJson = (JSONObject) o;
            int count = (int)(long)deviceJson.get("count");
            Room room = smartHomeManager.getRoomByID((int)(long)deviceJson.get("idRoom"));
            for (int i = 0; i < count; i++) {
                if (room != null) {
                    DeviceBuilder builder = new DeviceBuilder(smartHomeManager);
                    Device device = builder.withId(id++)
                            .withName((String)deviceJson.get("name"))
                            .withType((String)deviceJson.get("type"))
                            .inRoom(room)
                            .build();
                    if (device != null)
                        LOGGER.info("Created " + device.toString()  + ".");
                }
            }
        }
    }


    private void loadSensors(String nameConfig) {
        JSONArray array = load(nameConfig, "/sensors.json");
        int id = 1;
        for (Object o: array) {
            JSONObject sensorJson = (JSONObject) o;
            int count = (int)(long)sensorJson.get("count");
            Room room = smartHomeManager.getRoomByID((int)(long)sensorJson.get("idRoom"));
            for (int i = 0; i < count; i++) {
                if (room != null) {
                    String name = (String)sensorJson.get("name");
                    String type = (String)sensorJson.get("type");
                    Sensor sensor = factorySensor.createSensor(id++, type, name, room, smartHomeManager);

                    LOGGER.info("Created " + sensor + ".");
                }
            }
        }
    }



    private void loadPersons(String nameConfig) {
        JSONArray array = load(nameConfig, "/person.json");
        for (Object o: array) {
            JSONObject personJson = (JSONObject) o;
            PersonBuilder builder = new PersonBuilder();
            smartHomeManager.addPerson(builder.withName((String) personJson.get("name"))
                    .withAgeGroup((long)personJson.get("age"))
                    .withState(StateInhabitant.ACTIVITY)
                    .withDrivingLicense(personJson.get("haveDrivingLicense") != null
                            && (boolean) personJson.get("haveDrivingLicense"))
                    .build());
            LOGGER.info("Created " + builder.build().toStringForLog());
        }
    }

    private void loadPets(String nameConfig) {
        JSONArray array = load(nameConfig, "/pet.json");
        for (Object o: array) {
            JSONObject petJson = (JSONObject) o;
            PetBuilder builder = new PetBuilder();
            smartHomeManager.addPet(builder.withName((String)petJson.get("name"))
                    .withType((String)petJson.get("typePet"))
                    .withState(StateInhabitant.ACTIVITY)
                    .build());

            LOGGER.info("Created " + builder.build().toStringForLog());
        }
    }

    private void loadTransport(String nameConfig) {
        JSONArray array = load(nameConfig, "/transport.json");
        for (Object o: array) {
            JSONObject transportJson = (JSONObject) o;
            AutoBuilder builder = new AutoBuilder();
            smartHomeManager.addTransport(builder.withName((String)transportJson.get("name"))
                    .withType((String)transportJson.get("typeTransport"))
                    .withCurrentUser(null)
                    .build());

            LOGGER.info("Created " + builder.build().toStringForLog());
        }
    }
    private JSONArray load(String nameConfig, String fileName) {
        try {
            JSONParser parser = new JSONParser();
            return (JSONArray)parser.parse(new FileReader(PATH + nameConfig + fileName));
        } catch (IOException | ParseException e) {
            LOGGER.error("An error occurred while loading the file \"" + fileName + "\" with this name.");
            return null;
        }
    }
}