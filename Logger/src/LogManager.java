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

     private Logger configureLoggerFromConfig(String loggerName, Logger logger) {
        try (FileReader reader = new FileReader("log-config.properties")) {
            Properties properties = new Properties();
            properties.load(reader);

            String appenders = properties.getProperty(loggerName + ".appenders");

            if (appenders != null) {
                for (String appenderName : appenders.split(",")) {
                    switch (appenderName.trim()) {
                        case "ConsoleAppender":
                            logger.addAppender(new ConsoleAppender());
                            break;
                        case "FileAppender":
                            String fileName = properties.getProperty(loggerName + ".FileAppender.fileName");
                            if (fileName != null) {
                                logger.addAppender(new FileAppender(fileName));
                            }
                            break;
                        case "RollingFileAppender":
                            String rollingFileName = properties.getProperty(loggerName + ".RollingFileAppender.fileName");
                            String maxFileSizeStr = properties.getProperty(loggerName + ".RollingFileAppender.maxFileSize");
                            String maxBackupFilesStr = properties.getProperty(loggerName + ".RollingFileAppender.maxBackupFiles");

                            if (rollingFileName != null && maxFileSizeStr != null && maxBackupFilesStr != null) {
                                long maxFileSize = Long.parseLong(maxFileSizeStr);
                                int maxBackupFiles = Integer.parseInt(maxBackupFilesStr);
                                logger.addAppender(new RollingFileAppender(rollingFileName, maxFileSize, maxBackupFiles));
                            }
                            break;
                        default:
                            System.out.println("Unknown appender: " + appenderName);
                            break;
                    }
                }
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return logger;
    }

    public Logger getLogger(String name) {
        try (FileReader reader = new FileReader("log-config.properties")) {
            Properties properties = new Properties();
            properties.load(reader);
            String level = properties.getProperty(name + ".rootLogger");

            return loggerMap.computeIfAbsent(name, k -> {
                LogLevel logLevel = LogLevel.DEBUG; // Default level
                if (level != null) {
                    logLevel = LogLevel.valueOf(level);
                }
                Logger logger = new Logger(name, logLevel);
                configureLoggerFromConfig(name, logger);
                return logger;
            });
        } catch (IOException ioException) {
            ioException.printStackTrace();
            throw new IllegalStateException("Failed to load logger configuration for: " + name, ioException);
        }
    }
}
