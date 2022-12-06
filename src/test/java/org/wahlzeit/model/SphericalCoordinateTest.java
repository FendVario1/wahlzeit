package org.wahlzeit.model;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.wahlzeit.model.CartesianCoordinate.getCartesianCoordinate;
import static org.wahlzeit.model.SphericCoordinate.getSphericCoordinate;

public class SphericalCoordinateTest {
    private final double delta = 0.001;

    @Test
    public void testSphericalCoordinate() {
        double phi = 1.1;
        double theta = 2.2;
        double radius = 3.3;
        SphericCoordinate coordinate = getSphericCoordinate(phi, theta, radius);
        /* cast to Double as assertEquals works for Object and Double, which IntelliJ does not like */
        assertEquals(phi, coordinate.getPhi(), delta);
        assertEquals(theta, coordinate.getTheta(), delta);
        assertEquals(radius, coordinate.getRadius(), delta);
    }

    @Test
    public void testIsValueObject() {
        double x = 1.1;
        double y = 2.2;
        double z = 3.3;
        SphericCoordinate coordinate1 = getSphericCoordinate(x, y, z);
        // asserts equal coordinates refer to the same object by using their default toString implementation
        //      (e.g. org.wahlzeit.model.CartesianCoordinate@11acf7)
        assertEquals(getSphericCoordinate(x, y, z).toString(), getSphericCoordinate(x, y, z).toString());
        SphericCoordinate coordinate3 = getSphericCoordinate(2.2, y, z);
        assertNotEquals(coordinate1.toString(), coordinate3.toString());// asserts non-equal coordinates do not refer to the same object
    }

    @Test
    public void testCartesianConversion() {
        SphericCoordinate coordinate = getSphericCoordinate(0.9827937232473292, 0.900689018478286, 3.2202484376209237);
        CartesianCoordinate cartesianCoordinate = coordinate.asCartesianCoordinate();
        assertEquals(1.4, cartesianCoordinate.getX(), delta);
        assertEquals(2.1, cartesianCoordinate.getY(), delta);
        assertEquals(2, cartesianCoordinate.getZ(), delta);
        // check backwards cast works as well
        SphericCoordinate coordinate2 = cartesianCoordinate.asSphericCoordinate();
        assertEquals(coordinate.getRadius(), coordinate2.getRadius(), delta);
        assertEquals(coordinate.getPhi(), coordinate2.getPhi(), delta);
        assertEquals(coordinate.getTheta(), coordinate2.getTheta(), delta);
        assertEquals(coordinate.toString(), coordinate2.toString());
        SphericCoordinate coordinate3 = getSphericCoordinate(3.2202, 0.9827, 0.5);
        // check comparison of non-equal radius returns NaN
        assertTrue(Double.isNaN(coordinate.getCentralAngle(coordinate3)));
    }

    @Test
    public void testCentralAngleCalculation() {
        SphericCoordinate a = getSphericCoordinate(1, 2, 1);
        SphericCoordinate b = getSphericCoordinate(2, 1, 1);
        assertEquals(0.8715, a.getCentralAngle(b), delta);
        assertEquals(0.8715, b.getCentralAngle(a), delta);
    }

    @Test
    public void testModuloHelper() {
        double a = 1.2;
        double b = -1.2;
        // no change if modulo not needed
        assertEquals(a, SphericCoordinate.takeModulo(a), delta);
        assertEquals(b, SphericCoordinate.takeModulo(b), delta);
        // test positive angles
        assertEquals(a, SphericCoordinate.takeModulo(a + 2 * Math.PI), delta);
        assertEquals(b, SphericCoordinate.takeModulo(b + 2 * Math.PI), delta);
        // test negative angles are handled correctly
        assertEquals(a, SphericCoordinate.takeModulo(a - 2 * Math.PI), delta);
        assertEquals(b, SphericCoordinate.takeModulo(b - 2 * Math.PI), delta);
    }
}
