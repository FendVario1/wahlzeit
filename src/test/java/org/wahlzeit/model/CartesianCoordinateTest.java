package org.wahlzeit.model;

import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.wahlzeit.model.CartesianCoordinate.getCartesianCoordinate;

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
        CartesianCoordinate coordinate = getCartesianCoordinate(x, y, z);
        /* cast to Double as assertEquals works for Object and Double, which IntelliJ does not like */
        assertEquals(x, (Double) coordinate.getX());
        assertEquals(y, (Double) coordinate.getY());
        assertEquals(z, (Double) coordinate.getZ());
    }

    @Test
    public void testIsValueObject() {
        double x = 1.1;
        double y = 2.2;
        double z = 3.3;
        CartesianCoordinate coordinate1 = getCartesianCoordinate(x, y, z);
        // asserts equal coordinates refer to the same object by using their default toString implementation
        //      (e.g. org.wahlzeit.model.CartesianCoordinate@11acf7)
        assertEquals(getCartesianCoordinate(x, y, z).toString(), getCartesianCoordinate(x, y, z).toString());
        CartesianCoordinate coordinate3 = getCartesianCoordinate(2.2, y, z);
        assertNotEquals(coordinate1.toString(), coordinate3.toString());// asserts non-equal coordinates do not refer to the same object
    }

    @Test
    public void testGetDistance() {
        CartesianCoordinate coordinate1 = getCartesianCoordinate(1.1, 2.2, 3.3);
        CartesianCoordinate coordinate2 = getCartesianCoordinate(1.1, 2.2, 3.3);
        CartesianCoordinate coordinate3 = getCartesianCoordinate(0.0, 2.2, 3.3);
        CartesianCoordinate coordinate4 = getCartesianCoordinate(0.0, 1.1, 2.2);
        CartesianCoordinate coordinate5 = getCartesianCoordinate(0.0, 0.1, 0.2);
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
        CartesianCoordinate coordinate1 = getCartesianCoordinate(1.1, 2.2, 3.3);
        CartesianCoordinate coordinate2 = getCartesianCoordinate(1.1, 2.2, 3.3);
        CartesianCoordinate coordinate3 = getCartesianCoordinate(0.0, 2.2, 3.3);
        CartesianCoordinate coordinate4 = getCartesianCoordinate(1.1, 0.0, 3.3);
        CartesianCoordinate coordinate5 = getCartesianCoordinate(1.1, 2.2, 0.0);
        CartesianCoordinate coordinate6 = getCartesianCoordinate(1.100001, 2.2, 3.3);
        CartesianCoordinate coordinate7 = getCartesianCoordinate(1.1, 2.200001, 3.3);
        CartesianCoordinate coordinate8 = getCartesianCoordinate(1.1, 2.2, 3.300001);
        CartesianCoordinate coordinate9 = getCartesianCoordinate(1.1, 2.2, 3.300001);
        CartesianCoordinate coordinate10 = getCartesianCoordinate(1.1, 2.2, 3.300002);

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
        CartesianCoordinate coordinate1 = getCartesianCoordinate(1.1, 2.2, 3.3);
        CartesianCoordinate coordinate2 = getCartesianCoordinate(1.1, 2.2, 3.3);
        CartesianCoordinate coordinate3 = getCartesianCoordinate(0.0, 2.2, 3.3);
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
        CartesianCoordinate coordinate = getCartesianCoordinate(1.1, 2.2, 3.3);
        ResultSet rset = mock(ResultSet.class);

        coordinate.writeOn(rset);

        verify(rset, times(1)).updateLong("coordinate_x", 110000);
        verify(rset, times(1)).updateLong("coordinate_y", 220000);
        verify(rset, times(1)).updateLong("coordinate_z", 330000);
    }

    @Test
    public void testDeserialization() throws SQLException {
        ResultSet rset = mock(ResultSet.class);

        Coordinate.readFrom(rset);

        verify(rset, times(2)).getLong("coordinate_x");
        verify(rset, times(2)).getLong("coordinate_y");
        verify(rset, times(2)).getLong("coordinate_z");
    }

    @Test
    public void testSphericalConversion() {
        CartesianCoordinate coordinate = getCartesianCoordinate(1.4, 2.1, 2);
        SphericCoordinate sphericCoordinate = coordinate.asSphericCoordinate();
        assertEquals(0.9827, sphericCoordinate.getPhi(), delta);
        assertEquals(0.9006, sphericCoordinate.getTheta(), delta);
        assertEquals(3.2202, sphericCoordinate.getRadius(), delta);
        // check backwards cast works as well
        CartesianCoordinate coordinate2 = sphericCoordinate.asCartesianCoordinate();
        assertEquals(coordinate.getX(), coordinate2.getX(), delta);
        assertEquals(coordinate.getY(), coordinate2.getY(), delta);
        assertEquals(coordinate.getZ(), coordinate2.getZ(), delta);
        assertEquals(coordinate.toString(), coordinate2.toString());
    }
}
