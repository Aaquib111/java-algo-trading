package main.java.com.algotrader.dataclasses;

@FunctionalInterface
public interface DataCallbackFunction {
    void onData(MarketData data);
}
