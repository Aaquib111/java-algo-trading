package main.java.com.algotrader.client;

import java.net.http.HttpClient;

import main.java.com.algotrader.client.*;

public class AlpacaClient {

    String apiKey, secretKey;

    public AlpacaClient(String apiKey, String secretKey, boolean paperTrading){
        this.apiKey = apiKey;
        this.secretKey = secretKey;
    }

    public AlpacaStockDataClient getOptionsClient(){
        return new AlpacaStockDataClient(this.apiKey, this.secretKey);
    }
}
