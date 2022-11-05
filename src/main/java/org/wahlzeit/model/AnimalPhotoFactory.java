package org.wahlzeit.model;

import org.wahlzeit.services.SysLog;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AnimalPhotoFactory extends PhotoFactory {
    private static boolean initialized = false;

    /**
     * Public singleton access method.
     */
    public static synchronized AnimalPhotoFactory getInstance() {
        if (!initialized) {
            SysLog.logSysInfo("setting specific AnimalPhotoFactory");
            PhotoFactory.setInstance(new AnimalPhotoFactory());
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
    public AnimalPhoto createPhoto(ResultSet rs) throws SQLException {
        return new AnimalPhoto(rs);
    }
}
