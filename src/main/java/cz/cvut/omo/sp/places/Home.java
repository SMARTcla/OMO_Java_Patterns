package cz.cvut.omo.sp.places;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The `Home` class represents a home or residence. It is a singleton class that
 * provides access to a single instance of a home. The home may contain a garden,
 * multiple entrances, hallways, and floors.
 */
public class Home {

    private static Home home;
    private Garden garden;
    private List<Entrance> entrances;
    private List<Hallways> hallways;
    private List<Floor> floors;

    /**
     * Private constructor to prevent direct instantiation of the `Home` class.
     */
    private Home() {}

    /**
     * Returns the singleton instance of the `Home` class.
     *
     * @return The single instance of the `Home` class.
     */
    public static Home getHome() {
        if (home == null) {
            synchronized (Home.class) {
                if (home == null) {
                    home = new Home();
                }
            }
        }
        return home;
    }

    /**
     * Adds an entrance to the list of entrances in the home.
     *
     * @param entrance The entrance to be added to the home.
     * @throws NullPointerException if the provided entrance is null.
     */
    public void addEntrance(Entrance entrance) {
        Objects.requireNonNull(entrance, "Entrance cannot be null.");
        if (entrances == null) {
            entrances = new ArrayList<>();
        }
        entrances.add(entrance);
    }

    /**
     * Adds a hallway to the list of hallways in the home.
     *
     * @param hallway The hallway to be added to the home.
     * @throws NullPointerException if the provided hallway is null.
     */
    public void addHallway(Hallways hallway) {
        Objects.requireNonNull(hallway, "Hallway cannot be null.");
        if (hallways == null) {
            hallways = new ArrayList<>();
        }
        hallways.add(hallway);
    }

    /**
     * Adds a floor to the list of floors in the home.
     *
     * @param floor The floor to be added to the home.
     * @throws NullPointerException if the provided floor is null.
     */
    public void addFloor(Floor floor) {
        Objects.requireNonNull(floor, "Floor cannot be null.");
        if (floors == null) {
            floors = new ArrayList<>();
        }
        floors.add(floor);
    }


    public Garden getGarden() {
        return garden;
    }

    public void setGarden(Garden garden) {
        this.garden = garden;
    }

    public List<Entrance> getEntrances() {
        return entrances;
    }

    public void setEntrances(List<Entrance> entrances) {
        this.entrances = entrances;
    }

    public List<Hallways> getHallways() {
        return hallways;
    }

    public void setHallways(List<Hallways> hallways) {
        this.hallways = hallways;
    }

    public List<Floor> getFloors() {
        return floors;
    }

    public void setFloors(List<Floor> floors) {
        this.floors = floors;
    }
}
