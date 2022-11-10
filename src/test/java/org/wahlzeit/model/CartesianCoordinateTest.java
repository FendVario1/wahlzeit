package org.wahlzeit.model;

import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * All test cases of the class {@link CartesianCoordinate}.
 */
public class CartesianCoordinateTest {
    private final double delta = 0.0001;

    @Test
    public void testCartesianCoordinate() {
        Double x = 1.1;
        Double y = 2.2;
        Double z = 3.3;
        CartesianCoordinate coordinate = new CartesianCoordinate(x, y, z);
        /* cast to Double as assertEquals works for Object and Double, which IntelliJ does not like */
        assertEquals(x, (Double) coordinate.getX());
        assertEquals(y, (Double) coordinate.getY());
        assertEquals(z, (Double) coordinate.getZ());
    }

    @Test
    public void testGetDistance() {
        CartesianCoordinate coordinate1 = new CartesianCoordinate(1.1, 2.2, 3.3);
        CartesianCoordinate coordinate2 = new CartesianCoordinate(1.1, 2.2, 3.3);
        CartesianCoordinate coordinate3 = new CartesianCoordinate(0.0, 2.2, 3.3);
        CartesianCoordinate coordinate4 = new CartesianCoordinate(0.0, 1.1, 2.2);
        CartesianCoordinate coordinate5 = new CartesianCoordinate(0.0, 0.1, 0.2);
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
        CartesianCoordinate coordinate1 = new CartesianCoordinate(1.1, 2.2, 3.3);
        CartesianCoordinate coordinate2 = new CartesianCoordinate(1.1, 2.2, 3.3);
        CartesianCoordinate coordinate3 = new CartesianCoordinate(0.0, 2.2, 3.3);
        CartesianCoordinate coordinate4 = new CartesianCoordinate(1.1, 0.0, 3.3);
        CartesianCoordinate coordinate5 = new CartesianCoordinate(1.1, 2.2, 0.0);
        CartesianCoordinate coordinate6 = new CartesianCoordinate(1.100001, 2.2, 3.3);
        CartesianCoordinate coordinate7 = new CartesianCoordinate(1.1, 2.200001, 3.3);
        CartesianCoordinate coordinate8 = new CartesianCoordinate(1.1, 2.2, 3.300001);
        CartesianCoordinate coordinate9 = new CartesianCoordinate(1.1, 2.2, 3.300001);
        CartesianCoordinate coordinate10 = new CartesianCoordinate(1.1, 2.2, 3.3002);

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
        CartesianCoordinate coordinate1 = new CartesianCoordinate(1.1, 2.2, 3.3);
        CartesianCoordinate coordinate2 = new CartesianCoordinate(1.1, 2.2, 3.3);
        CartesianCoordinate coordinate3 = new CartesianCoordinate(0.0, 2.2, 3.3);
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

    @Test
    public void testSerialization() throws SQLException {
        CartesianCoordinate coordinate = new CartesianCoordinate(1.1, 2.2, 3.3);
        ResultSet rset = mock(ResultSet.class);

        coordinate.writeOn(rset);

        verify(rset, times(1)).updateDouble("coordinate_x", coordinate.getX());
        verify(rset, times(1)).updateDouble("coordinate_y", coordinate.getY());
        verify(rset, times(1)).updateDouble("coordinate_z", coordinate.getZ());
    }

    @Test
    public void testDeserialization() throws SQLException {
        CartesianCoordinate coordinate = new CartesianCoordinate(1.1, 2.2, 3.3);
        ResultSet rset = mock(ResultSet.class);

        coordinate.readFrom(rset);

        verify(rset, times(1)).getDouble("coordinate_x");
        verify(rset, times(1)).getDouble("coordinate_y");
        verify(rset, times(1)).getDouble("coordinate_z");
    }

    @Test
    public void testSphericalConversion() {
        CartesianCoordinate coordinate = new CartesianCoordinate(1.4, 2.1, 2);
        SphericCoordinate sphericCoordinate = coordinate.asSphericCoordinate();
        assertEquals(0.9827, sphericCoordinate.getPhi(), delta);
        assertEquals(0.9006, sphericCoordinate.getTheta(), delta);
        assertEquals(3.2202, sphericCoordinate.getRadius(), delta);
        // check backwards cast works as well
        CartesianCoordinate coordinate2 = sphericCoordinate.asCartesianCoordinate();
        assertEquals(coordinate.getX(), coordinate2.getX(), delta);
        assertEquals(coordinate.getY(), coordinate2.getY(), delta);
        assertEquals(coordinate.getZ(), coordinate2.getZ(), delta);
    }
}
