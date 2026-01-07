package com.proyek_softes.landing.main.utils;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

import org.testng.annotations.DataProvider;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


public class ImplementationDataProvider {

    private static final String JSON_FILE_PATH = "src/test/resources/implementation_landing/implementation_data.json";

    @DataProvider(name = "implementationData")
    public static Object[][] getImplementationData() {
        try {
            String absolutePath = Paths.get(JSON_FILE_PATH).toAbsolutePath().toString();
            FileReader reader = new FileReader(absolutePath);
            
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
            JsonArray testDataArray = jsonObject.getAsJsonArray("testData");
            
            Object[][] data = new Object[testDataArray.size()][8];
            
            for (int i = 0; i < testDataArray.size(); i++) {
                JsonObject testData = testDataArray.get(i).getAsJsonObject();
                
                data[i][0] = testData.get("fullName").getAsString();
                data[i][1] = testData.get("surname").getAsString();
                data[i][2] = testData.get("emailAddress").getAsString();
                data[i][3] = testData.get("companyName").getAsString();
                data[i][4] = testData.get("country").getAsString();
                data[i][5] = testData.get("checkPrivacyPolicy").getAsBoolean();
                data[i][6] = testData.get("checkMarketingComms").getAsBoolean();
                data[i][7] = testData.get("expectedSuccessMessage").getAsString();
            }
            
            reader.close();
            return data;
            
        } catch (IOException e) {
            System.err.println("Error reading JSON file: " + e.getMessage());
            e.printStackTrace();
            return new Object[0][0];
        }
    }
}
