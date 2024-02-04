package main.java.com.algotrader.dataclasses;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Trade implements MarketData {

    @JsonProperty("S")
    private String symbol;
    @JsonProperty("i")
    private int ID;
    @JsonProperty("p")
    private double price;
    @JsonProperty("s")
    private int size;
    @JsonProperty("x")
    private String exchange;
    @JsonProperty("t")
    private String timestamp;

    // Setters

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    // Getters

    public int getID() {
        return this.ID;
    }

    public double getPrice() {
        return this.price;
    }

    public int getSize() {
        return this.size;
    }

    public String getExchange() {
        return this.exchange;
    }

    public String getTimestamp() {
        return this.timestamp;
    }
}