package cz.cvut.omo.sp.reports;

import cz.cvut.omo.sp.tech.device.energy.*;
import cz.cvut.omo.sp.manager.SmartHomeManager;
import cz.cvut.omo.sp.tech.device.*;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.*;
import java.time.*;
import java.util.*;

public class ReportConsumption implements ReportVisitor {

    private static final Logger LOGGER = LogManager.getLogger(ReportConsumption.class.getName());

    @Setter
    private LocalDateTime currentTime;
    private int numberCase;
    private final String dateTime;
    private final NumberFormat f = new DecimalFormat("#.00");

    public ReportConsumption(int numberCase, String dateTime) {
        this.numberCase = numberCase;
        this.dateTime = dateTime;
    }

    /**
     * Visits the SmartHomeManager configuration and generates a report on energy consumption.
     *
     * @param configuration The SmartHomeManager configuration to visit.
     */
    @Override
    public void visit(SmartHomeManager configuration) {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("reports/" + "case" + numberCase + "-" +"ReportConsumption" + ".txt"),
                    StandardCharsets.UTF_8));

            out.write("Report House Consumption: \n\n");

            out.write("Energy costs. Devices: \n");

            // Write energy consumption information
            writeDeviceEnergyConsumption(out, "Alarms", configuration.getAlarms());
            writeDeviceEnergyConsumption(out, "Coffee Machines", configuration.getCoffeeMachines());
            writeDeviceEnergyConsumption(out, "Fridges", configuration.getFridges());
            writeDeviceEnergyConsumption(out, "Lamps", configuration.getLamps());
            writeDeviceEnergyConsumption(out, "PCs", configuration.getPcs());
            writeDeviceEnergyConsumption(out, "Play Stations", configuration.getPlayStations());
            writeDeviceEnergyConsumption(out, "Showers", configuration.getShowers());
            writeDeviceEnergyConsumption(out, "Speakers", configuration.getSpeakers());
            writeDeviceEnergyConsumption(out, "TVs", configuration.getTvs());
            writeDeviceEnergyConsumption(out, "Teapots", configuration.getTeapots());
            writeDeviceEnergyConsumption(out, "Vacuum Cleaners", configuration.getVacuumCleaners());


        } catch (IOException ex) {
            LOGGER.error("Error in Report Consumption");
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
     * Writes the energy consumption information for a list of devices to the output writer.
     *
     * @param out       The BufferedWriter to write to.
     * @param deviceType The type of devices.
     * @param devices    The list of devices to process.
     * @param <T>        The type of Device.
     * @throws IOException If an I/O error occurs while writing the energy consumption information.
     */
    private <T extends Device> void writeDeviceEnergyConsumption(BufferedWriter out, String deviceType, List<T> devices) throws IOException {
        if (devices != null) {
            out.write("\n\n " + deviceType + ": ");
            int i = 1;
            for (Device device : devices) {
                out.write("\n  " + i++ + "). " + visitDevice(device) + getCalculationElectricity(device));
            }
        }
    }


    /**
     * Visits a Device element and returns a string representation of its energy consumption information.
     *
     * @param device The Device element to visit.
     * @return A string representation of the visited Device's energy consumption information.
     */
    private String visitDevice(Device device) {
        StringBuilder s = new StringBuilder();
        return s.append("\n").append(device.getType())
                .append(" with id={")
                .append(device.getId())
                .append("} and name={")
                .append(device.getName())
                .append("}.\n Energy: ")
                .append(device.getEnergy().getType()).append("\n")
                .toString();
    }

    /**
     * Gets the calculation of water consumption for a specific device.
     *
     * @param device The Device element to calculate water consumption for.
     * @return A string representation of the water consumption calculation.
     */
    private String getCalculationWater(Device device) {
        StringBuilder s = new StringBuilder();
        List<EventEnergy> events = device.getEventEnergy();
        float cost = 0;
        float amount = 0;
        long timeDay;
        long timeNight;
        if (events != null) {
            for (EventEnergy event : events) {
                timeDay = getConsumptionPerDay(event);
                timeNight = getConsumptionPerNight(event);
                s.append(" ")
                        .append(timeDay)
                        .append("/ 60 * ")
                        .append(20)
                        .append(" * ")
                        .append(event.getPower())
                        .append(" + ")
                        .append(timeNight)
                        .append("/ 60 * ")
                        .append(10)
                        .append(" * ")
                        .append(event.getPower())
                        .append(" +");
                cost =  cost + ((float) timeDay/ 60) * 20 * event.getPower() +
                        ((float) timeNight/ 60) * 10 * event.getPower();
                amount = amount + (((float) timeDay/ 60)  + ((float) timeNight/ 60))* event.getPower();
            }
            s.deleteCharAt(s.lastIndexOf("+"))
                    .append("= ")
                    .append(f.format(cost))
                    .append("\n")
                    .append("Total energy consumed ")
                    .append(f.format(amount));
        }
        return s.toString();
    }

    /**
     * Gets the calculation of electricity consumption for a specific device.
     *
     * @param device The Device element to calculate electricity consumption for.
     * @return A string representation of the electricity consumption calculation.
     */
    private String getCalculationElectricity(Device device) {
        StringBuilder s = new StringBuilder();
        List<EventEnergy> events = device.getEventEnergy();
        float cost = 0;
        float amount = 0;
        long timeDay;
        long timeNight;
        if (events != null) {
            for (EventEnergy event : events) {
                timeDay = getConsumptionPerDay(event);
                timeNight = getConsumptionPerNight(event);
                s.append(" ")
                        .append(timeDay)
                        .append("/ 60 * ")
                        .append(10)
                        .append(" * ")
                        .append(event.getPower())
                        .append(" + ")
                        .append(timeNight)
                        .append("/ 60 * ")
                        .append(5)
                        .append(" * ")
                        .append(event.getPower())
                        .append(" +");
                cost = cost + ((float)timeDay / 60) * 10 * event.getPower() +
                        ((float) timeNight / 60) * 5 * event.getPower();
                amount = amount + (((float) timeDay/ 60)  + ((float) timeNight/ 60))* event.getPower();
            }
            s.deleteCharAt(s.lastIndexOf("+"))
                    .append("= ")
                    .append(f.format(cost)).append("\n")
                    .append("Total energy consumed ")
                    .append(f.format(amount));
        }
        return s.toString();
    }

    /**
     * Gets the consumption per day for a specific energy event.
     *
     * @param event The EventEnergy element to calculate consumption per day for.
     * @return The consumption per day in minutes.
     */
    private long getConsumptionPerDay(EventEnergy event) {
        int min = 0;
        LocalTime inStartDay = event.getStart().toLocalTime();
        LocalDateTime temp = event.getEnd();
        if (temp == null){
            event.setEnd(LocalDateTime.now());
        }
        LocalTime inEndDay = event.getEnd().toLocalTime();
        LocalTime timeStartDay = LocalTime.of(8, 59);
        LocalTime timeEndDay = LocalTime.of(23, 59);
        long days = Duration.between(event.getEnd(), event.getStart()).toDays();
        if (days > 0) {
            min += days * 15 * 60;
            if (inStartDay.isAfter(timeStartDay))
                min += Duration.between(inStartDay, timeStartDay).toMinutes();
            if (inEndDay.isAfter(timeStartDay))
                min += Duration.between(timeEndDay, inEndDay).toMinutes();
        } else if (Duration.between(event.getEnd(), event.getStart()).toMinutes() != 0)
            min += Duration.between(timeStartDay, inEndDay).toMinutes() > 0 ? Duration.between(timeStartDay, inEndDay).toMinutes() : 0;
        return min;
    }

    /**
     * Gets the consumption per night for a specific energy event.
     *
     * @param event The EventEnergy element to calculate consumption per night for.
     * @return The consumption per night in minutes.
     */
    private int getConsumptionPerNight(EventEnergy event) {
        int min = 0;
        if (event.getEnd() == null) event.setEnd(currentTime);
        min += Duration.between(event.getStart(), event.getEnd()).toMinutes();
        min -= getConsumptionPerDay(event);
        return Math.max(min, 0);
    }
}
