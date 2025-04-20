/**
 * Created by dhananjay.rathod at 20/04/25.
 */

public interface RateLimiter {

    boolean isAllowed(String key);
}
