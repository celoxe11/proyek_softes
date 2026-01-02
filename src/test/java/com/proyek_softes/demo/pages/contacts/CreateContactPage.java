package com.proyek_softes.demo.pages.contacts;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CreateContactPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Common buttons
    private final By buttonSave = By.xpath("//input[@title='Save' and @id='SAVE']");
    private final By buttonCancel = By.xpath("//input[@title='Cancel [Alt+l]' and @id='CANCEL']");
    private final By addEmailButton = By.cssSelector("button.email-address-add-button[title='Add Email Address ']");
    private final By removeEmail1Button = By.id("Contacts0removeButton1");
    private final By selectAccountButton = By.id("btn_account_name");
    private final By selectReportToButton = By.id("btn_report_to_name");

    private Map<String, By> overviewInputLocators;
    private Map<String, By> moreInformationInputLocators;

    public CreateContactPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;

        // Initialize input locators
        initializeOverviewInputLocators();
        initializeMoreInformationInputLocators();
    }

    private void initializeOverviewInputLocators() {
        overviewInputLocators = new HashMap<>();

        // Basic Information
        overviewInputLocators.put("salutation", By.id("salutation"));
        overviewInputLocators.put("firstName", By.id("first_name"));
        overviewInputLocators.put("lastName", By.id("last_name"));
        overviewInputLocators.put("officePhone", By.id("phone_work"));
        overviewInputLocators.put("mobilePhone", By.id("phone_mobile"));
        overviewInputLocators.put("jobTitle", By.id("title"));
        overviewInputLocators.put("department", By.id("department"));
        overviewInputLocators.put("accountName", By.id("account_name"));
        overviewInputLocators.put("accountId", By.id("account_id"));
        overviewInputLocators.put("fax", By.id("phone_fax"));

        // Email Address
        overviewInputLocators.put("email", By.id("Contacts0emailAddress0"));

        // Primary Address
        overviewInputLocators.put("primaryAddressStreet", By.id("primary_address_street"));
        overviewInputLocators.put("primaryAddressCity", By.id("primary_address_city"));
        overviewInputLocators.put("primaryAddressState", By.id("primary_address_state"));
        overviewInputLocators.put("primaryAddressPostalCode", By.id("primary_address_postalcode"));
        overviewInputLocators.put("primaryAddressCountry", By.id("primary_address_country"));

        // Alternative Address
        overviewInputLocators.put("altAddressStreet", By.id("alt_address_street"));
        overviewInputLocators.put("altAddressCity", By.id("alt_address_city"));
        overviewInputLocators.put("altAddressState", By.id("alt_address_state"));
        overviewInputLocators.put("altAddressPostalCode", By.id("alt_address_postalcode"));
        overviewInputLocators.put("altAddressCountry", By.id("alt_address_country"));
        overviewInputLocators.put("altAddressCopyCheckbox", By.id("alt_checkbox"));

        // Other Fields
        overviewInputLocators.put("description", By.id("description"));
        overviewInputLocators.put("assignedUserName", By.id("assigned_user_name"));
        overviewInputLocators.put("assignedUserId", By.id("assigned_user_id"));
    }

    private void initializeMoreInformationInputLocators() {
        moreInformationInputLocators = new HashMap<>();

        // Lead Source
        moreInformationInputLocators.put("leadSource", By.id("lead_source"));

        // Relationships
        moreInformationInputLocators.put("reportsToName", By.id("report_to_name"));
        moreInformationInputLocators.put("reportsToId", By.id("reports_to_id"));
        moreInformationInputLocators.put("btnSelectReportsTo", By.id("btn_report_to_name"));
        moreInformationInputLocators.put("btnClearReportsTo", By.id("btn_clr_report_to_name"));

        // Campaign
        moreInformationInputLocators.put("campaignName", By.id("campaign_name"));
        moreInformationInputLocators.put("campaignId", By.id("campaign_id"));
        moreInformationInputLocators.put("btnSelectCampaign", By.id("btn_campaign_name"));
        moreInformationInputLocators.put("btnClearCampaign", By.id("btn_clr_campaign_name"));

        // Select buttons for Account
        moreInformationInputLocators.put("btnSelectAccount", By.id("btn_account_name"));
        moreInformationInputLocators.put("btnClearAccount", By.id("btn_clr_account_name"));
    }

    public void save() {
        try {
            WebElement saveButton = wait.until(ExpectedConditions.presenceOfElementLocated(buttonSave));

            // Scroll the button into view
            ((org.openqa.selenium.JavascriptExecutor) driver)
                    .executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", saveButton);

            // Wait a moment for scroll to complete
            Thread.sleep(300);

            // Try to click normally first
            try {
                wait.until(ExpectedConditions.elementToBeClickable(buttonSave)).click();
            } catch (org.openqa.selenium.ElementClickInterceptedException e) {
                // If normal click fails, use JavaScript click
                System.out.println("  Note: Using JavaScript click for Save button due to overlap");
                ((org.openqa.selenium.JavascriptExecutor) driver)
                        .executeScript("arguments[0].click();", saveButton);
            }

            // wait a moment for save to process
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
            // Wait for the form to be ready by ensuring the last name field is present
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("last_name")));

            // Fill Basic Information
            String salutation = data.get("salutation");
            if (salutation != null && !salutation.isEmpty()) {
                selectDropdown("salutation", salutation, overviewInputLocators);
            }

            fillInputFieldFromData("firstName", data.get("firstName"));
            fillInputFieldFromData("lastName", data.get("lastName"));
            fillInputFieldFromData("officePhone", data.get("officePhone"));
            fillInputFieldFromData("mobilePhone", data.get("mobilePhone"));
            fillInputFieldFromData("jobTitle", data.get("jobTitle"));
            fillInputFieldFromData("department", data.get("department"));

            // Select Account Name
            clickChooseAccountName();
            if (data.get("accountName") != null && !data.get("accountName").isEmpty()) {
                selectAccountName(data.get("accountName"));
            } else {
                selectAccountName();
            }

            fillInputFieldFromData("fax", data.get("fax"));

            // Handle email addresses - first email goes in existing field, additional
            // emails need add button
            if (data.get("email") != null && !data.get("email").isEmpty()) {
                String[] emails = data.get("email").split(",");
                for (int i = 0; i < emails.length; i++) {
                    String email = emails[i].trim();
                    if (i == 0) {
                        // Fill the first email in the existing field
                        fillInputFieldFromData("email", email);
                    } else {
                        // Add new email fields for additional emails
                        addEmailWithValue(email, i);
                    }
                }
            }

            // Fill Primary Address
            fillInputFieldFromData("primaryAddressStreet", data.get("primaryAddressStreet"));
            fillInputFieldFromData("primaryAddressCity", data.get("primaryAddressCity"));
            fillInputFieldFromData("primaryAddressState", data.get("primaryAddressState"));
            fillInputFieldFromData("primaryAddressPostalCode", data.get("primaryAddressPostalCode"));
            fillInputFieldFromData("primaryAddressCountry", data.get("primaryAddressCountry"));

            // Fill Alternative Address
            fillInputFieldFromData("altAddressStreet", data.get("altAddressStreet"));
            fillInputFieldFromData("altAddressCity", data.get("altAddressCity"));
            fillInputFieldFromData("altAddressState", data.get("altAddressState"));
            fillInputFieldFromData("altAddressPostalCode", data.get("altAddressPostalCode"));
            fillInputFieldFromData("altAddressCountry", data.get("altAddressCountry"));

            // Fill Description
            fillInputFieldFromData("description", data.get("description"));

            // Fill More Information (dropdowns)
            String leadSource = data.get("leadSource");
            if (leadSource != null && !leadSource.isEmpty()) {
                selectDropdown("leadSource", leadSource, moreInformationInputLocators);
            }

            // Select Reports To
            clickChooseReportTo();
            if (data.get("reportsToName") != null && !data.get("reportsToName").isEmpty()) {
                selectAccountName(data.get("reportsToName"));
            } else {
                selectAccountName();
            }

            Thread.sleep(500);
            System.out.println("  → Filled contact data: " + data.get("firstName") + " " + data.get("lastName"));

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted while adding information from data", e);
        }
    }

    private void clickChooseAccountName() {
        wait.until(ExpectedConditions.elementToBeClickable(selectAccountButton)).click();
    }

    private void clickChooseReportTo() {
        wait.until(ExpectedConditions.elementToBeClickable(selectReportToButton)).click();
    }

    private void fillInputFieldFromData(String fieldKey, String value) {
        if (value != null && !value.isEmpty()) {
            By locator = overviewInputLocators.get(fieldKey);
            if (locator == null) {
                locator = moreInformationInputLocators.get(fieldKey);
            }
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

    private void selectDropdown(String fieldKey, String value, Map<String, By> locatorMap) {
        if (value != null && !value.isEmpty()) {
            By locator = locatorMap.get(fieldKey);
            if (locator != null) {
                WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
                Select dropdown = new Select(element);
                dropdown.selectByVisibleText(value);
            }
        }
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

            // Check if there's an alert and handle it
            try {
                wait.until(ExpectedConditions.alertIsPresent());
                driver.switchTo().alert().accept();
                System.out.println("  → Alert detected and accepted");
            } catch (Exception e) {
                // No alert present, continue normally
            }

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

    public void addEmail() {
        try {
            WebElement addButton = wait.until(ExpectedConditions.elementToBeClickable(addEmailButton));
            addButton.click();
            Thread.sleep(500); // Wait for email field to be added
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted while adding email", e);
        }
    }

    public void addEmailWithValue(String email, int index) {
        try {
            // Click the add email button to add a new email field
            addEmail();

            // Find the newly added email field using the index
            // The first email is index 0 (Contacts0emailAddress0), second is index 1, etc.
            By newEmailField = By.id("Contacts0emailAddress" + index);
            WebElement emailElement = wait.until(ExpectedConditions.presenceOfElementLocated(newEmailField));
            emailElement.clear();
            emailElement.sendKeys(email);

        } catch (Exception e) {
            throw new RuntimeException("Failed to add email with value: " + email + " at index " + index, e);
        }
    }

    // Backward compatibility method - adds email at index 1 (second email field)
    public void addEmailWithValue(String email) {
        addEmailWithValue(email, 1);
    }

    public void removeEmail() {
        try {
            WebElement removeButton = wait.until(ExpectedConditions.elementToBeClickable(removeEmail1Button));
            removeButton.click();
            Thread.sleep(500); // Wait for email field to be removed
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted while removing email", e);
        }
    }

    public boolean isContactSavedSuccessfully(String contactName) {
        try {
            By pageTitle = By.className("module-title-text");
            String title = wait.until(ExpectedConditions.presenceOfElementLocated(pageTitle)).getText();
            return title.toLowerCase().contains(contactName.toLowerCase());
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
