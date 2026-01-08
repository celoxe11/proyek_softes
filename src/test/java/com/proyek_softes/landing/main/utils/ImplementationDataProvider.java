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
                
                // Check which field exists - success or error message
                if (testData.has("expectedSuccessMessage")) {
                    data[i][7] = testData.get("expectedSuccessMessage").getAsString();
                } else if (testData.has("expectedErrorMessage")) {
                    data[i][7] = testData.get("expectedErrorMessage").getAsString();
                } else {
                    data[i][7] = ""; // Default empty string
                }
            }
            
            reader.close();
            return data;
            
        } catch (IOException e) {
            System.err.println("Error reading JSON file: " + e.getMessage());
            e.printStackTrace();
            return new Object[0][0];
        }
    }

    @DataProvider(name = "implementationDataSrv008")
    public static Object[][] getImplementationDataSrv008() {
        try {
            String absolutePath = Paths.get(JSON_FILE_PATH).toAbsolutePath().toString();
            FileReader reader = new FileReader(absolutePath);
            
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
            JsonArray testDataArray = jsonObject.getAsJsonArray("testData");
            
            // Filter hanya untuk SRV-008
            for (int i = 0; i < testDataArray.size(); i++) {
                JsonObject testData = testDataArray.get(i).getAsJsonObject();
                
                if (testData.get("testCaseId").getAsString().equals("SRV-008")) {
                    Object[][] data = new Object[1][8];
                    
                    data[0][0] = testData.get("fullName").getAsString();
                    data[0][1] = testData.get("surname").getAsString();
                    data[0][2] = testData.get("emailAddress").getAsString();
                    data[0][3] = testData.get("companyName").getAsString();
                    data[0][4] = testData.get("country").getAsString();
                    data[0][5] = testData.get("checkPrivacyPolicy").getAsBoolean();
                    data[0][6] = testData.get("checkMarketingComms").getAsBoolean();
                    
                    // For SRV-008, use expectedErrorMessage
                    if (testData.has("expectedErrorMessage")) {
                        data[0][7] = testData.get("expectedErrorMessage").getAsString();
                    } else if (testData.has("expectedSuccessMessage")) {
                        data[0][7] = testData.get("expectedSuccessMessage").getAsString();
                    } else {
                        data[0][7] = "";
                    }
                    
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
