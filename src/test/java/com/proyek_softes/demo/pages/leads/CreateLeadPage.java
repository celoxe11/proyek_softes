package com.proyek_softes.demo.pages.leads;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CreateLeadPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By buttonSave = By.xpath("//input[@title='Save' and @id='SAVE']");
    private final By buttonCancel = By.xpath("//input[@title='Cancel [Alt+l]' and @id='CANCEL']");

    private Map<String, By> inputLocators;

    public CreateLeadPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        initializeInputLocators();
    }

    private void initializeInputLocators() {
        inputLocators = new HashMap<>();

        // Basic Information
        inputLocators.put("salutation", By.id("salutation"));
        inputLocators.put("firstName", By.id("first_name"));
        inputLocators.put("lastName", By.id("last_name"));
        inputLocators.put("phoneWork", By.id("phone_work"));
        inputLocators.put("title", By.id("title"));
        inputLocators.put("phoneMobile", By.id("phone_mobile"));
        inputLocators.put("department", By.id("department"));
        inputLocators.put("phoneFax", By.id("phone_fax"));
        inputLocators.put("accountName", By.id("EditView_account_name"));
        inputLocators.put("website", By.id("website"));

        // Address
        inputLocators.put("primaryAddressStreet", By.id("primary_address_street"));
        inputLocators.put("primaryAddressCity", By.id("primary_address_city"));
        inputLocators.put("primaryAddressState", By.id("primary_address_state"));
        inputLocators.put("primaryAddressPostalCode", By.id("primary_address_postalcode"));
        inputLocators.put("primaryAddressCountry", By.id("primary_address_country"));

        inputLocators.put("altAddressStreet", By.id("alt_address_street"));
        inputLocators.put("altAddressCity", By.id("alt_address_city"));
        inputLocators.put("altAddressState", By.id("alt_address_state"));
        inputLocators.put("altAddressPostalCode", By.id("alt_address_postalcode"));
        inputLocators.put("altAddressCountry", By.id("alt_address_country"));

        // Email
        inputLocators.put("email", By.id("Leads0emailAddress0"));

        // Description
        inputLocators.put("description", By.id("description"));

        // More Information
        inputLocators.put("status", By.id("status"));
        inputLocators.put("leadSource", By.id("lead_source"));
        inputLocators.put("statusDescription", By.id("status_description"));
        inputLocators.put("leadSourceDescription", By.id("lead_source_description"));
        inputLocators.put("opportunityAmount", By.id("opportunity_amount"));
        inputLocators.put("referedBy", By.id("refered_by"));
        inputLocators.put("campaignName", By.id("campaign_name"));

        // Assignment
        inputLocators.put("assignedUserName", By.id("assigned_user_name"));
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
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("last_name")));

            String salutation = data.get("salutation");
            if (salutation != null && !salutation.isEmpty()) {
                selectDropdown("salutation", salutation);
            }

            fillInputFieldFromData("firstName", data.get("firstName"));
            fillInputFieldFromData("lastName", data.get("lastName"));
            fillInputFieldFromData("phoneWork", data.get("phoneWork"));
            fillInputFieldFromData("title", data.get("title"));
            fillInputFieldFromData("phoneMobile", data.get("phoneMobile"));
            fillInputFieldFromData("department", data.get("department"));
            fillInputFieldFromData("phoneFax", data.get("phoneFax"));
            fillInputFieldFromData("accountName", data.get("accountName"));
            fillInputFieldFromData("website", data.get("website"));

            fillInputFieldFromData("primaryAddressStreet", data.get("primaryAddressStreet"));
            fillInputFieldFromData("primaryAddressCity", data.get("primaryAddressCity"));
            fillInputFieldFromData("primaryAddressState", data.get("primaryAddressState"));
            fillInputFieldFromData("primaryAddressPostalCode", data.get("primaryAddressPostalCode"));
            fillInputFieldFromData("primaryAddressCountry", data.get("primaryAddressCountry"));

            fillInputFieldFromData("altAddressStreet", data.get("altAddressStreet"));
            fillInputFieldFromData("altAddressCity", data.get("altAddressCity"));
            fillInputFieldFromData("altAddressState", data.get("altAddressState"));
            fillInputFieldFromData("altAddressPostalCode", data.get("altAddressPostalCode"));
            fillInputFieldFromData("altAddressCountry", data.get("altAddressCountry"));

            fillInputFieldFromData("email", data.get("email"));
            fillInputFieldFromData("description", data.get("description"));

            String status = data.get("status");
            if (status != null && !status.isEmpty()) {
                selectDropdown("status", status);
            }

            String leadSource = data.get("leadSource");
            if (leadSource != null && !leadSource.isEmpty()) {
                selectDropdown("leadSource", leadSource);
            }

            fillInputFieldFromData("statusDescription", data.get("statusDescription"));
            fillInputFieldFromData("leadSourceDescription", data.get("leadSourceDescription"));
            fillInputFieldFromData("opportunityAmount", data.get("opportunityAmount"));
            fillInputFieldFromData("referedBy", data.get("referedBy"));
            fillInputFieldFromData("campaignName", data.get("campaignName"));

            Thread.sleep(500);
            System.out.println("  → Filled lead data: " + data.get("firstName") + " " + data.get("lastName"));

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

    public boolean isLeadSavedSuccessfully(String leadName) {
        try {
            By pageTitle = By.className("module-title-text");
            String title = wait.until(ExpectedConditions.presenceOfElementLocated(pageTitle)).getText();
            return title.toLowerCase().contains(leadName.toLowerCase());
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
