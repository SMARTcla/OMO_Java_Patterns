package cz.cvut.omo.sp.places;

import junit.framework.TestCase;

public class FloorTest extends TestCase {

    public void testTestToString() {
        Floor floor = new Floor(1);
        String expectedToString = "Floor{id=1, rooms=null}";
        assertEquals(expectedToString, floor.toString());
    }

    public void testAddRoom() {
        Floor floor = new Floor(1);
        Room room1 = new Room(101);
        Room room2 = new Room(102);

        floor.addRoom(room1);
        assertNotNull("Rooms list should not be null after adding a room", floor.getRooms());
        assertTrue("Rooms list should contain the added room", floor.getRooms().contains(room1));

        floor.addRoom(room2);
        assertTrue("Rooms list should contain the second added room", floor.getRooms().contains(room2));
    }
}
