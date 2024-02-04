package main.java.com.algotrader.dataclasses;

import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Orderbooks implements MarketData {
    private Map<String, Orderbook> orderbooks;

    // Getters and setters
    public Map<String, Orderbook> getOrderbooks() {
        return orderbooks;
    }

    public void setOrderbooks(Map<String, Orderbook> orderbooks) {
        this.orderbooks = orderbooks;
    }

    public static ObjectMapper getObjectMapper(){
        return new ObjectMapper();
    }

    public String toString(){
        // Format in a pretty table for each stock
        StringBuilder sb = new StringBuilder();
        for (String security : orderbooks.keySet()) {
            sb.append(security + "\n");
            sb.append("Timestamp: " + orderbooks.get(security).getTimestamp() + "\n");
            sb.append("AskPrice\tAskSize\n");
            for (Order ask : orderbooks.get(security).getA()) {
                sb.append(ask.getPrice() + "\t" + ask.getSize() + "\n");
            }
            sb.append("\n\nBidPrice\tBidSize\n");
            for (Order bid : orderbooks.get(security).getBids()) {
                sb.append(bid.getPrice() + "\t" + bid.getSize() + "\n");
            }
        }
        return sb.toString();
    }
}