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
    private By cancelButton = By.id("CANCEL");
    private By saveButton = By.id("SAVE");
    private By addEmailButton = By.id("Accounts6_email_widget_add");
    private By removeEmail1Button = By.id("Accounts0removeButton1");

    private Map<String, By> overviewInputLocators;
    private Map<String, By> moreInformationInputLocators;

    // Dummy data for testing
    private Map<String, String> dummyOverviewData;
    private Map<String, String> dummyMoreInformationData;

    public CreateAccountsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        this.actions = new Actions(driver);

        // Initialize input locators
        initializeOverviewInputLocators();
        initializMoreInformationInputLocators();

        // Initialize dummy data
        initializeDummyData();
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

    private void initializeDummyData() {
        // Initialize Overview dummy data
        dummyOverviewData = new HashMap<>();
        dummyOverviewData.put("name", "Tech Innovations Inc");
        dummyOverviewData.put("officePhone", "+1-555-0123");
        dummyOverviewData.put("website", "http://www.techinnovations.com");
        dummyOverviewData.put("fax", "+1-555-0124");
        dummyOverviewData.put("email", "contact@techinnovations.com");
        dummyOverviewData.put("billingStreet", "123 Innovation Drive");
        dummyOverviewData.put("billingCity", "San Francisco");
        dummyOverviewData.put("billingState", "California");
        dummyOverviewData.put("billingPostalCode", "94102");
        dummyOverviewData.put("billingCountry", "USA");
        dummyOverviewData.put("shippingStreet", "456 Tech Boulevard");
        dummyOverviewData.put("shippingCity", "San Jose");
        dummyOverviewData.put("shippingState", "California");
        dummyOverviewData.put("shippingPostalCode", "95110");
        dummyOverviewData.put("shippingCountry", "USA");
        dummyOverviewData.put("description", "Leading technology solutions provider specializing in AI and cloud computing.");

        // Initialize More Information dummy data
        dummyMoreInformationData = new HashMap<>();
        dummyMoreInformationData.put("accountType", "Customer");
        dummyMoreInformationData.put("industry", "Technology");
        dummyMoreInformationData.put("annualRevenue", "5000000");
        dummyMoreInformationData.put("employees", "250");
        dummyMoreInformationData.put("parentName", "");
        dummyMoreInformationData.put("campaignName", "");
    }

    public void cancel() {
        driver.findElement(cancelButton).click();
    }

    public void save() {
        driver.findElement(saveButton).click();
    }

    public void addInformation() {
        addOverviewInformation();
        addMoreInformation();
    }

    public void addOverviewInformation() {
        try {
            // Fill Basic Information
            fillInputField("name", dummyOverviewData.get("name"), overviewInputLocators);
            fillInputField("officePhone", dummyOverviewData.get("officePhone"), overviewInputLocators);
            fillInputField("website", dummyOverviewData.get("website"), overviewInputLocators);
            fillInputField("fax", dummyOverviewData.get("fax"), overviewInputLocators);

            // Fill Email Address (first email field is already present)
            fillInputField("email", dummyOverviewData.get("email"), overviewInputLocators);

            // Fill Billing Address
            fillInputField("billingStreet", dummyOverviewData.get("billingStreet"), overviewInputLocators);
            fillInputField("billingCity", dummyOverviewData.get("billingCity"), overviewInputLocators);
            fillInputField("billingState", dummyOverviewData.get("billingState"), overviewInputLocators);
            fillInputField("billingPostalCode", dummyOverviewData.get("billingPostalCode"), overviewInputLocators);
            fillInputField("billingCountry", dummyOverviewData.get("billingCountry"), overviewInputLocators);

            // Fill Shipping Address
            fillInputField("shippingStreet", dummyOverviewData.get("shippingStreet"), overviewInputLocators);
            fillInputField("shippingCity", dummyOverviewData.get("shippingCity"), overviewInputLocators);
            fillInputField("shippingState", dummyOverviewData.get("shippingState"), overviewInputLocators);
            fillInputField("shippingPostalCode", dummyOverviewData.get("shippingPostalCode"), overviewInputLocators);
            fillInputField("shippingCountry", dummyOverviewData.get("shippingCountry"), overviewInputLocators);

            // Fill Description
            fillInputField("description", dummyOverviewData.get("description"), overviewInputLocators);

            Thread.sleep(500); // Brief pause for visibility

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted while adding overview information", e);
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

    public void addEmailWithValue(String email) {
        try {
            // Click the add email button to add a new email field
            addEmail();

            // Find the newly added email field (second email field)
            By newEmailField = By.id("Accounts0emailAddress1");
            WebElement emailElement = wait.until(ExpectedConditions.presenceOfElementLocated(newEmailField));
            emailElement.clear();
            emailElement.sendKeys(email);

        } catch (Exception e) {
            throw new RuntimeException("Failed to add email with value: " + email, e);
        }
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

    public void addMoreInformation() {
        try {
            // Select Account Type
            selectDropdown("accountType", dummyMoreInformationData.get("accountType"), moreInformationInputLocators);

            // Select Industry
            selectDropdown("industry", dummyMoreInformationData.get("industry"), moreInformationInputLocators);

            // Fill Financial Information
            fillInputField("annualRevenue", dummyMoreInformationData.get("annualRevenue"), moreInformationInputLocators);
            fillInputField("employees", dummyMoreInformationData.get("employees"), moreInformationInputLocators);

            Thread.sleep(500); // Brief pause for visibility

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted while adding more information", e);
        }
    }

    private void fillInputField(String fieldKey, String value, Map<String, By> locatorMap) {
        if (value != null && !value.isEmpty()) {
            By locator = locatorMap.get(fieldKey);
            if (locator != null) {
                WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
                element.clear();
                element.sendKeys(value);
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

    public String cekValidationMessage() {
        // Cek apakah ada pesan validasi untuk field yang wajib diisi
        By validationMessage = By.className("validation-message");
        WebElement messageElement = wait.until(ExpectedConditions.presenceOfElementLocated(validationMessage));
        String messageText = messageElement.getText();
        return messageText;
    }

    public void onlyAddName() {
        fillInputField("name", dummyOverviewData.get("name"), overviewInputLocators);
    }
}
