package main.java.com.algotrader.dataclasses;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Bar class for market data
 */
public class Bar implements MarketData {

    @JsonProperty("S")
    private String symbol;
    
    @JsonProperty("c")
    private double close;

    @JsonProperty("h")
    private double high;

    @JsonProperty("l")
    private double low; 

    @JsonProperty("o")
    private double open;

    @JsonProperty("v")
    private long volume;

    @JsonProperty("vw")
    private double vwap; // Volume Weighted Average Price

    @JsonProperty("t")
    private String timestamp;

    @JsonProperty("n")
    private int numTransactions; 

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