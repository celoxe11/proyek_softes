package com.proyek_softes.demo.pages.locations;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CreateLocationPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By buttonSave = By.xpath("//input[@title='Save' and @id='SAVE']");
    private final By buttonCancel = By.xpath("//input[@title='Cancel [Alt+l]' and @id='CANCEL']");

    private Map<String, By> inputLocators;

    public CreateLocationPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        initializeInputLocators();
    }

    private void initializeInputLocators() {
        inputLocators = new HashMap<>();

        inputLocators.put("name", By.id("name"));
        inputLocators.put("description", By.id("description"));
        inputLocators.put("capacity", By.id("capacity"));

        inputLocators.put("address", By.id("address"));
        inputLocators.put("address_city", By.id("address_city"));
        inputLocators.put("address_postalcode", By.id("address_postalcode"));
        inputLocators.put("address_state", By.id("address_state"));
        inputLocators.put("address_country", By.id("address_country"));
    }

    public void save() {
        try {
            WebElement saveButton = wait.until(ExpectedConditions.presenceOfElementLocated(buttonSave));
            ((org.openqa.selenium.JavascriptExecutor) driver)
                    .executeScript(
                            "arguments[0].scrollIntoView({behavior:'smooth', block:'center'});",
                            saveButton
                    );
            Thread.sleep(300);

            try {
                wait.until(ExpectedConditions.elementToBeClickable(buttonSave)).click();
            } catch (org.openqa.selenium.ElementClickInterceptedException e) {
                System.out.println("  Location: Using JavaScript click for Save button");
                ((org.openqa.selenium.JavascriptExecutor) driver)
                        .executeScript("arguments[0].click();", saveButton);
            }

            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread interrupted while saving location", e);
        }
    }

    public void cancel() {
        driver.findElement(buttonCancel).click();
    }

    public void addInformationFromData(Map<String, String> data) {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("name")));

            fillInputFieldFromData("name", data.get("name"));
            fillInputFieldFromData("description", data.get("description"));
            fillInputFieldFromData("capacity", data.get("capacity"));

            fillInputFieldFromData("address", data.get("address"));
            fillInputFieldFromData("address_city", data.get("address_city"));
            fillInputFieldFromData("address_postalcode", data.get("address_postalcode"));
            fillInputFieldFromData("address_state", data.get("address_state"));
            fillInputFieldFromData("address_country", data.get("address_country"));

            Thread.sleep(500);
            System.out.println("  → Filled location data: " + data.get("name"));

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread interrupted while filling location data", e);
        }
    }

    private void fillInputFieldFromData(String fieldKey, String value) {
        if (value != null && !value.isEmpty()) {
            By locator = inputLocators.get(fieldKey);
            if (locator != null) {
                try {
                    WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
                    element.clear();
                    element.sendKeys(value);
                } catch (Exception e) {
                    System.out.println("  Warning: Could not fill field "
                            + fieldKey + ": " + e.getMessage());
                }
            }
        }
    }

    public boolean isLocationSavedSuccessfully(String locationName) {
        try {
            By pageTitle = By.className("module-title-text");
            String title = wait.until(ExpectedConditions.presenceOfElementLocated(pageTitle)).getText();
            return title.toLowerCase().contains(locationName.toLowerCase());
        } catch (Exception e) {
            return false;
        }
    }

    public boolean hasValidationError() {
        try {
            return driver.findElements(By.className("validation-message")).size() > 0
                    || driver.findElements(By.className("error")).size() > 0;
        } catch (Exception e) {
            return false;
        }
    }
}