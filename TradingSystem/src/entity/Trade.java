package entity;

import enums.Stock;

import java.time.LocalDateTime;

/**
 * Created by dhananjay.rathod at 27/04/25.
 */

public class Trade {

    private final String id;
    private final String buyerOrderId;
    private final String sellerOrderId;
    private final Stock stock;
    private final int quantity;
    private final double price;
    private final LocalDateTime tradeTime;

    public Trade(String id, String buyerOrderId, String sellerOrderId,
                 Stock stock, int quantity, double price, LocalDateTime tradeTime) {
        this.id = id;
        this.buyerOrderId = buyerOrderId;
        this.sellerOrderId = sellerOrderId;
        this.stock = stock;
        this.quantity = quantity;
        this.price = price;
        this.tradeTime = tradeTime;
    }

    public String getId() {
        return id;
    }

    public String getBuyerOrderId() {
        return buyerOrderId;
    }

    public String getSellerOrderId() {
        return sellerOrderId;
    }

    public Stock getStock() {
        return stock;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public LocalDateTime getTradeTime() {
        return tradeTime;
    }

    @Override
    public String toString() {
        return "Trade{" +
                "id=" + id +
                ", buyerOrderId=" + buyerOrderId +
                ", sellerOrderId=" + sellerOrderId +
                ", stock=" + stock +
                ", quantity=" + quantity +
                ", price=" + price +
                ", tradeTime=" + tradeTime +
                '}';
    }
}
