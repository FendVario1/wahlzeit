package org.wahlzeit.model;

import org.wahlzeit.annotations.PatternInstance;
import org.wahlzeit.exceptions.WahlzeitException;
import org.wahlzeit.exceptions.WahlzeitIllegalAssertStateException;
import org.wahlzeit.services.SysLog;

import java.sql.ResultSet;
import java.sql.SQLException;

@PatternInstance(
        patternName = "AbstractFactory",
        participants = {
                "ConcreteFactory"
        }
)
public class AnimalPhotoFactory extends PhotoFactory {
    private static boolean initialized = false;

    /**
     * Public singleton access method.
     */
    public static synchronized AnimalPhotoFactory getInstance() {
        if (!initialized) {
            SysLog.logSysInfo("setting specific AnimalPhotoFactory");
            try {
                PhotoFactory.setInstance(new AnimalPhotoFactory());
            } catch (IllegalStateException e) {
                SysLog.logThrowable(e);
                System.exit(-1);
            }
            initialized = true;
        }

        return (AnimalPhotoFactory) PhotoFactory.getInstance();
    }

    public static void initialize() {
        getInstance();
    }

    /**
     *
     */
    protected AnimalPhotoFactory() {
        // do nothing
    }

    /**
     * @methodtype factory
     */
    public AnimalPhoto createPhoto() {
        return new AnimalPhoto();
    }

    /**
     *
     */
    public AnimalPhoto createPhoto(PhotoId id) {
        return new AnimalPhoto(id);
    }

    /**
     *
     */
    public AnimalPhoto createPhoto(ResultSet rs) throws SQLException, WahlzeitException {
        try {
            return new AnimalPhoto(rs);
        } catch (WahlzeitIllegalAssertStateException e) {
            AnimalLog.logThrowable(e);
            throw new WahlzeitException(e);
        }
    }
}
