package org.wahlzeit.model;

import org.wahlzeit.services.DataObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 * Class representing coordinates in a 3-dimensional cartesian system
 */
public class Coordinate extends DataObject {
    private final static double delta = 0.001;

    private double x;
    private double y;
    private double z;

    /**
     *
     * @methodtype constructor
     */
    public Coordinate(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        incWriteCount();
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public double getDistance(Coordinate coordinate) {
        return Math.sqrt(Math.pow(coordinate.getX() - x, 2) + Math.pow(coordinate.getY() - y, 2) + Math.pow(coordinate.getZ() - z, 2));
    }

    public boolean isEqual(Coordinate coordinate) {
        return Math.abs(coordinate.getX() - x) <= delta && Math.abs(coordinate.getY() - y) <= delta && Math.abs(coordinate.getZ() - z) <= delta;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != Coordinate.class)
            return false;
        if (this == obj)
            return true;
        return isEqual((Coordinate) obj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    @Override
    public String getIdAsString() {
        return null;
    }

    @Override
    public void readFrom(ResultSet rset) throws SQLException {
        x = rset.getDouble("coordinate_x");
        y = rset.getDouble("coordinate_y");
        z = rset.getDouble("coordinate_z");
    }

    @Override
    public void writeOn(ResultSet rset) throws SQLException {
        rset.updateDouble("coordinate_x", x);
        rset.updateDouble("coordinate_y", y);
        rset.updateDouble("coordinate_z", z);
    }

    @Override
    public void writeId(PreparedStatement stmt, int pos) {
        incWriteCount();
    }
}
