package org.wahlzeit.model;

import org.wahlzeit.exceptions.WahlzeitIllegalAssertStateException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 * Class representing coordinates in a 3-dimensional cartesian system
 */
public class CartesianCoordinate extends AbstractCoordinate {
    private final static double delta = 0.001;

    private double x;
    private double y;
    private double z;

    /**
     *
     * @methodtype constructor
     */
    public CartesianCoordinate(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        incWriteCount();
        assertClassInvariants();
        assertDoubleEquals(x, this.x);
        assertDoubleEquals(y, this.y);
        assertDoubleEquals(z, this.z);
    }

    public double getX() {
        assertClassInvariants();
        return x;
    }

    public double getY() {
        assertClassInvariants();
        return y;
    }

    public double getZ() {
        assertClassInvariants();
        return z;
    }

    public double getDistance(CartesianCoordinate coordinate) {
        assertClassInvariants();
        assertNotNull(coordinate);
        double retVal = Math.sqrt(Math.pow(coordinate.getX() - x, 2) + Math.pow(coordinate.getY() - y, 2) + Math.pow(coordinate.getZ() - z, 2));
        if(retVal < 0) {
            throw new WahlzeitIllegalAssertStateException("Distance invalid in getDistance.");
        }
        return retVal;
    }

    public boolean isEqual(CartesianCoordinate coordinate) {
        assertClassInvariants();
        assertNotNull(coordinate);
        return Math.abs(coordinate.getX() - x) <= delta && Math.abs(coordinate.getY() - y) <= delta && Math.abs(coordinate.getZ() - z) <= delta;
    }

    @Override
    public boolean equals(Object obj) {
        assertClassInvariants();
        if (obj == null || obj.getClass() != CartesianCoordinate.class)
            return false;
        if (this == obj)
            return true;
        return isEqual((CartesianCoordinate) obj);
    }

    @Override
    public int hashCode() {
        assertClassInvariants();
        return Objects.hash(x, y, z);
    }

    @Override
    public void readFrom(ResultSet rset) throws SQLException {
        assertClassInvariants();
        assertNotNull(rset);
        x = rset.getDouble("coordinate_x");
        y = rset.getDouble("coordinate_y");
        z = rset.getDouble("coordinate_z");
        assertClassInvariants();
        assertDoubleEquals(x, rset.getDouble("coordinate_x"));
        assertDoubleEquals(y, rset.getDouble("coordinate_y"));
        assertDoubleEquals(z, rset.getDouble("coordinate_z"));
    }

    @Override
    public void writeOn(ResultSet rset) throws SQLException {
        assertClassInvariants();
        assertNotNull(rset);
        rset.updateDouble("coordinate_x", x);
        rset.updateDouble("coordinate_y", y);
        rset.updateDouble("coordinate_z", z);
    }

    protected void loadValues(CartesianCoordinate coordinate) {
        assertClassInvariants();
        assertNotNull(coordinate);
        x = coordinate.x;
        y = coordinate.y;
        z = coordinate.z;
        assertClassInvariants();
        assertDoubleEquals(x, coordinate.x);
        assertDoubleEquals(y, coordinate.y);
        assertDoubleEquals(z, coordinate.z);
    }

    @Override
    public CartesianCoordinate asCartesianCoordinate() {
        assertClassInvariants();
        return this;
    }

    @Override
    public double getCartesianDistance(Coordinate coordinate) {
        assertClassInvariants();
        assertNotNull(coordinate);
        return getDistance(coordinate.asCartesianCoordinate());
    }

    @Override
    public SphericCoordinate asSphericCoordinate() {
        assertClassInvariants();
        double radius = Math.sqrt(x * x + y * y + z * z);
        double phi = SphericCoordinate.takeModulo(Math.atan2(y, x));
        double theta = SphericCoordinate.takeModulo(Math.acos(z/radius));
        SphericCoordinate coordinate = new SphericCoordinate(phi, theta, radius);
        assertClassEquals(SphericCoordinate.class, coordinate.getClass());
        return coordinate;
    }

    @Override
    public void assertClassInvariants() {}
}
