package com.proyek_softes.demo.pages.leads;

import java.io.File;
import java.nio.file.Paths;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ImportLeadVCardPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Locators
    private final By inputFile = By.id("vcard_file");
    private final By importButton = By.id("import_vcard_button");

    public ImportLeadVCardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(20));
    }

    public void uploadFile(String fileName) {
        // Get the absolute path to the file in test resources
        String resourcePath = Paths.get("src", "test", "resources", "lead_demo", fileName).toAbsolutePath().toString();
        File file = new File(resourcePath);

        // Upload the file by sending the absolute path to the input field
        driver.findElement(inputFile).sendKeys(file.getAbsolutePath());
    }

    public void clickImportButton() {
        driver.findElement(importButton).click();
    }

    public boolean isLeadSavedSuccessfully(String leadName) {
        try {
            By pageTitle = By.className("module-title-text");
            String title = wait.until(ExpectedConditions.presenceOfElementLocated(pageTitle)).getText();
            return title.toLowerCase().contains(leadName.toLowerCase());
        } catch (Exception e) {
            return false;
        }
    }
}
