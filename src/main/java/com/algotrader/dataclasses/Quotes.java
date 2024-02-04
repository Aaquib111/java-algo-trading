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
@JsonDeserialize(using = QuotesDeserializer.class)
public class Quotes implements MarketData {
    public Map<String, List<Quote>> quotes;

    public Quotes() {
    }

    public Quotes(Map<String, List<Quote>> quotes) {
        this.quotes = quotes;
    }

    public static ObjectMapper getObjectMapper(){
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Quotes.class, new QuotesDeserializer());
        mapper.registerModule(module);
        return mapper;
    }

    public String toString() {
        // Format in a pretty table for each stock
        StringBuilder sb = new StringBuilder();
        for (String security : this.quotes.keySet()) {
            sb.append(security + "\n");
            sb.append("Timestamp\t\tAskPrice\tAskSize\tAskEx\tBidPrice\tBidSize\tBidEx\n");
            for (Quote quote : this.quotes.get(security)) {
                sb.append(quote.getTimestamp() + "\t" + quote.getAskPrice() + "\t" + quote.getAskSize() + "\t" + quote.getAskExchange() + "\t"
                        + quote.getBidPrice() + "\t" + quote.getBidSize() + "\t" + quote.getBidExchange() + "\t"
                        + "\n");
            }
        }
        return sb.toString();
    }
}

class QuotesDeserializer extends JsonDeserializer<Quotes> {
    @Override
    public Quotes deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        ObjectMapper mapper = (ObjectMapper) jp.getCodec();
        JsonNode root = mapper.readTree(jp);
        JsonNode quotesNode = root.get("quotes"); // Get the "quotes" node directly

        Map<String, List<Quote>> result = new HashMap<>();

        // Iterate over each field in the "quotes" node
        quotesNode.fields().forEachRemaining(entry -> {
            String symbol = entry.getKey(); // The ticker symbol, e.g., "AAPL"
            JsonNode value = entry.getValue(); // The value, which could be an object or array
            List<Quote> quotesList = new ArrayList<>();

            if (value.isArray()) {
                // If the value is an array, process each item
                value.forEach(quoteNode -> quotesList.add(mapper.convertValue(quoteNode, Quote.class)));
            } else if (value.isObject()) {
                // If the value is a single object, add it directly
                quotesList.add(mapper.convertValue(value, Quote.class));
            }
            result.put(symbol, quotesList);
        });

        return new Quotes(result);
    }
}
