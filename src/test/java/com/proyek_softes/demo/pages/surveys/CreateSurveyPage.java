package com.proyek_softes.demo.pages.surveys;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CreateSurveyPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By buttonSave = By.xpath("//input[@title='Save' and @id='SAVE']");
    private final By buttonCancel = By.xpath("//input[@title='Cancel [Alt+l]' and @id='CANCEL']");
    private final By newQuestionButton = By.id("newQuestionButton");

    private Map<String, By> inputLocators;

    public CreateSurveyPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        initializeInputLocators();
    }

    private void initializeInputLocators() {
        inputLocators = new HashMap<>();

        // Survey fields
        inputLocators.put("name", By.id("name"));
        inputLocators.put("status", By.id("status"));
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

    public void fillInformationFromData(Map<String, String> data) {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("name")));

            // Fill basic information
            fillInputFieldFromData("name", data.get("name"));
            fillInputFieldFromData("description", data.get("description"));

            // Select status
            String status = data.get("status");
            if (status != null && !status.isEmpty()) {
                selectDropdown("status", status);
            }

            // Handle dynamic questions
            int i = 0;
            while (true) {
                String key = "survey_questions_names[" + i + "]";
                String questionText = data.get(key);
                if (questionText == null) {
                    break;
                }

                // Click 'New Question' button to add input
                try {
                    WebElement addButton = wait.until(ExpectedConditions.elementToBeClickable(newQuestionButton));
                    // Scroll to button to ensure visibility
                    ((org.openqa.selenium.JavascriptExecutor) driver)
                            .executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});",
                                    addButton);
                    addButton.click();

                    // Wait for the new input to appear
                    // Using naming convention common in PHP/HTML forms for arrays
                    By questionInputLocator = By.name(key);

                    // Sometimes there is a slight delay/animation
                    WebElement questionInput = wait
                            .until(ExpectedConditions.visibilityOfElementLocated(questionInputLocator));
                    questionInput.clear();
                    questionInput.sendKeys(questionText);

                } catch (Exception e) {
                    System.out.println("  Warning: Could not add/fill question " + key + ": " + e.getMessage());
                }

                i++;
            }

            Thread.sleep(500);
            System.out.println("  → Filled survey data: " + data.get("name"));

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted while filling information from data", e);
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

    private void selectDropdown(String fieldKey, String value) {
        if (value != null && !value.isEmpty()) {
            By locator = inputLocators.get(fieldKey);
            if (locator != null) {
                WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
                Select dropdown = new Select(element);
                try {
                    dropdown.selectByVisibleText(value);
                } catch (Exception e) {
                    try {
                        // Fallback: Try selecting by value (often lower case or same as text)
                        dropdown.selectByValue(value);
                    } catch (Exception e2) {
                        try {
                            // Fallback: Try selecting by value lower case
                            dropdown.selectByValue(value.toLowerCase());
                        } catch (Exception e3) {
                            // Fallback: Iterate options and find approximate match
                            boolean found = false;
                            for (WebElement option : dropdown.getOptions()) {
                                if (option.getText().equalsIgnoreCase(value) || option.getText().contains(value)) {
                                    option.click();
                                    found = true;
                                    break;
                                }
                            }
                            if (!found) {
                                System.out
                                        .println("  Warning: Could not select '" + value + "' in dropdown " + fieldKey);
                            }
                        }
                    }
                }
            }
        }
    }

    public boolean isSurveySavedSuccessfully(String surveyName) {
        try {
            By pageTitle = By.cssSelector(".module-title-text");
            String title = wait.until(ExpectedConditions.presenceOfElementLocated(pageTitle)).getText();
            return title.toLowerCase().contains(surveyName.toLowerCase());
        } catch (Exception e) {
            return false;
        }
    }
}
