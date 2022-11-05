package org.wahlzeit.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AnimalPhoto extends Photo {
    public static final String speciesLabel = "animal_species";
    protected String species;

    public AnimalPhoto() {
        super();
    }

    public AnimalPhoto(Location location) {
        super(location);
    }

    public AnimalPhoto(PhotoId myId) {
        super(myId);
    }

    public AnimalPhoto(PhotoId myId, Location location) {
        super(myId, location);
    }

    public AnimalPhoto(ResultSet rset) throws SQLException {
        readFrom(rset);
    }

    public void readFrom(ResultSet rset) throws SQLException {
        species = rset.getString(speciesLabel);
        super.readFrom(rset);
    }

    public void writeOn(ResultSet rset) throws SQLException {
        rset.updateString(speciesLabel, species);
        super.writeOn(rset);
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
        incWriteCount();
    }
}
