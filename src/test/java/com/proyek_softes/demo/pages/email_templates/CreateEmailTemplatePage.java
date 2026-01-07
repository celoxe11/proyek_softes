package com.proyek_softes.demo.pages.email_templates;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CreateEmailTemplatePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By buttonSave = By.xpath("//input[@title='Save' and @id='SAVE']");
    private final By buttonCancel = By.xpath("//input[@title='Cancel [Alt+l]' and @id='CANCEL']");

    private final By selectAccountButton = By.id("btn_approver");

    private Map<String, By> inputLocators;

    public CreateEmailTemplatePage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        initializeInputLocators();
    }

    private void initializeInputLocators() {
        inputLocators = new HashMap<>();
        inputLocators.put("name", By.id("name"));
        inputLocators.put("type", By.name("type"));
        inputLocators.put("description", By.id("description"));
        inputLocators.put("assigned_user_name", By.id("assigned_user_name"));
        inputLocators.put("subject", By.id("subjectfield"));
        inputLocators.put("body_html", By.id("email_template_editor"));
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

            // Fill name
            fillInputFieldFromData("name", data.get("name"));

            // Select type
            selectDropdown("type", data.get("type"));

            // Fill description
            if (data.get("description") != null && !data.get("description").isEmpty()) {
                WebElement descriptionField = driver.findElement(By.id("description"));
                ((org.openqa.selenium.JavascriptExecutor) driver)
                        .executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", descriptionField);
                Thread.sleep(200);
                descriptionField.clear();
                descriptionField.sendKeys(data.get("description"));
                Thread.sleep(200);
            }

            // Fill assigned user name (if different from default)
            if (data.get("assigned_user_name") != null && !data.get("assigned_user_name").isEmpty()
                    && !data.get("assigned_user_name").equals("Will Westin")) {
                fillInputFieldFromData("assigned_user_name", data.get("assigned_user_name"));
            }

            // Fill subject
            fillInputFieldFromData("subject", data.get("subject"));

            // Fill body (TinyMCE)
            if (data.get("body_html") != null && !data.get("body_html").isEmpty()) {
                fillRichTextField("email_template_editor", data.get("body_html"));
            }

        } catch (InterruptedException e) {
            throw new RuntimeException("Error filling email template form: " + e.getMessage(), e);
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

    public boolean isEmailTemplateSavedSuccessfully(String emailTemplateName) {
        try {
            By pageTitle = By.cssSelector(".moduleTitle h2");
            String title = wait.until(ExpectedConditions.presenceOfElementLocated(pageTitle)).getText();
            System.out.println("Page title after save: " + title);
            System.out.println("Expected email template name: " + emailTemplateName);
            return title.toLowerCase().contains(emailTemplateName.toLowerCase());
        } catch (Exception e) {
            System.out.println("Error verifying email template save: " + e.getMessage());
            return false;
        }
    }
}
