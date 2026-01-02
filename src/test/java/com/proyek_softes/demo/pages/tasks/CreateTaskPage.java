package com.proyek_softes.demo.pages.tasks;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CreateTaskPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By buttonSave = By.xpath("//input[@title='Save' and @id='SAVE']");
    private final By buttonCancel = By.xpath("//input[@title='Cancel [Alt+l]' and @id='CANCEL']");

    private final By selectRelatedToButton = By.id("btn_parent_name");
    private final By selectContactButton = By.id("btn_contact_name");

    private Map<String, By> inputLocators;

    public CreateTaskPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        initializeInputLocators();
    }

    private void initializeInputLocators() {
        inputLocators = new HashMap<>();
        inputLocators.put("name", By.id("name"));
        inputLocators.put("status", By.id("status"));
        inputLocators.put("date_start_date", By.id("date_start_date"));
        inputLocators.put("date_start_hours", By.id("date_start_hours"));
        inputLocators.put("date_start_minutes", By.id("date_start_minutes"));
        inputLocators.put("date_start_meridiem", By.id("date_start_meridiem"));
        inputLocators.put("parent_type", By.id("parent_type"));
        inputLocators.put("parent_name", By.id("parent_name"));
        inputLocators.put("parent_id", By.id("parent_id"));
        inputLocators.put("date_due_date", By.id("date_due_date"));
        inputLocators.put("date_due_hours", By.id("date_due_hours"));
        inputLocators.put("date_due_minutes", By.id("date_due_minutes"));
        inputLocators.put("date_due_meridiem", By.id("date_due_meridiem"));
        inputLocators.put("contact_name", By.id("contact_name"));
        inputLocators.put("contact_id", By.id("contact_id"));
        inputLocators.put("priority", By.id("priority"));
        inputLocators.put("description", By.id("description"));
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

    public void addInformationFromData(java.util.Map<String, String> data) {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("name")));

            // Fill name
            fillInputFieldFromData("name", data.get("name"));

            // Select status
            String status = data.get("status");
            if (status != null && !status.isEmpty()) {
                selectDropdown("status", status);
            }

            // Select related to
            clickSelectRelatedTo();
            if (data.get("parent_name") != null && !data.get("parent_name").isEmpty()) {
                selectFirstItem(data.get("parent_name"));
            } else {
                selectFirstItem();
            }

            // select contact name
            clickSelectContact();
            if (data.get("contact_name") != null && !data.get("contact_name").isEmpty()) {
                selectFirstItem(data.get("contact_name"));
            } else {
                selectFirstItem();
            }

            // Fill start date fields
            fillInputFieldFromData("date_start_date", data.get("date_start_date"));

            String startHours = data.get("date_start_hours");
            if (startHours != null && !startHours.isEmpty()) {
                selectDropdown("date_start_hours", startHours);
            }

            String startMinutes = data.get("date_start_minutes");
            if (startMinutes != null && !startMinutes.isEmpty()) {
                selectDropdown("date_start_minutes", startMinutes);
            }

            String startMeridiem = data.get("date_start_meridiem");
            if (startMeridiem != null && !startMeridiem.isEmpty()) {
                selectDropdown("date_start_meridiem", startMeridiem);
            }

            // Select parent type
            String parentType = data.get("parent_type");
            if (parentType != null && !parentType.isEmpty()) {
                selectDropdown("parent_type", parentType);
            }

            // Fill parent name
            fillInputFieldFromData("parent_name", data.get("parent_name"));

            // Fill due date fields
            fillInputFieldFromData("date_due_date", data.get("date_due_date"));

            String dueHours = data.get("date_due_hours");
            if (dueHours != null && !dueHours.isEmpty()) {
                selectDropdown("date_due_hours", dueHours);
            }

            String dueMinutes = data.get("date_due_minutes");
            if (dueMinutes != null && !dueMinutes.isEmpty()) {
                selectDropdown("date_due_minutes", dueMinutes);
            }

            String dueMeridiem = data.get("date_due_meridiem");
            if (dueMeridiem != null && !dueMeridiem.isEmpty()) {
                selectDropdown("date_due_meridiem", dueMeridiem);
            }

            // Fill contact name
            fillInputFieldFromData("contact_name", data.get("contact_name"));

            // Select priority
            String priority = data.get("priority");
            if (priority != null && !priority.isEmpty()) {
                selectDropdown("priority", priority);
            }

            // Fill description
            fillInputFieldFromData("description", data.get("description"));

            // Fill assigned user
            fillInputFieldFromData("assigned_user_name", data.get("assigned_user_name"));

            Thread.sleep(500);
            System.out.println("  → Filled task data: " + data.get("name"));

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

    public boolean isTaskSavedSuccessfully(String taskName) {
        try {
            By pageTitle = By.className("module-title-text");
            String title = wait.until(ExpectedConditions.presenceOfElementLocated(pageTitle)).getText();
            return title.toLowerCase().contains(taskName.toLowerCase());
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

    public void clickSelectContact() {
        wait.until(ExpectedConditions.elementToBeClickable(selectContactButton)).click();
    }

    public void clickSelectRelatedTo() {
        wait.until(ExpectedConditions.elementToBeClickable(selectRelatedToButton)).click();
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

    // Overloaded method to select first account without specifying name
    public void selectFirstItem() {
        selectFirstItem(null);
    }
}
