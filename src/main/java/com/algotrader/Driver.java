package main.java.com.algotrader;

import main.java.com.algotrader.client.AlpacaStockDataClient;
import main.java.com.algotrader.dataclasses.StockBars;

class Driver {
    public static void main(String[] args) {
        System.out.println("Hello, World!"); 
        AlpacaStockDataClient client = new AlpacaStockDataClient("PKT5N9EF8NCC47MI80TP", "kCd3dDPzYKePOMCY843b06fZrs9p4Ie9ZiLs6FTa");
        StockBars response = client.getHistoricalBars(new String[]{"AAPL"}, "1D", "2024-01-01T00:00:00Z", "2024-01-04T00:00:00Z", 100);
        System.out.println(response);
    }
}