package com.proyek_softes.demo.pages.accounts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ImportAccountsPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private Actions actions;

    private By inputFile = By.id("userfile");
    private By importCreateButton = By.id("import_create");
    private By importCreateAndUpdateButton = By.id("import_update");
    private By nextButton = By.id("gonext");
    private By titlePage = By.className("module-title-text");
    private By addNewField = By.id("addrow"); // button di step 3
    private By importNowButton = By.id("importnow"); // button di step 4
    private By paginationText = By.cssSelector(".pageNumbers");
    private By tableRows = By.cssSelector(".list.View tbody tr:not(.pagination-unique):not([height='20']):has(td[scope='row'])");
    private By exitButton = By.id("finished");

    public ImportAccountsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(20));
        this.actions = new Actions(driver);
    }

    public void uploadFile(String fileName) {
        // Get the absolute path to the file in test resources
        String resourcePath = Paths.get("src", "test", "resources", "testdata", fileName).toAbsolutePath().toString();
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
        Actions actions = new Actions(driver);
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

    public By getAddNewField() {
        return addNewField;
    }

    public int getImportedRecordsCount() {
        // Extract count from pagination text like "(1 - 4 of 4)"
        String paginationStr = driver.findElement(paginationText).getText().trim();
        // Extract the total count (last number)
        String[] parts = paginationStr.replaceAll("[()]", "").split("of");
        if (parts.length == 2) {
            return Integer.parseInt(parts[1].trim());
        }
        return 0;
    }

    public int countCSVDataRows(String fileName) {
        String resourcePath = Paths.get("src", "test", "resources", "testdata", fileName).toAbsolutePath().toString();
        int rowCount = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(resourcePath))) {
            br.readLine(); // Skip header
            while (br.readLine() != null) {
                rowCount++;
            }
        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
        }
        return rowCount;
    }

    public void clickExit() {
        driver.findElement(exitButton).click();
    }
}
