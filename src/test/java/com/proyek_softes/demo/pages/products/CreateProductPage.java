package com.proyek_softes.demo.pages.products;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CreateProductPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By buttonSave = By.xpath("//input[@title='Save' and @id='SAVE']");
    private final By buttonCancel = By.xpath("//input[@title='Cancel [Alt+l]' and @id='CANCEL']");

    private final By selectContactButton = By.id("btn_contact");

    private final By attachmentInput = By.name("uploadimage");

    private Map<String, By> inputLocators;

    public CreateProductPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        initializeInputLocators();
    }

    private void initializeInputLocators() {
        inputLocators = new HashMap<>();

        inputLocators.put("name", By.id("name"));
        inputLocators.put("cost", By.id("cost"));
        inputLocators.put("price", By.id("price"));
        inputLocators.put("part_number", By.id("part_number"));
        inputLocators.put("description", By.id("description"));
        inputLocators.put("url", By.id("url"));

        // select box
        inputLocators.put("type", By.id("type"));

        // contact fields (popup)
        inputLocators.put("contact_name", By.id("contact_name"));
        inputLocators.put("contact_id", By.id("contact_id"));
    }

    /*
     * =========================
     * SAVE / CANCEL
     * =========================
     */

    public void save() {
        try {
            WebElement saveButton = wait.until(ExpectedConditions.presenceOfElementLocated(buttonSave));

            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({behavior:'smooth',block:'center'});",
                    saveButton);
            Thread.sleep(300);

            try {
                wait.until(ExpectedConditions.elementToBeClickable(buttonSave)).click();
            } catch (org.openqa.selenium.ElementClickInterceptedException e) {
                System.out.println("  Note: Using JavaScript click for Save button");
                ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].click();", saveButton);
            }

            Thread.sleep(1000);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread interrupted while saving product", e);
        }
    }

    public void cancel() {
        driver.findElement(buttonCancel).click();
    }

    /*
     * =========================
     * MAIN FORM FILLER
     * =========================
     */

    public void addInformationFromData(Map<String, String> data) {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("name")));

            // name
            fillInputFieldFromData("name", data.get("name"));

            // type (select)
            if (data.get("type") != null && !data.get("type").isEmpty()) {
                selectDropdown("type", data.get("type"));
            }

            // contact (popup selector)
            clickSelectContact();
            if (data.get("contact_name") != null && !data.get("contact_name").isEmpty()) {
                selectFirstItem(data.get("contact_name"));
            } else {
                selectFirstItem();
            }

            // cost
            fillInputFieldFromData("cost", data.get("cost"));

            // price
            fillInputFieldFromData("price", data.get("price"));

            // part number
            fillInputFieldFromData("part_number", data.get("part_number"));

            // url
            fillInputFieldFromData("url", data.get("url"));

            // description
            fillInputFieldFromData("description", data.get("description"));

            // === ATTACHMENT (File Upload) ===
            if (data.get("attachment") != null && !data.get("attachment").isEmpty()) {
                uploadAttachment(data.get("attachment"));
                Thread.sleep(500);
            }

            Thread.sleep(500);
            System.out.println("  → Filled product data: " + data.get("name"));

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(
                    "Thread interrupted while filling product data", e);
        }
    }

    /*
     * =========================
     * HELPERS
     * =========================
     */

    private void fillInputFieldFromData(String fieldKey, String value) {
        if (value != null && !value.isEmpty()) {
            By locator = inputLocators.get(fieldKey);
            if (locator != null) {
                try {
                    WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
                    element.clear();
                    element.sendKeys(value);
                } catch (Exception e) {
                    System.out.println(
                            "  Warning: Could not fill field " + fieldKey + ": " + e.getMessage());
                }
            }
        }
    }

    private void selectDropdown(String fieldKey, String value) {
        By locator = inputLocators.get(fieldKey);
        if (locator != null && value != null && !value.isEmpty()) {
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            new Select(element).selectByVisibleText(value);
        }
    }

    public void clickSelectContact() {
        wait.until(ExpectedConditions.elementToBeClickable(selectContactButton)).click();
    }

    public void selectFirstItem(String name) {
        try {
            String mainWindow = driver.getWindowHandle();

            wait.until(ExpectedConditions.numberOfWindowsToBe(2));

            for (String window : driver.getWindowHandles()) {
                if (!window.equals(mainWindow)) {
                    driver.switchTo().window(window);
                    break;
                }
            }

            wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector("table.list.view")));

            By firstRow = By.cssSelector("table.list.view tbody tr:first-child td:first-child a");

            wait.until(ExpectedConditions.elementToBeClickable(firstRow)).click();

            wait.until(ExpectedConditions.numberOfWindowsToBe(1));
            driver.switchTo().window(mainWindow);

            Thread.sleep(500);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(
                    "Thread interrupted while selecting contact", e);
        }
    }

    public void selectFirstItem() {
        selectFirstItem(null);
    }

    /*
     * =========================
     * ASSERTION
     * =========================
     */

    public boolean isProductSavedSuccessfully(String productName) {
        try {
            By pageTitle = By.className("module-title-text");
            String title = wait.until(
                    ExpectedConditions.presenceOfElementLocated(pageTitle)).getText().toLowerCase();

            return title.contains(productName.toLowerCase());
        } catch (Exception e) {
            return false;
        }
    }

    private void uploadAttachment(String fileName) {
        try {
            File file = new File("src/test/resources/product_demo/" + fileName);
            if (!file.exists()) {
                throw new RuntimeException("Attachment file not found: " + file.getAbsolutePath());
            }

            WebElement fileInput = wait.until(
                    ExpectedConditions.presenceOfElementLocated(attachmentInput));
            fileInput.sendKeys(file.getAbsolutePath());

            Thread.sleep(500);
            System.out.println("  → Attachment uploaded: " + fileName);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted while uploading attachment", e);
        }
    }
}
