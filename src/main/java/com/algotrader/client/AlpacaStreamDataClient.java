package main.java.com.algotrader.client;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
// import okio.ByteString;

public class AlpacaStreamDataClient extends WebSocketListener {
    private String url, apiKey, secretKey;
    private WebSocket webSocket;

    public AlpacaStreamDataClient(String url, String apiKey, String secretKey) {
        this.url = url;
        this.apiKey = apiKey;
        this.secretKey = secretKey;
    }

    public void run() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        this.webSocket = client.newWebSocket(request, this);
        // Trigger shutdown of the dispatcher's executor so this process can exit
        // cleanly
        client.dispatcher().executorService().shutdown();
    }

    // Method to close the WebSocket connection
    public void closeConnection() {
        if (this.webSocket != null) {
            this.webSocket.close(1000, "Closing the connection"); // Use appropriate status code and reason
        }
    }

    @Override
    public void onOpen(WebSocket webSocket, okhttp3.Response response) {
        System.out.println("Connection opened");
        String authMessage = String.format("{\"action\":\"auth\",\"key\":\"%s\",\"secret\":\"%s\"}", this.apiKey,
                this.secretKey);
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        System.out.println("Receiving : " + text);
    }

    // @Override
    // public void onMessage(WebSocket webSocket, ByteString bytes) {
    // System.out.println("Receiving bytes : " + bytes.hex());
    // }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        System.out.println("Closing : " + code + " / " + reason);
    }

    @Override
    public void onClosed(WebSocket webSocket, int code, String reason) {
        System.out.println("Closed : " + code + " / " + reason);
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, okhttp3.Response response) {
        System.out.println("Error : " + t.getMessage());
    }
}
