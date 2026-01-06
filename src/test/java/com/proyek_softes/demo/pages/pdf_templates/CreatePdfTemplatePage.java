package com.proyek_softes.demo.pages.pdf_templates;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CreatePdfTemplatePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By buttonSave = By.xpath("//input[@title='Save' and @id='SAVE']");
    private final By buttonCancel = By.xpath("//input[@title='Cancel [Alt+l]' and @id='CANCEL']");

    private Map<String, By> inputLocators;

    public CreatePdfTemplatePage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        initializeInputLocators();
    }

    private void initializeInputLocators() {
        inputLocators = new HashMap<>();
        inputLocators.put("name", By.id("name"));
        inputLocators.put("assigned_user_name", By.id("assigned_user_name"));
        inputLocators.put("type", By.id("type"));
        inputLocators.put("sample", By.id("sample"));
        inputLocators.put("active", By.id("active"));
        inputLocators.put("page_size", By.id("page_size"));
        inputLocators.put("orientation", By.id("orientation"));
        inputLocators.put("module_name", By.id("module_name"));
        inputLocators.put("variable_name", By.id("variable_name"));
        inputLocators.put("margin_left", By.id("margin_left"));
        inputLocators.put("margin_right", By.id("margin_right"));
        inputLocators.put("margin_top", By.id("margin_top"));
        inputLocators.put("margin_bottom", By.id("margin_bottom"));
        inputLocators.put("margin_header", By.id("margin_header"));
        inputLocators.put("margin_footer", By.id("margin_footer"));
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

            // Fill text input fields
            fillInputFieldFromData("name", data.get("name"));

            // Fill dropdown fields
            selectDropdown("type", data.get("type"));
            
            // Wait for module_name dropdown to be populated after type selection
            Thread.sleep(800);
            
            selectDropdown("page_size", data.get("page_size"));
            selectDropdown("orientation", data.get("orientation"));

            // Check active checkbox (if provided)
            String active = data.get("active");
            if (active != null && !active.isEmpty()) {
                WebElement activeCheckbox = driver.findElement(By.id("active"));
                boolean shouldBeChecked = active.equals("1") || active.equalsIgnoreCase("true");
                if (activeCheckbox.isSelected() != shouldBeChecked) {
                    activeCheckbox.click();
                }
            }

            // Fill module_name and variable_name dropdowns
            selectDropdown("module_name", data.get("module_name"));
            selectDropdown("variable_name", data.get("variable_name"));

            // Fill description (TinyMCE rich text editor)
            String description = data.get("description");
            if (description != null && !description.isEmpty()) {
                fillRichTextField("description", description);
            }

            // Fill pdfheader (TinyMCE rich text editor)
            String pdfheader = data.get("pdfheader");
            if (pdfheader != null && !pdfheader.isEmpty()) {
                fillRichTextField("pdfheader", pdfheader);
            }

            // Fill pdffooter (TinyMCE rich text editor)
            String pdffooter = data.get("pdffooter");
            if (pdffooter != null && !pdffooter.isEmpty()) {
                fillRichTextField("pdffooter", pdffooter);
            }

            // Fill margin fields
            fillInputFieldFromData("margin_left", data.get("margin_left"));
            fillInputFieldFromData("margin_right", data.get("margin_right"));
            fillInputFieldFromData("margin_top", data.get("margin_top"));
            fillInputFieldFromData("margin_bottom", data.get("margin_bottom"));
            fillInputFieldFromData("margin_header", data.get("margin_header"));
            fillInputFieldFromData("margin_footer", data.get("margin_footer"));

            Thread.sleep(500);

        } catch (InterruptedException e) {
            throw new RuntimeException("Error filling PDF template form: " + e.getMessage(), e);
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

    public boolean isPdfTemplateSavedSuccessfully(String pdfTemplateName) {
        try {
            By pageTitle = By.className("module-title-text");
            String title = wait.until(ExpectedConditions.presenceOfElementLocated(pageTitle)).getText();
            return title.toLowerCase().contains(pdfTemplateName.toLowerCase());
        } catch (Exception e) {
            return false;
        }
    }

}
