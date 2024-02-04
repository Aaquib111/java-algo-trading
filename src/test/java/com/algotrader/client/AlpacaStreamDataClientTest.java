package test.java.com.algotrader.client;

import java.io.InputStream;

import org.junit.Test;

import main.java.com.algotrader.client.AlpacaClientConstants;
import main.java.com.algotrader.client.AlpacaStreamDataClient;
import main.java.com.algotrader.dataclasses.DataCallbackFunction;
import main.java.com.algotrader.dataclasses.MarketData;

public class AlpacaStreamDataClientTest {
   
    private void initializeClientConstants(){
        InputStream inputStream = AlpacaStreamDataClientTest.class.getResourceAsStream("/.env");
        AlpacaClientConstants.initalizeFromFile(inputStream);
    }

    /**
     * Tests x@xxxx AlpacaStreamDataClient#connect()}
     */
    @Test
    public void testConnect() {
        initializeClientConstants();
        DataCallbackFunction callback = (MarketData data) -> System.out.println(data);

        AlpacaStreamDataClient streamClient = new AlpacaStreamDataClient(
                "wss://stream.data.alpaca.markets/v2/iex",
                AlpacaClientConstants.API_KEY,
                AlpacaClientConstants.SECRET_KEY,
                callback);

        streamClient.run();

        try{
            streamClient.awaitAuthentication();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        streamClient.closeConnection();
    }

    /**
     * Tests @link AlpacaStreamDataClient#subscribeQuotes(String[])}
     */
    @Test
    public void testSubscribeQuotes() {
        initializeClientConstants();
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
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        streamClient.closeConnection();
    }

    /**
     * Tests @link AlpacaStreamDataClient#subscribeTrades(String[])}
     */
    @Test
    public void testSubscribeTrades() {
        initializeClientConstants();
        DataCallbackFunction callback = (MarketData data) -> System.out.println(data);
        AlpacaStreamDataClient streamClient = new AlpacaStreamDataClient(
                "wss://stream.data.alpaca.markets/v2/iex",
                AlpacaClientConstants.API_KEY,
                AlpacaClientConstants.SECRET_KEY,
                callback);

        streamClient.run();

        try{
            streamClient.awaitAuthentication();
            streamClient.subscribeTrades(new String[] {"BTCUSD"});
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        streamClient.closeConnection();
    }

    /**
     * Tests @link AlpacaStreamDataClient#subscribeBars(String[])}
     */
    @Test
    public void testSubscribeBars() {
        initializeClientConstants();
        DataCallbackFunction callback = (MarketData data) -> System.out.println(data);
        AlpacaStreamDataClient streamClient = new AlpacaStreamDataClient(
                "wss://stream.data.alpaca.markets/v2/iex",
                AlpacaClientConstants.API_KEY,
                AlpacaClientConstants.SECRET_KEY,
                callback);

        streamClient.run();

        try{
            streamClient.awaitAuthentication();
            streamClient.subscribeBars(new String[] {"BTCUSD"});
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        streamClient.closeConnection();
    }

    /**
     * Tests @link AlpacaStreamDataClient#subscribeOrderbooks(String[])}
     */
    @Test
    public void testSubscribeOrderbooks() {
        initializeClientConstants();
        DataCallbackFunction callback = (MarketData data) -> System.out.println(data);
        AlpacaStreamDataClient streamClient = new AlpacaStreamDataClient(
                "wss://stream.data.alpaca.markets/v2/iex",
                AlpacaClientConstants.API_KEY,
                AlpacaClientConstants.SECRET_KEY,
                callback);

        streamClient.run();

        try{
            streamClient.awaitAuthentication();
            streamClient.subscribeOrderbooks(new String[] {"BTCUSD"});
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        streamClient.closeConnection();
    }
}
