package exception;

/**
 * Created by dhananjay.rathod at 27/04/25.
 */

public class UserPermissionDeniedException extends RuntimeException {

    public UserPermissionDeniedException(String userId) {
        super("User with ID [" + userId + "] is not authorized to perform this action.");
    }
}
