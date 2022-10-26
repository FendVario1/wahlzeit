package org.wahlzeit.model;

import org.jetbrains.annotations.NotNull;
import org.wahlzeit.services.DataObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Location extends DataObject {

    public final Coordinate coordinate;


    /**
     *
     * @methodtype constructor
     */
    public Location(@NotNull Coordinate coordinate) {
        this.coordinate = coordinate;
        incWriteCount();
    }

    @Override
    public String getIdAsString() {
        return null;
    }

    @Override
    public void readFrom(ResultSet rset) throws SQLException {
        coordinate.readFrom(rset);
    }

    @Override
    public void writeOn(ResultSet rset) throws SQLException {
        coordinate.writeOn(rset);
    }

    @Override
    public void writeId(PreparedStatement stmt, int pos) throws SQLException {
        incWriteCount();
    }
}
