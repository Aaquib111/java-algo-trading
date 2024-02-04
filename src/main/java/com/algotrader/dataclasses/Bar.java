package main.java.com.algotrader.dataclasses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Bar implements MarketData {

    @JsonProperty("S")
    private String symbol;
    
    @JsonProperty("c")
    private double close; // Close

    @JsonProperty("h")
    private double high; // High

    @JsonProperty("l")
    private double low; // Low

    @JsonProperty("o")
    private double open; // Open

    @JsonProperty("v")
    private long volume; // Volume

    @JsonProperty("vw")
    private double vwap; // Volume Weighted Average Price

    @JsonProperty("t")
    private String timestamp; // Timestamp

    @JsonProperty("n")
    private int numTransactions; // Number of transactions

    public Bar() {
    }

    public Bar(double close, double high, double low, double open, long volume, double vwap, String timestamp,
            int numTransactions) {
        this.close = close;
        this.high = high;
        this.low = low;
        this.open = open;
        this.volume = volume;
        this.vwap = vwap;
        this.timestamp = timestamp;
        this.numTransactions = numTransactions;
    }

    // Getters
    public double getClose() {
        return close;
    }

    public double getHigh() {
        return high;
    }

    public double getLow() {
        return low;
    }

    public double getOpen() {
        return open;
    }

    public long getVolume() {
        return volume;
    }

    public double getVwap() {
        return vwap;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public int getNumTransactions() {
        return numTransactions;
    }
}