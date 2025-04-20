/**
 * Created by dhananjay.rathod at 21/04/25.
 */

public class RateLimiterConfig {

    private final String type;
    private final int maxLimit;
    private final long windowSizeInMillis;

    public RateLimiterConfig(String type, int maxLimit, long windowSizeInMillis) {
        this.type = type;
        this.maxLimit = maxLimit;
        this.windowSizeInMillis = windowSizeInMillis;
    }

    public String getType() {
        return type;
    }

    public int getMaxLimit() {
        return maxLimit;
    }
    public long getWindowSizeInMillis() {
        return windowSizeInMillis;
    }
}
