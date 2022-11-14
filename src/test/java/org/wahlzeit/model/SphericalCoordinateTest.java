package org.wahlzeit.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SphericalCoordinateTest {
    private final double delta = 0.0001;

    @Test
    public void testSphericalCoordinate() {
        double phi = 1.1;
        double theta = 2.2;
        double radius = 3.3;
        SphericCoordinate coordinate = new SphericCoordinate(phi, theta, radius);
        /* cast to Double as assertEquals works for Object and Double, which IntelliJ does not like */
        assertEquals(phi, coordinate.getPhi(), delta);
        assertEquals(theta, coordinate.getTheta(), delta);
        assertEquals(radius, coordinate.getRadius(), delta);
    }

    @Test
    public void testCartesianConversion() {
        SphericCoordinate coordinate = new SphericCoordinate(0.9827937232473292, 0.900689018478286, 3.2202484376209237);
        CartesianCoordinate cartesianCoordinate = coordinate.asCartesianCoordinate();
        assertEquals(1.4, cartesianCoordinate.getX(), delta);
        assertEquals(2.1, cartesianCoordinate.getY(), delta);
        assertEquals(2, cartesianCoordinate.getZ(), delta);
        // check backwards cast works as well
        SphericCoordinate coordinate2 = cartesianCoordinate.asSphericCoordinate();
        assertEquals(coordinate.getRadius(), coordinate2.getRadius(), delta);
        assertEquals(coordinate.getPhi(), coordinate2.getPhi(), delta);
        assertEquals(coordinate.getTheta(), coordinate2.getTheta(), delta);
        SphericCoordinate coordinate3 = new SphericCoordinate(3.2202, 0.9827, 0.5);
        // check comparison of non-equal radius returns NaN
        assertTrue(Double.isNaN(coordinate.getCentralAngle(coordinate3)));
    }

    @Test
    public void testCentralAngleCalculation() {
        SphericCoordinate a = new SphericCoordinate(1, 2, 1);
        SphericCoordinate b = new SphericCoordinate(2, 1, 1);
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

    @Test
    public void testLoadValues() {
        SphericCoordinate coordinate = new SphericCoordinate(0.9827937232473292, 0.900689018478286, 3.2202484376209237);
        CartesianCoordinate cartesianCoordinate = coordinate.asCartesianCoordinate();
        SphericCoordinate coordinate1 = new SphericCoordinate(0.0, 0.0, 0.0);
        coordinate1.loadValues(cartesianCoordinate);
        assertEquals(coordinate.getRadius(), coordinate1.getRadius(), delta);
        assertEquals(coordinate.getPhi(), coordinate1.getPhi(), delta);
        assertEquals(coordinate.getTheta(), coordinate1.getTheta(), delta);
    }
}
