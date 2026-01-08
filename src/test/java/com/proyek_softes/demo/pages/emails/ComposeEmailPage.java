package com.proyek_softes.demo.pages.emails;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ComposeEmailPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By sendEmailButton = By.xpath("//button[contains(@class, 'btn-send-email') and @title='Send']");
    private final By modalOkButton = By.cssSelector(".modal-footer .btn-ok");

    private Map<String, By> inputLocators;

    public ComposeEmailPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        initializeInputLocators();
    }

    private void initializeInputLocators() {
        inputLocators = new HashMap<>();
        inputLocators.put("emailTemplate", By.id("emails_email_templates_name"));
        inputLocators.put("relatedToType", By.id("parent_type"));
        inputLocators.put("relatedToName", By.id("parent_name"));
        inputLocators.put("from", By.id("from_addr_name"));
        inputLocators.put("to", By.id("to_addrs_names"));
        inputLocators.put("cc", By.id("cc_addrs_names"));
        inputLocators.put("bcc", By.id("bcc_addrs_names"));
        inputLocators.put("subject", By.id("name"));
        inputLocators.put("body", By.id("description"));
    }

    private void fillRichTextField(String fieldName, String value) {
        if (value == null || value.isEmpty()) {
            return;
        }

        try {
            // Wait for TinyMCE to initialize
            Thread.sleep(1000);

            // TinyMCE iframe ID is always "email_template_editor_ifr" for the campaign template editor
            By iframeLocator = By.id("description_ifr");

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

    private void fillEmailAddressField(String fieldName) {
        try {
            // Close any existing tooltips by clicking elsewhere first
            try {
                WebElement body = driver.findElement(By.tagName("body"));
                ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", body);
                Thread.sleep(300);
            } catch (Exception e) {
                // Ignore if tooltip closing fails
            }

            // Get the field locator
            By fieldLocator = inputLocators.get(fieldName);
            WebElement field = wait.until(ExpectedConditions.presenceOfElementLocated(fieldLocator));

            // Scroll field into view
            ((org.openqa.selenium.JavascriptExecutor) driver)
                    .executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", field);
            Thread.sleep(300);

            // Click on the field to trigger the tooltip
            field.click();
            Thread.sleep(1000);

            // Wait for any visible tooltip to appear and find the Contacts button
            By contactButtonLocator = By.xpath("//div[contains(@class, 'qtip-content') and contains(@style, 'display')]//button[@data-open-popup-module='Contacts']");
            WebElement contactButton = wait.until(ExpectedConditions.elementToBeClickable(contactButtonLocator));
            contactButton.click();

            // Wait for the popup window to appear
            Thread.sleep(1000);

            selectFirstItem();

            System.out.println("Successfully filled email address field: " + fieldName);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted while filling email address field: " + fieldName, e);
        } catch (Exception e) {
            System.err.println("Error filling email address field " + fieldName + ": " + e.getMessage());
            throw new RuntimeException("Failed to fill email address field: " + fieldName, e);
        }
    }

    private void fillToField() {
        try {
            // Scroll field into view
            WebElement field = wait.until(ExpectedConditions.presenceOfElementLocated(inputLocators.get("to")));
            ((org.openqa.selenium.JavascriptExecutor) driver)
                    .executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", field);
            Thread.sleep(300);

            // Click on the To field to trigger the tooltip
            field.click();
            Thread.sleep(1500);

            // Wait for the visible tooltip and click Contacts button using aria-hidden
            By contactButtonLocator = By.xpath("//div[contains(@class, 'qtip') and @aria-hidden='false']//button[@data-open-popup-module='Contacts']");
            WebElement contactButton = wait.until(ExpectedConditions.elementToBeClickable(contactButtonLocator));
            contactButton.click();

            Thread.sleep(1000);
            selectFirstItem();

            System.out.println("Successfully filled To field");

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted while filling To field", e);
        } catch (Exception e) {
            System.err.println("Error filling To field: " + e.getMessage());
            throw new RuntimeException("Failed to fill To field", e);
        }
    }

    private void fillCcField() {
        try {
            // Scroll field into view
            WebElement field = wait.until(ExpectedConditions.presenceOfElementLocated(inputLocators.get("cc")));
            ((org.openqa.selenium.JavascriptExecutor) driver)
                    .executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", field);
            Thread.sleep(300);

            // Click on the CC field to trigger the tooltip
            field.click();
            Thread.sleep(1500);

            // Wait for the visible tooltip and click Contacts button using aria-hidden
            By contactButtonLocator = By.xpath("//div[contains(@class, 'qtip') and @aria-hidden='false']//button[@data-open-popup-module='Contacts']");
            WebElement contactButton = wait.until(ExpectedConditions.elementToBeClickable(contactButtonLocator));
            contactButton.click();

            Thread.sleep(1000);
            selectFirstItem();

            System.out.println("Successfully filled CC field");

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted while filling CC field", e);
        } catch (Exception e) {
            System.err.println("Error filling CC field: " + e.getMessage());
            throw new RuntimeException("Failed to fill CC field", e);
        }
    }

    private void fillBccField() {
        try {
            // Scroll field into view
            WebElement field = wait.until(ExpectedConditions.presenceOfElementLocated(inputLocators.get("bcc")));
            ((org.openqa.selenium.JavascriptExecutor) driver)
                    .executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", field);
            Thread.sleep(300);

            // Click on the BCC field to trigger the tooltip
            field.click();
            Thread.sleep(1500);

            // Wait for the visible tooltip and click Contacts button using aria-hidden
            By contactButtonLocator = By.xpath("//div[contains(@class, 'qtip') and @aria-hidden='false']//button[@data-open-popup-module='Contacts']");
            WebElement contactButton = wait.until(ExpectedConditions.elementToBeClickable(contactButtonLocator));
            contactButton.click();

            Thread.sleep(1000);
            selectFirstItem();

            System.out.println("Successfully filled BCC field");

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted while filling BCC field", e);
        } catch (Exception e) {
            System.err.println("Error filling BCC field: " + e.getMessage());
            throw new RuntimeException("Failed to fill BCC field", e);
        }
    }

    private void clickSelectButton(String fieldName) {
        try {
            String buttonId = "btn_" + inputLocators.get(fieldName).toString().split("id: ")[1];
            By buttonLocator = By.id(buttonId);
            WebElement button = wait.until(ExpectedConditions.elementToBeClickable(buttonLocator));

            // Scroll button into view
            ((org.openqa.selenium.JavascriptExecutor) driver)
                    .executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", button);
            Thread.sleep(300);

            button.click();
            Thread.sleep(1000);

            // Select the first item from the popup
            selectFirstItem();

            System.out.println("Successfully selected first item for: " + fieldName);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted while clicking select button: " + fieldName, e);
        } catch (Exception e) {
            System.err.println("Error clicking select button " + fieldName + ": " + e.getMessage());
            throw new RuntimeException("Failed to click select button: " + fieldName, e);
        }
    }

    public void addInformationFromData(Map<String, String> data) {
        try {
            // clickSelectButton("emailTemplate");
            // Thread.sleep(300);
            // clickOkInModalIfPresent();
            // Thread.sleep(1000);

            // Fill Related To Type dropdown
            if (data.containsKey("relatedToType") && data.get("relatedToType") != null && !data.get("relatedToType").isEmpty()) {
                WebElement relatedToTypeElement = wait.until(ExpectedConditions.presenceOfElementLocated(inputLocators.get("relatedToType")));
                ((org.openqa.selenium.JavascriptExecutor) driver)
                        .executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", relatedToTypeElement);
                Thread.sleep(300);
                Select relatedToTypeSelect = new Select(relatedToTypeElement);
                relatedToTypeSelect.selectByVisibleText(data.get("relatedToType"));
                System.out.println("Selected Related To Type: " + data.get("relatedToType"));
                Thread.sleep(300);
            }

            clickSelectButton("relatedToName");
            Thread.sleep(300);

            // // Fill From dropdown
            // if (data.containsKey("from") && data.get("from") != null && !data.get("from").isEmpty()) {
            //     WebElement fromElement = wait.until(ExpectedConditions.presenceOfElementLocated(inputLocators.get("from")));
            //     ((org.openqa.selenium.JavascriptExecutor) driver)
            //             .executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", fromElement);
            //     Thread.sleep(300);
            //     Select fromSelect = new Select(fromElement);
            //     fromSelect.selectByVisibleText(data.get("from"));
            //     System.out.println("Selected From: " + data.get("from"));
            //     Thread.sleep(300);
            // }
            
            fillToField();
            Thread.sleep(300);

            fillCcField();
            Thread.sleep(300);

            fillBccField();
            Thread.sleep(300);

            // Fill Subject
            if (data.containsKey("subject") && data.get("subject") != null && !data.get("subject").isEmpty()) {
                WebElement subjectElement = wait.until(ExpectedConditions.presenceOfElementLocated(inputLocators.get("subject")));
                ((org.openqa.selenium.JavascriptExecutor) driver)
                        .executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", subjectElement);
                Thread.sleep(300);
                subjectElement.clear();
                subjectElement.sendKeys(data.get("subject"));
                System.out.println("Filled Subject: " + data.get("subject"));
                Thread.sleep(300);
            }

            // Fill Body
            if (data.containsKey("body") && data.get("body") != null && !data.get("body").isEmpty()) {
                fillRichTextField("body", data.get("body"));
                Thread.sleep(300);
            }

            System.out.println("Successfully filled all email compose fields");

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted while filling form", e);
        } catch (Exception e) {
            System.err.println("Error filling form: " + e.getMessage());
            throw new RuntimeException("Failed to fill form", e);
        }
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

    private void selectFirstItem() {
        selectFirstItem(null);
    }

    public void clickSendButton() {
        try {
            WebElement sendButton = wait.until(ExpectedConditions.elementToBeClickable(sendEmailButton));

            // Scroll button into view
            ((org.openqa.selenium.JavascriptExecutor) driver)
                    .executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", sendButton);
            Thread.sleep(300);

            sendButton.click();
            Thread.sleep(1000);

            System.out.println("Clicked Send Email button");

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted while clicking send button", e);
        } catch (Exception e) {
            System.err.println("Error clicking send button: " + e.getMessage());
            throw new RuntimeException("Failed to click send button", e);
        }
    }

    public void clickOkInModal() {
        try {
            // Wait for modal to be visible
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("modal-content")));
            
            // Wait for OK button to be clickable
            WebElement okButton = wait.until(ExpectedConditions.elementToBeClickable(modalOkButton));
            okButton.click();
            
            // Wait for modal to disappear
            Thread.sleep(500);
            
            System.out.println("Clicked OK in modal dialog");
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted while clicking OK in modal", e);
        } catch (Exception e) {
            System.err.println("Error clicking OK in modal: " + e.getMessage());
            throw new RuntimeException("Failed to click OK in modal", e);
        }
    }

    public void clickOkInModalIfPresent() {
        try {
            // Create a shorter wait for checking if modal appears
            WebDriverWait shortWait = new WebDriverWait(driver, java.time.Duration.ofSeconds(3));
            
            // Try to find the modal with shorter timeout
            try {
                shortWait.until(ExpectedConditions.visibilityOfElementLocated(By.className("modal-content")));
                
                // Modal appeared, click OK button
                WebElement okButton = shortWait.until(ExpectedConditions.elementToBeClickable(modalOkButton));
                okButton.click();
                
                // Wait for modal to disappear
                Thread.sleep(500);
                
                System.out.println("Clicked OK in modal dialog");
            } catch (org.openqa.selenium.TimeoutException e) {
                // Modal didn't appear - this is fine, it means there was no content to override
                System.out.println("No modal appeared - continuing without modal confirmation");
            }
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted while checking for modal", e);
        } catch (Exception e) {
            System.err.println("Error handling modal: " + e.getMessage());
            throw new RuntimeException("Failed to handle modal", e);
        }
    }

    public boolean isEmailSendErrorModalPresent() {
        try {
            // Create a shorter wait for checking if error modal appears
            WebDriverWait shortWait = new WebDriverWait(driver, java.time.Duration.ofSeconds(3));
            
            // Check for error modal
            By errorModalLocator = By.xpath("//div[contains(@class, 'modal-content')]//div[@class='modal-body' and contains(text(), 'Error Sending Email')]");
            shortWait.until(ExpectedConditions.visibilityOfElementLocated(errorModalLocator));
            
            WebElement errorModal = driver.findElement(errorModalLocator);
            String errorText = errorModal.getText();
            System.out.println("Email send error detected: " + errorText);
            return true;
            
        } catch (org.openqa.selenium.TimeoutException e) {
            // No error modal appeared - this is good
            return false;
        } catch (Exception e) {
            // Any other exception, assume no error modal
            return false;
        }
    }

    public String getEmailSendErrorMessage() {
        try {
            By errorModalLocator = By.xpath("//div[contains(@class, 'modal-content')]//div[@class='modal-body']");
            WebElement errorModal = driver.findElement(errorModalLocator);
            return errorModal.getText();
        } catch (Exception e) {
            return "Unable to retrieve error message";
        }
    }

    public boolean isEmailSentSuccessfully() {
        try {
            By successMessageLocator = By.cssSelector(".alert.alert-success");
            wait.until(ExpectedConditions.visibilityOfElementLocated(successMessageLocator));
            WebElement successMessage = driver.findElement(successMessageLocator);
            String messageText = successMessage.getText().toLowerCase();
            System.out.println("Success message: " + messageText);
            return messageText.contains("email sent successfully");
        } catch (Exception e) {
            System.err.println("Email sent success message not found: " + e.getMessage());
            return false;
        }
    }
}
