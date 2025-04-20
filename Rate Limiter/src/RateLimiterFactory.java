/**
 * Created by dhananjay.rathod at 21/04/25.
 */

public class RateLimiterFactory {
    public static RateLimiter createRateLimiter(String type, int maxLimit, long windowSizeInMillis) {
        switch (type) {
            case "fixed":
                return new FixedWindowRateLimiter(maxLimit, windowSizeInMillis);
            case "sliding":
                return new SlidingWindowRateLimiter(maxLimit, windowSizeInMillis);
            case "token":
                return new TokenBucketRateLimiter(maxLimit, windowSizeInMillis, maxLimit / 2);
            case "leaky":
                return new LeakyBucketRateLimiter(maxLimit, windowSizeInMillis / maxLimit);
            default:
                throw new IllegalArgumentException("Unknown rate limiter type: " + type);
        }
    }
}
