package test.java.com.algotrader.client;

import static org.junit.Assert.*;
import org.junit.Test;

import java.io.InputStream;

import main.java.com.algotrader.client.AlpacaClientConstants;

public class AlpacaClientConstantsTest {

    /**
     * Tests {@link AlpacaClientConstants#initalizeFromFile(InputStream)}
     */
    @Test
    public void testInitalizeFromFile() {
        InputStream inputStream = AlpacaClientConstantsTest.class.getResourceAsStream("/.test_env");

        AlpacaClientConstants.initalizeFromFile(inputStream);

        assertEquals("TEST_KEY", AlpacaClientConstants.API_KEY);
        assertEquals("TEST_SECRET", AlpacaClientConstants.SECRET_KEY);
    }
}
