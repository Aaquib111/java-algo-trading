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
public class Trades implements MarketData {
    public Map<String, List<Trade>> trades;

    public Trades() {
    }

    public Trades(Map<String, List<Trade>> trades) {
        this.trades = trades;
    }

    public static ObjectMapper getObjectMapper(){
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Trades.class, new TradesDeserializer());
        mapper.registerModule(module);
        return mapper;
    }

    public String toString() {
        // Format in a pretty table for each stock
        StringBuilder sb = new StringBuilder();
        for (String security : this.trades.keySet()) {
            sb.append(security + "\n");
            sb.append("Timestamp\t\tID\tPrice\tSize\tExchange\n");
            for (Trade trade : this.trades.get(security)) {
                sb.append(trade.getTimestamp() + "\t" + trade.getID() + "\t" + trade.getPrice() + "\t" + trade.getSize()
                        + "\t" + trade.getExchange() + "\n");
            }
        }
        return sb.toString();
    }
}

class TradesDeserializer extends JsonDeserializer<Trades> {
    @Override
    public Trades deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
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

        return new Trades(result);
    }
}
