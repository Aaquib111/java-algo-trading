package main.java.com.algotrader.client;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import main.java.com.algotrader.dataclasses.Bars;
import main.java.com.algotrader.dataclasses.Quotes;
import main.java.com.algotrader.dataclasses.Trades;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;


public class AlpacaBaseDataClient {
    String apiKey, secretKey, BASE_URL;
    OkHttpClient client;

    public AlpacaBaseDataClient(String apiKey, String secretKey) {
        this.apiKey = apiKey;
        this.secretKey = secretKey;
        this.client = new OkHttpClient();
    }

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

    public Trades getLatestTrades(String[] symbols, String requestUrl) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(requestUrl).newBuilder();
        urlBuilder.addQueryParameter("symbols", String.join(",", symbols));
        urlBuilder.addQueryParameter("feed", "iex");

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
