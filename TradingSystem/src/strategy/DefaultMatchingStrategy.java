package strategy;

import entity.Order;
import entity.Trade;
import enums.Status;
import enums.Stock;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;
import java.util.UUID;

/**
 * Created by dhananjay.rathod at 27/04/25.
 */

public class DefaultMatchingStrategy implements MatchingStrategy {

    @Override
    public List<Trade> match(
            NavigableMap<Double, List<Order>> buyOrdersByPriceMap,
            NavigableMap<Double, List<Order>> sellOrdersByPriceMap,
            Stock stock) {

        List<Trade> trades = new ArrayList<>();

        while (!buyOrdersByPriceMap.isEmpty() && !sellOrdersByPriceMap.isEmpty()) {

            double highestBuyPrice = buyOrdersByPriceMap.firstKey();
            double lowestSellPrice = sellOrdersByPriceMap.firstKey();

            if (highestBuyPrice >= lowestSellPrice) {
                List<Order> buyOrders = buyOrdersByPriceMap.get(highestBuyPrice);
                List<Order> sellOrders = sellOrdersByPriceMap.get(lowestSellPrice);

                Order buyOrder = buyOrders.get(0);
                Order sellOrder = sellOrders.get(0);

                int tradeQuantity = Math.min(buyOrder.getRemainingQuantity(), sellOrder.getRemainingQuantity());

                Trade trade = new Trade(
                        UUID.randomUUID().toString(),
                        buyOrder.getOrderId(),
                        sellOrder.getOrderId(),
                        stock,
                        tradeQuantity,
                        lowestSellPrice,
                        LocalDateTime.now()
                );

                trades.add(trade);

                buyOrder.setRemainingQuantity(buyOrder.getRemainingQuantity() - tradeQuantity);
                sellOrder.setRemainingQuantity(sellOrder.getRemainingQuantity() - tradeQuantity);

                if (buyOrder.getRemainingQuantity() == 0) {
                    buyOrder.setStatus(Status.EXECUTED);
                    buyOrders.remove(buyOrder);
                    if (buyOrders.isEmpty()) {
                        buyOrdersByPriceMap.remove(highestBuyPrice);
                    }
                } else {
                    buyOrder.setStatus(Status.PARTIAL);
                }

                if (sellOrder.getRemainingQuantity() == 0) {
                    buyOrder.setStatus(Status.EXECUTED);
                    sellOrders.remove(sellOrder);
                    if (sellOrders.isEmpty()) {
                        sellOrdersByPriceMap.remove(lowestSellPrice);
                    }
                } else {
                    sellOrder.setStatus(Status.PARTIAL);
                }

            } else {
                System.out.println("No matching orders found. Highest Buy Price: "
                        + highestBuyPrice + ", Lowest Sell Price: " + lowestSellPrice);
                break;
            }
        }
        return trades;
    }
}
