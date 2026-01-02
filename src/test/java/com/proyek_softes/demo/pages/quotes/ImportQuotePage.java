package com.proyek_softes.demo.pages.quotes;

import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ImportQuotePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By fileInput = By.id("userfile");
    private final By nextButton = By.id("gonext");
    private final By importButton = By.id("importnow");
    private final By pageTitle = By.className("module-title-text");

    public ImportQuotePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(20));
    }

    public void uploadFile(String fileName) {
        try {
            File file = new File("src/test/resources/test_data/" + fileName);
            String absolutePath = file.getAbsolutePath();

            wait.until(ExpectedConditions.presenceOfElementLocated(fileInput));
            WebElement fileInputElement = driver.findElement(fileInput);
            fileInputElement.sendKeys(absolutePath);

            Thread.sleep(500);
            System.out.println("  → Uploaded file: " + fileName);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted while uploading file", e);
        }
    }

    public void clickNextButton() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(nextButton));
            driver.findElement(nextButton).click();
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted while clicking next button", e);
        }
    }

    public void clickImportButton() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(importButton));
            driver.findElement(importButton).click();
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted while clicking import button", e);
        }
    }

    public boolean isImportSuccessful() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("module-title-text")));
            String title = driver.findElement(pageTitle).getText();
            return title.toUpperCase().contains("QUOTES");
        } catch (Exception e) {
            return false;
        }
    }
}
