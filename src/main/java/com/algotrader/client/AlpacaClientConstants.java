package main.java.com.algotrader.client;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * Constants for the Alpaca client.
 */
public class AlpacaClientConstants {
    public static String API_KEY;
    public static String SECRET_KEY;

    /**
     * Initializes the API_KEY and SECRET_KEY from a file.
     * @param filePath
     */
    public static void initalizeFromFile(InputStream filePath) {
        Properties envProps = new Properties();
        try{
            envProps.load(filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        API_KEY = envProps.getProperty("APCA_API_KEY_ID");
        SECRET_KEY = envProps.getProperty("APCA_API_SECRET_KEY");
    }
}
