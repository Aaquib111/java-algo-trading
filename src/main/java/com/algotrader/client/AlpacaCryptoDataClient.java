package main.java.com.algotrader.client;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import main.java.com.algotrader.dataclasses.Bars;
import main.java.com.algotrader.dataclasses.Quotes;
import main.java.com.algotrader.dataclasses.Trades;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class AlpacaCryptoDataClient extends AlpacaBaseDataClient {
    String apiKey, secretKey, BASE_URL;

    public AlpacaCryptoDataClient(String apiKey, String secretKey) {
        super(apiKey, secretKey);
        this.BASE_URL = "https://data.alpaca.markets/v1beta3/crypto/us/";
    }

    public Bars getHistoricalBars(String[] symbols, String timeframe, String startTime, String endTime,
            int limit) {
        String requestURL = BASE_URL + "bars";
        return super.getHistoricalBars(symbols, timeframe, startTime, endTime, limit, requestURL);
    }

    public Bars getLatestBars(String[] symbols) {
        String requestUrl = BASE_URL + "latest/bars";
        return super.getLatestBars(symbols, requestUrl);
    }

    public Quotes getHistoricalQuotes(String[] symbols, String startTime, String endTime,
            int limit) {
        String requestURL = BASE_URL + "quotes";
        return super.getHistoricalQuotes(symbols, startTime, endTime, limit, requestURL);
    }

    public Quotes getLatestQuotes(String[] symbols) {
        String requestUrl = BASE_URL + "latest/quotes";
        return super.getLatestQuotes(symbols, requestUrl);
    }

    public Trades getHistoricalTrades(String[] symbols, String startTime, String endTime,
            int limit) {
        String requestURL = BASE_URL + "trades";
        return super.getHistoricalTrades(symbols, startTime, endTime, limit, requestURL);
    }

    public Trades getLatestTrades(String[] symbols) {
        String requestUrl = BASE_URL + "latest/trades";
        return super.getLatestTrades(symbols, requestUrl);
    }
}
