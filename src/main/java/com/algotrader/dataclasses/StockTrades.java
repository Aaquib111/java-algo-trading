package main.java.com.algotrader.dataclasses;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = TradesDeserializer.class)
public class StockTrades {
    public Map<String, List<Trade>> trades;

    public StockTrades() {
    }

    public StockTrades(Map<String, List<Trade>> trades) {
        this.trades = trades;
    }

    public static ObjectMapper getObjectMapper(){
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(StockTrades.class, new TradesDeserializer());
        mapper.registerModule(module);
        return mapper;
    }

    public String toString() {
        // Format in a pretty table for each stock
        StringBuilder sb = new StringBuilder();
        for (String stock : this.trades.keySet()) {
            sb.append(stock + "\n");
            sb.append("Timestamp\t\tID\tPrice\tSize\tExchange\n");
            for (Trade trade : this.trades.get(stock)) {
                sb.append(trade.getTimestamp() + "\t" + trade.getID() + "\t" + trade.getPrice() + "\t" + trade.getSize()
                        + "\t" + trade.getExchange() + "\n");
            }
        }
        return sb.toString();
    }
}

class TradesDeserializer extends JsonDeserializer<StockTrades> {
    @Override
    public StockTrades deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        ObjectMapper mapper = (ObjectMapper) jp.getCodec();
        JsonNode root = mapper.readTree(jp);
        JsonNode tradesNode = root.get("trades"); // Get the "trades" node directly

        Map<String, List<Trade>> result = new HashMap<>();

        // Iterate over each field in the "trades" node
        tradesNode.fields().forEachRemaining(entry -> {
            String symbol = entry.getKey(); // The ticker symbol, e.g., "AAPL"
            JsonNode value = entry.getValue(); // The value, which could be an object or array
            List<Trade> tradesList = new ArrayList<>();

            if (value.isArray()) {
                // If the value is an array, process each item
                value.forEach(tradeNode -> tradesList.add(mapper.convertValue(tradeNode, Trade.class)));
            } else if (value.isObject()) {
                // If the value is a single object, add it directly
                tradesList.add(mapper.convertValue(value, Trade.class));
            }
            result.put(symbol, tradesList);
        });

        return new StockTrades(result);
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class Trade {

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