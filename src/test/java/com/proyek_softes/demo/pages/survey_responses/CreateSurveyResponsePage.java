package com.proyek_softes.demo.pages.survey_responses;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CreateSurveyResponsePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By buttonSave = By.id("SAVE");
    private final By buttonCancel = By.id("CANCEL");

    private Map<String, By> inputLocators;

    public CreateSurveyResponsePage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        initializeInputLocators();
    }

    private void initializeInputLocators() {
        inputLocators = new HashMap<>();
        inputLocators.put("name", By.id("name"));
        inputLocators.put("survey_name", By.id("survey_name"));
        inputLocators.put("contact_name", By.id("contact_name"));
        inputLocators.put("description", By.id("description"));
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

    public void fillInformationFromData(Map<String, String> data) {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("name")));

            // Iterate over data and fill corresponding fields
            for (Map.Entry<String, String> entry : data.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

                if (inputLocators.containsKey(key)) {
                    fillInputFieldFromData(key, value);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Error filling survey response data", e);
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
                    System.out.println("  Warning: Could not fill field " + fieldKey + ": " + e.getMessage());
                }
            }
        }
    }

    public boolean isSurveyResponseSavedSuccessfully(String name) {
        try {
            By pageTitleLocator = By.className("module-title-text");
            // Check title first
            try {
                String title = wait.until(ExpectedConditions.presenceOfElementLocated(pageTitleLocator)).getText();
                if (title.toLowerCase().contains(name.toLowerCase())) {
                    return true;
                }
            } catch (Exception ignore) {
            }

            // Fallback: Check body
            try {
                wait.until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector("body"), name));
                return true;
            } catch (Exception e) {
                return false;
            }

        } catch (Exception e) {
            return false;
        }
    }

    public boolean isCreatePageDisplayed() {
        try {
            String expectedUrlPart = "module=SurveyResponses&action=EditView&return_module=SurveyResponses&return_action=DetailView";
            wait.until(ExpectedConditions.urlContains(expectedUrlPart));
            return driver.getCurrentUrl().contains(expectedUrlPart);
        } catch (Exception e) {
            return false;
        }
    }
}
