package org.wahlzeit.model;

import org.wahlzeit.services.SysLog;

public class AnimalPhotoManager extends PhotoManager {

    public AnimalPhotoManager() {
        super();
    }
    private static boolean initialized = false;

    /**
     * Public singleton access method.
     */
    public static synchronized AnimalPhotoManager getInstance() {
        if (!initialized) {
            SysLog.logSysInfo("setting specific AnimalPhotoFactory");
            PhotoManager.setInstance(new AnimalPhotoManager());
            initialized = true;
        }

        return (AnimalPhotoManager) PhotoManager.getInstance();
    }

    public static void initialize() {
        getInstance();
    }
}
