package cz.cvut.omo.sp.manager;

import cz.cvut.omo.sp.alive.person.Person;
import cz.cvut.omo.sp.alive.pet.Pet;
import cz.cvut.omo.sp.places.Floor;
import cz.cvut.omo.sp.places.Home;
import cz.cvut.omo.sp.places.Room;
import cz.cvut.omo.sp.tech.auto.Transport;
import cz.cvut.omo.sp.places.Window;
import junit.framework.TestCase;

public class SmartHomeManagerTest extends TestCase {

    public void testAddWindow() {
        SmartHomeManager smartHomeManager = new SmartHomeManager();
        Window window = new Window(1, null);
        smartHomeManager.addWindow(window);

        assertTrue("Window should be added", smartHomeManager.getWindows().contains(window));
    }

    public void testGetPersons() {
        SmartHomeManager smartHomeManager = new SmartHomeManager();
        Person person = new Person("John", null, null);
        smartHomeManager.addPerson(person);

        assertTrue("Persons list should contain added person", smartHomeManager.getPersons().contains(person));
    }

    public void testAddPerson() {
        SmartHomeManager smartHomeManager = new SmartHomeManager();
        Person person = new Person("Alice", null, null);
        smartHomeManager.addPerson(person);

        assertTrue("Persons list should contain added person", smartHomeManager.getPersons().contains(person));
    }

    public void testAddPet() {
        SmartHomeManager smartHomeManager = new SmartHomeManager();
        Pet pet = new Pet("Fluffy", null, null);
        smartHomeManager.addPet(pet);

        assertTrue("Pets list should contain added pet", smartHomeManager.getPets().contains(pet));
    }

    public void testAddTransport() {
        SmartHomeManager smartHomeManager = new SmartHomeManager();
        Transport transport = new Transport("Car", null);
        smartHomeManager.addTransport(transport);

        assertTrue("Transports list should contain added transport", smartHomeManager.getTransports().contains(transport));
    }

}
