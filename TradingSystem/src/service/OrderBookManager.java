package service;

import entity.Order;
import enums.Stock;
import exception.TradingException;
import strategy.MatchingStrategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by dhananjay.rathod at 27/04/25.
 */

public class OrderBookManager {

    private final Map<Stock, OrderBook> orderBooks;
    private final MatchingStrategy matchingStrategy;

    public OrderBookManager(MatchingStrategy matchingStrategy) {
        this.orderBooks = new ConcurrentHashMap<>();
        this.matchingStrategy = matchingStrategy;
    }

    public OrderBook getOrderBook(Stock stock) {
        return orderBooks.computeIfAbsent(stock, k -> new OrderBook(stock, matchingStrategy));
    }

    public void placeOrder(Order order) {
        OrderBook orderBook = getOrderBook(order.getStock());
        orderBook.placeOrder(order);
    }

    public void cancelOrder(Stock stock, String orderId, String userId) {
        OrderBook orderBook = getOrderBook(stock);
        if (orderBook == null) {
            throw new TradingException("Order book not found for stock: " + stock);
        }
        orderBook.cancelOrder(orderId, userId);
    }

    public void modifyOrder(Stock stock, String userId, String orderId, int newQuantity, double newPrice) {
        OrderBook orderBook = getOrderBook(stock);
        if (orderBook == null) {
            throw new TradingException("Order book not found for stock: " + stock);
        }
        orderBook.modifyOrder(orderId, userId, newPrice, newQuantity);
    }

    public Order getOrder (Stock stock, String orderId) {
        OrderBook orderBook = getOrderBook(stock);
        if (orderBook == null) {
            throw new TradingException("Order book not found for stock: " + stock);
        }
        return orderBook.getOrder(orderId);
    }

    public Map<String, Order> getOrdersForStock(Stock stock) {
        OrderBook orderBook = getOrderBook(stock);
        if (orderBook == null) {
            throw new TradingException("Order book not found for stock: " + stock);
        }
        return orderBook.getOrdersMap();
    }

    public List<Order> getUnexecutedOrdersForStock(Stock stock) {
        OrderBook orderBook = getOrderBook(stock);
        if (orderBook == null) {
            throw new TradingException("Order book not found for stock: " + stock);
        }
        return orderBook.getUnexecutedOrders();
    }

    public List<Order> getOrdersForUser(String userId) {
        List<Order> userOrders = new ArrayList<>();
        for (OrderBook orderBook : orderBooks.values()) {
            userOrders.addAll(orderBook.getOrdersByUser(userId));
        }
        return Collections.unmodifiableList(userOrders);
    }

}
