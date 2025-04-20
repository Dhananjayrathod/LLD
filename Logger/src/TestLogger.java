import java.io.IOException;

/**
 * Created by dhananjay.rathod at 20/04/25.
 */

public class TestLogger {

    LogManager logManager = LogManager.getLogManager();
    Logger logger = logManager.getLogger("MyLogger");

    public void printLogs(String message) {
        logger.info(message);
    }
}
