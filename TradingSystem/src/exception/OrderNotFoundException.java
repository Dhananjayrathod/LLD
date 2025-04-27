package exception;

/**
 * Created by dhananjay.rathod at 27/04/25.
 */

public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(String orderId) {
        super("Order with ID [" + orderId + "] not found.");
    }
}
