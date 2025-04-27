package strategy;

import entity.Order;
import entity.Trade;
import enums.Stock;

import java.util.List;
import java.util.NavigableMap;

/**
 * Created by dhananjay.rathod at 27/04/25.
 */

public interface MatchingStrategy {

    List<Trade> match(
            NavigableMap<Double, List<Order>> buyOrdersByPriceMap,
            NavigableMap<Double, List<Order>> sellOrdersByPriceMap,
            Stock stock
    );

}
