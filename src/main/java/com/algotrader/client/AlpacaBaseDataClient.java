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
 * A class that provides a base client for accessing Alpaca's data API.
 * You should only require one instance of this class to access all of the data API's functionality.
 * @see <a href="https://docs.alpaca.markets/docs/about-market-data-api/">Alpaca Data API</a>
 */
public class AlpacaBaseDataClient {
    String apiKey, secretKey, BASE_URL;
    OkHttpClient client;

    /**
     * Constructs a new AlpacaBaseDataClient with the given API key and secret key.
     * @param apiKey The API key to use for authentication.
     * @param secretKey The secret key to use for authentication.
     */
    public AlpacaBaseDataClient(String apiKey, String secretKey) {
        this.apiKey = apiKey;
        this.secretKey = secretKey;
        this.client = new OkHttpClient();
    }

    /**
     * Gets the historical bars for the given symbols, timeframe, start time, end time, and limit.
     * @param symbols
     * @param timeframe
     * @param startTime
     * @param endTime
     * @param limit
     * @param requestURL
     * @return @link Bars
     */
    public Bars getHistoricalBars(String[] symbols, String timeframe, String startTime, String endTime,
            int limit, String requestURL) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(requestURL).newBuilder();
        urlBuilder.addQueryParameter("adjustment", "raw");
        urlBuilder.addQueryParameter("feed", "sip");
        urlBuilder.addQueryParameter("sort", "asc");
        urlBuilder.addQueryParameter("symbols", String.join(",", symbols));
        urlBuilder.addQueryParameter("timeframe", timeframe);
        urlBuilder.addQueryParameter("start", startTime);
        urlBuilder.addQueryParameter("end", endTime);
        urlBuilder.addQueryParameter("limit", Integer.toString(limit));
        String url = urlBuilder.build().toString();

        Request req = buildRequestFromURL(url);

        try {
            ObjectMapper mapper = Bars.getObjectMapper();

            String response = this.client.newCall(req).execute().body().string();
            System.out.println(response);
            return mapper.readValue(response, Bars.class);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Gets the latest bars for the given symbols.
     * @param symbols
     * @param requestUrl
     * @return @link Bars
     */
    public Bars getLatestBars(String[] symbols, String requestUrl) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(requestUrl).newBuilder();
        urlBuilder.addQueryParameter("symbols", String.join(",", symbols));

        String url = urlBuilder.build().toString();

        Request req = buildRequestFromURL(url);

        try {
            ObjectMapper mapper = Bars.getObjectMapper();

            String response = this.client.newCall(req).execute().body().string();
            System.out.println(response);
            return mapper.readValue(response, Bars.class);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Gets the historical quotes for the given symbols, start time, end time, and limit.
     * @param symbols
     * @param startTime
     * @param endTime
     * @param limit
     * @param requestUrl
     * @return @link Quotes
     */
    public Quotes getHistoricalQuotes(String[] symbols, String startTime, String endTime,
            int limit, String requestUrl) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(requestUrl).newBuilder();
        urlBuilder.addQueryParameter("feed", "sip");
        urlBuilder.addQueryParameter("sort", "asc");
        urlBuilder.addQueryParameter("symbols", String.join(",", symbols));
        urlBuilder.addQueryParameter("start", startTime);
        urlBuilder.addQueryParameter("end", endTime);
        urlBuilder.addQueryParameter("limit", Integer.toString(limit));
        String url = urlBuilder.build().toString();

        Request req = buildRequestFromURL(url);

        try {
            ObjectMapper mapper = Quotes.getObjectMapper();

            String response = this.client.newCall(req).execute().body().string();
            System.out.println(response);
            return mapper.readValue(response, Quotes.class);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Gets the latest quotes for the given symbols.
     * @param symbols
     * @param requestUrl
     * @return @link Quotes
     */
    public Quotes getLatestQuotes(String[] symbols, String requestUrl) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(requestUrl).newBuilder();
        urlBuilder.addQueryParameter("symbols", String.join(",", symbols));
        urlBuilder.addQueryParameter("feed", "iex");

        String url = urlBuilder.build().toString();

        Request req = buildRequestFromURL(url);

        try {
            ObjectMapper mapper = Quotes.getObjectMapper();

            String response = this.client.newCall(req).execute().body().string();
            System.out.println(response);
            return mapper.readValue(response, Quotes.class);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Gets the historical trades for the given symbols, start time, end time, and limit.
     * @param symbols
     * @param startTime
     * @param endTime
     * @param limit
     * @param requestUrl
     * @return @link Trades
     */
    public Trades getHistoricalTrades(String[] symbols, String startTime, String endTime,
            int limit, String requestUrl) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(requestUrl).newBuilder();
        urlBuilder.addQueryParameter("feed", "sip");
        urlBuilder.addQueryParameter("sort", "asc");
        urlBuilder.addQueryParameter("symbols", String.join(",", symbols));
        urlBuilder.addQueryParameter("start", startTime);
        urlBuilder.addQueryParameter("end", endTime);
        urlBuilder.addQueryParameter("limit", Integer.toString(limit));
        String url = urlBuilder.build().toString();

        Request req = buildRequestFromURL(url);

        try {
            ObjectMapper mapper = Trades.getObjectMapper();

            String response = this.client.newCall(req).execute().body().string();
            System.out.println(response);
            return mapper.readValue(response, Trades.class);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Gets the latest trades for the given symbols.
     * @param symbols
     * @param requestUrl
     * @return @link Trades
     */
    public Trades getLatestTrades(String[] symbols, String requestUrl) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(requestUrl).newBuilder();
        urlBuilder.addQueryParameter("symbols", String.join(",", symbols));
        // urlBuilder.addQueryParameter("feed", "iex");

        String url = urlBuilder.build().toString();

        Request req = buildRequestFromURL(url);

        try {
            ObjectMapper mapper = Trades.getObjectMapper();

            String response = this.client.newCall(req).execute().body().string();
            System.out.println(response);
            return mapper.readValue(response, Trades.class);

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
                .addHeader("APCA-API-KEY-ID", this.apiKey)
                .addHeader("APCA-API-SECRET-KEY", this.secretKey)
                .build();
    }
}
