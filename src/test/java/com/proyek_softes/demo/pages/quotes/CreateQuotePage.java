package com.proyek_softes.demo.pages.quotes;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CreateQuotePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By buttonSave = By.xpath("//input[@title='Save' and @id='SAVE']");
    private final By buttonCancel = By.xpath("//input[@title='Cancel [Alt+l]' and @id='CANCEL']");
    private final By selectOpportunityButton = By.id("btn_opportunity");
    private final By selectBillingAccountButton = By.id("btn_billing_account");
    private final By selectBillingContactButton = By.id("btn_billing_contact");

    private Map<String, By> inputLocators;

    public CreateQuotePage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        initializeInputLocators();
    }

    private void initializeInputLocators() {
        inputLocators = new HashMap<>();

        // Overview fields
        inputLocators.put("name", By.id("name"));
        inputLocators.put("opportunity", By.id("opportunity"));
        inputLocators.put("opportunityId", By.id("opportunity_id"));
        inputLocators.put("stage", By.id("stage"));
        inputLocators.put("expiration", By.id("expiration"));
        inputLocators.put("invoiceStatus", By.id("invoice_status"));
        inputLocators.put("term", By.id("term"));
        inputLocators.put("approvalStatus", By.id("approval_status"));
        inputLocators.put("approvalIssue", By.id("approval_issue"));

        // Address fields
        inputLocators.put("billingAccount", By.id("billing_account"));
        inputLocators.put("billingAccountId", By.id("billing_account_id"));
        inputLocators.put("billingContact", By.id("billing_contact"));
        inputLocators.put("billingContactId", By.id("billing_contact_id"));
        inputLocators.put("billingAddressStreet", By.id("billing_address_street"));
        inputLocators.put("billingAddressCity", By.id("billing_address_city"));
        inputLocators.put("billingAddressState", By.id("billing_address_state"));
        inputLocators.put("billingAddressPostalCode", By.id("billing_address_postalcode"));
        inputLocators.put("billingAddressCountry", By.id("billing_address_country"));

        inputLocators.put("shippingAddressStreet", By.id("shipping_address_street"));
        inputLocators.put("shippingAddressCity", By.id("shipping_address_city"));
        inputLocators.put("shippingAddressState", By.id("shipping_address_state"));
        inputLocators.put("shippingAddressPostalCode", By.id("shipping_address_postalcode"));
        inputLocators.put("shippingAddressCountry", By.id("shipping_address_country"));
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

            // Fill basic information
            fillInputFieldFromData("name", data.get("name"));

            // Select opportunity
            clickSelectOpportunity();
            if (data.get("opportunity") != null && !data.get("opportunity").isEmpty()) {
                selectFirstItem(data.get("opportunity"));
            } else {
                selectFirstItem();
            }

            // Select stage
            String stage = data.get("stage");
            if (stage != null && !stage.isEmpty()) {
                selectDropdown("stage", stage);
            }

            // Fill expiration date
            fillInputFieldFromData("expiration", data.get("expiration"));

            // Select invoice status
            String invoiceStatus = data.get("invoiceStatus");
            if (invoiceStatus != null && !invoiceStatus.isEmpty()) {
                selectDropdown("invoiceStatus", invoiceStatus);
            }

            // Select term
            String term = data.get("term");
            if (term != null && !term.isEmpty()) {
                selectDropdown("term", term);
            }

            // Select approval status
            String approvalStatus = data.get("approvalStatus");
            if (approvalStatus != null && !approvalStatus.isEmpty()) {
                selectDropdown("approvalStatus", approvalStatus);
            }

            fillInputFieldFromData("approvalIssue", data.get("approvalIssue"));

            // Fill billing address
            // select account
            clickSelectBillingAccount();
            if (data.get("billingAccount") != null && !data.get("billingAccount").isEmpty()) {
                selectFirstItem(data.get("billingAccount"));
            } else {
                selectFirstItem();
            }

            // select contact
            clickSelectBillingContact();
            if (data.get("billingContact") != null && !data.get("billingContact").isEmpty()) {
                selectFirstItem(data.get("billingContact"));
            } else {
                selectFirstItem();
            }

            fillInputFieldFromData("billingAddressStreet", data.get("billingAddressStreet"));
            fillInputFieldFromData("billingAddressCity", data.get("billingAddressCity"));
            fillInputFieldFromData("billingAddressState", data.get("billingAddressState"));
            fillInputFieldFromData("billingAddressPostalCode", data.get("billingAddressPostalCode"));
            fillInputFieldFromData("billingAddressCountry", data.get("billingAddressCountry"));

            // Fill shipping address
            fillInputFieldFromData("shippingAddressStreet", data.get("shippingAddressStreet"));
            fillInputFieldFromData("shippingAddressCity", data.get("shippingAddressCity"));
            fillInputFieldFromData("shippingAddressState", data.get("shippingAddressState"));
            fillInputFieldFromData("shippingAddressPostalCode", data.get("shippingAddressPostalCode"));
            fillInputFieldFromData("shippingAddressCountry", data.get("shippingAddressCountry"));

            Thread.sleep(500);
            System.out.println("  → Filled quote data: " + data.get("name"));

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

    public boolean isQuoteSavedSuccessfully(String quoteName) {
        try {
            By pageTitle = By.className("module-title-text");
            String title = wait.until(ExpectedConditions.presenceOfElementLocated(pageTitle)).getText();
            return title.toLowerCase().contains(quoteName.toLowerCase());
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

    public void clickSelectOpportunity() {
        wait.until(ExpectedConditions.elementToBeClickable(selectOpportunityButton)).click();
    }

    public void clickSelectBillingAccount() {
        wait.until(ExpectedConditions.elementToBeClickable(selectBillingAccountButton)).click();
    }

    public void clickSelectBillingContact() {
        wait.until(ExpectedConditions.elementToBeClickable(selectBillingContactButton)).click();
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
