package com.proyek_softes.demo.pages.notes;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CreateNotePage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By buttonSave = By.xpath("//input[@title='Save' and @id='SAVE']");
    private final By buttonCancel = By.xpath("//input[@title='Cancel [Alt+l]' and @id='CANCEL']");

    private final By selectRelatedToButton = By.id("btn_parent_name");
    private final By selectContactButton = By.id("btn_contact_name");
    private final By attachmentInput = By.id("filename_file");

    private Map<String, By> inputLocators;

    public CreateNotePage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        initializeInputLocators();
    }

    private void initializeInputLocators() {
        inputLocators = new HashMap<>();
        inputLocators.put("contact_name", By.id("contact_name"));
        inputLocators.put("parent_type", By.id("parent_type"));
        inputLocators.put("name", By.id("name"));
        inputLocators.put("description", By.id("description"));
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

            // Select contact name
            clickSelectContact();
            if (data.get("contact_name") != null && !data.get("contact_name").isEmpty()) {
                selectFirstItem(data.get("contact_name"));
            } else {
                selectFirstItem();
            }

            // Select parent type
            String parentType = data.get("parent_type");
            if (parentType != null && !parentType.isEmpty()) {
                selectDropdown("parent_type", parentType);
            }

            // Fill contact name
            fillInputFieldFromData("contact_name", data.get("contact_name"));

            // Fill parent name
            fillInputFieldFromData("parent_name", data.get("parent_name"));

            // Fill  name
            fillInputFieldFromData("name", data.get("name"));

            // === ATTACHMENT ===
            if (data.get("attachment") != null && !data.get("attachment").isEmpty()) {
                uploadAttachment(data.get("attachment"));
            }

            // Fill description
            fillInputFieldFromData("description", data.get("description"));

            Thread.sleep(500);
            System.out.println("  → Filled note data: " + data.get("name"));

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

    private void uploadAttachment(String fileName) {
        try {
            File file = new File("src/test/resources/note_demo/" + fileName);
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

    public boolean isNoteSavedSuccessfully(String taskName) {
        try {
            By pageTitle = By.className("module-title-text");
            String title = wait.until(ExpectedConditions.presenceOfElementLocated(pageTitle)).getText();
            return title.toLowerCase().contains(taskName.toLowerCase());
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

    public void clickSelectContact() {
        wait.until(ExpectedConditions.elementToBeClickable(selectContactButton)).click();
    }

    public void clickSelectRelatedTo() {
        wait.until(ExpectedConditions.elementToBeClickable(selectRelatedToButton)).click();
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
