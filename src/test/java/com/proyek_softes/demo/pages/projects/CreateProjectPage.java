package com.proyek_softes.demo.pages.projects;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CreateProjectPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By buttonSave = By.xpath("//input[@title='Save' and @id='SAVE_HEADER']");
    private final By buttonCancel = By.xpath("//input[@title='Cancel [Alt+l]' and @id='CANCEL']");

    private final By errorMessage = By.className("error");

    private final By inviteesSearchFirstName = By.id("search_first_name");
    private final By inviteesSearchLastName = By.id("search_last_name");
    private final By inviteesSearchEmail = By.id("search_email");
    private final By inviteesSearchButton = By.id("invitees_search");
    private final By inviteesAddButton1 = By.id("invitees_add_1");
    private final By inviteesListDiv = By.id("list_div_win");

    private Map<String, By> inputLocators;

    public CreateProjectPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        initializeInputLocators();
    }

    private void initializeInputLocators() {
        inputLocators = new HashMap<>();

        // Basic information fields
        inputLocators.put("name", By.id("name"));
        inputLocators.put("status", By.id("status"));
        inputLocators.put("estimated_start_date", By.id("estimated_start_date"));
        inputLocators.put("priority", By.id("priority"));
        inputLocators.put("estimated_end_date", By.id("estimated_end_date"));
        inputLocators.put("override_business_hours", By.id("override_business_hours"));
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

            // Fill basic information fields
            fillInputFieldFromData("name", data.get("name"));
            selectDropdown("status", data.get("status"));
            fillInputFieldFromData("estimated_start_date", data.get("estimated_start_date"));
            selectDropdown("priority", data.get("priority"));
            fillInputFieldFromData("estimated_end_date", data.get("estimated_end_date"));
            fillCheckboxFromData("override_business_hours", data.get("override_business_hours"));

            // Add invitees if specified in test data
            String inviteeFirstName = data.get("invitee_first_name");
            if (inviteeFirstName != null && !inviteeFirstName.isEmpty()) {
                addInvitees(inviteeFirstName);
            }

            Thread.sleep(500);

        } catch (InterruptedException e) {
            throw new RuntimeException("Error filling project form: " + e.getMessage(), e);
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

    private void fillCheckboxFromData(String fieldName, String value) {
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

            boolean shouldBeChecked = value.equalsIgnoreCase("true")
                    || value.equalsIgnoreCase("1")
                    || value.equalsIgnoreCase("yes");

            if (shouldBeChecked != element.isSelected()) {
                element.click();
            }
            Thread.sleep(200);
        } catch (InterruptedException e) {
            System.err.println("Error filling checkbox " + fieldName + ": " + e.getMessage());
            throw new RuntimeException("Failed to fill checkbox: " + fieldName, e);
        }
    }

    public boolean isProjectSavedSuccessfully(String projectName) {
        try {
            By pageTitle = By.className("module-title-text");
            String title = wait.until(ExpectedConditions.presenceOfElementLocated(pageTitle)).getText();
            System.out.println("Page title after saving: " + title.toLowerCase());
            System.out.println(projectName.toLowerCase());
            return title.toLowerCase().contains(projectName.toLowerCase());
        } catch (Exception e) {
            return false;
        }
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

    public boolean isErrorMessagePresent() {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(errorMessage));
            String errorText = driver.findElement(errorMessage).getText().trim();
            return errorText.toLowerCase().contains("you do not have access to this area.");
        } catch (Exception e) {
            return false;
        }
    }
}
