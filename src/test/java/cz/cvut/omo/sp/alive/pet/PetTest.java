package cz.cvut.omo.sp.alive.pet;

import junit.framework.TestCase;
import cz.cvut.omo.sp.alive.inhabitant.TypeInhabitant;
import org.junit.jupiter.api.Assertions;

import java.util.Arrays;

public class PetTest extends TestCase {

    public void testSetActions() {
        Pet pet = new Pet("Buddy", TypeInhabitant.PET, TypePet.DOG);
        pet.setActions(Arrays.asList(ActionTypePet.SLEEP, ActionTypePet.DRINK));
        Assertions.assertEquals(Arrays.asList(ActionTypePet.SLEEP, ActionTypePet.DRINK), pet.getActions());
    }

    public void testGetTypePet() {
        Pet pet = new Pet("Mittens", TypeInhabitant.PET, TypePet.CAT);
        Assertions.assertEquals(TypePet.CAT, pet.getTypePet());
    }

    public void testSetTypePet() {
        Pet pet = new Pet("Max", TypeInhabitant.PET, TypePet.DOG);
        pet.setTypePet(TypePet.HAMSTER);
        Assertions.assertEquals(TypePet.HAMSTER, pet.getTypePet());
    }

    public void testToString() {
        Pet pet = new Pet("Whiskers", TypeInhabitant.PET, TypePet.CAT);
        Assertions.assertEquals("name={Whiskers} ", pet.toString());
    }

    public void testToStringForLog() {
        Pet pet = new Pet("Luna", TypeInhabitant.PET, TypePet.CAT);
        pet.setActions(Arrays.asList(ActionTypePet.PLAY, ActionTypePet.SLEEP));
        String expected = "Pet{name='Luna', events=null, state=null, typePet=CAT, actions=[PLAY, SLEEP]}";
        Assertions.assertEquals(expected, pet.toStringForLog());
    }
}
