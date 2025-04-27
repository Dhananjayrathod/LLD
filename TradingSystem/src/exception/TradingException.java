package exception;

/**
 * Created by dhananjay.rathod at 27/04/25.
 */

public class TradingException extends RuntimeException {

    public TradingException(String message) {
        super(message);
    }

    TradingException(String message, Throwable cause) {
        super(message, cause);
    }
}
