package main.java.com.algotrader;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import main.java.com.algotrader.client.AlpacaClientConstants;
import main.java.com.algotrader.client.AlpacaCryptoDataClient;
import main.java.com.algotrader.client.AlpacaStockDataClient;
import main.java.com.algotrader.dataclasses.Orderbooks;
import main.java.com.algotrader.dataclasses.Bars;
import main.java.com.algotrader.dataclasses.Quotes;
import main.java.com.algotrader.dataclasses.Trades;

class Driver {
    public static void main(String[] args) {
        InputStream inputStream = Driver.class.getResourceAsStream("/.env");

        AlpacaClientConstants.initalizeFromFile(inputStream);
        AlpacaStockDataClient client = new AlpacaStockDataClient(
                AlpacaClientConstants.API_KEY,
                AlpacaClientConstants.SECRET_KEY);
        
        AlpacaCryptoDataClient cryptoClient = new AlpacaCryptoDataClient(
                AlpacaClientConstants.API_KEY,
                AlpacaClientConstants.SECRET_KEY);
        
        Orderbooks o = cryptoClient.getLatestOrderbooks(new String[]{"BTC/USD", "LTC/USD"});
        System.out.println(o);
    }
}