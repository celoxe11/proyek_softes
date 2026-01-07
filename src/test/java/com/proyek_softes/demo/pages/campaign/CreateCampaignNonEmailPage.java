package com.proyek_softes.demo.pages.campaign;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CreateCampaignNonEmailPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By buttonSave = By.xpath("//input[@title='Save' and @id='SAVE']");
    private final By buttonCancel = By.xpath("//input[@title='Cancel' and @id='wiz_cancel_button']");
    private final By buttonNextStep1 = By.id("wiz_next_button");
    private final By buttonNextStep2 = By.id("wiz_submit_finish_button");

    private final By firstExistingTargetList = By.xpath("(//li[@class='target-list-item'])[1]/a");
    private final By createTargetListButton = By.xpath("//input[@type='button' and @value='Create' and contains(@onclick, 'add_target')]");

    private final By targetMessage = By.id("target_message");

    private Map<String, By> inputLocators;

    public CreateCampaignNonEmailPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        initializeInputLocators();
    }

    private void initializeInputLocators() {
        inputLocators = new HashMap<>();

        // campaign header inputs
        inputLocators.put("name", By.id("name"));
        inputLocators.put("assignedUserName", By.id("assigned_user_name"));
        inputLocators.put("status", By.id("status"));
        inputLocators.put("campaignType", By.id("campaign_type"));
        inputLocators.put("description", By.id("wiz_content"));

        // campaign budget inputs (step 2)
        inputLocators.put("budget", By.id("budget"));
        inputLocators.put("actualCost", By.id("actual_cost"));
        inputLocators.put("expectedRevenue", By.id("expected_revenue"));
        inputLocators.put("expectedCost", By.id("expected_cost"));
        inputLocators.put("currencyId", By.id("currency_id"));
        inputLocators.put("impressions", By.id("impressions"));
        inputLocators.put("objective", By.id("objective"));

        // Target Lists (Step 3)
        inputLocators.put("targetListName", By.id("target_list_name"));
        inputLocators.put("targetListType", By.id("target_list_type"));
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

    public void next(int step) {
        try {
            By buttonNext;
            switch (step) {
                case 1, 3:
                    buttonNext = buttonNextStep1;
                    break;
                case 2, 4:
                    buttonNext = buttonNextStep2;
                    break;
                default:
                    throw new IllegalArgumentException("Invalid step: " + step);
            }

            WebElement nextButton = wait.until(ExpectedConditions.presenceOfElementLocated(buttonNext));
            ((org.openqa.selenium.JavascriptExecutor) driver)
                    .executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", nextButton);
            Thread.sleep(300);

            try {
                wait.until(ExpectedConditions.elementToBeClickable(buttonNext)).click();
            } catch (org.openqa.selenium.ElementClickInterceptedException e) {
                System.out.println("  Note: Using JavaScript click for Next button due to overlap");
                ((org.openqa.selenium.JavascriptExecutor) driver)
                        .executeScript("arguments[0].click();", nextButton);
            }

            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted while saving", e);
        }
    }

    public void addInformationFromData(java.util.Map<String, String> data) {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("name")));

            /**
             * Step 1: Campaign Details
             */
            // Fill campaign header fields
            fillInputFieldFromData("name", data.get("name"));
            fillInputFieldFromData("assignedUserName", data.get("assignedUserName"));
            fillInputFieldFromData("description", data.get("description"));

            String status = data.get("status");
            if (status != null && !status.isEmpty()) {
                selectDropdown("status", status);
            }

            next(1);
            Thread.sleep(1000);

            /**
             * Step 2: Campaign Budget
             */
            // Fill campaign budget fields
            fillInputFieldFromData("budget", data.get("budget"));
            fillInputFieldFromData("actualCost", data.get("actualCost"));
            fillInputFieldFromData("expectedRevenue", data.get("expectedRevenue"));
            fillInputFieldFromData("expectedCost", data.get("expectedCost"));
            fillInputFieldFromData("impressions", data.get("impressions"));
            fillInputFieldFromData("objective", data.get("objective"));

            String currencyId = data.get("currencyId");
            if (currencyId != null && !currencyId.isEmpty()) {
                selectDropdown("currencyId", currencyId);
            }

            Thread.sleep(500);
            System.out.println("  → Filled campaign data: " + data.get("name"));

            next(1);
            Thread.sleep(1000);

            /**
             * Step 3: Target Lists
             */
            // Click first existing target list if available
            try {
                java.util.List<WebElement> existingLists = driver.findElements(By.xpath("//li[@class='target-list-item']"));
                if (!existingLists.isEmpty()) {
                    WebElement firstList = wait.until(ExpectedConditions.elementToBeClickable(firstExistingTargetList));
                    ((org.openqa.selenium.JavascriptExecutor) driver)
                            .executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", firstList);
                    Thread.sleep(300);
                    firstList.click();
                    Thread.sleep(500);
                    System.out.println("  → Clicked first existing target list");
                }
            } catch (Exception e) {
                System.out.println("  Note: No existing target lists found or unable to click: " + e.getMessage());
            }

            // Create new target list
            String targetListName = data.get("targetListName");
            String targetListType = data.get("targetListType");

            if (targetListName != null && !targetListName.isEmpty()) {
                // Scroll to create target list section
                WebElement targetNameField = wait.until(ExpectedConditions.presenceOfElementLocated(inputLocators.get("targetListName")));
                ((org.openqa.selenium.JavascriptExecutor) driver)
                        .executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", targetNameField);
                Thread.sleep(300);

                fillInputFieldFromData("targetListName", targetListName);
                Thread.sleep(300);

                if (targetListType != null && !targetListType.isEmpty()) {
                    selectDropdown("targetListType", targetListType);
                    Thread.sleep(300);
                }

                // Click Create button
                WebElement createButton = wait.until(ExpectedConditions.elementToBeClickable(createTargetListButton));
                ((org.openqa.selenium.JavascriptExecutor) driver)
                        .executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", createButton);
                Thread.sleep(300);

                try {
                    createButton.click();
                } catch (org.openqa.selenium.ElementClickInterceptedException e) {
                    System.out.println("  Note: Using JavaScript click for Create button");
                    ((org.openqa.selenium.JavascriptExecutor) driver)
                            .executeScript("arguments[0].click();", createButton);
                }

                Thread.sleep(500);
                System.out.println("  → Created new target list: " + targetListName);
            }

            next(2);
            Thread.sleep(1000);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted while adding information from data", e);
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
                dropdown.selectByVisibleText(value);
            }
        }
    }

    public String getTargetMessage() {
        try {
            WebElement messageElement = wait.until(ExpectedConditions.presenceOfElementLocated(targetMessage));
            return messageElement.getText();
        } catch (Exception e) {
            return null;
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
