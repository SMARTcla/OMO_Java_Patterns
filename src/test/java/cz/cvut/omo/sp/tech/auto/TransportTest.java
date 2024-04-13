package cz.cvut.omo.sp.tech.auto;

import org.junit.Test;

import static org.junit.Assert.*;

public class TransportTest {

    @Test
    public void testIsAuto() {

        Transport car = new Transport("Car", TransportType.CAR);
        Transport bicycle = new Transport("Bicycle", TransportType.BICYCLE);

        assertTrue("Car should be considered auto", car.isAuto());
        assertFalse("Bicycle should not be considered auto", bicycle.isAuto());
    }

}
