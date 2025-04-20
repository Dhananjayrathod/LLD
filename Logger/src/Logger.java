import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by dhananjay.rathod at 20/04/25.
 */

public class Logger {

    private final String name;
    private final LogLevel level;
    private final List<Appender> appenderList = new ArrayList<>();
    private final BlockingQueue<String> logQueue = new LinkedBlockingQueue<>();
    private final Thread loggerThread;


    public Logger(String name, LogLevel level) {
        this.name = name;
        this.level = level;

        loggerThread = new Thread(() -> {
            while (true) {
                try {
                    String logMessage = logQueue.take();
                    for (Appender appender : appenderList) {
                        appender.append(logMessage);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
        loggerThread.setDaemon(true);
        loggerThread.start();

    }

    private void log(LogLevel level, String message) {
        if (this.level.ordinal() <= level.ordinal()) {
            String formattedMessage = formatMessage(level, message);
            logQueue.offer(formattedMessage);
        }
    }

    public void debug(String message) {
        log(LogLevel.DEBUG, message);
    }

    public void info(String message) {
        log(LogLevel.INFO, message);
    }

    public void warn(String message) {
        log(LogLevel.WARN, message);
    }

    public void error(String message) {
        log(LogLevel.ERROR, message);
    }

    public void fatal(String message) {
        log(LogLevel.FATAL, message);
    }

    private String formatMessage(LogLevel logLevel, String message) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        String fileName = " ";
        if (stackTrace.length > 4) {
            fileName = stackTrace[4].getFileName();
        }
        return String.format("[%s] - [%s] - [%s]- [%s] - %s", new Date(), logLevel,
                Thread.currentThread().getName(), fileName, message);
    }

    public void addAppender(Appender appender) {
        appenderList.add(appender);
    }
}
