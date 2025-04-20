/**
 * Created by dhananjay.rathod at 20/04/25.
 */

public class ConsoleAppender implements Appender {

    @Override
    public void append(String message) {
        System.out.println(message);
    }
}
