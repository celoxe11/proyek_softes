package com.proyek_softes.demo.pages.products_categories;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CreateProductCategoryPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By buttonSave = By.xpath("//input[@title='Save' and @id='SAVE']");
    private final By buttonCancel = By.xpath("//input[@title='Cancel [Alt+l]' and @id='CANCEL']");

    private Map<String, By> inputLocators;

    public CreateProductCategoryPage(WebDriver driver, WebDriverWait wait) {
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

            // description
            fillInputFieldFromData("description", data.get("description"));

            Thread.sleep(500);
            System.out.println("  → Filled product category data: " + data.get("name"));

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(
                    "Thread interrupted while filling product category data", e);
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
}
