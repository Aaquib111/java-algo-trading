package main.java.com.algotrader.dataclasses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Quote implements MarketData {

    @JsonProperty("S")
    private String symbol;

    @JsonProperty("ap")
    private double askPrice;

    @JsonProperty("as")
    private int askSize;

    @JsonProperty("ax")
    private String askExchange;

    @JsonProperty("bp")
    private double bidPrice;

    @JsonProperty("bs")
    private int bidSize;

    @JsonProperty("bx")
    private String bidExchange;

    @JsonProperty("t")
    private String timestamp;

    public Quote() {

    }

    // Setters

    public void setAskPrice(double askPrice) {
        this.askPrice = askPrice;
    }

    public void setAskSize(int askSize) {
        this.askSize = askSize;
    }

    public void setAskExchange(String askExchange) {
        this.askExchange = askExchange;
    }

    public void setBidPrice(double bidPrice) {
        this.bidPrice = bidPrice;
    }

    public void setBidSize(int bidSize) {
        this.bidSize = bidSize;
    }

    public void setBidExchange(String bidExchange) {
        this.bidExchange = bidExchange;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    // Getters

    public double getAskPrice() {
        return askPrice;
    }

    public int getAskSize() {
        return askSize;
    }

    public String getAskExchange() {
        return askExchange;
    }

    public double getBidPrice() {
        return bidPrice;
    }

    public int getBidSize() {
        return bidSize;
    }

    public String getBidExchange() {
        return bidExchange;
    }

    public String getTimestamp() {
        return timestamp;
    }
}