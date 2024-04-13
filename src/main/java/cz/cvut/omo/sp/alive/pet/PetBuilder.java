package cz.cvut.omo.sp.alive.pet;

import cz.cvut.omo.sp.alive.inhabitant.StateInhabitant;
import cz.cvut.omo.sp.alive.inhabitant.TypeInhabitant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * A builder class for creating instances of Pet objects.
 *
 * This class provides a convenient way to construct Pet objects with various attributes, including name, state,
 * and type. It also defines the available actions for different types of pets.
 */
public class PetBuilder {

    private static final Logger LOGGER = LogManager.getLogger(PetBuilder.class.getName());

    /** The name of the pet. */
    private String name;

    /** The state of the pet. */
    private StateInhabitant state;

    /** The type of the pet. */
    private TypePet type;

    /**
     * Creates a new instance of PetBuilder.
     */
    public PetBuilder() {
    }

    /**
     * Sets the name of the pet.
     *
     * @param name The name of the pet.
     * @return This PetBuilder instance.
     */
    public PetBuilder withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Sets the state of the pet.
     *
     * @param state The state of the pet.
     * @return This PetBuilder instance.
     */
    public PetBuilder withState(StateInhabitant state) {
        this.state = state;
        return this;
    }

    /**
     * Sets the type of the pet based on a string representation.
     *
     * @param type The string representation of the pet type.
     * @return This PetBuilder instance.
     */
    public PetBuilder withType(String type) {
        this.type = getTypePet(type);
        return this;
    }

    /**
     * Converts a string representation of the pet type to a TypePet enum value.
     *
     * @param type The string representation of the pet type.
     * @return The corresponding TypePet enum value.
     */
    private TypePet getTypePet(String type) {
        return TypePet.valueOf(type);
    }

    /**
     * Builds a Pet object with the specified attributes.
     *
     * @return The constructed Pet object, or null if required arguments are not provided.
     */
    public Pet build() {
        if (this.name == null || this.state == null || this.type == null) {
            LOGGER.warn("Error in PetBuilder");
            return null;
        }
        Pet pet = new Pet(this.name, TypeInhabitant.PET, this.type);
        pet.setState(this.state);
        pet.setActions(setActionTypePet(pet));
        return pet;
    }

    /**
     * Sets the available actions for the pet based on its type.
     *
     * @param pet The pet for which actions are set.
     * @return A list of available actions for the pet.
     */
    private List<ActionTypePet> setActionTypePet(Pet pet) {
        List<ActionTypePet> actions = new ArrayList<>();
        actions.add(ActionTypePet.FEED);
        actions.add(ActionTypePet.SLEEP);
        if (pet.getTypePet() != TypePet.FISH)
            actions.add(ActionTypePet.DRINK);
        if (pet.getTypePet() == TypePet.DOG)
            actions.add(ActionTypePet.WALK);
        if (pet.getTypePet() == TypePet.DOG || pet.getTypePet() == TypePet.CAT)
            actions.add(ActionTypePet.PLAY);
        return actions;
    }
}
