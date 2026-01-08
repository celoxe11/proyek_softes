package com.proyek_softes.demo.pages;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProfilePage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By buttonSave = By.id("SAVE_HEADER");
    private By pageTitle = By.className("module-title-text");

    private Map<String, By> inputLocators;

    public ProfilePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        initializeInputLocators();
    }
    
    private void initializeInputLocators() {
        inputLocators = new HashMap<>();
        
        // Basic Information
        inputLocators.put("first_name", By.id("first_name"));
        inputLocators.put("last_name", By.id("last_name"));
        inputLocators.put("title", By.id("title"));
        inputLocators.put("department", By.id("department"));
        
        // Phone Numbers
        inputLocators.put("phone_work", By.id("phone_work"));
        inputLocators.put("phone_mobile", By.id("phone_mobile"));
        inputLocators.put("phone_home", By.id("phone_home"));
        inputLocators.put("phone_other", By.id("phone_other"));
        inputLocators.put("phone_fax", By.id("phone_fax"));
        
        // Address Information
        inputLocators.put("address_street", By.id("address_street"));
        inputLocators.put("address_city", By.id("address_city"));
        inputLocators.put("address_state", By.id("address_state"));
        inputLocators.put("address_postalcode", By.id("address_postalcode"));
        inputLocators.put("address_country", By.id("address_country"));
        
        // Messenger Information
        inputLocators.put("messenger_type", By.id("messenger_type"));
        inputLocators.put("messenger_id", By.id("messenger_id"));
        
        // Description
        inputLocators.put("description", By.id("description"));
    }
    
    public void addInformationFromData(Map<String, String> data) {
        for (Map.Entry<String, String> entry : data.entrySet()) {
            String fieldName = entry.getKey();
            String value = entry.getValue();
            
            if (value == null || value.isEmpty()) {
                continue;
            }
            
            try {
                By locator = inputLocators.get(fieldName);
                if (locator != null) {
                    WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
                    
                    // Check if it's a dropdown/select element
                    if (fieldName.equals("messenger_type")) {
                        Select select = new Select(element);
                        select.selectByVisibleText(value);
                    } else {
                        // Clear and enter text
                        element.clear();
                        element.sendKeys(value);
                    }
                    
                    System.out.println("Successfully filled field: " + fieldName + " with value: " + value);
                } else {
                    System.out.println("Warning: Locator not found for field: " + fieldName);
                }
            } catch (Exception e) {
                System.out.println("Error filling field " + fieldName + ": " + e.getMessage());
            }
        }
    }
    
    public void clickSaveButton() {
        try {
            // First scroll to top of page where save button is located
            ((org.openqa.selenium.JavascriptExecutor) driver)
                    .executeScript("window.scrollTo(0, 0);");
            Thread.sleep(500);
            
            // Wait for save button to be present
            WebElement saveButton = wait.until(ExpectedConditions.presenceOfElementLocated(buttonSave));
            
            // Make sure it's visible and clickable
            wait.until(ExpectedConditions.visibilityOf(saveButton));
            
            // Use JavaScript click for better cross-browser compatibility
            ((org.openqa.selenium.JavascriptExecutor) driver)
                    .executeScript("arguments[0].click();", saveButton);
            
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted while saving", e);
        }
    }
    
    public String getFieldValue(String fieldName) {
        try {
            By locator = inputLocators.get(fieldName);
            if (locator != null) {
                WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
                
                if (fieldName.equals("messenger_type")) {
                    Select select = new Select(element);
                    return select.getFirstSelectedOption().getText();
                } else {
                    return element.getAttribute("value");
                }
            }
        } catch (Exception e) {
            System.out.println("Error getting field value for " + fieldName + ": " + e.getMessage());
        }
        return null;
    }
    
    public boolean isOnProfileEditPage() {
        try {
            WebElement editViewForm = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("EditView")));
            return editViewForm.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isProfileSavedSuccessfully(String expectedName) {
        try {
            WebElement titleElement = wait.until(ExpectedConditions.presenceOfElementLocated(pageTitle));
            String titleText = titleElement.getText();
            System.out.println("Page title after save: " + titleText);
            System.out.println("Expected name: " + expectedName);
            return titleText.toLowerCase().contains(expectedName.toLowerCase());
        } catch (Exception e) {
            return false;
        }
        
    }
}
