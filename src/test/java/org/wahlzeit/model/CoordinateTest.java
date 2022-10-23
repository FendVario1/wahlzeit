package org.wahlzeit.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * All test cases of the class {@link Coordinate}.
 */
public class CoordinateTest {

    @Test
    public void testCoordinate() {
        Double x = 1.1;
        Double y = 2.2;
        Double z = 3.3;
        Coordinate coordinate = new Coordinate(x, y, z);
        /* cast to Double as assertEquals works for Object and Double, which IntelliJ does not like */
        assertEquals(x, (Double) coordinate.getX());
        assertEquals(y, (Double) coordinate.getY());
        assertEquals(z, (Double) coordinate.getZ());
    }
}
