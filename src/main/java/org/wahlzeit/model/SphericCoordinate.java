package org.wahlzeit.model;

import org.wahlzeit.exceptions.WahlzeitIllegalAssertStateException;

import java.util.HashMap;
import java.util.Objects;

import static org.wahlzeit.model.CartesianCoordinate.getCartesianCoordinate;

public class SphericCoordinate extends AbstractCoordinate {

    private final static int INTERNAL_FACTOR_MULTIPLIER = 100000;

    private final long phi;
    private final long theta;
    private final long radius;

    private static final HashMap<Integer, SphericCoordinate> coordinates = new HashMap<>();

    /**
     *
     * @methodtype constructor
     */
    private SphericCoordinate(long phi, long theta, long radius) {
        if(radius < 0) {
            throw new WahlzeitIllegalAssertStateException(SphericCoordinate.class.getName() + ": radius " + radius + " is not positive.");
        }
        this.phi = phi;
        this.theta = theta;
        this.radius = radius;
        assertLongEquals(radius, radius);
        assertLongEquals(this.phi, phi);
        assertLongEquals(this.theta, theta);
        assertClassInvariants();
    }

    public static SphericCoordinate getSphericCoordinate(double phi, double theta, double radius) {
        if(radius < 0) {
            throw new WahlzeitIllegalAssertStateException(SphericCoordinate.class.getName() + ": radius " + radius + " is not positive.");
        }
        long _phi = (long) (takeModulo(phi) * INTERNAL_FACTOR_MULTIPLIER);
        long _theta = (long) (takeModulo(theta) * INTERNAL_FACTOR_MULTIPLIER);
        long _radius = (long) (radius * INTERNAL_FACTOR_MULTIPLIER);
        SphericCoordinate retVal = coordinates.get(getHashCode(_phi, _theta, _radius));
        if(retVal == null) {
            retVal = new SphericCoordinate(_phi, _theta, _radius);
            coordinates.put(retVal.hashCode(), retVal);
        }
        Coordinate.assertNotNull(retVal);
        assertDoubleEquals(retVal.getPhi(), takeModulo(phi));
        assertDoubleEquals(retVal.getTheta(), takeModulo(theta));
        assertDoubleEquals(retVal.getRadius(), radius);
        assertIntEqualsDouble(retVal.phi, takeModulo(phi), INTERNAL_FACTOR_MULTIPLIER);
        assertIntEqualsDouble(retVal.theta, takeModulo(theta), INTERNAL_FACTOR_MULTIPLIER);
        assertIntEqualsDouble(retVal.radius, radius, INTERNAL_FACTOR_MULTIPLIER);
        return retVal;
    }

    public double getPhi() {
        assertClassInvariants();
        return (double) phi/INTERNAL_FACTOR_MULTIPLIER;
    }

    public double getTheta() {
        assertClassInvariants();
        return (double) theta/INTERNAL_FACTOR_MULTIPLIER;
    }

    public double getRadius() {
        assertClassInvariants();
        return (double) radius/INTERNAL_FACTOR_MULTIPLIER;
    }

    @Override
    public CartesianCoordinate asCartesianCoordinate() {
        assertClassInvariants();
        CartesianCoordinate coordinate = CartesianCoordinate.sphericReferences.get(hashCode());
        if(coordinate == null) {// derive cartesian coordinate if not yet available
            double radius = getRadius();
            double theta = getTheta();
            double phi = getPhi();
            double x = radius * Math.sin(theta) * Math.cos(phi);
            double y = radius * Math.sin(theta) * Math.sin(phi);
            double z = radius * Math.cos(theta);
            coordinate = getCartesianCoordinate(x, y, z);
            // save reference
            CartesianCoordinate.sphericReferences.put(hashCode(), coordinate);
        }
        Coordinate.assertNotNull(coordinate);
        assertClassEquals(CartesianCoordinate.class, coordinate.getClass());
        return coordinate;
    }

    @Override
    public SphericCoordinate asSphericCoordinate() {
        assertClassInvariants();
        return this;
    }

    @Override
    public double getCentralAngle(Coordinate coordinate) {
        assertClassInvariants();
        Coordinate.assertNotNull(coordinate);
        return calculateCentralAngle(this, coordinate.asSphericCoordinate());
    }

    private static double calculateCentralAngle(SphericCoordinate a, SphericCoordinate b) {
        Coordinate.assertNotNull(a);
        Coordinate.assertNotNull(b);
        a.assertClassInvariants();
        b.assertClassInvariants();
        Double retVal = null;
        if(Math.abs(a.getRadius()-b.getRadius()) > 0.01)
            retVal = Double.NaN;// return NaN as distance can not be calculated using this function
        else
            retVal = Math.acos(Math.sin(a.getPhi())*Math.sin(b.getPhi()) +
                Math.cos(a.getPhi())*Math.cos(b.getPhi())*Math.cos(a.getTheta()-b.getTheta()));
        Coordinate.assertNotNull(retVal);
        if(retVal < 0 && !retVal.isNaN()) {
            throw new WahlzeitIllegalAssertStateException("Distance invalid in calculateCentralAngle.");
        }
        return retVal;
    }

    protected static double takeModulo(double angle) {
        return floatMod(angle + Math.PI) - Math.PI;
    }

    // Handles negative angles
    // taken from SO: https://stackoverflow.com/a/55205438
    private static double floatMod(double x){
        return (x - Math.floor(x/(2*Math.PI)) * 2*Math.PI);
    }

    @Override
    public void assertClassInvariants() {
        if((double) phi/INTERNAL_FACTOR_MULTIPLIER < -Math.PI || (double) phi/INTERNAL_FACTOR_MULTIPLIER > Math.PI)
            throw new WahlzeitIllegalAssertStateException(SphericCoordinate.class.getName() + ": phi " + (double) phi /INTERNAL_FACTOR_MULTIPLIER + " is not within [-Pi, Pi].");
        if((double) theta/INTERNAL_FACTOR_MULTIPLIER < -Math.PI || (double) theta/INTERNAL_FACTOR_MULTIPLIER > Math.PI)
            throw new WahlzeitIllegalAssertStateException(SphericCoordinate.class.getName() + ": theta " + (double) theta /INTERNAL_FACTOR_MULTIPLIER + " is not within [-Pi, Pi].");
        if((double) radius/INTERNAL_FACTOR_MULTIPLIER < 0) {
            throw new WahlzeitIllegalAssertStateException(SphericCoordinate.class.getName() + ": radius " + (double) radius /INTERNAL_FACTOR_MULTIPLIER + " is not positive.");
        }
    }

    @Override
    public boolean equals(Object obj) {
        assertClassInvariants();
        if (obj == null || obj.getClass() != SphericCoordinate.class)
            return false;
        return isEqual((SphericCoordinate) obj);
    }

    @Override
    public int hashCode() {
        assertClassInvariants();
        return getHashCode(phi, theta, radius);
    }

    private static int getHashCode(long phi, long theta, long radius) {
        return Objects.hash(phi, theta, radius);
    }
}
