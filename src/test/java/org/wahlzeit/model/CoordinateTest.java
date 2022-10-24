package org.wahlzeit.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * All test cases of the class {@link Coordinate}.
 */
public class CoordinateTest {
    private final double delta = 0.0001;

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

    @Test
    public void testGetDistance() {
        Coordinate coordinate1 = new Coordinate(1.1, 2.2, 3.3);
        Coordinate coordinate2 = new Coordinate(1.1, 2.2, 3.3);
        Coordinate coordinate3 = new Coordinate(0.0, 2.2, 3.3);
        Coordinate coordinate4 = new Coordinate(0.0, 1.1, 2.2);
        Coordinate coordinate5 = new Coordinate(0.0, 0.1, 0.2);
        // test distance with self is 0
        assertEquals(0, coordinate1.getDistance(coordinate1), delta);
        // test distance with identical coordinate is 0
        assertEquals(0, coordinate1.getDistance(coordinate2), delta);
        // test one different coordinate produces correct result
        assertEquals(1.1, coordinate1.getDistance(coordinate3), delta);
        // test different coordinates produces correct result
        assertEquals(Math.sqrt(Math.pow(1.1, 2)*3), coordinate1.getDistance(coordinate4), delta);
        // Test individual values are correctly associated
        assertEquals(Math.sqrt(Math.pow(1.1, 2)+Math.pow(2.1, 2)+Math.pow(3.1, 2)), coordinate1.getDistance(coordinate5), delta);
    }

    @Test
    public void testIsEqual() {
        Coordinate coordinate1 = new Coordinate(1.1, 2.2, 3.3);
        Coordinate coordinate2 = new Coordinate(1.1, 2.2, 3.3);
        Coordinate coordinate3 = new Coordinate(0.0, 2.2, 3.3);
        Coordinate coordinate4 = new Coordinate(1.1, 0.0, 3.3);
        Coordinate coordinate5 = new Coordinate(1.1, 2.2, 0.0);
        Coordinate coordinate6 = new Coordinate(1.100001, 2.2, 3.3);
        Coordinate coordinate7 = new Coordinate(1.1, 2.200001, 3.3);
        Coordinate coordinate8 = new Coordinate(1.1, 2.2, 3.300001);
        Coordinate coordinate9 = new Coordinate(1.1, 2.2, 3.300001);
        Coordinate coordinate10 = new Coordinate(1.1, 2.2, 3.3002);

        // test self is equal
        assertTrue(coordinate1.isEqual(coordinate1));
        // test identical is equal
        assertTrue(coordinate1.isEqual(coordinate2));
        // test x difference is detected
        assertFalse(coordinate1.isEqual(coordinate3));
        // test y difference is detected
        assertFalse(coordinate1.isEqual(coordinate4));
        // test z difference is detected
        assertFalse(coordinate1.isEqual(coordinate5));
        // test small x difference is equal
        assertTrue(coordinate1.isEqual(coordinate6));
        // test small y difference is equal
        assertTrue(coordinate1.isEqual(coordinate7));
        // test small z difference is equal
        assertTrue(coordinate1.isEqual(coordinate8));
        // test small overall difference is equal
        assertTrue(coordinate1.isEqual(coordinate9));
        // test not so small difference is detected
        assertTrue(coordinate1.isEqual(coordinate10));
    }

    @Test
    public void testEquals() {
        Coordinate coordinate1 = new Coordinate(1.1, 2.2, 3.3);
        Coordinate coordinate2 = new Coordinate(1.1, 2.2, 3.3);
        Coordinate coordinate3 = new Coordinate(0.0, 2.2, 3.3);
        Location location = new Location(coordinate1);
        // test null not equal
        assertNotEquals(null, coordinate1);
        // test other class object not equal
        assertNotEquals(coordinate1, location);
        // test self equal
        assertEquals(coordinate1, coordinate1);
        // test identical equal
        assertEquals(coordinate1, coordinate2);
        // test difference is detected
        assertNotEquals(coordinate1, coordinate3);
    }
}
