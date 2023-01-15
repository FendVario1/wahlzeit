package org.wahlzeit.model;

import org.wahlzeit.annotations.PatternInstance;
import org.wahlzeit.exceptions.WahlzeitException;
import org.wahlzeit.exceptions.WahlzeitIllegalAssertStateException;

import java.sql.ResultSet;
import java.sql.SQLException;

@PatternInstance(
        patternName = "AbstractFactory",
        participants = {
                "ConcreteProduct"
        }
)
public class AnimalPhoto extends Photo {

    protected Animal animal;

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

    public AnimalPhoto(ResultSet rset) throws SQLException, WahlzeitException {
        readFrom(rset);
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public Animal getAnimal() {
        return animal;
    }


    public void readFrom(ResultSet rset) throws SQLException, WahlzeitException {
        try {
            super.readFrom(rset);
        } catch (WahlzeitIllegalAssertStateException e) {
            AnimalLog.logThrowable(e);
            throw new WahlzeitException(e);
        }
    }

    public void writeOn(ResultSet rset) throws SQLException, WahlzeitException {
        try {
            super.writeOn(rset);
        } catch (WahlzeitIllegalAssertStateException e) {
            AnimalLog.logThrowable(e);
            throw new WahlzeitException(e);
        }
    }
}
