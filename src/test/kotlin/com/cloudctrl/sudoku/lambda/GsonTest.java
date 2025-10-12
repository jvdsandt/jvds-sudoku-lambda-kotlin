package com.cloudctrl.sudoku.lambda;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class GsonTest {

    @Test
    public void testToMap() {

        Gson gson = new Gson();

        Map data = gson.fromJson("{\n" +
                "  \"key3\": \"value3\",\n" +
                "  \"key2\": \"value2\",\n" +
                "  \"keyN\": [1,2,3],\n" +
                "  \"key1\": \"value1\"\n" +
                "}", HashMap.class);

        assertEquals("value1", data.get("key1"));
        assertNull(data.get("keyX"));
    }
}
