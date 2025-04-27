package entity;

import enums.Status;
import enums.Stock;
import enums.Type;

import java.time.LocalDateTime;

/**
 * Created by dhananjay.rathod at 27/04/25.
 */

public class Order {

    private final String orderId;
    private final String userId;
    private final Type orderType;
    private final Stock stock;
    private int quantity;
    private int remainingQuantity;
    private double price;
    private final LocalDateTime orderTime;
    private Status status;


    public Order(String orderId, String userId, Type orderType, Stock stock, int quantity, double price) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderType = orderType;
        this.stock = stock;
        this.quantity = quantity;
        this.remainingQuantity = quantity;
        this.price = price;
        this.orderTime = LocalDateTime.now();
        this.status = Status.ACCEPTED;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getUserId() {
        return userId;
    }

    public Type getOrderType() {
        return orderType;
    }

    public Stock getStock() {
        return stock;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.remainingQuantity = quantity;
    }

    public int getRemainingQuantity() {
        return remainingQuantity;
    }

    public void setRemainingQuantity(int remainingQuantity) {
        this.remainingQuantity = remainingQuantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", userId='" + userId + '\'' +
                ", orderType=" + orderType +
                ", stock=" + stock +
                ", quantity=" + quantity +
                ", remainingQuantity=" + remainingQuantity +
                ", price=" + price +
                ", orderTime=" + orderTime +
                ", status=" + status +
                '}';
    }
}
