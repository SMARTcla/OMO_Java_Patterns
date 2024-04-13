package cz.cvut.omo.sp.alive.person;

import cz.cvut.omo.sp.alive.inhabitant.TypeInhabitant;
import junit.framework.TestCase;
import org.junit.jupiter.api.Assertions;

import java.util.*;

public class PersonTest extends TestCase {

    private static final String PERSON_NAME = "John";

    public void testToString() {
        Person person = new Person(PERSON_NAME, TypeInhabitant.PERSON, AgeGroup.ADULT);
        Assertions.assertEquals(" name={John} ", person.toString());
    }

    public void testCanUseTransportWithTransportAction() {
        Person person = new Person(PERSON_NAME, TypeInhabitant.PERSON, AgeGroup.ADULT);
        person.setActions(List.of(ActionTypePerson.USAGE_AUTO));
        Assertions.assertTrue(person.canUseTransport());
    }

    public void testCanUseTransportWithoutTransportAction() {
        Person person = new Person(PERSON_NAME, TypeInhabitant.PERSON, AgeGroup.ADULT);
        person.setActions(List.of(ActionTypePerson.USAGE_DEVICE));
        Assertions.assertFalse(person.canUseTransport());
    }

    public void testCanUseDeviceWithDeviceAction() {
        Person person = new Person(PERSON_NAME, TypeInhabitant.PERSON, AgeGroup.ADULT);
        person.setActions(List.of(ActionTypePerson.USAGE_DEVICE));
        Assertions.assertTrue(person.canUseDevice());
    }

    public void testCanUseDeviceWithoutDeviceAction() {
        Person person = new Person(PERSON_NAME, TypeInhabitant.PERSON, AgeGroup.ADULT);
        person.setActions(List.of(ActionTypePerson.USAGE_AUTO));
        Assertions.assertFalse(person.canUseDevice());
    }
}