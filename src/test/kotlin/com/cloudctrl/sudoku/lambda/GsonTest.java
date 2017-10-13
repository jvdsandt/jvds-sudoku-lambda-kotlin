package com.cloudctrl.sudoku.lambda;

import com.google.gson.Gson;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
