# java-algo-trading
Algorithmic Trading Platform in Java, based on the [Alpaca API](https://docs.alpaca.markets/docs/getting-started). 
The library offers an easy Java interface to connect to the Alpaca API via its `HTTP` and `WebSocket` protocols. 
It allows for easy trading algorithm development using historical, latest, or high-resolution real-time streamed market data.

# Features
- Get historical and latest stock and crypto `Bars`, `Quotes`, `Trades`, and `Orderbooks` as neatly packaged Java objects
- Stream live stock and crypto data through a web socket connection, supporting custom callbacks for streamed data.
  - Stream data includes resolution from daily `Bars` to `Trades` and `Quotes`.

# Setup
### Prerequisites
- **Bazel**: Install Bazel, instructions can be found on [the official website](https://bazel.build/).
- Clone this repo
- Add a `.env` file to `src/main/resources/` formatted as below:
  ```
  APCA_API_KEY_ID=YOUR_API_KEY
  APCA_API_SECRET_KEY=YOUR_SECRET_KEY
  ```
  You can obtain the keys by signing up on the [Alpaca website](https://alpaca.markets/).

**To Test**

`bazel test //src/test/java/...`

**To Build**

`bazel build AlgoTrader`

**To Run**

`bazel run AlgoTrader`

# Examples
## Using the `AlpacaStockDataClient`
### Historical Data
```java
public static void main(String[] args){
  InputStream inputStream = Driver.class.getResourceAsStream("/.env");

  AlpacaClientConstants.initalizeFromFile(inputStream);
  AlpacaStockDataClient client = new AlpacaStockDataClient(
          AlpacaClientConstants.API_KEY,
          AlpacaClientConstants.SECRET_KEY);
  
  System.out.println(client.getHistoricalBars(
      new String[]{"AAPL"},
      "1D",
      "2021-01-01",
      "2021-01-10",
      10
  ));
}
```

Output: 
```
AAPL
Timestamp               Open    High     Low     Close   Volume      VWAP    NumTransactions
2021-01-04T05:00:00Z    133.52  133.6116 126.76  129.41  158211374   129.737072      1310229
2021-01-05T05:00:00Z    128.89  131.74   128.43  131.01  105863439   130.718871      707583
2021-01-06T05:00:00Z    127.72  131.0499 126.382 126.66  165568781   128.248795      1202579
2021-01-07T05:00:00Z    128.36  131.63   127.86  130.92  118743769   130.183321      718362
2021-01-08T05:00:00Z    132.43  132.63   130.23  132.05  112696090   131.602946      798393
```
### Latest Data
```java
public static void main(String[] args){
  InputStream inputStream = Driver.class.getResourceAsStream("/.env");

  AlpacaClientConstants.initalizeFromFile(inputStream);
  AlpacaStockDataClient client = new AlpacaStockDataClient(
          AlpacaClientConstants.API_KEY,
          AlpacaClientConstants.SECRET_KEY);
  
  System.out.println(client.getLatestQuotes(
      new String[]{"AAPL"}
  ));
}
```

Output:
```
AAPL
Timestamp                       AskPrice  AskSize AskEx   BidPrice  BidSize BidEx
2024-02-02T21:00:00.001575912Z  195.23    1       V       0.0       0       V
```

## Using the `AlpacaCryptoDataClient`

### Historical Data
```java
public static void main(String[] args){
  InputStream inputStream = Driver.class.getResourceAsStream("/.env");

  AlpacaClientConstants.initalizeFromFile(inputStream);

  AlpacaCryptoDataClient cryptoClient = new AlpacaCryptoDataClient(
          AlpacaClientConstants.API_KEY,
          AlpacaClientConstants.SECRET_KEY);
  
  System.out.println(cryptoClient.getHistoricalBars(
      new String[]{"BTC/USD,LTC/USD"},
      "1D",
      "2021-01-01T00:00:00-04:00",
      "2021-01-02T00:00:00-04:00",
      10
  ));
}
```

Output:
```
BTC/USD
Timestamp               Open      High      Low       Close     Volume  VWAP              NumTransactions
2021-01-01T06:00:00Z    29255.71  29682.29  28707.56  29676.79  848     29316.4446253671  29639

LTC/USD
Timestamp               Open      High      Low       Close     Volume  VWAP              NumTransactions
2021-01-01T06:00:00Z    131.03    132.11    123.42    126.07    8656    126.8682294745    2572
```

### Latest Data
```java
public static void main(String[] args){
  InputStream inputStream = Driver.class.getResourceAsStream("/.env");

  AlpacaClientConstants.initalizeFromFile(inputStream);

  AlpacaCryptoDataClient cryptoClient = new AlpacaCryptoDataClient(
          AlpacaClientConstants.API_KEY,
          AlpacaClientConstants.SECRET_KEY);
  
  System.out.println(cryptoClient.getLatestOrderbooks(
      new String[]{"LTC/USD"}
  ));
}
```

Output (Truncated):
```
LTC/USD
Timestamp: 2024-02-04T05:45:47.689758819Z

AskPrice        AskSize
67.91           48.313
68.0913         97.1799
68.494          145.7
69.203          0.735857677
...

BidPrice        BidSize
67.732          49.01
67.53           98.163
67.174          146.46
66.515          0.75171014
...
```
## Using the `AlpacaStreamDataClient`

### Streaming Data
```java
public static void main(String[] args){
  InputStream inputStream = Driver.class.getResourceAsStream("/.env");

  AlpacaClientConstants.initalizeFromFile(inputStream);
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
      Thread.sleep(10000);
  } catch (InterruptedException e) {
      e.printStackTrace();
  }

  streamClient.closeConnection();
}
```

Output:
```
Connection opened
Receiving: {"T":"success","msg":"connected"}
Receiving: {"T":"success","msg":"authenticated"}
{"action":"subscribe","quotes":["BTCUSD"]}
Receiving: {"T":"subscription","trades":[],"quotes":["BTCUSD"],"bars":[],"updatedBars":[],"dailyBars":[],"statuses":[],"lulds":[],"corrections":[],"cancelErrors":[]}

... [Quotes Data] ...
```
