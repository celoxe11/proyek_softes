package com.proyek_softes.demo.pages.documents;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CreateDocumentPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By buttonSave = By.xpath("//input[@title='Save' and @id='SAVE']");
    private final By buttonCancel = By.xpath("//input[@title='Cancel [Alt+l]' and @id='CANCEL']");

    private final By attachmentInput = By.id("filename_file");

    private Map<String, By> inputLocators;

    public CreateDocumentPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        initializeInputLocators();
    }

    private void initializeInputLocators() {
        inputLocators = new HashMap<>();
        inputLocators.put("document_name", By.id("document_name"));
        inputLocators.put("status_id", By.id("status_id"));
        inputLocators.put("revision", By.name("revision"));
        inputLocators.put("template_type", By.id("template_type"));
        inputLocators.put("is_template", By.id("is_template"));
        inputLocators.put("active_date", By.id("active_date"));
        inputLocators.put("exp_date", By.id("exp_date"));
        inputLocators.put("category_id", By.id("category_id"));
        inputLocators.put("subcategory_id", By.id("subcategory_id"));
        inputLocators.put("description", By.id("description"));
        inputLocators.put("assigned_user_name", By.id("assigned_user_name"));
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
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("document_name")));

            // === ATTACHMENT (File Upload) ===
            if (data.get("attachment") != null && !data.get("attachment").isEmpty()) {
                uploadAttachment(data.get("attachment"));
                Thread.sleep(500);
            }

            // === DOCUMENT NAME ===
            fillInputFieldFromData("document_name", data.get("document_name"));

            // === STATUS ===
            selectDropdown("status_id", data.get("status_id"));

            // === REVISION ===
            fillInputFieldFromData("revision", data.get("revision"));

            // === TEMPLATE TYPE ===
            selectDropdown("template_type", data.get("template_type"));

            // === IS TEMPLATE (Checkbox) ===
            fillCheckboxFromData("is_template", data.get("is_template"));

            // === ACTIVE DATE (Publish Date) ===
            fillInputFieldFromData("active_date", data.get("active_date"));

            // === EXPIRATION DATE ===
            fillInputFieldFromData("exp_date", data.get("exp_date"));

            // === CATEGORY ===
            selectDropdown("category_id", data.get("category_id"));

            // === SUB CATEGORY ===
            selectDropdown("subcategory_id", data.get("subcategory_id"));

            // === DESCRIPTION ===
            fillInputFieldFromData("description", data.get("description"));

            // === ASSIGNED USER ===
            fillInputFieldFromData("assigned_user_name", data.get("assigned_user_name"));

            Thread.sleep(500);
            System.out.println("  → Filled document data: " + data.get("document_name"));

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
                try {
                    WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
                    Select dropdown = new Select(element);
                    dropdown.selectByVisibleText(value);
                } catch (Exception e) {
                    System.out.println("  Warning: Could not select dropdown " + fieldKey + " with value " + value + ": " + e.getMessage());
                }
            }
        }
    }

    private void fillCheckboxFromData(String fieldKey, String value) {
        if (value != null && !value.isEmpty()) {
            By locator = inputLocators.get(fieldKey);
            if (locator != null) {
                try {
                    WebElement checkbox = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
                    boolean shouldBeChecked = value.equalsIgnoreCase("true") || value.equals("1");
                    if (shouldBeChecked != checkbox.isSelected()) {
                        checkbox.click();
                    }
                } catch (Exception e) {
                    System.out.println("  Warning: Could not set checkbox " + fieldKey + ": " + e.getMessage());
                }
            }
        }
    }

    private void uploadAttachment(String fileName) {
        try {
            File file = new File("src/test/resources/document_demo/" + fileName);
            if (!file.exists()) {
                throw new RuntimeException("Attachment file not found: " + file.getAbsolutePath());
            }

            WebElement fileInput = wait.until(
                    ExpectedConditions.presenceOfElementLocated(attachmentInput)
            );
            fileInput.sendKeys(file.getAbsolutePath());

            Thread.sleep(500);
            System.out.println("  → Attachment uploaded: " + fileName);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted while uploading attachment", e);
        }
    }

    public boolean isDocumentSavedSuccessfully(String documentName) {
        try {
            By pageTitle = By.className("module-title-text");
            String title = wait.until(ExpectedConditions.presenceOfElementLocated(pageTitle)).getText();
            return title.toLowerCase().contains(documentName.toLowerCase());
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
