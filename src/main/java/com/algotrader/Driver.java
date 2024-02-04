package main.java.com.algotrader;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import main.java.com.algotrader.client.AlpacaClientConstants;
import main.java.com.algotrader.client.AlpacaCryptoDataClient;
import main.java.com.algotrader.client.AlpacaStockDataClient;
import main.java.com.algotrader.client.AlpacaStreamDataClient;
import main.java.com.algotrader.dataclasses.Orderbooks;
import main.java.com.algotrader.dataclasses.Bars;
import main.java.com.algotrader.dataclasses.DataCallbackFunction;
import main.java.com.algotrader.dataclasses.MarketData;
import main.java.com.algotrader.dataclasses.Quotes;
import main.java.com.algotrader.dataclasses.Trades;


public class Driver {
    public static void main(String[] args) {
        InputStream inputStream = Driver.class.getResourceAsStream("/.env");

        AlpacaClientConstants.initalizeFromFile(inputStream);
        AlpacaStockDataClient client = new AlpacaStockDataClient(
                AlpacaClientConstants.API_KEY,
                AlpacaClientConstants.SECRET_KEY);
        
        AlpacaCryptoDataClient cryptoClient = new AlpacaCryptoDataClient(
                AlpacaClientConstants.API_KEY,
                AlpacaClientConstants.SECRET_KEY);
        
        DataCallbackFunction callback = (MarketData data) -> System.out.println(data);

        AlpacaStreamDataClient streamClient = new AlpacaStreamDataClient(
                "wss://stream.data.alpaca.markets/v2/iex",
                AlpacaClientConstants.API_KEY,
                AlpacaClientConstants.SECRET_KEY,
                callback);

        streamClient.run();

        try{
            streamClient.awaitAuthentication();
            streamClient.subscribeQuotes(new String[] {"BTCUSD"});
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        streamClient.closeConnection();
    }
}
