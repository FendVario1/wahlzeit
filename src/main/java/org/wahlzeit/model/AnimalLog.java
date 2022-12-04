package org.wahlzeit.model;

import org.wahlzeit.services.Log;

/**
 * Logging class for logging animal photo related messages.
 *
 */
 public class AnimalLog extends Log {

    public static void logAnimalInfo(String s) {
        Log.logInfo("al", s);
    }

    public static void logThrowable(Throwable t) {
        Throwable cause = t.getCause();
        if (cause != null) {
            logThrowable(cause);
        }

        StringBuffer sb = createLogEntry("al");
        addLogType(sb, "exception");
        addThrowable(sb, t);
        addStacktrace(sb, t);
        log(sb);
    }
}
