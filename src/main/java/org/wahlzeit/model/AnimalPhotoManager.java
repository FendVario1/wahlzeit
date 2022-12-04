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
            try {
                PhotoManager.setInstance(new AnimalPhotoManager());
            } catch (IllegalStateException e) {
                SysLog.logThrowable(e);
                System.exit(-1);
            }
            initialized = true;
        }

        return (AnimalPhotoManager) PhotoManager.getInstance();
    }

    public static void initialize() {
        getInstance();
    }
}
