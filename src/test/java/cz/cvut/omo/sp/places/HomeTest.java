package cz.cvut.omo.sp.places;

import junit.framework.TestCase;

public class HomeTest extends TestCase {

    public void testAddFloor() {
        Home home = Home.getHome();
        Floor floor1 = new Floor(1);
        Floor floor2 = new Floor(2);

        home.addFloor(floor1);
        assertNotNull("Floors list should not be null after adding a floor", home.getFloors());
        assertTrue("Floors list should contain the added floor", home.getFloors().contains(floor1));

        home.addFloor(floor2);
        assertTrue("Floors list should contain the second added floor", home.getFloors().contains(floor2));
    }
}
