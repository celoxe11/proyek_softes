package com.proyek_softes.demo.pages.products;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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

public class ImportProductPage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final Actions actions;

    private final By downloadLink =
            By.linkText("Download Import File Template");
    private final By inputFile = By.id("userfile");
    private final By importCreateButton = By.id("import_create");
    private final By importCreateAndUpdateButton = By.id("import_update");
    private final By nextButton = By.id("gonext");
    private final By titlePage = By.className("module-title-text");
    private final By addNewField = By.id("addrow");
    private final By importNowButton = By.id("importnow");
    private final By paginationText = By.cssSelector(".pageNumbers");
    private final By exitButton = By.id("finished");
    private final By summaryText =
            By.xpath("//span[@style='font-size: 14px']");

    public ImportProductPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(20));
        this.actions = new Actions(driver);
    }

    /**
     * Downloads template and verifies it through browser download history
     */
    public boolean verifyDownloadedTemplateIsCSV(
            int timeoutSeconds,
            String screenshotName
    ) {
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
                    org.openqa.selenium.JavascriptExecutor js =
                            (org.openqa.selenium.JavascriptExecutor) driver;

                    fileName = (String) js.executeScript(
                            "var manager = document.querySelector('downloads-manager');"
                            + "if (!manager || !manager.shadowRoot) return null;"
                            + "var item = manager.shadowRoot.querySelector('downloads-item');"
                            + "if (!item || !item.shadowRoot) return null;"
                            + "var fileLink = item.shadowRoot.querySelector('#file-link');"
                            + "return fileLink ? fileLink.textContent : null;"
                    );

                    if (fileName != null && !fileName.trim().isEmpty()) {
                        System.out.println("Found downloaded file: " + fileName);
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

            return fileName != null
                    && isTemplateFileInCSVFormat(fileName)
                    && fileName.toLowerCase().contains("products");

        } catch (Exception e) {
            System.err.println("Error checking downloads: " + e.getMessage());
            try {
                driver.get(originalUrl);
            } catch (Exception ignored) {}
            return false;
        }
    }

    private void takeScreenshotOfDownloadsPage(String screenshotName) {
        try {
            File screenshot =
                    ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File destination =
                    new File("screenshots/" + screenshotName + ".png");
            destination.getParentFile().mkdirs();
            Files.copy(
                    screenshot.toPath(),
                    destination.toPath(),
                    java.nio.file.StandardCopyOption.REPLACE_EXISTING
            );
        } catch (Exception e) {
            System.err.println("Screenshot failed: " + e.getMessage());
        }
    }

    public boolean isTemplateFileInCSVFormat(String fileName) {
        return fileName.toLowerCase().endsWith(".csv");
    }

    public void uploadFile(String fileName) {
        String resourcePath = Paths.get(
                "src", "test", "resources", "product_demo", fileName
        ).toAbsolutePath().toString();

        driver.findElement(inputFile).sendKeys(resourcePath);
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
            return driver.findElement(summaryText)
                    .getText()
                    .toLowerCase()
                    .contains("records were created");
        } catch (Exception e) {
            return false;
        }
    }

    public WebElement getSummaryElement() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(summaryText));
        return driver.findElement(summaryText);
    }

    public int getCreatedRecordsCount() {
        try {
            String text = getSummaryElement().getText();
            return Integer.parseInt(text.split(" ")[0].trim());
        } catch (Exception e) {
            return 0;
        }
    }

    public By getAddNewField() {
        return addNewField;
    }

    public int getImportedRecordsCount() {
        String paginationStr =
                driver.findElement(paginationText).getText().trim();
        String[] parts =
                paginationStr.replaceAll("[()]", "").split("of");
        return parts.length == 2
                ? Integer.parseInt(parts[1].trim())
                : 0;
    }

    public int countCSVDataRows(String fileName) {
        String path = Paths.get(
                "src", "test", "resources", "product_demo", fileName
        ).toAbsolutePath().toString();

        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            br.readLine();
            while (br.readLine() != null) count++;
        } catch (IOException e) {
            System.err.println("CSV read error: " + e.getMessage());
        }
        return count;
    }

    public void clickExit() {
        driver.findElement(exitButton).click();
    }
}
