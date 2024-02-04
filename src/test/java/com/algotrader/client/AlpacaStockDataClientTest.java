package test.java.com.algotrader.client;

import java.io.InputStream;

import org.junit.Test;
import static org.junit.Assert.assertNotNull;

import main.java.com.algotrader.client.AlpacaClientConstants;

import main.java.com.algotrader.client.AlpacaStockDataClient;
import main.java.com.algotrader.dataclasses.Bars;
import main.java.com.algotrader.dataclasses.Quotes;
import main.java.com.algotrader.dataclasses.Trades;

public class AlpacaStockDataClientTest {

    private void initializeClientConstants(){
        InputStream inputStream = AlpacaStockDataClientTest.class.getResourceAsStream("/.env");
        AlpacaClientConstants.initalizeFromFile(inputStream);
    }

    /**
     * Tests @link AlpacaStockDataClient#getHistoricalBars(String[], String, String, String, int)}
     */
    @Test
    public void testGetHistoricalBars() {
        initializeClientConstants();
        AlpacaStockDataClient client = new AlpacaStockDataClient(
                AlpacaClientConstants.API_KEY,
                AlpacaClientConstants.SECRET_KEY);
        Bars response = client.getHistoricalBars(new String[] { "AAPL", "MSFT" },
                "1D", "2024-01-01T00:00:00Z", "2024-01-04T00:00:00Z", 10);

        assertNotNull(response);
    }

    /**
     * Tests @link AlpacaStockDataClient#getLatestBars(String[])}
     */
    @Test
    public void testGetLatestBars() {
        initializeClientConstants();
        AlpacaStockDataClient client = new AlpacaStockDataClient(
                AlpacaClientConstants.API_KEY,
                AlpacaClientConstants.SECRET_KEY);
        Bars response = client.getLatestBars(new String[] { "AAPL", "MSFT" });
        assertNotNull(response);
    }

    /**
     * Tests @link AlpacaStockDataClient#getHistoricalQuotes(String[], String, String, int)}
     */
    @Test
    public void testGetHistoricalQuotes(){
        initializeClientConstants();
        AlpacaStockDataClient client = new AlpacaStockDataClient(
                AlpacaClientConstants.API_KEY,
                AlpacaClientConstants.SECRET_KEY);
        Quotes response = client.getHistoricalQuotes(
                new String[] { "AAPL" },
                "2024-01-01",
                "2024-01-02",
                3);
        assertNotNull(response);
    }

    /**
     * Tests @link AlpacaStockDataClient#getLatestQuotes(String[])}
     */
    @Test
    public void testGetLatestQuotes(){
        initializeClientConstants();
        AlpacaStockDataClient client = new AlpacaStockDataClient(
                AlpacaClientConstants.API_KEY,
                AlpacaClientConstants.SECRET_KEY);
        Quotes response = client.getLatestQuotes(new String[] { "AAPL" });
        assertNotNull(response);
    }

    /**
     * Tests @link AlpacaStockDataClient#getHistoricalTrades(String[], String, String, int)}
     */
    @Test
    public void testGetHistoricalTrades(){
        initializeClientConstants();
        AlpacaStockDataClient client = new AlpacaStockDataClient(
                AlpacaClientConstants.API_KEY,
                AlpacaClientConstants.SECRET_KEY);
        Trades response = client.getHistoricalTrades(
                new String[] { "AAPL" },
                "2024-01-01",
                "2024-01-02",
                3);
        assertNotNull(response);
    }

    /**
     * Tests @link AlpacaStockDataClient#getLatestTrades(String[])}
     */
    @Test
    public void testGetLatestTrades(){
        initializeClientConstants();
        AlpacaStockDataClient client = new AlpacaStockDataClient(
                AlpacaClientConstants.API_KEY,
                AlpacaClientConstants.SECRET_KEY);
        Trades response = client.getLatestTrades(new String[] { "AAPL" });
        assertNotNull(response);
    }
}
