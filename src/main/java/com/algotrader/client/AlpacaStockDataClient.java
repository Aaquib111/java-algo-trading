package main.java.com.algotrader.client;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import main.java.com.algotrader.dataclasses.StockBars;
import main.java.com.algotrader.dataclasses.StockQuotes;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
public class AlpacaStockDataClient {
    String apiKey, secretKey, BASE_URL;
    OkHttpClient client;

    public AlpacaStockDataClient(String apiKey, String secretKey) {
        this.apiKey = apiKey;
        this.secretKey = secretKey;
        this.BASE_URL = "https://data.alpaca.markets/v2/stocks/";
        this.client = new OkHttpClient();
    }

    public StockBars getHistoricalBars(String[] symbols, String timeframe, String startTime, String endTime,
            int limit) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(BASE_URL + "bars").newBuilder();
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
            ObjectMapper mapper = StockBars.getObjectMapper();

            String response = this.client.newCall(req).execute().body().string();
            System.out.println(response);
            return mapper.readValue(response, StockBars.class);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public StockBars getLatestBars(String[] symbols) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(BASE_URL + "bars/latest").newBuilder();
        urlBuilder.addQueryParameter("symbols", String.join(",", symbols));
        urlBuilder.addQueryParameter("feed", "iex");

        String url = urlBuilder.build().toString();

        Request req = buildRequestFromURL(url);

        try {
            ObjectMapper mapper = StockBars.getObjectMapper();

            String response = this.client.newCall(req).execute().body().string();
            System.out.println(response);
            return mapper.readValue(response, StockBars.class);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public StockQuotes getHistoricalQuotes(String[] symbols, String startTime, String endTime,
            int limit) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(BASE_URL + "quotes").newBuilder();
        urlBuilder.addQueryParameter("feed", "sip");
        urlBuilder.addQueryParameter("sort", "asc");
        urlBuilder.addQueryParameter("symbols", String.join(",", symbols));
        urlBuilder.addQueryParameter("start", startTime);
        urlBuilder.addQueryParameter("end", endTime);
        urlBuilder.addQueryParameter("limit", Integer.toString(limit));
        String url = urlBuilder.build().toString();

        Request req = buildRequestFromURL(url);

        try {
            ObjectMapper mapper = StockQuotes.getObjectMapper();

            String response = this.client.newCall(req).execute().body().string();
            System.out.println(response);
            return mapper.readValue(response, StockQuotes.class);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public StockQuotes getLatestQuotes(String[] symbols) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(BASE_URL + "quotes/latest").newBuilder();
        urlBuilder.addQueryParameter("symbols", String.join(",", symbols));
        urlBuilder.addQueryParameter("feed", "iex");

        String url = urlBuilder.build().toString();

        Request req = buildRequestFromURL(url);

        try {
            ObjectMapper mapper = StockQuotes.getObjectMapper();

            String response = this.client.newCall(req).execute().body().string();
            System.out.println(response);
            return mapper.readValue(response, StockQuotes.class);

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
