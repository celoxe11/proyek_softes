package com.proyek_softes.demo.pages.campaign;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CreatePersonFormPage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final Actions actions;
    private final String browserType;

    // Field choices section
    private final By addAllFieldButton = By.id("btnAddAllFields");
    private final By nextButton = By.id("btnNext");

    // Form settings section
    private final By formHeaderInput = By.id("web_header");
    private final By formDescriptionTextarea = By.id("web_description");
    private final By submitButtonLabelInput = By.id("web_submit");
    private final By redirectUrlInput = By.id("redirect_url");
    private final By formFooterInput = By.name("web_footer");
    private final By campaignSelectButton = By.cssSelector("input[name='btn1'][onclick*='Campaigns']");

    private final By generateFormButton = By.id("btnGenerateForm");
    private final By saveWebFormButton = By.xpath("//input[@title='Save Web Form']");
    private final By webToPersonFormLink = By.linkText("Web To Person Form");

    public CreatePersonFormPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        this.actions = new Actions(driver);
        
        // Detect browser type from driver class
        String driverClassName = driver.getClass().getSimpleName().toLowerCase();
        if (driverClassName.contains("firefox")) {
            this.browserType = "firefox";
        } else {
            this.browserType = "chrome";
        }
    }

    public void clickAddAllFields() {
        wait.until(ExpectedConditions.elementToBeClickable(addAllFieldButton));
        driver.findElement(addAllFieldButton).click();
    }

    public void clickNext() {
        wait.until(ExpectedConditions.elementToBeClickable(nextButton));
        driver.findElement(nextButton).click();
    }

    public void clickGenerateForm() {
        wait.until(ExpectedConditions.elementToBeClickable(generateFormButton));
        driver.findElement(generateFormButton).click();
    }

    public void clickSaveWebForm() {
        wait.until(ExpectedConditions.elementToBeClickable(saveWebFormButton));
        driver.findElement(saveWebFormButton).click();
    }

    public void addInformationFromData(Map<String, String> data) {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(formHeaderInput));

            // Fill form header
            String formHeader = data.get("form_header");
            if (formHeader != null && !formHeader.isEmpty()) {
                WebElement headerInput = wait.until(ExpectedConditions.presenceOfElementLocated(formHeaderInput));
                headerInput.clear();
                headerInput.sendKeys(formHeader);
            }

            // Fill form description
            String formDescription = data.get("form_description");
            if (formDescription != null && !formDescription.isEmpty()) {
                WebElement descriptionTextarea = driver.findElement(formDescriptionTextarea);
                descriptionTextarea.clear();
                descriptionTextarea.sendKeys(formDescription);
            }

            // Fill submit button label
            String submitLabel = data.get("submit_button_label");
            if (submitLabel != null && !submitLabel.isEmpty()) {
                WebElement submitInput = driver.findElement(submitButtonLabelInput);
                submitInput.clear();
                submitInput.sendKeys(submitLabel);
            }

            // Fill redirect URL
            String redirectUrl = data.get("redirect_url");
            if (redirectUrl != null && !redirectUrl.isEmpty()) {
                WebElement redirectInput = driver.findElement(redirectUrlInput);
                redirectInput.clear();
                redirectInput.sendKeys(redirectUrl);
            }

            // Select campaign
            driver.findElement(campaignSelectButton).click();
            Thread.sleep(500);
            selectFirstCampaign();
            Thread.sleep(500);

            String formFooter = data.get("form_footer");
            if (formFooter != null && !formFooter.isEmpty()) {
                WebElement footerInput = driver.findElement(formFooterInput);
                footerInput.clear();
                footerInput.sendKeys(formFooter);
            }

            System.out.println("Filled Create Person Form with provided data.");

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted while filling form", e);
        }
    }

    private void selectFirstCampaign() {
        try {
            String mainWindow = driver.getWindowHandle();
            wait.until(ExpectedConditions.numberOfWindowsToBe(2));

            for (String windowHandle : driver.getWindowHandles()) {
                if (!windowHandle.equals(mainWindow)) {
                    driver.switchTo().window(windowHandle);
                    break;
                }
            }

            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("table.list.view")));
            By firstCampaignLink = By.cssSelector("table.list.view tbody tr:first-child td:first-child a");
            WebElement campaignLink = wait.until(ExpectedConditions.elementToBeClickable(firstCampaignLink));
            campaignLink.click();

            wait.until(ExpectedConditions.numberOfWindowsToBe(1));
            driver.switchTo().window(mainWindow);
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted while selecting campaign", e);
        }
    }

    public void clickWebToPersonFormLink() {
        wait.until(ExpectedConditions.elementToBeClickable(webToPersonFormLink)).click();
    }

    public boolean verifyDownloadedWebToLeadForm() {
        try {
            // Navigate to downloads page
            navigateToDownloadsPage();
            Thread.sleep(1000);
            
            // Get downloaded file name
            String fileName = getDownloadedFileName(10);
            
            // Check if file name contains WebToLeadForm
            if (fileName != null && fileName.contains("WebToLeadForm")) {
                System.out.println("✓ Downloaded file is WebToLeadForm: " + fileName);
                return true;
            } else {
                System.err.println("✗ Downloaded file is NOT WebToLeadForm. Found: " + fileName);
                return false;
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Thread interrupted while verifying download");
            return false;
        }
    }

    private void navigateToDownloadsPage() {
        String downloadsUrl = browserType.equals("firefox") ? "about:downloads" : "chrome://downloads";
        driver.get(downloadsUrl);
    }

    private String getDownloadedFileName(int timeoutSeconds) {
        long endTime = System.currentTimeMillis() + (timeoutSeconds * 1000L);
        String fileName = null;
        
        while (System.currentTimeMillis() < endTime) {
            try {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                
                if (browserType.equals("firefox")) {
                    // Firefox: Access downloads from the download richlistbox
                    fileName = (String) js.executeScript(
                        "var downloads = document.getElementById('downloadsListBox');" +
                        "if (!downloads) return null;" +
                        "var items = downloads.getElementsByTagName('richlistitem');" +
                        "if (items.length === 0) return null;" +
                        "var fileElem = items[0].querySelector('.downloadTarget');" +
                        "return fileElem ? fileElem.getAttribute('value') : null;");
                } else {
                    // Chrome: Access shadow DOM to get download manager
                    fileName = (String) js.executeScript(
                        "var manager = document.querySelector('downloads-manager');" +
                        "if (!manager || !manager.shadowRoot) return null;" +
                        "var item = manager.shadowRoot.querySelector('downloads-item');" +
                        "if (!item || !item.shadowRoot) return null;" +
                        "var fileLink = item.shadowRoot.querySelector('#file-link');" +
                        "return fileLink ? fileLink.textContent : null;");
                }
                
                if (fileName != null && !fileName.trim().isEmpty()) {
                    System.out.println("Found downloaded file in " + browserType + " browser history: " + fileName);
                    return fileName;
                }
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return null;
            }
        }
        
        return fileName;
    }

    public void takeScreenshotOfDownloadsPage(String screenshotName) {
        try {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File destination = new File("screenshots/" + screenshotName + ".png");
            destination.getParentFile().mkdirs();
            Files.copy(screenshot.toPath(), destination.toPath(), 
                      java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Screenshot saved: " + destination.getAbsolutePath());
        } catch (IOException | WebDriverException e) {
            System.err.println("Failed to take screenshot: " + e.getMessage());
        }
    }
    
}
