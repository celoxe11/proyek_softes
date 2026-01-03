package com.proyek_softes.demo.pages.meetings;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ScheduleMeetingPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By buttonSave = By.xpath("//input[@title='Save [Alt+a]' and @id='SAVE_HEADER']");
    private final By buttonCancel = By.xpath("//input[@title='Cancel [Alt+l]' and @id='CANCEL']");

    private final By selectParentButton = By.id("btn_parent_name");

    private final By addReminderButton = By.id("reminder_add_btn");
    private final By reminderView = By.id("reminder_view");
    private final By reminderItem = By.className("reminder_item");
    private final By popupCheckbox = By.className("popup_chkbox");
    private final By emailCheckbox = By.className("email_chkbox");
    private final By timerSelPopup = By.className("timer_sel_popup");
    private final By timerSelEmail = By.className("timer_sel_email");

    private final By inviteesSearchFirstName = By.id("search_first_name");
    // private final By inviteesSearchLastName = By.id("search_last_name");
    // private final By inviteesSearchEmail = By.id("search_email");
    private final By inviteesSearchButton = By.id("invitees_search");
    private final By inviteesAddButton1 = By.id("invitees_add_1");
    private final By inviteesListDiv = By.id("list_div_win");

    private Map<String, By> inputLocators;

    public ScheduleMeetingPage(WebDriver driver, WebDriverWait wait) {
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
        inputLocators.put("date_end_date", By.id("date_end_date"));
        inputLocators.put("date_end_hours", By.id("date_end_hours"));
        inputLocators.put("date_end_minutes", By.id("date_end_minutes"));
        inputLocators.put("date_end_meridiem", By.id("date_end_meridiem"));
        inputLocators.put("location", By.id("location"));
        inputLocators.put("duration", By.id("duration"));
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

    public void addInformationFromData(Map<String, String> data) {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("name")));

            // Fill name (subject) - required
            fillInputFieldFromData("name", data.get("name"));

            // Select status (Planned/Held/Not Held)
            String status = data.get("status");
            if (status != null && !status.isEmpty()) {
                selectDropdown("status", status);
            }

            // Fill start date and time - required
            fillInputFieldFromData("date_start_date", data.get("date_start_date"));
            String dateStartHours = data.get("date_start_hours");
            if (dateStartHours != null && !dateStartHours.isEmpty()) {
                selectDropdown("date_start_hours", dateStartHours);
            }
            String dateStartMinutes = data.get("date_start_minutes");
            if (dateStartMinutes != null && !dateStartMinutes.isEmpty()) {
                selectDropdown("date_start_minutes", dateStartMinutes);
            }
            String dateStartMeridiem = data.get("date_start_meridiem");
            if (dateStartMeridiem != null && !dateStartMeridiem.isEmpty()) {
                selectDropdown("date_start_meridiem", dateStartMeridiem);
            }

            // Select parent type (Account/Contact/etc.)
            String parentType = data.get("parent_type");
            if (parentType != null && !parentType.isEmpty()) {
                selectDropdown("parent_type", parentType);
            }

            // Choose related to
            clickSelectParent();
            if (data.get("parent_name") != null && !data.get("parent_name").isEmpty()) {
                selectFirstItem(data.get("parent_name"));
            } else {
                selectFirstItem();
            }

            // Fill end date and time - required
            String dateEndDate = data.get("date_end_date");
            if (dateEndDate != null && !dateEndDate.isEmpty()) {
                fillInputFieldFromData("date_end_date", dateEndDate);
            }
            String dateEndHours = data.get("date_end_hours");
            if (dateEndHours != null && !dateEndHours.isEmpty()) {
                selectDropdown("date_end_hours", dateEndHours);
            }
            String dateEndMinutes = data.get("date_end_minutes");
            if (dateEndMinutes != null && !dateEndMinutes.isEmpty()) {
                selectDropdown("date_end_minutes", dateEndMinutes);
            }
            String dateEndMeridiem = data.get("date_end_meridiem");
            if (dateEndMeridiem != null && !dateEndMeridiem.isEmpty()) {
                selectDropdown("date_end_meridiem", dateEndMeridiem);
            }

            // Fill location
            String location = data.get("location");
            if (location != null && !location.isEmpty()) {
                fillInputFieldFromData("location", location);
            }

            // Select duration
            String duration = data.get("duration");
            if (duration != null && !duration.isEmpty()) {
                selectDropdown("duration", duration);
            }

            // Fill description
            String description = data.get("description");
            if (description != null && !description.isEmpty()) {
                fillInputFieldFromData("description", description);
            }

            String hasReminder = data.get("has_reminder");
            if (hasReminder != null && hasReminder.equalsIgnoreCase("true")) {
                setupReminder(
                        data.get("reminder_popup"),
                        data.get("reminder_email"),
                        data.get("reminder_popup_time"),
                        data.get("reminder_email_time")
                );
            }

            // Add invitees if specified in test data
            String inviteeFirstName = data.get("invitee_first_name");
            if (inviteeFirstName != null && !inviteeFirstName.isEmpty()) {
                addInvitees(inviteeFirstName);
            }

            Thread.sleep(500);

        } catch (InterruptedException e) {
            throw new RuntimeException("Error filling meeting form: " + e.getMessage(), e);
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

    private void clickSelectParent() {
        try {
            WebElement selectButton = wait.until(ExpectedConditions.elementToBeClickable(selectParentButton));
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

    public boolean isMeetingSavedSuccessfully(String meetingName) {
        try {
            By pageTitle = By.className("module-title-text");
            String title = wait.until(ExpectedConditions.presenceOfElementLocated(pageTitle)).getText();
            return title.toLowerCase().contains(meetingName.toLowerCase());
        } catch (Exception e) {
            return false;
        }
    }

    public void clickAddReminder() {
        wait.until(ExpectedConditions.elementToBeClickable(addReminderButton)).click();
    }

    /**
     * Check if a reminder card already exists
     */
    private boolean hasReminderCard() {
        try {
            WebElement reminderViewElement = driver.findElement(reminderView);
            return reminderViewElement.findElements(reminderItem).size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Setup reminder with popup and/or email options
     *
     * @param enablePopup "true" to enable popup reminder
     * @param enableEmail "true" to enable email reminder
     * @param popupTime Time option for popup (e.g., "1800" for 30 minutes
     * prior)
     * @param emailTime Time option for email (e.g., "3600" for 1 hour prior)
     */
    public void setupReminder(String enablePopup, String enableEmail, String popupTime, String emailTime) {
        try {
            // Check if reminder card exists, if not add one
            if (!hasReminderCard()) {
                System.out.println("  → No reminder card found, clicking Add reminder button");
                clickAddReminder();
                Thread.sleep(500);
            }

            // Get the first reminder item
            WebElement reminderViewElement = wait.until(ExpectedConditions.presenceOfElementLocated(reminderView));
            WebElement firstReminder = reminderViewElement.findElement(reminderItem);

            // Handle popup checkbox
            WebElement popupChkbox = firstReminder.findElement(popupCheckbox);
            boolean isPopupChecked = popupChkbox.isSelected();
            boolean shouldEnablePopup = enablePopup != null && enablePopup.equalsIgnoreCase("true");

            if (shouldEnablePopup && !isPopupChecked) {
                popupChkbox.click();
                System.out.println("  → Enabled popup reminder");
            } else if (!shouldEnablePopup && isPopupChecked) {
                popupChkbox.click();
                System.out.println("  → Disabled popup reminder");
            }

            // Set popup time if enabled
            if (shouldEnablePopup && popupTime != null && !popupTime.isEmpty()) {
                WebElement popupTimeSelect = firstReminder.findElement(timerSelPopup);
                Select popupDropdown = new Select(popupTimeSelect);
                popupDropdown.selectByValue(popupTime);
                System.out.println("  → Set popup time to: " + popupTime);
            }

            // Handle email checkbox
            WebElement emailChkbox = firstReminder.findElement(emailCheckbox);
            boolean isEmailChecked = emailChkbox.isSelected();
            boolean shouldEnableEmail = enableEmail != null && enableEmail.equalsIgnoreCase("true");

            if (shouldEnableEmail && !isEmailChecked) {
                emailChkbox.click();
                System.out.println("  → Enabled email reminder");
            } else if (!shouldEnableEmail && isEmailChecked) {
                emailChkbox.click();
                System.out.println("  → Disabled email reminder");
            }

            // Set email time if enabled
            if (shouldEnableEmail && emailTime != null && !emailTime.isEmpty()) {
                WebElement emailTimeSelect = firstReminder.findElement(timerSelEmail);
                Select emailDropdown = new Select(emailTimeSelect);
                emailDropdown.selectByValue(emailTime);
                System.out.println("  → Set email time to: " + emailTime);
            }

            Thread.sleep(500);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted while setting up reminder", e);
        }
    }

    /**
     * Setup reminder with default settings (popup enabled, 30 minutes prior)
     */
    public void setupDefaultReminder() {
        setupReminder("true", "false", "1800", null);
    }

    /**
     * Add invitees to the call by searching for first name and adding the first
     * result
     *
     * @param inviteeFirstName The first name to search for
     */
    public void addInvitees(String inviteeFirstName) {
        try {
            // Scroll to the invitees section
            WebElement firstNameField = wait.until(ExpectedConditions.presenceOfElementLocated(inviteesSearchFirstName));
            ((org.openqa.selenium.JavascriptExecutor) driver)
                    .executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", firstNameField);
            Thread.sleep(300);

            // Enter first name in search field
            firstNameField.clear();
            firstNameField.sendKeys(inviteeFirstName);
            System.out.println("  → Searching for invitee with first name: " + inviteeFirstName);

            // Click search button
            WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(inviteesSearchButton));
            searchButton.click();

            // Wait for search results to load
            Thread.sleep(1000);
            wait.until(ExpectedConditions.presenceOfElementLocated(inviteesListDiv));

            // Click the first "Add" button in the results
            WebElement addButton = wait.until(ExpectedConditions.elementToBeClickable(inviteesAddButton1));
            addButton.click();
            System.out.println("  → Added first invitee from search results");

            Thread.sleep(500);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted while adding invitees", e);
        } catch (Exception e) {
            System.out.println("  Warning: Could not add invitee: " + e.getMessage());
        }
    }
}
