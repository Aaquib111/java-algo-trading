package main.java.com.algotrader.dataclasses;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Orderbooks {
    private Map<String, Orderbook> orderbooks;

    // Getters and setters
    public Map<String, Orderbook> getOrderbooks() {
        return orderbooks;
    }

    public void setOrderbooks(Map<String, Orderbook> orderbooks) {
        this.orderbooks = orderbooks;
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

class Orderbook {
    @JsonProperty("a")
    private List<Order> asks; // Asks
    @JsonProperty("b")
    private List<Order> bids; // Bids
    @JsonProperty("t")
    private String timestamp; // Timestamp

    // Getters and setters
    public List<Order> getA() {
        return this.asks;
    }

    public void setAsks(List<Order> asks) {
        this.asks = asks;
    }

    public List<Order> getBids() {
        return this.bids;
    }

    public void setBids(List<Order> bids) {
        this.bids = bids;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setT(String timestamp) {
        this.timestamp = timestamp;
    }
}

class Order {
    @JsonProperty("p")
    private double price; // Price
    @JsonProperty("s")
    private double size; // Size

    // Getters and setters
    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }
}