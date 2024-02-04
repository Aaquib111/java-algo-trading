package main.java.com.algotrader.dataclasses;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Orderbook implements MarketData {
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