package main.java.com.algotrader.client;

import main.java.com.algotrader.dataclasses.StockBars;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;

import com.fasterxml.jackson.databind.ObjectMapper;

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
        urlBuilder = addBaseQueries(urlBuilder);
        urlBuilder.addQueryParameter("symbols", String.join(",", symbols));
        urlBuilder.addQueryParameter("timeframe", timeframe);
        urlBuilder.addQueryParameter("start", startTime);
        urlBuilder.addQueryParameter("end", endTime);
        urlBuilder.addQueryParameter("limit", Integer.toString(limit));
        String url = urlBuilder.build().toString();

        Request req = buildRequestFromURL(url);

        try {
            ObjectMapper mapper = new ObjectMapper();

            String response = this.client.newCall(req).execute().body().string();
            return mapper.readValue(response, StockBars.class);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private HttpUrl.Builder addBaseQueries(HttpUrl.Builder builder) {
        builder.addQueryParameter("adjustment", "raw");
        builder.addQueryParameter("feed", "sip");
        builder.addQueryParameter("sort", "asc");
        return builder;
    }

    private Request buildRequestFromURL(String url) {
        return new Request.Builder()
                .url(url)
                .get()
                .addHeader("accept", "application/json")
                .addHeader("APCA-API-KEY-ID", "PKT5N9EF8NCC47MI80TP")
                .addHeader("APCA-API-SECRET-KEY", "kCd3dDPzYKePOMCY843b06fZrs9p4Ie9ZiLs6FTa")
                .build();
    }

}
