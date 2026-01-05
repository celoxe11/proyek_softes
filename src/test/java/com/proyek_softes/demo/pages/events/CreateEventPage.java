package com.proyek_softes.demo.pages.events;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CreateEventPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By buttonSave = By.xpath("//input[@title='Save' and @id='SAVE']");
    private final By buttonCancel = By.xpath("//input[@title='Cancel [Alt+l]' and @id='CANCEL']");

    private Map<String, By> inputLocators;

    public CreateEventPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        initializeInputLocators();
    }

    private void initializeInputLocators() {
        inputLocators = new HashMap<>();
        inputLocators.put("name", By.id("name"));

        // start date
        inputLocators.put("date_start_date", By.id("date_start_date"));
        inputLocators.put("date_start_hours", By.id("date_start_hours"));
        inputLocators.put("date_start_minutes", By.id("date_start_minutes"));

        // end date
        inputLocators.put("date_end_date", By.id("date_end_date"));
        inputLocators.put("date_end_hours", By.id("date_end_hours"));
        inputLocators.put("date_end_minutes", By.id("date_end_minutes"));

        inputLocators.put("budget", By.id("budget"));
        inputLocators.put("description", By.id("description"));
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
                System.out.println("  Event: Using JavaScript click for Save button");
                ((org.openqa.selenium.JavascriptExecutor) driver)
                        .executeScript("arguments[0].click();", saveButton);
            }

            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread interrupted while saving event", e);
        }
    }

    public void cancel() {
        driver.findElement(buttonCancel).click();
    }

    public void addInformationFromData(Map<String, String> data) {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("name")));

            // name
            fillInputFieldFromData("name", data.get("name"));

            // start date & time
            fillInputFieldFromData("date_start_date", data.get("date_start_date"));
            selectDropdown("date_start_hours", data.get("date_start_hours"));
            selectDropdown("date_start_minutes", data.get("date_start_minutes"));
            selectDropdown("date_start_meridiem", data.get("date_start_meridiem"));

            // duration
            selectDropdown("duration", data.get("duration"));

            // end date & time
            fillInputFieldFromData("date_end_date", data.get("date_end_date"));
            selectDropdown("date_end_hours", data.get("date_end_hours"));
            selectDropdown("date_end_minutes", data.get("date_end_minutes"));
            selectDropdown("date_end_meridiem", data.get("date_end_meridiem"));

            // budget
            fillInputFieldFromData("budget", data.get("budget"));

            // description
            fillInputFieldFromData("description", data.get("description"));

            Thread.sleep(500);
            System.out.println("  → Filled event data: " + data.get("name"));

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread interrupted while filling event data", e);
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

    private void selectDropdown(String fieldKey, String value) {
        if (value != null && !value.isEmpty()) {
            By locator = inputLocators.get(fieldKey);
            if (locator != null) {
                WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
                Select dropdown = new Select(element);
                dropdown.selectByVisibleText(value);
            }
        }
    }

    public boolean isEventSavedSuccessfully(String eventName) {
        try {
            By pageTitle = By.className("module-title-text");
            String title = wait.until(ExpectedConditions.presenceOfElementLocated(pageTitle)).getText();
            return title.toLowerCase().contains(eventName.toLowerCase());
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
