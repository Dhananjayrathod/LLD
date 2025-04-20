import java.util.HashMap;
import java.util.Map;

/**
 * Created by dhananjay.rathod at 20/04/25.
 */

public class LogManager {

    private static LogManager logManager;
    private Map<String, Logger> loggerMap = new HashMap<>();

    private LogManager() {
    }

    public static LogManager getLogManager() {
        if (logManager == null) {
            logManager = new LogManager();
        }
        return logManager;
    }

    public Logger getLogger(String name) {
        return loggerMap.computeIfAbsent(name, k -> new Logger(name, LogLevel.DEBUG));
    }
}
