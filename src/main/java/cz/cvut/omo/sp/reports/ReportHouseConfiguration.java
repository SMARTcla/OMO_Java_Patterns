package cz.cvut.omo.sp.reports;

import cz.cvut.omo.sp.manager.SmartHomeManager;
import cz.cvut.omo.sp.places.Floor;
import cz.cvut.omo.sp.places.Home;
import cz.cvut.omo.sp.places.Room;
import cz.cvut.omo.sp.places.Window;
import cz.cvut.omo.sp.tech.auto.Transport;
import cz.cvut.omo.sp.tech.device.Device;
import cz.cvut.omo.sp.alive.inhabitant.*;
import cz.cvut.omo.sp.alive.person.*;
import cz.cvut.omo.sp.alive.pet.*;
import cz.cvut.omo.sp.tech.sencor.Sensor;
import org.apache.logging.log4j.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class ReportHouseConfiguration implements ReportVisitor {

    private static final Logger LOGGER = LogManager.getLogger(ReportHouseConfiguration.class.getName());

    private int numberCase;
    private String dateTime;
    private int number = 0; // variable for numbering the list

    public ReportHouseConfiguration(int numberCase, String dateTime) {
        this.numberCase = numberCase;
        this.dateTime = dateTime;
    }

    /**
     * Visits the SmartHomeManager configuration and generates a report.
     *
     * @param configuration The SmartHomeManager configuration to visit.
     */
    @Override
    public void visit(SmartHomeManager configuration) {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("reports/" + "case" + numberCase + "-" + "ReportHouseConfiguration" + ".txt"),
                    StandardCharsets.UTF_8));
            out.write(" Report House Configuration: \n ");

            number = 0;
            out.write("\n Transports: \n");
            for (Transport transport : configuration.getTransports()) {
                number++;
                out.write(visit(transport));
            }

            out.write("\n Persons: \n");
            for (Person person : configuration.getPersons()) {
                number++;
                out.write(visit(person));
            }

            number = 0;
            out.write("\n Pets: \n");
            for (Pet pet : configuration.getPets()) {
                number++;
                out.write(visit(pet));
            }

            out.write(" Floors: \n");
            for (Floor floor : configuration.getHome().getFloors()) {
                out.write(visit(floor));

                for (Room room : floor.getRooms()) {
                    List<Window> windows = configuration.getWindows().stream()
                            .filter(window -> window.getRoom().getId() == room.getId()).toList();
                    if (!windows.isEmpty()) {
                        out.write("   Windows: \n");
                        for (Window window : windows) {
                            out.write(visit(window));
                        }
                    }
                    out.write(visit(room));
                    int i;

                    processDevices(out, configuration.getAlarms(), "Alarms");
                    processDevices(out, configuration.getCoffeeMachines(), "Coffee Machines");
                    processDevices(out, configuration.getFridges(), "Fridges");
                    processDevices(out, configuration.getLamps(), "Lamps");
                    processDevices(out, configuration.getPcs(), "PCs");
                    processDevices(out, configuration.getPlayStations(), "Play Stations");
                    processDevices(out, configuration.getShowers(), "Showers");
                    processDevices(out, configuration.getSpeakers(), "Speakers");
                    processDevices(out, configuration.getTvs(), "TVs");
                    processDevices(out, configuration.getTeapots(), "Teapots");
                    processDevices(out, configuration.getVacuumCleaners(), "Vacuum Cleaners");

                    processSensors(out, configuration.getAirQualitySensors(), room, "Air Quality Sensors");
                    processSensors(out, configuration.getLightSensors(), room, "Light Sensors");
                    processSensors(out, configuration.getMotionSensors(), room, "Motion Sensors");
                    processSensors(out, configuration.getSmartEnergyMeters(), room, "Smart Energy Meters");
                    processSensors(out, configuration.getSoundLevelSensors(), room, "Sound Level Sensors");
                    processSensors(out, configuration.getStructuralHealthSensors(), room, "Structural Health Sensors");
                    processSensors(out, configuration.getTemperatureSensors(), room, "Temperature Sensors");
                    processSensors(out, configuration.getWaterLeakSensors(), room, "Water Leak Sensors");
                }
            }


        } catch (IOException ex) {
            LOGGER.error("Error in ReportHouseConfiguration");
            throw new RuntimeException("Error processing the file", ex);

        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    LOGGER.error("Failed to close the output stream in" + getClass());
                    throw new RuntimeException("Error closing BufferedWriter", e);
                }
            }
        }
    }

    /**
     * Private method to process and output a list of devices.
     *
     * @param out        The BufferedWriter to write to.
     * @param devices    The list of devices to process.
     * @param deviceType The type of the devices (e.g., "Lamps").
     * @throws IOException If an I/O error occurs.
     */
    private <T extends Device> void processDevices(BufferedWriter out, List<T> devices, String deviceType) throws IOException {
        if (devices != null && !devices.isEmpty()) {
            int i = 1;
            out.write("   " + deviceType + ": \n");
            for (T device : devices) {
                out.write("  " + i++ + ". " + visit(device));
            }
        }
    }

    /**
     * Private method to process and output a list of sensors.
     *
     * @param out        The BufferedWriter to write to.
     * @param sensors    The list of sensors to process.
     * @param room       The room for which sensors are being processed.
     * @param sensorType The type of the sensors (e.g., "Air Quality Sensors").
     * @throws IOException If an I/O error occurs.
     */
    private <T extends Sensor> void processSensors(BufferedWriter out, List<T> sensors, Room room, String sensorType) throws IOException {
        if (sensors != null) {
            List<T> filteredSensors = sensors.stream()
                    .filter(sensor -> sensor.getRoom().getId() == room.getId()).toList();
            if (!filteredSensors.isEmpty()) {
                int i = 1;
                out.write("   " + sensorType + ": \n");
                for (T sensor : filteredSensors) {
                    out.write("  " + i++ + ". " + visit(sensor));
                }
            }
        }
    }

    /**
     * Visit method for a Home.
     *
     * @param home The Home element to visit.
     * @return A string representation of the visited Home.
     */
    public String visit(Home home) {
        return "  Home \n";
    }

    /**
     * Visit method for a Floor.
     *
     * @param floor The Floor element to visit.
     * @return A string representation of the visited Floor.
     */
    public String visit(Floor floor) {
        StringBuilder s = new StringBuilder();
        return s.append(floor.getId())
                .append(") Count rooms: ")
                .append(floor.getRooms().size())
                .append(".\n").toString();
    }

    /**
     * Visit method for a Room.
     *
     * @param room The Room element to visit.
     * @return A string representation of the visited Room.
     */
    public String visit(Room room) {
        StringBuilder s = new StringBuilder();
        return  s.append(" -> Room id ")
                .append(room.getId())
                .append(".\n").toString();
    }

    /**
     * Visit method for a Window.
     *
     * @param window The Window element to visit.
     * @return A string representation of the visited Window.
     */
    public String visit(Window window) {
        StringBuilder s = new StringBuilder();
        return s.append("  ")
                .append(window.getId())
                .append("\n").toString();
    }

    /**
     * Private visit method for a Device.
     *
     * @param device The Device element to visit.
     * @return A string representation of the visited Device.
     */
    private String visit(Device device){
        StringBuilder s = new StringBuilder();
        return s.append("ID ")
                .append(device.getId())
                .append(". ")
                .append(device.getName())
                .append(". State: ")
                .append(device.getState())
                .append(device.getCurrentUser() == null ? "" : ". Current user: " + device.getCurrentUser().getName())
                .append(device.getExpects() == null || device.getExpects().size() == 0 ? "" : getInhabitantInLine(device.getExpects()))
                .append("\n").toString();
    }

    /**
     * Private visit method for a Sensor.
     *
     * @param sensor The Sensor element to visit.
     * @return A string representation of the visited Sensor.
     */
    private String visit(Sensor sensor){
        StringBuilder s = new StringBuilder();
        return s.append("ID ")
                .append(sensor.getId())
                .append(". ")
                .append(sensor.getName())
                .append(". Type: ")
                .append(sensor.getType())
                .append(". Data: ")
                .append(sensor.getReadings())
                .append(" \n").toString();
    }

    /**
     * Visit method for a Person.
     *
     * @param person The Person element to visit.
     * @return A string representation of the visited Person.
     */
    public String visit(Person person) {
        StringBuilder s = new StringBuilder();
        return s.append(number)
                .append(". ")
                .append(person.getAgeGroup())
                .append(" ")
                .append(person.getName())
                .append(". State: ")
                .append(person.getState())
                .append("\n").toString();
    }

    /**
     * Visit method for a Pet.
     *
     * @param pet The Pet element to visit.
     * @return A string representation of the visited Pet.
     */
    public String visit(Pet pet) {
        StringBuilder s = new StringBuilder();
        return s.append(number)
                .append(". ")
                .append(pet.getType())
                .append(" ")
                .append(pet.getName())
                .append(". State: ")
                .append(pet.getState())
                .append("\n").toString();
    }

    /**
     * Visit method for a Transport.
     *
     * @param transport The Transport element to visit.
     * @return A string representation of the visited Transport.
     */
    public String visit(Transport transport) {
        StringBuilder s = new StringBuilder();
        return  s.append(number)
                .append(". ")
                .append(transport.getType())
                .append(" ")
                .append(transport.getName())
                .append(transport.getCurrentUser() == null ? "" : ". Current user: " + transport.getCurrentUser().getName())
                .append(transport.getExpects() == null || transport.getExpects().size() == 0 ? "" :
                        getInhabitantInLine(transport.getExpects().stream().toList()))
                .append(".\n").toString();
    }

    /**
     * Get a comma-separated line of inhabitants.
     *
     * @param persons The list of inhabitants to process.
     * @return A formatted string with inhabitants in line.
     */
    private String getInhabitantInLine(List<? extends Inhabitant> persons) {
        String str = persons.stream()
                .map(Inhabitant::getName)
                .reduce(". Waiting in line:", (s, s2) -> s.concat(" ").concat(s2 + ","));
        return str.substring(0, str.length() - 1);
    }
}
