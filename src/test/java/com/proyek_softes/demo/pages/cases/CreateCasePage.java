package com.proyek_softes.demo.pages.cases;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CreateCasePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By buttonSave = By.xpath("//input[@title='Save' and @id='SAVE']");
    private final By buttonCancel = By.xpath("//input[@title='Cancel [Alt+l]' and @id='CANCEL']");

    private final By selectAccountButton = By.id("btn_account_name");

    private Map<String, By> inputLocators;

    public CreateCasePage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        initializeInputLocators();
    }

    private void initializeInputLocators() {
        inputLocators = new HashMap<>();
        inputLocators.put("name", By.id("name"));
        inputLocators.put("priority", By.id("priority"));
        inputLocators.put("state", By.id("state"));
        inputLocators.put("status", By.id("status"));
        inputLocators.put("type", By.id("type"));
        inputLocators.put("account_name", By.id("account_name"));
        inputLocators.put("account_id", By.id("account_id"));
        inputLocators.put("description", By.id("description"));
        inputLocators.put("resolution", By.id("resolution"));
        inputLocators.put("update_text", By.id("update_text"));
        inputLocators.put("internal", By.id("internal"));
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
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("name")));

            // Fill subject/name - required
            fillInputFieldFromData("name", data.get("name"));

            // Select priority (High/Medium/Low)
            String priority = data.get("priority");
            if (priority != null && !priority.isEmpty()) {
                selectDropdown("priority", priority);
            }

            // Select state (Open/Closed)
            String state = data.get("state");
            if (state != null && !state.isEmpty()) {
                selectDropdown("state", state);
                Thread.sleep(500); // Wait for status dropdown to update based on state
            }

            // Select status (depends on state - dynamic dropdown)
            String status = data.get("status");
            if (status != null && !status.isEmpty()) {
                selectDropdown("status", status);
            }

            // Select type (Administration/Product/User)
            String type = data.get("type");
            if (type != null && !type.isEmpty()) {
                selectDropdown("type", type);
            }

            clickSelectAccount();
            // Select account name - required
            if (data.get("account_name") != null && !data.get("account_name").isEmpty()) {
                selectFirstItem(data.get("account_name"));
            } else {
                selectFirstItem();
            }

            // Fill description (TinyMCE rich text editor)
            String description = data.get("description");
            if (description != null && !description.isEmpty()) {
                fillRichTextField("description", description);
            }

            // Fill resolution
            String resolution = data.get("resolution");
            if (resolution != null && !resolution.isEmpty()) {
                fillInputFieldFromData("resolution", resolution);
            }

            // Fill update_text (if provided)
            String updateText = data.get("update_text");
            if (updateText != null && !updateText.isEmpty()) {
                fillInputFieldFromData("update_text", updateText);
            }

            // Check internal checkbox (if provided)
            String internal = data.get("internal");
            if (internal != null && !internal.isEmpty()) {
                WebElement internalCheckbox = driver.findElement(inputLocators.get("internal"));
                boolean shouldBeChecked = internal.equalsIgnoreCase("true") || internal.equals("1");
                if (shouldBeChecked != internalCheckbox.isSelected()) {
                    internalCheckbox.click();
                }
            }

            Thread.sleep(500);

        } catch (InterruptedException e) {
            throw new RuntimeException("Error filling case form: " + e.getMessage(), e);
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

    private void fillRichTextField(String fieldName, String value) {
        if (value == null || value.isEmpty()) {
            return;
        }

        try {
            // Wait for TinyMCE to initialize
            Thread.sleep(1000);

            // TinyMCE iframe ID is fieldName + "_ifr"
            String iframeId = fieldName + "_ifr";
            By iframeLocator = By.id(iframeId);

            // Wait for iframe to be present
            WebElement iframe = wait.until(ExpectedConditions.presenceOfElementLocated(iframeLocator));
            
            // Scroll iframe into view
            ((org.openqa.selenium.JavascriptExecutor) driver)
                    .executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", iframe);
            Thread.sleep(300);

            // Switch to iframe
            driver.switchTo().frame(iframe);

            // Find the body element inside TinyMCE (usually has id="tinymce")
            By bodyLocator = By.id("tinymce");
            WebElement body = wait.until(ExpectedConditions.presenceOfElementLocated(bodyLocator));

            // Clear existing content and input new text
            body.clear();
            body.sendKeys(value);

            // Switch back to main content
            driver.switchTo().defaultContent();

            Thread.sleep(300);
            
            System.out.println("Successfully filled rich text field: " + fieldName);

        } catch (InterruptedException e) {
            driver.switchTo().defaultContent(); // Make sure we switch back
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted while filling rich text field: " + fieldName, e);
        } catch (Exception e) {
            driver.switchTo().defaultContent(); // Make sure we switch back
            System.err.println("Error filling rich text field " + fieldName + ": " + e.getMessage());
            throw new RuntimeException("Failed to fill rich text field: " + fieldName, e);
        }
    }

    private void clickSelectAccount() {
        try {
            WebElement selectButton = wait.until(ExpectedConditions.elementToBeClickable(selectAccountButton));
            ((org.openqa.selenium.JavascriptExecutor) driver)
                    .executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", selectButton);
            Thread.sleep(300);
            selectButton.click();
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException("Failed to click select parent button", e);
        }
    }

    public void selectFirstItem(String name) {
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

    private void selectFirstItem() {
        selectFirstItem(null);
    }

    public boolean isCaseSavedSuccessfully(String caseName) {
        try {
            By pageTitle = By.className("module-title-text");
            String title = wait.until(ExpectedConditions.presenceOfElementLocated(pageTitle)).getText();
            return title.toLowerCase().contains(caseName.toLowerCase());
        } catch (Exception e) {
            return false;
        }
    }

}
