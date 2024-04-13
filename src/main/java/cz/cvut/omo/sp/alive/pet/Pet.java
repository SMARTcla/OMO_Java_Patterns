package cz.cvut.omo.sp.alive.pet;
import cz.cvut.omo.sp.alive.inhabitant.Inhabitant;
import cz.cvut.omo.sp.alive.inhabitant.StateInhabitant;
import cz.cvut.omo.sp.alive.inhabitant.TypeInhabitant;
import cz.cvut.omo.sp.events.TypeEvent;
import cz.cvut.omo.sp.facade.Facade;

import java.time.LocalDateTime;
import java.util.List;
/**
 * Class representing a pet in a simulation.
 *
 * This class extends the Inhabitant class and adds specific attributes and behaviors for a pet
 * in the simulation, including their type, available actions, and handling of events.
 */
public class Pet extends Inhabitant{


    private TypePet typePet;
    private List<ActionTypePet> actions;
    public List<ActionTypePet> getActions() {
        return actions;
    }

    public void setActions(List<ActionTypePet> actions) {
        this.actions = actions;
    }


    public Pet(String name, TypeInhabitant type, TypePet typePet) {
        super(name, type);
        this.typePet = typePet;
    }
    public TypePet getTypePet() {
        return typePet;
    }
    /**
     * Sets the type of the pet.
     *
     * @param typePet The new type of the pet.
     */
    public void setTypePet(TypePet typePet) {
        this.typePet = typePet;
    }

    @Override
    public String toString() {
        return "name={" + name + "} ";
    }
    /**
     * Updates the pet's state based on the given event type.
     *
     * @param type     The type of the event.
     * @param dateTime The date and time of the event.
     * @param facade   The facade object for coordinating events.
     */
    @Override
    public void update(TypeEvent type, LocalDateTime dateTime, Facade facade) {
        if (type == TypeEvent.DANGER) {
            facade.theEndLastEvent(this, dateTime);
            this.setState(StateInhabitant.OUTSIDE);
        }
    }
    /**
     * Returns a string representation of the pet, including their name, events, state, type, and actions.
     *
     * @return A string representation of the pet.
     */
    public String toStringForLog() {
        return "Pet{" +
                "name='" + name + '\'' +
                ", events=" + events +
                ", state=" + state +
                ", typePet=" + typePet +
                ", actions=" + actions +
                '}';
    }

}
