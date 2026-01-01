package com.proyek_softes.demo.pages.opportunity;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CreateOpportunityPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Common buttons
    private final By buttonSave = By.xpath("//input[@title='Save' and @id='SAVE']");
    private final By buttonCancel = By.xpath("//input[@title='Cancel [Alt+l]' and @id='CANCEL']");

    private final By accountNameButton = By.id("btn_account_name");

    private Map<String, By> inputLocators;

    public CreateOpportunityPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        initializeInputLocators();
    }

    private void initializeInputLocators() {
        inputLocators = new HashMap<>();

        // Basic Information
        inputLocators.put("name", By.id("name"));
        inputLocators.put("accountName", By.id("account_name"));
        inputLocators.put("accountId", By.id("account_id"));
        inputLocators.put("currencyId", By.id("currency_id_select"));
        inputLocators.put("dateClosed", By.id("date_closed"));
        inputLocators.put("amount", By.id("amount"));
        inputLocators.put("opportunityType", By.id("opportunity_type"));
        inputLocators.put("salesStage", By.id("sales_stage"));
        inputLocators.put("leadSource", By.id("lead_source"));
        inputLocators.put("probability", By.id("probability"));
        inputLocators.put("campaignName", By.id("campaign_name"));
        inputLocators.put("campaignId", By.id("campaign_id"));
        inputLocators.put("nextStep", By.id("next_step"));
        inputLocators.put("description", By.id("description"));

        // Assignment
        inputLocators.put("assignedUserName", By.id("assigned_user_name"));
        inputLocators.put("assignedUserId", By.id("assigned_user_id"));
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

    public void addInformationFromData(java.util.Map<String, String> data) {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("name")));

            fillInputFieldFromData("name", data.get("name"));

            clickChooseAccountName();

            if (data.get("accountName") != null && !data.get("accountName").isEmpty()) {
                selectAccountName(data.get("accountName"));
            } else {
                selectAccountName();
            }

            fillInputFieldFromData("dateClosed", data.get("dateClosed"));
            fillInputFieldFromData("amount", data.get("amount"));

            String opportunityType = data.get("opportunityType");
            if (opportunityType != null && !opportunityType.isEmpty()) {
                selectDropdown("opportunityType", opportunityType);
            }

            String salesStage = data.get("salesStage");
            if (salesStage != null && !salesStage.isEmpty()) {
                selectDropdown("salesStage", salesStage);
            }

            String leadSource = data.get("leadSource");
            if (leadSource != null && !leadSource.isEmpty()) {
                selectDropdown("leadSource", leadSource);
            }

            fillInputFieldFromData("probability", data.get("probability"));
            fillInputFieldFromData("campaignName", data.get("campaignName"));
            fillInputFieldFromData("nextStep", data.get("nextStep"));
            fillInputFieldFromData("description", data.get("description"));

            Thread.sleep(500);
            System.out.println("  → Filled opportunity data: " + data.get("name"));

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

    public boolean isOpportunitySavedSuccessfully(String opportunityName) {
        try {
            By pageTitle = By.className("module-title-text");
            String title = wait.until(ExpectedConditions.presenceOfElementLocated(pageTitle)).getText();
            return title.toLowerCase().contains(opportunityName.toLowerCase());
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

    public void clickChooseAccountName() {
        wait.until(ExpectedConditions.elementToBeClickable(accountNameButton)).click();
    }

    public void selectAccountName(String accountName) {
        try {
            // Store the current window handle
            String mainWindow = driver.getWindowHandle();

            // Wait for the popup window to appear
            wait.until(ExpectedConditions.numberOfWindowsToBe(2));

            // Switch to the popup window
            for (String windowHandle : driver.getWindowHandles()) {
                if (!windowHandle.equals(mainWindow)) {
                    driver.switchTo().window(windowHandle);
                    break;
                }
            }

            // Wait for the table to load in the popup
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("table.list.view")));

            // Find and click the account name in the first row
            By firstAccountLink = By.cssSelector("table.list.view tbody tr:first-child td:first-child a");
            WebElement accountLink = wait.until(ExpectedConditions.elementToBeClickable(firstAccountLink));
            accountLink.click();

            // Wait for popup to close and switch back to main window
            wait.until(ExpectedConditions.numberOfWindowsToBe(1));
            driver.switchTo().window(mainWindow);

            // Wait a moment for the account name to populate
            Thread.sleep(500);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted while selecting account name", e);
        }
    }

    // Overloaded method to select first account without specifying name
    public void selectAccountName() {
        selectAccountName(null);
    }
}
