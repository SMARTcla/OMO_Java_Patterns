package cz.cvut.omo.sp;

import cz.cvut.omo.sp.generator.Simulation;

/**
 * The {@code Main} class serves as the entry point for running the smart home simulation.
 * It initializes a simulation with specific parameters and starts the simulation process.
 *
 * This class contains the `main` method, which is executed when the program is run.
 */
public class Main {

    /**
     * The main method that initiates and runs the smart home simulation.
     *
     * @param args Command-line arguments (not used in this application).
     */
    public static void main(String[] args) {
        Simulation simulation = new Simulation(5, "2023-03-09T07:12:00", 1200);
        simulation.start();
    }
}
