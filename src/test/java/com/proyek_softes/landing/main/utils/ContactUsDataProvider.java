package com.proyek_softes.landing.main.utils;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

import org.testng.annotations.DataProvider;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


public class ContactUsDataProvider {

    private static final String JSON_FILE_PATH = "src/test/resources/contact_us_landing/contact_us_data.json";

    @DataProvider(name = "contactUsData")
    public static Object[][] getContactUsData() {
        try {
            String absolutePath = Paths.get(JSON_FILE_PATH).toAbsolutePath().toString();
            FileReader reader = new FileReader(absolutePath);
            
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
            JsonArray testDataArray = jsonObject.getAsJsonArray("testData");
            
            Object[][] data = new Object[testDataArray.size()][11];
            
            for (int i = 0; i < testDataArray.size(); i++) {
                JsonObject testData = testDataArray.get(i).getAsJsonObject();
                
                data[i][0] = testData.get("firstName").getAsString();
                data[i][1] = testData.get("lastName").getAsString();
                data[i][2] = testData.get("businessEmail").getAsString();
                data[i][3] = testData.get("phone").getAsString();
                data[i][4] = testData.get("company").getAsString();
                data[i][5] = testData.get("jobTitle").getAsString();
                data[i][6] = testData.get("country").getAsString();
                data[i][7] = testData.get("message").getAsString();
                data[i][8] = testData.get("checkPrivacyPolicy").getAsBoolean();
                data[i][9] = testData.get("checkMarketingComms").getAsBoolean();
                data[i][10] = testData.get("expectedSuccessMessage").getAsString();
            }
            
            reader.close();
            return data;
            
        } catch (IOException e) {
            System.err.println("Error reading JSON file: " + e.getMessage());
            e.printStackTrace();
            return new Object[0][0];
        }
    }
    
    @DataProvider(name = "contactUsDataAbt013")
    public static Object[][] getContactUsDataAbt013() {
        try {
            String absolutePath = Paths.get(JSON_FILE_PATH).toAbsolutePath().toString();
            FileReader reader = new FileReader(absolutePath);
            
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
            JsonArray testDataArray = jsonObject.getAsJsonArray("testData");
            
            // Find only ABT-013 data
            for (int i = 0; i < testDataArray.size(); i++) {
                JsonObject testData = testDataArray.get(i).getAsJsonObject();
                
                if (testData.get("testCaseId").getAsString().equals("ABT-013")) {
                    Object[][] data = new Object[1][11];
                    data[0][0] = testData.get("firstName").getAsString();
                    data[0][1] = testData.get("lastName").getAsString();
                    data[0][2] = testData.get("businessEmail").getAsString();
                    data[0][3] = testData.get("phone").getAsString();
                    data[0][4] = testData.get("company").getAsString();
                    data[0][5] = testData.get("jobTitle").getAsString();
                    data[0][6] = testData.get("country").getAsString();
                    data[0][7] = testData.get("message").getAsString();
                    data[0][8] = testData.get("checkPrivacyPolicy").getAsBoolean();
                    data[0][9] = testData.get("checkMarketingComms").getAsBoolean();
                    data[0][10] = testData.get("expectedSuccessMessage").getAsString();
                    
                    reader.close();
                    return data;
                }
            }
            
            reader.close();
            return new Object[0][0];
            
        } catch (IOException e) {
            System.err.println("Error reading JSON file: " + e.getMessage());
            e.printStackTrace();
            return new Object[0][0];
        }
    }
}
