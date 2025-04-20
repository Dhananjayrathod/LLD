import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        LogManager logManager = LogManager.getLogManager();
        Logger logger = logManager.getLogger("MyLogger");

        logger.addAppender(new ConsoleAppender());
        try {
            logger.addAppender(new FileAppender("application.log"));
            logger.addAppender(new RollingFileAppender("rolling.log", 128 * 128, 5));
        } catch (IOException e) {
            e.printStackTrace();
        }

        logger.debug("This is a debug message");
        logger.info("This is an info message");
        logger.warn("This is a warning message");
        logger.error("This is an error message");
        logger.fatal("This is a fatal message");

        Runnable loggingTask = () -> {
            for (int i = 0; i  < 10; i++) {
                logger.info(" - Message: " + i);
            }
        };

        int threadCount = 5;
        Thread[] threads = new Thread[threadCount];
        for(int i = 0; i < threadCount; i++) {
            threads[i] = new Thread(loggingTask);
            threads[i].start();
        }

        // Block to test Rolling File Appender
        TestLogger testLogger = new TestLogger();
        for (int i = 0; i < 10000; i++) {
            testLogger.printLogs("This is a test log message: " + i);
        }

    }

}