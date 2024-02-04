package main.java.com.algotrader.client;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import main.java.com.algotrader.dataclasses.Bars;
import main.java.com.algotrader.dataclasses.Quotes;
import main.java.com.algotrader.dataclasses.Trades;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * A class that provides a client for accessing Alpaca's stock data API.
 */
public class AlpacaStockDataClient extends AlpacaBaseDataClient {
    String apiKey, secretKey, BASE_URL;

    /**
     * Constructs a new AlpacaStockDataClient with the given API key and secret key.
     * @param apiKey
     * @param secretKey
     */
    public AlpacaStockDataClient(String apiKey, String secretKey) {
        super(apiKey, secretKey);
        this.BASE_URL = "https://data.alpaca.markets/v2/stocks/";
    }

    /**
     * Gets the historical bars for the given symbols, timeframe, start time, end time, and limit.
     * @param symbols
     * @param timeframe
     * @param startTime
     * @param endTime
     * @param limit
     * @return @link Bars
     */
    public Bars getHistoricalBars(String[] symbols, String timeframe, String startTime, String endTime,
            int limit) {
        String requestURL = BASE_URL + "bars";
        return super.getHistoricalBars(symbols, timeframe, startTime, endTime, limit, requestURL);
    }

    /**
     * Gets the latest bars for the given symbols.
     * @param symbols
     * @return @link Bars
     */
    public Bars getLatestBars(String[] symbols) {
        String requestUrl = BASE_URL + "bars/latest";
        return super.getLatestBars(symbols, requestUrl);
    }

    /**
     * Gets the historical quotes for the given symbols, start time, end time, and limit.
     * @param symbols
     * @param startTime
     * @param endTime
     * @param limit
     * @return @link Quotes
     */
    public Quotes getHistoricalQuotes(String[] symbols, String startTime, String endTime,
            int limit) {
        String requestURL = BASE_URL + "quotes";
        return super.getHistoricalQuotes(symbols, startTime, endTime, limit, requestURL);
    }

    /**
     * Gets the latest quotes for the given symbols.
     * @param symbols
     * @return @link Quotes
     */
    public Quotes getLatestQuotes(String[] symbols) {
        String requestUrl = BASE_URL + "quotes/latest";
        return super.getLatestQuotes(symbols, requestUrl);
    }

    /**
     * Gets the historical trades for the given symbols, start time, end time, and limit.
     * @param symbols
     * @param startTime
     * @param endTime
     * @param limit
     * @return @link Trades
     */
    public Trades getHistoricalTrades(String[] symbols, String startTime, String endTime,
            int limit) {
        String requestURL = BASE_URL + "trades";
        return super.getHistoricalTrades(symbols, startTime, endTime, limit, requestURL);
    }

    /**
     * Gets the latest trades for the given symbols.
     * @param symbols
     * @return @link Trades
     */
    public Trades getLatestTrades(String[] symbols) {
        String requestUrl = BASE_URL + "trades/latest";
        return super.getLatestTrades(symbols, requestUrl);
    }
}
