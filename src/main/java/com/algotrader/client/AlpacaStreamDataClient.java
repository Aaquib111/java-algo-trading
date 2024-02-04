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

// import okio.ByteString;

public class AlpacaStreamDataClient extends WebSocketListener {
    private String url, apiKey, secretKey;
    private WebSocket webSocket;
    private DataCallbackFunction callback;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private CountDownLatch latch = new CountDownLatch(2);

    public AlpacaStreamDataClient(String url, String apiKey, String secretKey, DataCallbackFunction callback) {
        this.url = url;
        this.apiKey = apiKey;
        this.secretKey = secretKey;
        this.callback = callback;
    }

    public void run() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        this.webSocket = client.newWebSocket(request, this);

        client.dispatcher().executorService().shutdown();
    }

    public void subscribeTrades(String[] symbols) {
        String subscribeMessage = String.format("{\"action\":\"subscribe\",\"trades\":[\"%s\"]}",
                String.join("\",\"", symbols));
        this.webSocket.send(subscribeMessage);
    }

    public void subscribeQuotes(String[] symbols) {
        String subscribeMessage = String.format("{\"action\":\"subscribe\",\"quotes\":[\"%s\"]}",
                String.join("\",\"", symbols));
        System.out.println(subscribeMessage);
        this.webSocket.send(subscribeMessage);
    }

    public void subscribeBars(String[] symbols) {
        String subscribeMessage = String.format("{\"action\":\"subscribe\",\"bars\":[\"%s\"]}",
                String.join("\",\"", symbols));
        this.webSocket.send(subscribeMessage);
    }

    public void subscribeOrderbooks(String[] symbols) {
        String subscribeMessage = String.format("{\"action\":\"subscribe\",\"orderbooks\":[\"%s\"]}",
                String.join("\",\"", symbols));
        this.webSocket.send(subscribeMessage);
    }

    public void unsubscribeAll() {
        String unsubscribeMessage = "{\"action\":\"unsubscribe\",\"trades\":[*],\"quotes\":[*],\"bars\":[*],\"orderbooks\":[*]}";
        this.webSocket.send(unsubscribeMessage);
    }

    public void closeConnection() {
        if (this.webSocket != null) {
            this.webSocket.close(1000, "Closing the connection"); // Use appropriate status code and reason
        }
    }

    public void awaitAuthentication() throws InterruptedException {
        latch.await();
    }

    @Override
    public void onOpen(WebSocket webSocket, okhttp3.Response response) {
        System.out.println("Connection opened");
        String authMessage = String.format("{\"action\":\"auth\",\"key\":\"%s\",\"secret\":\"%s\"}", this.apiKey,
                this.secretKey);
        webSocket.send(authMessage);
    }

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

    public void onClosing(WebSocket webSocket, int code, String reason) {
        System.out.println("Closing : " + code + " / " + reason);
    }

    public void onClosed(WebSocket webSocket, int code, String reason) {
        System.out.println("Closed : " + code + " / " + reason);
    }

    public void onFailure(WebSocket webSocket, Throwable t, okhttp3.Response response) {
        System.out.println("Error : " + t.getMessage());
    }
}
