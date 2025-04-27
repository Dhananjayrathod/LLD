package exception;

/**
 * Created by dhananjay.rathod at 27/04/25.
 */

public class InvalidOrderException extends RuntimeException {

    public InvalidOrderException(String reason) {
        super("Invalid order: " + reason);
    }

}
