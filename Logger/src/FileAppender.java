import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by dhananjay.rathod at 20/04/25.
 */

public class FileAppender implements Appender {

    private FileWriter fileWriter;

    public FileAppender(String fileName) throws IOException {
        this.fileWriter = new FileWriter(fileName, true);
    }

    @Override
    public void append(String message) {
        try {
            fileWriter.write(message + "\n");
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
