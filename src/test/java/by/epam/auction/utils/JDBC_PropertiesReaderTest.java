package by.epam.auction.utils;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

 public class JDBC_PropertiesReaderTest {

     Map<String, String> toCompare = new HashMap<>();
     Map<String, String> toCompareWith = new HashMap<>();

    @Test
    public void shouldReturnTrueWhenGetPropertiesFromConfig() {
        toCompareWith.put("JDBC_DRIVER", "a");
        toCompareWith.put("JDBC_DB_URL", "b");
        toCompareWith.put("JDBC_USER", "c");
        toCompareWith.put("JDBC_PASS", "d");
        toCompare = JDBC_PropertiesReader.getProperties("src/test/resources/testDB.properties");
        assertEquals(toCompare, toCompareWith);
    }
}