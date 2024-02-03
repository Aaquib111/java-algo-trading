package main.java.com.algotrader;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import main.java.com.algotrader.client.AlpacaClientConstants;
import main.java.com.algotrader.client.AlpacaStockDataClient;
import main.java.com.algotrader.dataclasses.StockBars;
import main.java.com.algotrader.dataclasses.StockQuotes;
import main.java.com.algotrader.dataclasses.StockTrades;

class Driver {
    public static void main(String[] args) {
        InputStream inputStream = Driver.class.getResourceAsStream("/.env");

        AlpacaClientConstants.initalizeFromFile(inputStream);
        AlpacaStockDataClient client = new AlpacaStockDataClient(
                AlpacaClientConstants.API_KEY,
                AlpacaClientConstants.SECRET_KEY);
    }
}