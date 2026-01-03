package com.proyek_softes.demo.pages.targets;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CreateTargetPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By buttonSave = By.xpath("//input[@title='Save' and @id='SAVE']");
    private final By buttonCancel = By.xpath("//input[@title='Cancel [Alt+l]' and @id='CANCEL']");

    private final By selectAccountButton = By.id("btn_account_name");

    private Map<String, By> inputLocators;

    public CreateTargetPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        initializeInputLocators();
    }

    private void initializeInputLocators() {
        inputLocators = new HashMap<>();
        
        // Basic information fields
        inputLocators.put("salutation", By.id("salutation"));
        inputLocators.put("first_name", By.id("first_name"));
        inputLocators.put("last_name", By.id("last_name"));
        inputLocators.put("phone_work", By.id("phone_work"));
        inputLocators.put("title", By.id("title"));
        inputLocators.put("phone_mobile", By.id("phone_mobile"));
        inputLocators.put("department", By.id("department"));
        inputLocators.put("phone_fax", By.id("phone_fax"));
        inputLocators.put("account_name", By.id("account_name"));
        
        // Primary address fields
        inputLocators.put("primary_address_street", By.id("primary_address_street"));
        inputLocators.put("primary_address_city", By.id("primary_address_city"));
        inputLocators.put("primary_address_state", By.id("primary_address_state"));
        inputLocators.put("primary_address_postalcode", By.id("primary_address_postalcode"));
        inputLocators.put("primary_address_country", By.id("primary_address_country"));
        
        // Alternate address fields
        inputLocators.put("alt_address_street", By.id("alt_address_street"));
        inputLocators.put("alt_address_city", By.id("alt_address_city"));
        inputLocators.put("alt_address_state", By.id("alt_address_state"));
        inputLocators.put("alt_address_postalcode", By.id("alt_address_postalcode"));
        inputLocators.put("alt_address_country", By.id("alt_address_country"));
        
        // Other fields
        inputLocators.put("email", By.id("Prospects0emailAddress0"));
        inputLocators.put("description", By.id("description"));
        inputLocators.put("do_not_call", By.id("do_not_call"));
        inputLocators.put("assigned_user_name", By.id("assigned_user_name"));
        inputLocators.put("assigned_user_id", By.id("assigned_user_id"));
    }

    public void save() {
        try {
            WebElement saveButton = wait.until(ExpectedConditions.presenceOfElementLocated(buttonSave));
            ((org.openqa.selenium.JavascriptExecutor) driver)
                    .executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", saveButton);
            Thread.sleep(300);

            try {
                wait.until(ExpectedConditions.elementToBeClickable(buttonSave)).click();
            } catch (org.openqa.selenium.ElementClickInterceptedException e) {
                System.out.println("  Note: Using JavaScript click for Save button due to overlap");
                ((org.openqa.selenium.JavascriptExecutor) driver)
                        .executeScript("arguments[0].click();", saveButton);
            }
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted while saving", e);
        }
    }

    public void cancel() {
        driver.findElement(buttonCancel).click();
    }

    public void addInformationFromData(Map<String, String> data) {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("last_name")));

            // Fill basic information fields
            selectDropdown("salutation", data.get("salutation"));
            fillInputFieldFromData("first_name", data.get("first_name"));
            fillInputFieldFromData("last_name", data.get("last_name"));
            fillInputFieldFromData("phone_work", data.get("phone_work"));
            fillInputFieldFromData("title", data.get("title"));
            fillInputFieldFromData("phone_mobile", data.get("phone_mobile"));
            fillInputFieldFromData("department", data.get("department"));
            fillInputFieldFromData("phone_fax", data.get("phone_fax"));
            fillInputFieldFromData("account_name", data.get("account_name"));
            
            // Fill primary address fields
            fillInputFieldFromData("primary_address_street", data.get("primary_address_street"));
            fillInputFieldFromData("primary_address_city", data.get("primary_address_city"));
            fillInputFieldFromData("primary_address_state", data.get("primary_address_state"));
            fillInputFieldFromData("primary_address_postalcode", data.get("primary_address_postalcode"));
            fillInputFieldFromData("primary_address_country", data.get("primary_address_country"));
            
            // Fill alternate address fields
            fillInputFieldFromData("alt_address_street", data.get("alt_address_street"));
            fillInputFieldFromData("alt_address_city", data.get("alt_address_city"));
            fillInputFieldFromData("alt_address_state", data.get("alt_address_state"));
            fillInputFieldFromData("alt_address_postalcode", data.get("alt_address_postalcode"));
            fillInputFieldFromData("alt_address_country", data.get("alt_address_country"));
            
            // Fill email address
            fillInputFieldFromData("email", data.get("email"));
            
            // Fill description
            fillInputFieldFromData("description", data.get("description"));
            
            // Handle do_not_call checkbox
            if (data.containsKey("do_not_call") && "true".equalsIgnoreCase(data.get("do_not_call"))) {
                WebElement checkbox = driver.findElement(inputLocators.get("do_not_call"));
                if (!checkbox.isSelected()) {
                    checkbox.click();
                    Thread.sleep(200);
                }
            }
            
            // Fill assigned user
            // fillInputFieldFromData("assigned_user_name", data.get("assigned_user_name"));

            Thread.sleep(500);

        } catch (InterruptedException e) {
            throw new RuntimeException("Error filling target form: " + e.getMessage(), e);
        }
    }

    private void fillInputFieldFromData(String fieldName, String value) {
        if (value == null || value.isEmpty()) {
            return;
        }

        By locator = inputLocators.get(fieldName);
        if (locator == null) {
            throw new IllegalArgumentException("No locator defined for field: " + fieldName);
        }

        try {
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            ((org.openqa.selenium.JavascriptExecutor) driver)
                    .executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
            Thread.sleep(200);

            element.clear();
            element.sendKeys(value);
            Thread.sleep(200);
        } catch (InterruptedException e) {
            System.err.println("Error filling field " + fieldName + ": " + e.getMessage());
            throw new RuntimeException("Failed to fill field: " + fieldName, e);
        }
    }

    private void selectDropdown(String fieldName, String value) {
        if (value == null || value.isEmpty()) {
            return;
        }

        By locator = inputLocators.get(fieldName);
        if (locator == null) {
            throw new IllegalArgumentException("No locator defined for field: " + fieldName);
        }

        try {
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            ((org.openqa.selenium.JavascriptExecutor) driver)
                    .executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
            Thread.sleep(200);

            Select select = new Select(element);
            try {
                select.selectByVisibleText(value);
            } catch (Exception e) {
                try {
                    select.selectByValue(value);
                } catch (Exception e2) {
                    System.err.println("Could not select value " + value + " in dropdown " + fieldName);
                    throw e2;
                }
            }
            Thread.sleep(200);
        } catch (Exception e) {
            System.err.println("Error selecting dropdown " + fieldName + ": " + e.getMessage());
            throw new RuntimeException("Failed to select dropdown: " + fieldName, e);
        }
    }

    public boolean isTargetSavedSuccessfully(String targetName) {
        try {
            By pageTitle = By.className("module-title-text");
            String title = wait.until(ExpectedConditions.presenceOfElementLocated(pageTitle)).getText();
            System.out.println("Page title after saving: " + title.toLowerCase());
            System.out.println(targetName.toLowerCase());
            return title.toLowerCase().contains(targetName.toLowerCase());
        } catch (Exception e) {
            return false;
        }
    }
}
