package org.wahlzeit.model;

import org.wahlzeit.exceptions.WahlzeitIllegalAssertStateException;
import org.wahlzeit.services.DataObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractCoordinate extends DataObject implements Coordinate  {

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

    @Override
    public String getIdAsString() {
        assertClassInvariants();
        return null;
    }

    @Override
    public void writeId(PreparedStatement stmt, int pos) {
        assertClassInvariants();
        incWriteCount();
        assertClassInvariants();
    }

    @Override
    public void readFrom(ResultSet rset) throws SQLException {
        assertClassInvariants();
        CartesianCoordinate coord = new CartesianCoordinate(0,0,0);
        coord.readFrom(rset);
        this.loadValues(coord);
        assertClassInvariants();
    }

    @Override
    public void writeOn(ResultSet rset) throws SQLException {
        assertClassInvariants();
        CartesianCoordinate coord = this.asCartesianCoordinate();
        coord.writeOn(rset);
    }

    abstract protected void loadValues(CartesianCoordinate coordinate);

    protected static void assertNotNull(Object obj) {
        if(obj == null) {
            throw new WahlzeitIllegalAssertStateException("Argument is null.");
        }
    }

    protected static void assertDoubleEquals(double a, double b) {
        if(Math.abs(a - b) > 0.00001) {
            throw new WahlzeitIllegalAssertStateException("Doubles " + a + " and " + b + " are not equal.");
        }
    }

    protected static void assertClassEquals(Class a, Class b) {
        if (a != b) {
            throw new WahlzeitIllegalAssertStateException("Classes " + a + " and " + b + " are not equal.");
        }
    }
}
