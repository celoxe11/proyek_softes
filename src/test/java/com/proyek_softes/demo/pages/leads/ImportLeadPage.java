package com.proyek_softes.demo.pages.leads;

import java.io.File;
import java.nio.file.Paths;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.proyek_softes.demo.pages.BaseImportPage;

public class ImportLeadPage extends BaseImportPage {

    private final Actions actions;

    private final By downloadLink = By.linkText("Download Import File Template");
    private final By inputFile = By.id("userfile");
    private final By importCreateButton = By.id("import_create");
    private final By importCreateAndUpdateButton = By.id("import_update");
    private final By nextButton = By.id("gonext");
    private final By titlePage = By.className("module-title-text");
    private final By addNewField = By.id("addrow"); // button di step 3
    private final By importNowButton = By.id("importnow"); // button di step 4
    private final By summaryText = By.xpath("//span[@style='font-size: 14px']");

    public ImportLeadPage(WebDriver driver) {
        super(driver);
        this.actions = new Actions(driver);
    }

    /**
     * Downloads template and verifies it through browser download history
     *
     * @param timeoutSeconds maximum time to wait for download
     * @param screenshotName optional screenshot name (without extension) to
     * capture the downloads page, pass null to skip
     * @return true if file is downloaded and is in CSV format
     */
    public boolean verifyDownloadedTemplateIsCSV(int timeoutSeconds, String screenshotName) {
        // Store current URL to navigate back later
        String originalUrl = driver.getCurrentUrl();

        // Click download
        driver.findElement(downloadLink).click();

        // Wait a bit for download to start
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        try {
            // Navigate to appropriate downloads page based on browser
            navigateToDownloadsPage();

            // Wait for downloads page to load
            Thread.sleep(1000);

            // Get the downloaded file name using base class method
            String fileName = getDownloadedFileName(timeoutSeconds);

            // Take screenshot if requested
            if (screenshotName != null && fileName != null) {
                takeScreenshotOfDownloadsPage(screenshotName);
            }

            // Navigate back to original page
            driver.get(originalUrl);

            // Check if file is CSV
            if (fileName != null) {
                return isTemplateFileInCSVFormat(fileName) && fileName.toLowerCase().contains("leads");
            } else {
                System.err.println("No file found in browser download history");
                return false;
            }

        } catch (InterruptedException e) {
            System.err.println("Error checking browser downloads: " + e.getMessage());

            // Try to navigate back to original page
            try {
                driver.get(originalUrl);
            } catch (Exception ex) {
                System.err.println("Could not navigate back to original page: " + ex.getMessage());
            }
            return false;
        }
    }

    public void uploadFile(String fileName) {
        // Get the absolute path to the file in test resources
        String resourcePath = Paths.get("src", "test", "resources", "lead_demo", fileName).toAbsolutePath().toString();
        File file = new File(resourcePath);

        // Upload the file by sending the absolute path to the input field
        driver.findElement(inputFile).sendKeys(file.getAbsolutePath());
    }

    public void clickImportCreate() {
        driver.findElement(importCreateButton).click();
    }

    public void clickImportCreateAndUpdate() {
        driver.findElement(importCreateAndUpdateButton).click();
    }

    public void clickNext() {
        actions.moveToElement(driver.findElement(nextButton)).perform();
        wait.until(ExpectedConditions.elementToBeClickable(nextButton)).click();
    }

    public boolean confirmInStep(String stepName) {
        String pageTitle = driver.findElement(titlePage).getText();
        System.out.println("Current Page Title: " + pageTitle);
        return pageTitle.contains(stepName);
    }

    public void clickAddNewField() {
        driver.findElement(addNewField).click();
    }

    public void clickImportNow() {
        driver.findElement(importNowButton).click();
    }

    public boolean isRecordsImported() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(summaryText));
            String summaryContent = driver.findElement(summaryText).getText();
            System.out.println("Import summary: " + summaryContent);
            return summaryContent.toLowerCase().contains("records were created");
        } catch (Exception e) {
            System.err.println("Failed to find import summary: " + e.getMessage());
            return false;
        }
    }

    public WebElement getSummaryElement() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(summaryText));
            return driver.findElement(summaryText);
        } catch (Exception e) {
            System.err.println("Failed to find summary element: " + e.getMessage());
            return null;
        }
    }

    public int getCreatedRecordsCount() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(summaryText));
            String summaryContent = driver.findElement(summaryText).getText();
            // Extract number from text like "4 records were created"
            String[] parts = summaryContent.split(" ");
            if (parts.length > 0) {
                return Integer.parseInt(parts[0].trim());
            }
            return 0;
        } catch (NumberFormatException e) {
            System.err.println("Failed to extract created records count: " + e.getMessage());
            return 0;
        }
    }
}
