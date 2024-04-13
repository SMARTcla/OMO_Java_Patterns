package cz.cvut.omo.sp.reports;


import cz.cvut.omo.sp.alive.person.Person;
import cz.cvut.omo.sp.alive.pet.ActionTypePet;
import cz.cvut.omo.sp.alive.pet.Pet;
import cz.cvut.omo.sp.data.InhabitantData;
import cz.cvut.omo.sp.data.PersonData;
import cz.cvut.omo.sp.data.PetData;
import cz.cvut.omo.sp.manager.SmartHomeManager;
import cz.cvut.omo.sp.events.*;
import cz.cvut.omo.sp.Entity;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.*;
import java.time.temporal.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class ReportActivityAndUsage implements ReportVisitor {

    private static final Logger LOGGER = LogManager.getLogger(ReportActivityAndUsage.class.getName());

    private int numberCase;
    private final String dateTime;
    @Setter
    private LocalDateTime currentTime;

    public ReportActivityAndUsage(int numberCase, String dateTime) {
        this.numberCase = numberCase;
        this.dateTime = dateTime;
    }

    /**
     * Visits the SmartHomeManager configuration and generates a report on activities and usage.
     *
     * @param configuration The SmartHomeManager configuration to visit.
     */
    public void visit(SmartHomeManager configuration) {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("reports/" + "case" + numberCase + "-" + "ReportActivityAndUsage" + ".txt"),
                    StandardCharsets.UTF_8));

            out.write("Report Activity and Usage: \n");

            out.write("\n Persons activities: \n");
            List<Person> persons = configuration.getPersons();
            if (persons != null) {
                AtomicInteger i = new AtomicInteger(1);

                AtomicInteger personIndex = new AtomicInteger(1);
                BufferedWriter finalOut = out;
                persons.stream().forEach(person -> {
                    try {
                        finalOut.write("  " + i.getAndIncrement() + ". " + person.getAgeGroup() + " " + person.getName() + " \n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        finalOut.write("  " + personIndex.getAndIncrement() + ". " + person.getAgeGroup() + " " + person.getName() + " \n");
                        List<Event> events = person.getEvents();
                        if (events != null) {
                            for (Event event : events) {
                                if (event.getEndDateTime() == null) event.setEndDateTime(currentTime);
                            }
                            finalOut.write("-> Walked with pet " + getTimeActivities(events, PetData.PET_ACTIVITY_WALK_WITH_OWNER) + "\n");
                            finalOut.write("   Pets: " + getSourceByContext(events, PetData.PET_ACTIVITY_WALK_WITH_OWNER) + "\n");
                            finalOut.write("-> Played with pet " + getTimeActivities(events, PetData.PET_ACTIVITY_PLAY_WITH_OWNER) + "\n");
                            finalOut.write("   Pets: " + getSourceByContext(events, PetData.PET_ACTIVITY_WALK_WITH_OWNER) + "\n");
                            finalOut.write("-> Fed the child " + getTimeActivities(events, PersonData.PERSON_EAT_WITH_CHILD) + "\n");
                            finalOut.write("   Babies:" + getSourceByContext(events, PersonData.PERSON_PLAY_WITH_CHILD) + "\n");
                            finalOut.write("-> Played with the babies for " + getTimeActivities(events, PersonData.PERSON_PLAY_WITH_CHILD) + "\n");
                            finalOut.write("   Babies:" + getSourceByContext(events, PersonData.PERSON_PLAY_WITH_CHILD) + "\n");
                            finalOut.write("-> Walked with babies " + getTimeActivities(events, PersonData.PERSON_WALK_WITH_CHILD) + "\n");
                            finalOut.write("   Babies: " + getSourceByContext(events, PersonData.PERSON_WALK_WITH_CHILD) + "\n");
                            finalOut.write("-> Was not at home and did not walk with anyone " + getTimeActivities(events, PersonData.PERSON_LEAVE_HOME) + "\n");
                            finalOut.write("-> Used transports " + getTimeActivities(events, PersonData.PERSON_USE_CAR) + "\n");
                            finalOut.write("   Transport: " + getTargetByContext(events, PersonData.PERSON_USE_CAR) + "\n");
                            finalOut.write("   Was waiting in line to use the transport " + getTimeActivities(events, PersonData.PERSON_WAIT_FOR_CAR) + "\n");
                            finalOut.write("-> Used device " + getTimeActivities(events, InhabitantData.INHABITANT_USE_ELECTRONIC_DEVICE) + "\n");
                            finalOut.write("   Devices:" + getTargetByContext(events, InhabitantData.INHABITANT_USE_ELECTRONIC_DEVICE) + "\n");
                            finalOut.write("   Was waiting in line to use the devices " + getTimeActivities(events, InhabitantData.INHABITANT_USE_ELECTRONIC_DEVICE) + "\n");
                            finalOut.write("-> Fixed device " + getTimeActivities(events, PersonData.PERSON_FIX_DEVICE) + "\n");
                            finalOut.write("-> Add content to device " + getTimeActivities(events, PersonData.PERSON_DEVICE_ADD_CONTENT) + "\n");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }

            out.write(" Pets activities: \n");
            List<Pet> pets = configuration.getPets();
            if (pets != null) {
                int i = 1;
                for (Pet pet : pets) {
                    out.write("  " + i++ + ". " + pet.getType() + " " + pet.getName() + " \n");
                    List<Event> events = pet.getEvents();
                    if (events != null) {
                        for (Event event : events) {
                            if (event.getEndDateTime() == null)
                                event.setEndDateTime(currentTime);
                        }
                        if (pet.getActions().contains(ActionTypePet.WALK)) {
                            out.write("-> Walked with person " + getTimeActivities(events, PetData.PET_ACTIVITY_WALK_WITH_OWNER) + "\n");
                            out.write("   Persons:" + getTargetByContext(events, PetData.PET_ACTIVITY_WALK_WITH_OWNER) + "\n");
                        }
                        if (pet.getActions().contains(ActionTypePet.PLAY)) {
                            out.write("-> Played alone " + getTimeActivities(events, PetData.PET_ACTIVITY_SOLO_PLAY) + "\n");
                            out.write("-> Played with person " + getTimeActivities(events, PetData.PET_ACTIVITY_PLAY_WITH_OWNER) + "\n");
                            out.write("   Persons:" + getTargetByContext(events, PetData.PET_ACTIVITY_PLAY_WITH_OWNER) + "\n");
                        }
                        out.write("-> Slept " + getTimeActivities(events, PetData.PET_ACTIVITY_WALK_WITH_OWNER) + "\n");
                        out.write("-> Use devises " + getTimeActivities(events, InhabitantData.INHABITANT_USE_ELECTRONIC_DEVICE) + "\n");
                        out.write("   Devises:" + getTargetByContext(events, InhabitantData.INHABITANT_USE_ELECTRONIC_DEVICE) + "\n");
                    }
                }
            }



        } catch (IOException ex) {
            LOGGER.error("Error in ReportActivityAndUsage");
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
     * Gets the formatted string representing the time spent on activities.
     *
     * @param events   The list of events for the entity.
     * @param context  The context of the activities.
     * @return The formatted string representing the time spent on activities.
     */
    private String getTimeActivities(List<Event> events, String context) {
        List<Event> eventsList = events.stream()
                .filter(event -> event.getContext().equals(context)).toList();
        long minutes = 0;
        for (Event event : eventsList)
            minutes += ChronoUnit.MINUTES.between(event.getStartDateTime(), event.getEndDateTime());
        return minutes / 60 + " hours " + minutes % 60 + " minutes.";
    }

    /**
     * Gets the targets of activities by context and formats the information.
     *
     * @param events   The list of events for the entity.
     * @param context  The context of the activities.
     * @return The formatted string representing the targets of activities.
     */
    private String getTargetByContext(List<Event> events, String context) {
        Map<Entity, Long> eventsMap = events.stream()
                .filter(event -> event.getContext().equals(context) && event.getTarget() != null)
                .collect(Collectors.groupingBy(Event::getTarget, Collectors.counting()));

        if (eventsMap.isEmpty()) return "";

        StringBuilder s = new StringBuilder();
        for (Map.Entry<Entity, Long> entry : eventsMap.entrySet()) {
            s.append(" ")
                    .append(entry.getKey().toString())
                    .append(" ")
                    .append(entry.getValue())
                    .append(" times,");
        }
        return s.toString();
    }

    /**
     * Gets the sources involved in activities by filtering events with a specific context.
     *
     * @param events  The list of events to filter.
     * @param context The context to filter events by.
     * @return A string representation of the sources involved in activities.
     */
    private String getSourceByContext(List<Event> events, String context) {
        Map<Entity, Long> eventsMap = events.stream()
                .filter(event -> event.getContext().equals(context))
                .collect(Collectors.groupingBy(Event::getSource, Collectors.counting()));
        if (eventsMap.size() == 0) return "";
        StringBuilder s = new StringBuilder();
        for (Entity source : eventsMap.keySet()) {
            s.append(" ")
                    .append(source.toString())
                    .append(eventsMap.get(source))
                    .append(" times,");
        }
        return s.toString();
    }

}
