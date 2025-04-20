//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        RateLimiterManager rateLimiterManager = RateLimiterManager.getInstance();

        for(int i = 0; i < 15; i++) {
            System.out.println(rateLimiterManager.isAllowed("client1"));
        }

        rateLimiterManager.updateRateLimiterConfig("sliding", 5, 2000);

        for(int i = 0; i < 15; i++) {
            System.out.println(rateLimiterManager.isAllowed("client1"));
        }
    }
}