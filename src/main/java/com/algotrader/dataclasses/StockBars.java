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
@JsonDeserialize(using = BarsDeserializer.class)
public class StockBars {
    public Map<String, List<Bar>> bars;

    public StockBars() {
    }

    public StockBars(Map<String, List<Bar>> bars) {
        this.bars = bars;
    }

    public Map<String, List<Bar>> getBars() {
        return bars;
    }

    public String toString() {
        // Format in a pretty table for each stock
        StringBuilder sb = new StringBuilder();
        for (String stock : bars.keySet()) {
            sb.append(stock + "\n");
            sb.append("Timestamp\t\tOpen\tHigh\tLow\tClose\tVolume\tVWAP\tNumTransactions\n");
            for (Bar bar : bars.get(stock)) {
                sb.append(bar.getTimestamp() + "\t" + bar.getOpen() + "\t" + bar.getHigh() + "\t" + bar.getLow() + "\t"
                        + bar.getClose() + "\t" + bar.getVolume() + "\t" + bar.getVwap() + "\t"
                        + bar.getNumTransactions()
                        + "\n");
            }
        }
        return sb.toString();
    }

    public static ObjectMapper getObjectMapper(){
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(StockBars.class, new BarsDeserializer());
        mapper.registerModule(module);
        return mapper;
    }
}

class BarsDeserializer extends JsonDeserializer<StockBars> {
    @Override
    public StockBars deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        ObjectMapper mapper = (ObjectMapper) jp.getCodec();
        JsonNode root = mapper.readTree(jp);
        JsonNode barsNode = root.get("bars"); // Get the "bars" node directly

        Map<String, List<Bar>> result = new HashMap<>();

        // Iterate over each field in the "bars" node
        barsNode.fields().forEachRemaining(entry -> {
            String symbol = entry.getKey(); // The ticker symbol, e.g., "AAPL"
            JsonNode value = entry.getValue(); // The value, which could be an object or array
            List<Bar> barList = new ArrayList<>();

            if (value.isArray()) {
                // If the value is an array, process each item
                value.forEach(barNode -> barList.add(mapper.convertValue(barNode, Bar.class)));
            } else if (value.isObject()) {
                // If the value is a single object, add it directly
                barList.add(mapper.convertValue(value, Bar.class));
            }
            result.put(symbol, barList);
        });

        return new StockBars(result);
    }
}

class Bar {
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
