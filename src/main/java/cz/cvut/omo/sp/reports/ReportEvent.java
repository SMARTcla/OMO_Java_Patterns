package cz.cvut.omo.sp.reports;

import cz.cvut.omo.sp.alive.person.Person;
import cz.cvut.omo.sp.alive.pet.Pet;
import cz.cvut.omo.sp.tech.device.Device;
import cz.cvut.omo.sp.events.Event;
import cz.cvut.omo.sp.manager.SmartHomeManager;
import cz.cvut.omo.sp.alive.inhabitant.Inhabitant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.List;

public class ReportEvent implements ReportVisitor {

    private static final Logger LOGGER = LogManager.getLogger(ReportEvent.class.getName());

    private final int numberCase;
    private String dateTime;

    public ReportEvent(int numberCase, String dateTime) {
        this.numberCase = numberCase;
        this.dateTime = dateTime;
    }

    /**
     * Visits the SmartHomeManager configuration and generates a report on events.
     *
     * @param configuration The SmartHomeManager configuration to visit.
     */
    @Override
    public void visit(SmartHomeManager configuration) {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("reports/" + "case" + numberCase + "-" + "ReportEvent" + ".txt"),
                    StandardCharsets.UTF_8));
            out.write(" Report Event: \n ");

            // Write event information for persons
            out.write("\n Event Person: \n");
            List<Person> persons = configuration.getPersons();
            if (persons!= null) {
                int i = 1;
                for (Person person: persons) {
                    out.write("\n  " + i ++ + "). " + inhabitantVisit(person));
                }
            }

            // Write event information for pets
            out.write(" Event Pet: \n");
            List<Pet> pets = configuration.getPets();
            if (pets!= null) {
                int i = 1;
                for (Pet pet: pets) {
                    out.write("\n  " + i ++ + "). " + inhabitantVisit(pet));
                }
            }

            // Write event information for various devices
            out.write("\n \n Event Devices: \n");
            int i = 1;

            writeDeviceEvents(out, i, configuration.getAlarms());
            writeDeviceEvents(out, i, configuration.getCoffeeMachines());
            writeDeviceEvents(out, i, configuration.getFridges());
            writeDeviceEvents(out, i, configuration.getLamps());
            writeDeviceEvents(out, i, configuration.getPcs());
            writeDeviceEvents(out, i, configuration.getPlayStations());
            writeDeviceEvents(out, i, configuration.getShowers());
            writeDeviceEvents(out, i, configuration.getSpeakers());
            writeDeviceEvents(out, i, configuration.getTvs());
            writeDeviceEvents(out, i, configuration.getTeapots());
            writeDeviceEvents(out, i, configuration.getVacuumCleaners());

        } catch (IOException ex) {
            LOGGER.error("Error in ReportEvent");
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
     * Visits an Inhabitant element (Person or Pet) and returns a string representation of its events.
     *
     * @param inhabitant The Inhabitant element to visit.
     * @return A string representation of the visited Inhabitant's events.
     */
    private String inhabitantVisit(Inhabitant inhabitant) {
        StringBuilder s = new StringBuilder();
        List<Event> events = inhabitant.getEvents();
        s.append(" ").append(inhabitant).append("\n ");
        processEvents(s, events);
        return s.toString();
    }

    /**
     * Visits a Device element and returns a string representation of its events.
     *
     * @param device The Device element to visit.
     * @return A string representation of the visited Device's events.
     */
    private String deviceVisit(Device device) {
        StringBuilder s = new StringBuilder();
        List<Event> events = device.getEvents();
        s.append(" ").append(device).append("\n ");
        processEvents(s, events);
        return s.toString();
    }

    /**
     * Process and append events to the StringBuilder.
     *
     * @param stringBuilder The StringBuilder to append events to.
     * @param events        The list of events to process.
     */
    private void processEvents(StringBuilder stringBuilder, List<Event> events) {
        if (events != null) {
            List<Event> sortedEvents = events.stream()
                    .sorted(Comparator
                            .comparing(Event::getType)
                            .thenComparing(Event::getContext)).toList();
            for (Event event : sortedEvents) {
                stringBuilder.append(event).append("\n ");
            }
        }
    }

    /**
     * Writes the events of a list of devices to the output writer.
     *
     * @param out       The BufferedWriter to write to.
     * @param i         The counter variable for formatting.
     * @param devices   The list of devices to process.
     * @param <T>       The type of Device.
     * @throws IOException If an I/O error occurs while writing the events.
     */
    private <T extends Device> void writeDeviceEvents(BufferedWriter out, int i, List<T> devices) throws IOException {
        if (devices != null) {
            for (Device device : devices) {
                out.write("\n  " + i++ + "). " + deviceVisit(device));
            }
        }
    }
}
