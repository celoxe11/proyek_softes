package com.proyek_softes.demo.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonDataReader {

    /**
     * Read JSON file from resources folder
     * 
     * @param filePath path relative to resources folder (e.g.,
     *                 "testdata/account_data.json")
     * @return JsonObject containing the parsed JSON
     */
    public static JsonObject readJsonFile(String filePath) {
        try {
            InputStream inputStream = JsonDataReader.class.getClassLoader().getResourceAsStream(filePath);
            if (inputStream == null) {
                throw new RuntimeException("File not found: " + filePath);
            }
            Reader reader = new InputStreamReader(inputStream);
            return JsonParser.parseReader(reader).getAsJsonObject();
        } catch (Exception e) {
            throw new RuntimeException("Failed to read JSON file: " + filePath, e);
        }
    }

    /**
     * Get data array from JSON file for DataProvider
     * 
     * @param filePath  path to JSON file
     * @param arrayName name of the JSON array to retrieve
     * @return Object[][] suitable for TestNG DataProvider
     */
    public static Object[][] getDataProviderArray(String filePath, String arrayName) {
        JsonObject jsonObject = readJsonFile(filePath);
        JsonArray jsonArray = jsonObject.getAsJsonArray(arrayName);

        if (jsonArray == null || jsonArray.size() == 0) {
            return new Object[0][0];
        }

        List<Object[]> dataList = new ArrayList<>();

        for (JsonElement element : jsonArray) {
            JsonObject obj = element.getAsJsonObject();
            Map<String, String> dataMap = new HashMap<>();

            for (String key : obj.keySet()) {
                JsonElement value = obj.get(key);
                dataMap.put(key, convertJsonValueToString(value));
            }

            dataList.add(new Object[] { dataMap });
        }

        return dataList.toArray(new Object[0][0]);
    }

    /**
     * Get specific test data by testCase name
     * 
     * @param filePath     path to JSON file
     * @param arrayName    name of the JSON array
     * @param testCaseName name of the test case to find
     * @return Map containing the test data
     */
    public static Map<String, String> getTestDataByName(String filePath, String arrayName, String testCaseName) {
        JsonObject jsonObject = readJsonFile(filePath);
        JsonArray jsonArray = jsonObject.getAsJsonArray(arrayName);

        for (JsonElement element : jsonArray) {
            JsonObject obj = element.getAsJsonObject();
            if (obj.has("testCase") && obj.get("testCase").getAsString().equals(testCaseName)) {
                Map<String, String> dataMap = new HashMap<>();
                for (String key : obj.keySet()) {
                    JsonElement value = obj.get(key);
                    dataMap.put(key, convertJsonValueToString(value));
                }
                return dataMap;
            }
        }

        throw new RuntimeException("Test case not found: " + testCaseName);
    }

    /**
     * Get all test data as List of Maps
     * 
     * @param filePath  path to JSON file
     * @param arrayName name of the JSON array
     * @return List of Maps containing test data
     */
    public static List<Map<String, String>> getAllTestData(String filePath, String arrayName) {
        JsonObject jsonObject = readJsonFile(filePath);
        JsonArray jsonArray = jsonObject.getAsJsonArray(arrayName);

        List<Map<String, String>> dataList = new ArrayList<>();

        for (JsonElement element : jsonArray) {
            JsonObject obj = element.getAsJsonObject();
            Map<String, String> dataMap = new HashMap<>();

            for (String key : obj.keySet()) {
                JsonElement value = obj.get(key);
                dataMap.put(key, convertJsonValueToString(value));
            }

            dataList.add(dataMap);
        }

        return dataList;
    }

    /**
     * Convert JsonElement to String, handling arrays by joining with commas
     * 
     * @param value JsonElement to convert
     * @return String representation of the value
     */
    private static String convertJsonValueToString(JsonElement value) {
        if (value.isJsonNull()) {
            return "";
        } else if (value.isJsonArray()) {
            // Handle arrays by joining elements with commas
            JsonArray array = value.getAsJsonArray();
            List<String> elements = new ArrayList<>();
            for (JsonElement element : array) {
                if (!element.isJsonNull()) {
                    elements.add(element.getAsString());
                }
            }
            return String.join(",", elements);
        } else {
            return value.getAsString();
        }
    }
}
