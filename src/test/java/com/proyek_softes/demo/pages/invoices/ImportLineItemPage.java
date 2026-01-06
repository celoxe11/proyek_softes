package com.proyek_softes.demo.pages.invoices;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ImportLineItemPage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final Actions actions;

    private final By downloadLink = By.linkText("Download Import File Template");
    private final By inputFile = By.id("userfile");
    private final By importCreateButton = By.id("import_create");
    private final By importCreateAndUpdateButton = By.id("import_update");
    private final By nextButton = By.id("gonext");
    private final By titlePage = By.className("module-title-text");
    private final By addNewField = By.id("addrow");
    private final By importNowButton = By.id("importnow");
    private final By paginationText = By.cssSelector(".pageNumbers");
    private final By exitButton = By.id("finished");
    private final By summaryText = By.xpath("//span[@style='font-size: 14px']");

    public ImportLineItemPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(20));
        this.actions = new Actions(driver);
    }

    public boolean verifyDownloadedTemplateIsCSV(int timeoutSeconds, String screenshotName) {
        String originalUrl = driver.getCurrentUrl();

        driver.findElement(downloadLink).click();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        try {
            driver.get("chrome://downloads");
            Thread.sleep(1000);

            long endTime = System.currentTimeMillis() + (timeoutSeconds * 1000L);
            String fileName = null;

            while (System.currentTimeMillis() < endTime) {
                try {
                    org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) driver;

                    fileName = (String) js.executeScript(
                            "var manager = document.querySelector('downloads-manager');"
                            + "if (!manager || !manager.shadowRoot) return null;"
                            + "var item = manager.shadowRoot.querySelector('downloads-item');"
                            + "if (!item || !item.shadowRoot) return null;"
                            + "var fileLink = item.shadowRoot.querySelector('#file-link');"
                            + "return fileLink ? fileLink.textContent : null;");

                    if (fileName != null && !fileName.trim().isEmpty()) {
                        System.out.println("Found downloaded file in browser history: " + fileName);
                        break;
                    }
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.sleep(500);
                }
            }

            if (screenshotName != null && fileName != null) {
                takeScreenshotOfDownloadsPage(screenshotName);
            }

            driver.get(originalUrl);

            if (fileName != null) {
                return isTemplateFileInCSVFormat(fileName) && fileName.toLowerCase().contains("line");
            } else {
                System.err.println("No file found in browser download history");
                return false;
            }

        } catch (Exception e) {
            System.err.println("Error checking browser downloads: " + e.getMessage());
            e.printStackTrace();

            try {
                driver.get(originalUrl);
            } catch (Exception ex) {
                System.err.println("Could not navigate back to original page: " + ex.getMessage());
            }
            return false;
        }
    }

    private void takeScreenshotOfDownloadsPage(String screenshotName) {
        try {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File destination = new File("screenshots/" + screenshotName + ".png");
            destination.getParentFile().mkdirs();
            Files.copy(screenshot.toPath(), destination.toPath(),
                    java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Screenshot saved: " + destination.getAbsolutePath());
        } catch (Exception e) {
            System.err.println("Failed to take screenshot: " + e.getMessage());
        }
    }

    public boolean isTemplateFileInCSVFormat(String fileName) {
        return fileName.toLowerCase().endsWith(".csv");
    }

    public void uploadFile(String fileName) {
        String resourcePath = Paths.get("src", "test", "resources", "invoice_demo", fileName).toAbsolutePath().toString();
        File file = new File(resourcePath);
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
            String[] parts = summaryContent.split(" ");
            if (parts.length > 0) {
                return Integer.parseInt(parts[0].trim());
            }
            return 0;
        } catch (Exception e) {
            System.err.println("Failed to extract created records count: " + e.getMessage());
            return 0;
        }
    }
}
