import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by dhananjay.rathod at 20/04/25.
 */

public class RollingFileAppender implements Appender {

    private final String fileName;
    private final long maxFileSize;
    private final int maxBackupFiles;
    private FileWriter writer;

    public RollingFileAppender(String fileName, long maxFileSize,
                               int maxBackupFiles) throws IOException {
        this.fileName = fileName;
        this.maxFileSize = maxFileSize;
        this.maxBackupFiles = maxBackupFiles;
        this.writer = new FileWriter(fileName, true);
    }

    @Override
    public void append(String message) {
        try {
            File logFile = new File(fileName);
            if (logFile.length() >= maxFileSize) {
                rotateFiles();
            }
            writer.write(message + "\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void rotateFiles() throws IOException {
        writer.close();
        for (int i = maxBackupFiles; i > 1; i--) {
            File oldFile = new File(fileName + "." +  (i -1));
            File newFile = new File(fileName + "." + (i));
            if (oldFile.exists()) {
                oldFile.renameTo(newFile);
            }
        }

        File currentFile = new File(fileName);
        File backupFile = new File(fileName + ".1");
        if (currentFile.exists()) {
            currentFile.renameTo(backupFile);
        }

        writer = new FileWriter(fileName, true);
    }
}
