package service;

import entity.Order;
import entity.Trade;
import enums.Status;
import enums.Stock;
import enums.Type;
import exception.InvalidOrderException;
import exception.OrderNotFoundException;
import exception.UserPermissionDeniedException;
import strategy.MatchingStrategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Collectors;

/**
 * Created by dhananjay.rathod at 27/04/25.
 */

public class OrderBook {

    private final Stock stock;
    private final Map<String, Order> ordersMap;
    private final NavigableMap<Double, List<Order>> buyOrdersByPriceMap;
    private final NavigableMap<Double, List<Order>> sellOrdersByPriceMap;
    private final List<Trade> tradeHistory;
    private final MatchingStrategy matchingStrategy;


    public OrderBook(Stock stock, MatchingStrategy matchingStrategy) {
        this.stock = stock;
        this.matchingStrategy = matchingStrategy;
        this.ordersMap = new ConcurrentHashMap<>();
        this.tradeHistory = Collections.synchronizedList(new ArrayList<>());

        this.buyOrdersByPriceMap = new ConcurrentSkipListMap<>(Comparator.reverseOrder());
        this.sellOrdersByPriceMap = new ConcurrentSkipListMap<>();
    }

    public void placeOrder(Order order) {

        if (order.getQuantity() <= 0 || order.getPrice() <= 0) {
            throw new InvalidOrderException("Order must have positive price and quantity");
        }
        ordersMap.put(order.getOrderId(), order);
        if (order.getOrderType() == Type.BUY) {
            List<Order> buyList = buyOrdersByPriceMap.computeIfAbsent(order.getPrice(),
                    k -> Collections.synchronizedList(new LinkedList<>()));
            buyList.add(order);
        } else {
            List<Order> sellList = sellOrdersByPriceMap.computeIfAbsent(order.getPrice(),
                    k -> Collections.synchronizedList(new LinkedList<>()));
            sellList.add(order);
        }
        matchOrders();
    }

    public void cancelOrder(String orderId, String userId) {
        Order order = ordersMap.get(orderId);
        if (order == null) {
            throw new OrderNotFoundException("Order not found: " + orderId);
        }
        if (!order.getUserId().equals(userId)) {
            throw new UserPermissionDeniedException("User not allowed to cancel this order.");
        }

        NavigableMap<Double, List<Order>> priceMap = order.getOrderType() == Type.BUY
                ? buyOrdersByPriceMap : sellOrdersByPriceMap;

        List<Order> ordersAtPrice = priceMap.get(order.getPrice());
        if (ordersAtPrice != null) {
            synchronized (ordersAtPrice) {
                ordersAtPrice.remove(order);
                if (ordersAtPrice.isEmpty()) {
                    priceMap.remove(order.getPrice());
                }
            }
        }
        ordersMap.remove(orderId);
    }

    public void modifyOrder(String orderId, String userId, double newPrice, int newQuantity) {
        Order order = ordersMap.get(orderId);

        if (order == null) {
            throw new OrderNotFoundException(orderId);
        }

        if (!order.getUserId().equals(userId) || (order.getStatus() != Status.ACCEPTED
                && order.getStatus() != Status.PARTIAL)) {
            throw new UserPermissionDeniedException(userId);
        }

        if (newQuantity <= 0 || newPrice <= 0) {
            throw new InvalidOrderException("Quantity and Price must be greater than 0");
        }

        NavigableMap<Double, List<Order>> priceMap
                = order.getOrderType() == Type.BUY ? buyOrdersByPriceMap : sellOrdersByPriceMap;

        List<Order> ordersAtPrice = priceMap.get(order.getPrice());
        if (ordersAtPrice != null) {
            synchronized (ordersAtPrice) {
                ordersAtPrice.remove(order);
                if (ordersAtPrice.isEmpty()) {
                    priceMap.remove(order.getPrice());
                }
            }
        }

        order.setPrice(newPrice);
        order.setQuantity(newQuantity);

        List<Order> newPriceList = priceMap.computeIfAbsent(newPrice, k ->
                Collections.synchronizedList(new LinkedList<>()));
        synchronized (newPriceList) {
            newPriceList.add(order);
        }

        matchOrders();
    }

    private void matchOrders() {
        List<Trade> tradeList = matchingStrategy.match(buyOrdersByPriceMap, sellOrdersByPriceMap, stock);
        synchronized (tradeHistory) {
            tradeHistory.addAll(tradeList);
        }
    }

    public Order getOrder(String orderId) {
        Order order = ordersMap.get(orderId);
        if(order == null) {
            throw new OrderNotFoundException("Order not found: " + orderId);
        }
        return order;
    }

    public Map<String, Order> getOrdersMap() {
        return Collections.unmodifiableMap(ordersMap);
    }

    public List<Trade> getTradeHistory() {
        return Collections.unmodifiableList(tradeHistory);
    }

    public List<Order> getUnexecutedOrders() {
        return ordersMap.values()
                .stream()
                .filter(order -> order.getStatus() == Status.ACCEPTED || order.getStatus() == Status.PARTIAL)
                .collect(Collectors.toList());
    }

    public List<Order> getOrdersByUser(String userId) {
        return ordersMap.values()
                .stream()
                .filter(order -> order.getUserId().equals(userId))
                .collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
    }
}
