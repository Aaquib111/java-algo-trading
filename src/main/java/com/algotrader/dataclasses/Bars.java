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
public class Bars implements MarketData {
    public Map<String, List<Bar>> bars;

    public Bars() {
    }

    public Bars(Map<String, List<Bar>> bars) {
        this.bars = bars;
    }

    public Map<String, List<Bar>> getBars() {
        return bars;
    }

    public String toString() {
        // Format in a pretty table for each stock
        StringBuilder sb = new StringBuilder();
        for (String security : bars.keySet()) {
            sb.append(security + "\n");
            sb.append("Timestamp\t\tOpen\tHigh\tLow\tClose\tVolume\tVWAP\tNumTransactions\n");
            for (Bar bar : bars.get(security)) {
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
        module.addDeserializer(Bars.class, new BarsDeserializer());
        mapper.registerModule(module);
        return mapper;
    }
}

class BarsDeserializer extends JsonDeserializer<Bars> {
    @Override
    public Bars deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
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

        return new Bars(result);
    }
}
