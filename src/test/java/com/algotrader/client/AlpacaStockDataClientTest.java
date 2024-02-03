package test.java.com.algotrader.client;

import java.io.InputStream;

import org.junit.Test;
import static org.junit.Assert.assertNotNull;

import main.java.com.algotrader.client.AlpacaClientConstants;

import main.java.com.algotrader.client.AlpacaStockDataClient;
import main.java.com.algotrader.dataclasses.StockBars;
import main.java.com.algotrader.dataclasses.StockQuotes;
import main.java.com.algotrader.dataclasses.StockTrades;

public class AlpacaStockDataClientTest {

    private void initializeClientConstants(){
        InputStream inputStream = AlpacaStockDataClientTest.class.getResourceAsStream("/.env");
        AlpacaClientConstants.initalizeFromFile(inputStream);
    }

    @Test
    public void testGetHistoricalBars() {
        initializeClientConstants();
        AlpacaStockDataClient client = new AlpacaStockDataClient(
                AlpacaClientConstants.API_KEY,
                AlpacaClientConstants.SECRET_KEY);
        StockBars response = client.getHistoricalBars(new String[] { "AAPL", "MSFT" },
                "1D", "2024-01-01T00:00:00Z", "2024-01-04T00:00:00Z", 10);

        assertNotNull(response);
    }

    @Test
    public void testGetLatestBars() {
        initializeClientConstants();
        AlpacaStockDataClient client = new AlpacaStockDataClient(
                AlpacaClientConstants.API_KEY,
                AlpacaClientConstants.SECRET_KEY);
        StockBars response = client.getLatestBars(new String[] { "AAPL", "MSFT" });
        assertNotNull(response);
    }

    @Test
    public void testGetHistoricalQuotes(){
        initializeClientConstants();
        AlpacaStockDataClient client = new AlpacaStockDataClient(
                AlpacaClientConstants.API_KEY,
                AlpacaClientConstants.SECRET_KEY);
        StockQuotes response = client.getHistoricalQuotes(
                new String[] { "AAPL" },
                "2024-01-01",
                "2024-01-02",
                3);
        assertNotNull(response);
    }

    @Test
    public void testGetLatestQuotes(){
        initializeClientConstants();
        AlpacaStockDataClient client = new AlpacaStockDataClient(
                AlpacaClientConstants.API_KEY,
                AlpacaClientConstants.SECRET_KEY);
        StockQuotes response = client.getLatestQuotes(new String[] { "AAPL" });
        assertNotNull(response);
    }

    @Test
    public void testGetHistoricalTrades(){
        initializeClientConstants();
        AlpacaStockDataClient client = new AlpacaStockDataClient(
                AlpacaClientConstants.API_KEY,
                AlpacaClientConstants.SECRET_KEY);
        StockTrades response = client.getHistoricalTrades(
                new String[] { "AAPL" },
                "2024-01-01",
                "2024-01-02",
                3);
        assertNotNull(response);
    }

    @Test
    public void testGetLatestTrades(){
        initializeClientConstants();
        AlpacaStockDataClient client = new AlpacaStockDataClient(
                AlpacaClientConstants.API_KEY,
                AlpacaClientConstants.SECRET_KEY);
        StockTrades response = client.getLatestTrades(new String[] { "AAPL" });
        assertNotNull(response);
    }
}
