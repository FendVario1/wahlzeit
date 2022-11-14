package org.wahlzeit.model;

import org.wahlzeit.services.DataObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractCoordinate extends DataObject implements Coordinate  {

    @Override
    public double getCartesianDistance(Coordinate coordinate) {
        return this.asCartesianCoordinate().getCartesianDistance(coordinate);
    }

    @Override
    public double getCentralAngle(Coordinate coordinate) {
        return this.asSphericCoordinate().getCentralAngle(coordinate);
    }

    @Override
    public boolean isEqual(Coordinate coordinate) {
        return this.asCartesianCoordinate().isEqual(coordinate.asCartesianCoordinate());
    }

    @Override
    public String getIdAsString() {
        return null;
    }

    @Override
    public void writeId(PreparedStatement stmt, int pos) {
        incWriteCount();
    }

    @Override
    public void readFrom(ResultSet rset) throws SQLException {
        CartesianCoordinate coord = new CartesianCoordinate(0,0,0);
        coord.readFrom(rset);
        this.loadValues(coord);
    }

    @Override
    public void writeOn(ResultSet rset) throws SQLException {
        CartesianCoordinate coord = this.asCartesianCoordinate();
        coord.writeOn(rset);
    }

    abstract protected void loadValues(CartesianCoordinate coordinate);
}
