package main.java.com.algotrader.client;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

import java.util.concurrent.CountDownLatch;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import main.java.com.algotrader.dataclasses.Bar;
import main.java.com.algotrader.dataclasses.DataCallbackFunction;
import main.java.com.algotrader.dataclasses.Orderbook;
import main.java.com.algotrader.dataclasses.Quote;
import main.java.com.algotrader.dataclasses.Trade;
import main.java.com.algotrader.dataclasses.MarketData;


/*
 * This class is a client for the Alpaca Stream API. It is used to subscribe to
 * real-time data streams and receive data from the streams.
 */
public class AlpacaStreamDataClient extends WebSocketListener {
    private String url, apiKey, secretKey;
    private WebSocket webSocket;
    private DataCallbackFunction callback;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private CountDownLatch latch = new CountDownLatch(2);

    /**
     * Constructs a new AlpacaStreamDataClient with the given URL, API key, secret
     * key, and callback function.
     * 
     * @param url       The URL to connect to.
     * @param apiKey    The API key to use for authentication.
     * @param secretKey The secret key to use for authentication.
     * @param callback  The callback function to call when data is received.
     */
    public AlpacaStreamDataClient(String url, String apiKey, String secretKey, DataCallbackFunction callback) {
        this.url = url;
        this.apiKey = apiKey;
        this.secretKey = secretKey;
        this.callback = callback;
    }

    /**
     * Opens a connection to the Alpaca Stream API and starts listening for data.
     */
    public void run() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        this.webSocket = client.newWebSocket(request, this);

        client.dispatcher().executorService().shutdown();
    }

    /**
     * Subscribes to the trades stream for the given symbols.
     * 
     * @param symbols The symbols to subscribe to.
     */
    public void subscribeTrades(String[] symbols) {
        String subscribeMessage = String.format("{\"action\":\"subscribe\",\"trades\":[\"%s\"]}",
                String.join("\",\"", symbols));
        this.webSocket.send(subscribeMessage);
    }

    /**
     * Subscribes to the quotes stream for the given symbols.
     * 
     * @param symbols The symbols to subscribe to.
     */
    public void subscribeQuotes(String[] symbols) {
        String subscribeMessage = String.format("{\"action\":\"subscribe\",\"quotes\":[\"%s\"]}",
                String.join("\",\"", symbols));
        System.out.println(subscribeMessage);
        this.webSocket.send(subscribeMessage);
    }

    /**
     * Subscribes to the bars stream for the given symbols.
     * 
     * @param symbols The symbols to subscribe to.
     */
    public void subscribeBars(String[] symbols) {
        String subscribeMessage = String.format("{\"action\":\"subscribe\",\"bars\":[\"%s\"]}",
                String.join("\",\"", symbols));
        this.webSocket.send(subscribeMessage);
    }

    /**
     * Subscribes to the orderbooks stream for the given symbols.
     * 
     * @param symbols The symbols to subscribe to.
     */
    public void subscribeOrderbooks(String[] symbols) {
        String subscribeMessage = String.format("{\"action\":\"subscribe\",\"orderbooks\":[\"%s\"]}",
                String.join("\",\"", symbols));
        this.webSocket.send(subscribeMessage);
    }

    /**
     * Unsubscribes from all streams.
     */
    public void unsubscribeAll() {
        String unsubscribeMessage = "{\"action\":\"unsubscribe\",\"trades\":[*],\"quotes\":[*],\"bars\":[*],\"orderbooks\":[*]}";
        this.webSocket.send(unsubscribeMessage);
    }

    /**
     * Closes the connection to the Alpaca Stream API.
     */
    public void closeConnection() {
        if (this.webSocket != null) {
            this.webSocket.close(1000, "Closing the connection"); // Use appropriate status code and reason
        }
    }

    /**
     * Blocks the current thread until the client has been authenticated.
     * 
     * @throws InterruptedException
     */
    public void awaitAuthentication() throws InterruptedException {
        latch.await();
    }

    /**
     * Called when the connection is opened.
     * 
     * @param webSocket The WebSocket that was opened.
     * @param response  The response from the server.
     */
    @Override
    public void onOpen(WebSocket webSocket, okhttp3.Response response) {
        System.out.println("Connection opened");
        String authMessage = String.format("{\"action\":\"auth\",\"key\":\"%s\",\"secret\":\"%s\"}", this.apiKey,
                this.secretKey);
        webSocket.send(authMessage);
    }

    /**
     * Called when a message is received from the server.
     * Parses the message and calls the callback function with the parsed data.
     * 
     * @param webSocket The WebSocket that received the message.
     * @param json      The JSON message that was received.
     * @throws IllegalArgumentException
     */
    @Override
    public void onMessage(WebSocket webSocket, String json) throws IllegalArgumentException{
        JsonNode rootNode = null;
        try {
            rootNode = objectMapper.readTree(json).get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Receiving: " + rootNode);

        JsonNode tNode = rootNode.path("T"); // Assuming "t" is directly under the root
        
        if (tNode.isMissingNode()) {
            throw new IllegalArgumentException("Missing type indicator 'T'");
        }

        String type = tNode.asText();
        MarketData data = null;
        try{
            switch (type) {
                case "success":
                    latch.countDown();
                    break;
                case "o":
                    data = objectMapper.readValue(json, Orderbook.class);
                case "q":
                    data = objectMapper.readValue(json, Quote.class);
                case "b":
                    data = objectMapper.readValue(json, Bar.class);
                case "t":
                    data = objectMapper.readValue(json, Trade.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(data != null){
            this.callback.onData(data);
        }
    }
}
