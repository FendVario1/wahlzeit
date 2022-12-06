package org.wahlzeit.model;

import org.wahlzeit.exceptions.WahlzeitIllegalAssertStateException;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractCoordinate implements Coordinate  {

    @Override
    public double getCartesianDistance(Coordinate coordinate) {
        assertClassInvariants();
        return this.asCartesianCoordinate().getCartesianDistance(coordinate);
    }

    @Override
    public double getCentralAngle(Coordinate coordinate) {
        assertClassInvariants();
        return this.asSphericCoordinate().getCentralAngle(coordinate);
    }

    @Override
    public boolean isEqual(Coordinate coordinate) {
        assertClassInvariants();
        return this.asCartesianCoordinate().isEqual(coordinate.asCartesianCoordinate());
    }

    public void writeOn(ResultSet rset) throws SQLException {
        assertClassInvariants();
        CartesianCoordinate coord = this.asCartesianCoordinate();
        coord.writeOn(rset);
    }

    protected static void assertDoubleEquals(double a, double b) {
        if(Math.abs(a - b) > 0.0001) {
            throw new WahlzeitIllegalAssertStateException("Doubles " + a + " and " + b + " are not equal.");
        }
    }

    protected static void assertIntEqualsDouble(long a, double b, int factor) {
        if(Math.abs(a/(double) factor - b) > 1.1/factor) {
            throw new WahlzeitIllegalAssertStateException("Int " + a/(double) factor + " and double " + b + " are not equal under factor " + factor + ".");
        }
    }

    protected static void assertLongEquals(long a, long b) {
        if(a != b) {
            throw new WahlzeitIllegalAssertStateException("Integers " + a + " and " + b + " are not equal.");
        }
    }

    protected static void assertClassEquals(Class a, Class b) {
        if (a != b) {
            throw new WahlzeitIllegalAssertStateException("Classes " + a + " and " + b + " are not equal.");
        }
    }
}
