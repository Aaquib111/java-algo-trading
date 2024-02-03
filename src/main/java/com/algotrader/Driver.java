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
        // StockBars response = client.getHistoricalBars(new String[] { "AAPL", "MSFT" },
        //         "1D", "2024-01-01T00:00:00Z", "2024-01-04T00:00:00Z", 100);
        // StockBars response = client.getLatestBars(new String[] { "AAPL" });
        // StockQuotes response = client.getHistoricalQuotes(
        //         new String[] { "AAPL" },
        //         "2024-01-01",
        //         "2024-01-02",
        //         3);
        // StockQuotes response = client.getLatestQuotes(new String[] { "AAPL" });
        // StockTrades response = client.getHistoricalTrades(
        //         new String[] { "AAPL" },
        //         "2024-01-01",
        //         "2024-01-02",
        //         3);

        StockTrades response = client.getLatestTrades(new String[] { "AAPL" });
        System.out.println(response);
    }
}