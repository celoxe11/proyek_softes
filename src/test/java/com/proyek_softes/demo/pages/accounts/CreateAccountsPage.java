package com.proyek_softes.demo.pages.accounts;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CreateAccountsPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private Actions actions;

    // locators can be added here
    private By pageTitle = By.className("module-title-text");
    private By listTable = By.xpath(
            "//*[contains(@class, 'list') and contains(@class, 'view') and contains(@class, 'table-responsive')]");
    private By buttonSave = By.xpath("//input[@title='Save' and @id='SAVE']");
    private By buttonCancel = By.xpath("//input[@title='Cancel [Alt+l]' and @id='CANCEL']");
    private By addEmailButton = By.cssSelector("button.email-address-add-button[title='Add Email Address ']");
    private By removeEmail1Button = By.id("Accounts0removeButton1");

    // Duplicate account warning locator - looks for "Save Account" h2 heading
    private By duplicateWarningMessage = By.xpath("//h2[contains(text(), 'Save Account')]");
    private By duplicateWarningButton = By.xpath("//input[@title='Save' and @onclick='this.form.action.value=\'Save\';']");

    private Map<String, By> overviewInputLocators;
    private Map<String, By> moreInformationInputLocators;

    public CreateAccountsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        this.actions = new Actions(driver);

        // Initialize input locators
        initializeOverviewInputLocators();
        initializMoreInformationInputLocators();
    }

    private void initializeOverviewInputLocators() {
        overviewInputLocators = new HashMap<>();

        // Basic Information
        overviewInputLocators.put("name", By.id("name"));
        overviewInputLocators.put("officePhone", By.id("phone_office"));
        overviewInputLocators.put("website", By.id("website"));
        overviewInputLocators.put("fax", By.id("phone_fax"));

        // Email Address
        overviewInputLocators.put("email", By.id("Accounts0emailAddress0"));

        // Billing Address
        overviewInputLocators.put("billingStreet", By.id("billing_address_street"));
        overviewInputLocators.put("billingCity", By.id("billing_address_city"));
        overviewInputLocators.put("billingState", By.id("billing_address_state"));
        overviewInputLocators.put("billingPostalCode", By.id("billing_address_postalcode"));
        overviewInputLocators.put("billingCountry", By.id("billing_address_country"));

        // Shipping Address
        overviewInputLocators.put("shippingStreet", By.id("shipping_address_street"));
        overviewInputLocators.put("shippingCity", By.id("shipping_address_city"));
        overviewInputLocators.put("shippingState", By.id("shipping_address_state"));
        overviewInputLocators.put("shippingPostalCode", By.id("shipping_address_postalcode"));
        overviewInputLocators.put("shippingCountry", By.id("shipping_address_country"));
        overviewInputLocators.put("shippingCopyCheckbox", By.id("shipping_checkbox"));

        // Other Fields
        overviewInputLocators.put("description", By.id("description"));
        overviewInputLocators.put("assignedUserName", By.id("assigned_user_name"));
        overviewInputLocators.put("assignedUserId", By.id("assigned_user_id"));
    }

    private void initializMoreInformationInputLocators() {
        moreInformationInputLocators = new HashMap<>();

        // Account Type and Industry
        moreInformationInputLocators.put("accountType", By.id("account_type"));
        moreInformationInputLocators.put("industry", By.id("industry"));

        // Financial Information
        moreInformationInputLocators.put("annualRevenue", By.id("annual_revenue"));
        moreInformationInputLocators.put("employees", By.id("employees"));

        // Relationships
        moreInformationInputLocators.put("parentName", By.id("parent_name"));
        moreInformationInputLocators.put("parentId", By.id("parent_id"));
        moreInformationInputLocators.put("btnSelectParent", By.id("btn_parent_name"));
        moreInformationInputLocators.put("btnClearParent", By.id("btn_clr_parent_name"));

        // Campaign
        moreInformationInputLocators.put("campaignName", By.id("campaign_name"));
        moreInformationInputLocators.put("campaignId", By.id("campaign_id"));
        moreInformationInputLocators.put("btnSelectCampaign", By.id("btn_campaign_name"));
        moreInformationInputLocators.put("btnClearCampaign", By.id("btn_clr_campaign_name"));
    }

    public void cancel() {
        driver.findElement(buttonCancel).click();
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

            // Wait a bit for page to respond
            Thread.sleep(1000);

            // Check if duplicate warning appears
            if (isDuplicateWarningDisplayed()) {
                System.out.println("  Note: Duplicate account warning detected, proceeding to save anyway...");
                saveDuplicateAccount();
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted while saving", e);
        }
    }

    public boolean isDuplicateWarningDisplayed() {
        try {
            // Create a short wait (3 seconds) to allow page to load
            WebDriverWait shortWait = new WebDriverWait(driver, java.time.Duration.ofSeconds(3));

            // Wait for element to be visible, returns null if timeout
            WebElement warningElement = shortWait.until(driver -> {
                var elements = driver.findElements(duplicateWarningMessage);
                if (!elements.isEmpty() && elements.get(0).isDisplayed()) {
                    return elements.get(0);
                }
                return null;
            });

            return warningElement != null;
        } catch (Exception e) {
            return false;
        }
    }

    public void saveDuplicateAccount() {
        System.out.println("  → Confirming save for potential duplicate account...");

        wait.until(ExpectedConditions.presenceOfElementLocated(duplicateWarningButton));

        // Scroll the button into view
        ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", duplicateWarningButton);

        // Try to click normally first
        try {
            wait.until(ExpectedConditions.elementToBeClickable(duplicateWarningButton)).click();
        } catch (org.openqa.selenium.ElementClickInterceptedException e) {
            // If normal click fails, use JavaScript click
            System.out.println("  Note: Using JavaScript click for Save button due to overlap");
            ((org.openqa.selenium.JavascriptExecutor) driver)
                    .executeScript("arguments[0].click();", duplicateWarningButton);
        }

        System.out.println("  ✓ Duplicate account saved successfully");
    }

    public void addInformationFromData(java.util.Map<String, String> data) {
        try {
            // Wait for the form to be ready by ensuring the name field is present
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("name")));

            // Fill Basic Information
            fillInputFieldFromData("name", data.get("name"));
            fillInputFieldFromData("officePhone", data.get("officePhone"));
            fillInputFieldFromData("website", data.get("website"));
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

            // Fill Billing Address
            fillInputFieldFromData("billingStreet", data.get("billingStreet"));
            fillInputFieldFromData("billingCity", data.get("billingCity"));
            fillInputFieldFromData("billingState", data.get("billingState"));
            fillInputFieldFromData("billingPostalCode", data.get("billingPostalCode"));
            fillInputFieldFromData("billingCountry", data.get("billingCountry"));

            // Fill Description
            fillInputFieldFromData("description", data.get("description"));

            // Fill More Information (dropdowns)
            String accountType = data.get("accountType");
            if (accountType != null && !accountType.isEmpty()) {
                selectDropdown("accountType", accountType, moreInformationInputLocators);
            }

            String industry = data.get("industry");
            if (industry != null && !industry.isEmpty()) {
                selectDropdown("industry", industry, moreInformationInputLocators);
            }

            fillInputFieldFromData("annualRevenue", data.get("annualRevenue"));
            fillInputFieldFromData("employees", data.get("employees"));

            Thread.sleep(500);
            System.out.println("  → Filled account data: " + data.get("name"));

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted while adding information from data", e);
        }
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
            // The first email is index 0 (Accounts0emailAddress0), second is index 1, etc.
            By newEmailField = By.id("Accounts0emailAddress" + index);
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

    public String cekValidationMessage() {
        // Cek apakah ada pesan validasi untuk field yang wajib diisi
        By validationMessage = By.className("validation-message");
        WebElement messageElement = wait.until(ExpectedConditions.presenceOfElementLocated(validationMessage));
        String messageText = messageElement.getText();
        return messageText;
    }

    public boolean isAccountSavedSuccessfully(String accountName) {
        try {
            String title = wait.until(ExpectedConditions.presenceOfElementLocated(pageTitle)).getText();
            return title.toLowerCase().contains(accountName.toLowerCase());
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

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public boolean isOnDetailViewPage() {
        try {
            String currentUrl = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("DetailView"))).getText();
            return currentUrl.contains("action=DetailView");
        } catch (Exception e) {
            return false;
        }
    }
}
