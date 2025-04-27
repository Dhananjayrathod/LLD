import entity.Order;
import enums.Stock;
import enums.Type;
import service.OrderBookManager;
import strategy.DefaultMatchingStrategy;

import java.util.List;
import java.util.Map;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    private static OrderBookManager orderBookManager;
    private static final Stock stock = Stock.PHONE_PE;
    private static final String userId = "user_123";

    public static void main(String[] args) {

        DefaultMatchingStrategy matchingStrategy = new DefaultMatchingStrategy();
        orderBookManager = new OrderBookManager(matchingStrategy);

        placeModifyCancelOrder();
        executeTradeOperations();
        displayUnexecutedOrders();
        displayOrdersForUser("user_buy");

    }

    private static void placeModifyCancelOrder() {
        // Place initial order
        String orderId = "order_001";
        Order order = new Order(orderId, userId, Type.BUY, stock, 90, 500.5);
        orderBookManager.placeOrder(order);
        System.out.println("Order placed: " + order);

        // Modify the order
        orderBookManager.modifyOrder(stock, userId, orderId, 100, 511.0);
        Order modifiedOrder = orderBookManager.getOrder(stock, orderId);
        System.out.println("Order modified: " + modifiedOrder);

        // Cancel the order
        orderBookManager.cancelOrder(stock, orderId, userId);
        try {
            orderBookManager.getOrder(stock, orderId);
        } catch (Exception e) {
            System.out.println("Order " + orderId + " cancelled successfully");
        }
    }

    private static void executeTradeOperations() {
        // Place buy orders
        Order buyOrder1 = new Order("buy_001", "user_buy", Type.BUY, stock, 10, 505.0);
        orderBookManager.placeOrder(buyOrder1);
        System.out.println("Buy order placed: " + buyOrder1);

        Order buyOrder2 = new Order("buy_002", "user_buy", Type.BUY, stock, 10, 500.0);
        orderBookManager.placeOrder(buyOrder2);
        System.out.println("Buy order placed: " + buyOrder2);

        // Display current orders
        Map<String, Order> orders = orderBookManager.getOrdersForStock(stock);
        System.out.println("----------------------------------");
        System.out.println("Orders for stock: " + stock);
        orders.values().forEach(System.out::println);
        System.out.println("----------------------------------");

        // Place sell orders
        Order sellOrder1 = new Order("sell_001", "user_sell", Type.SELL, stock, 25, 495.0);
        orderBookManager.placeOrder(sellOrder1);
        System.out.println("Sell order placed: " + sellOrder1);

        Order sellOrder2 = new Order("sell_002", "user_sell2", Type.SELL, stock, 50, 595.0);
        orderBookManager.placeOrder(sellOrder2);
        System.out.println("Sell order placed: " + sellOrder2);

        // Display executed trades
        orderBookManager.getOrderBook(stock)
                .getTradeHistory()
                .forEach(trade -> System.out.println("Trade executed: " + trade));

        // Display orders after trade execution
        Map<String, Order> ordersAfterTrade = orderBookManager.getOrdersForStock(stock);
        System.out.println("----------------------------------");
        System.out.println("Orders after trade executed for stock: " + stock);
        ordersAfterTrade.values().forEach(System.out::println);
        System.out.println("----------------------------------");
    }

    private static void displayUnexecutedOrders() {
        // Display unexecuted orders for the given stock
        List<Order> unexecutedOrders = orderBookManager.getUnexecutedOrdersForStock(stock);
        System.out.println("------- Unexecuted orders for stock: " + stock);
        unexecutedOrders.forEach(System.out::println);
        System.out.println("----------------------------------");
    }

    private static void displayOrdersForUser(String userId) {
        // Display orders for the given user
        List<Order> userOrders = orderBookManager.getOrdersForUser(userId);
        System.out.println("------- Orders for user: " + userId);
        userOrders.forEach(System.out::println);
        System.out.println("----------------------------------");
    }


}