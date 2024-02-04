package main.java.com.algotrader.client;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import main.java.com.algotrader.dataclasses.Bars;
import main.java.com.algotrader.dataclasses.Orderbooks;
import main.java.com.algotrader.dataclasses.Quotes;
import main.java.com.algotrader.dataclasses.Trades;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * A class that provides a client for accessing Alpaca's crypto data API.
 */
public class AlpacaCryptoDataClient extends AlpacaBaseDataClient {
    String apiKey, secretKey, BASE_URL;

    /**
     * Constructs a new AlpacaCryptoDataClient with the given API key and secret key.
     * @param apiKey
     * @param secretKey
     */
    public AlpacaCryptoDataClient(String apiKey, String secretKey) {
        super(apiKey, secretKey);
        this.BASE_URL = "https://data.alpaca.markets/v1beta3/crypto/us/";
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
        String requestUrl = BASE_URL + "latest/bars";
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
        String requestUrl = BASE_URL + "latest/quotes";
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
        String requestUrl = BASE_URL + "latest/trades";
        return super.getLatestTrades(symbols, requestUrl);
    }

    /**
     * Gets the latest orderbooks for the given symbols.
     * @param symbols
     * @return @link Orderbooks
     */
    public Orderbooks getLatestOrderbooks(String[] symbols) {
        String requestUrl = BASE_URL + "latest/orderbooks";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(requestUrl).newBuilder();
        urlBuilder.addQueryParameter("symbols", String.join(",", symbols));

        String url = urlBuilder.build().toString();

        Request req = buildRequestFromURL(url);

        try {
            ObjectMapper mapper = new ObjectMapper();
            String response = this.client.newCall(req).execute().body().string();
            return mapper.readValue(response, Orderbooks.class);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Request buildRequestFromURL(String url) {
        return new Request.Builder()
                .url(url)
                .get()
                .addHeader("accept", "application/json")
                // .addHeader("APCA-API-KEY-ID", this.apiKey)
                // .addHeader("APCA-API-SECRET-KEY", this.secretKey)
                .build();
    }
}