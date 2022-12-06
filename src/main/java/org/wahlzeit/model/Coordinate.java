package org.wahlzeit.model;

import org.wahlzeit.exceptions.WahlzeitIllegalAssertStateException;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Coordinate {
    CartesianCoordinate asCartesianCoordinate();

    double getCartesianDistance(Coordinate coordinate);
    SphericCoordinate asSphericCoordinate();

    double getCentralAngle(Coordinate coordinate);

    boolean isEqual(Coordinate coordinate);

    static Coordinate readFrom(ResultSet rset) throws SQLException {
        CartesianCoordinate coord = CartesianCoordinate.readFrom(rset);
        assertNotNull(coord);
        return coord;
    }

    void writeOn(ResultSet rset) throws SQLException;

    void assertClassInvariants();

    static void assertNotNull(Object obj) {
        if(obj == null) {
            throw new WahlzeitIllegalAssertStateException("Argument is null.");
        }
    }
}
