package org.wahlzeit.model;

import org.wahlzeit.exceptions.WahlzeitIllegalAssertStateException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Objects;

import static org.wahlzeit.model.SphericCoordinate.*;

/**
 * Class representing coordinates in a 3-dimensional cartesian system
 */
public class CartesianCoordinate extends AbstractCoordinate {

    private final static int INTERNAL_FACTOR_MULTIPLIER = 100000;

    private final long x;
    private final long y;
    private final long z;

    private static final HashMap<Integer, CartesianCoordinate> coordinates = new HashMap<>();

    // references a CartesianCoordinate Object using the hashCode of a spheric one
    protected static final HashMap<Integer, CartesianCoordinate> sphericReferences = new HashMap<>();

    /**
     *
     * @methodtype constructor
     */
    private CartesianCoordinate(long x, long y, long z) {
        this.x = x;
        this.y = y;
        this.z = z;
        assertClassInvariants();
        assertLongEquals(this.x, x);
        assertLongEquals(this.y, y);
        assertLongEquals(this.z, z);
    }

    public synchronized static CartesianCoordinate getCartesianCoordinate(double x, double y, double z) {
        long _x = (long) (x * INTERNAL_FACTOR_MULTIPLIER);
        long _y = (long) (y * INTERNAL_FACTOR_MULTIPLIER);
        long _z = (long) (z * INTERNAL_FACTOR_MULTIPLIER);
        CartesianCoordinate retVal = coordinates.get(getHashCode(_x, _y, _z));
        if(retVal == null) {
            retVal = new CartesianCoordinate(_x, _y, _z);
            coordinates.put(retVal.hashCode(), retVal);
        }
        Coordinate.assertNotNull(retVal);
        assertIntEqualsDouble(retVal.x, x, INTERNAL_FACTOR_MULTIPLIER);
        assertIntEqualsDouble(retVal.y, y, INTERNAL_FACTOR_MULTIPLIER);
        assertIntEqualsDouble(retVal.z, z, INTERNAL_FACTOR_MULTIPLIER);
        return retVal;
    }

    public double getX() {
        assertClassInvariants();
        return (double) x/INTERNAL_FACTOR_MULTIPLIER;
    }

    public double getY() {
        assertClassInvariants();
        return (double) y/INTERNAL_FACTOR_MULTIPLIER;
    }

    public double getZ() {
        assertClassInvariants();
        return (double) z/INTERNAL_FACTOR_MULTIPLIER;
    }

    public double getDistance(CartesianCoordinate coordinate) {
        assertClassInvariants();
        Coordinate.assertNotNull(coordinate);
        double retVal = Math.sqrt(Math.pow(coordinate.getX() - (double) x/INTERNAL_FACTOR_MULTIPLIER, 2)
                + Math.pow(coordinate.getY() - (double) y/INTERNAL_FACTOR_MULTIPLIER, 2)
                + Math.pow(coordinate.getZ() - (double) z/INTERNAL_FACTOR_MULTIPLIER, 2));
        if(retVal < 0) {
            throw new WahlzeitIllegalAssertStateException("Distance invalid in getDistance.");
        }
        return retVal;
    }

    public boolean isEqual(CartesianCoordinate coordinate) {
        assertClassInvariants();
        Coordinate.assertNotNull(coordinate);
        return coordinate.x == x && coordinate.y == y && coordinate.z == z;
    }

    @Override
    public boolean equals(Object obj) {
        assertClassInvariants();
        if (obj == null || obj.getClass() != CartesianCoordinate.class)
            return false;
        return isEqual((CartesianCoordinate) obj);
    }

    @Override
    public int hashCode() {
        assertClassInvariants();
        return getHashCode(x, y, z);
    }

    private static int getHashCode(long x, long y, long z) {
        return Objects.hash(x, y, z);
    }

    public static CartesianCoordinate readFrom(ResultSet rset) throws SQLException {
        long x = rset.getLong("coordinate_x");
        long y = rset.getLong("coordinate_y");
        long z = rset.getLong("coordinate_z");
        CartesianCoordinate coord = getCartesianCoordinate((double) x/INTERNAL_FACTOR_MULTIPLIER, (double) y/INTERNAL_FACTOR_MULTIPLIER,(double) z/INTERNAL_FACTOR_MULTIPLIER);
        assertLongEquals(coord.x, rset.getLong("coordinate_x"));
        assertLongEquals(coord.y, rset.getLong("coordinate_y"));
        assertLongEquals(coord.z, rset.getLong("coordinate_z"));
        return coord;
    }

    public void writeOn(ResultSet rset) throws SQLException {
        assertClassInvariants();
        Coordinate.assertNotNull(rset);
        rset.updateLong("coordinate_x", x);
        rset.updateLong("coordinate_y", y);
        rset.updateLong("coordinate_z", z);
    }

    @Override
    public CartesianCoordinate asCartesianCoordinate() {
        assertClassInvariants();
        return this;
    }

    @Override
    public double getCartesianDistance(Coordinate coordinate) {
        assertClassInvariants();
        Coordinate.assertNotNull(coordinate);
        return getDistance(coordinate.asCartesianCoordinate());
    }

    @Override
    public SphericCoordinate asSphericCoordinate() {
        assertClassInvariants();
        double radius = Math.sqrt(getX() * getX() + getY() * getY() + getZ() * getZ());
        double phi = takeModulo(Math.atan2(getY(), getX()));
        double theta = takeModulo(Math.acos(getZ()/radius));
        SphericCoordinate coordinate = getSphericCoordinate(phi, theta, radius);
        sphericReferences.put(coordinate.hashCode(), this);
        assertClassEquals(SphericCoordinate.class, coordinate.getClass());
        return coordinate;
    }

    @Override
    public void assertClassInvariants() {}
}
