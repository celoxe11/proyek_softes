package com.proyek_softes.demo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class OpportunitiesPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By createOpportunityBtn = By.name("Create");
    private By opportunityNameField = By.id("name");
    private By amountField = By.id("amount");
    private By salesStageDropdown = By.id("sales_stage");
    private By saveBtn = By.id("SAVE");
    private By firstOpportunityLink = By.xpath("(//table[@class='list view table-responsive']//tr[@class='oddListRowS1']//a)[1]");
    private By editBtn = By.id("edit_button");
    private By deleteBtn = By.id("delete_button");

    public OpportunitiesPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public OpportunitiesPage createOpportunity(String name, String amount, String salesStage) {
        wait.until(ExpectedConditions.elementToBeClickable(createOpportunityBtn)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(opportunityNameField)).sendKeys(name);
        driver.findElement(amountField).sendKeys(amount);
        
        Select salesStageSelect = new Select(driver.findElement(salesStageDropdown));
        salesStageSelect.selectByVisibleText(salesStage);
        
        driver.findElement(saveBtn).click();
        return this;
    }

    public OpportunitiesPage openFirstOpportunity() {
        wait.until(ExpectedConditions.elementToBeClickable(firstOpportunityLink)).click();
        return this;
    }

    public OpportunitiesPage updateOpportunity(String newName) {
        wait.until(ExpectedConditions.elementToBeClickable(editBtn)).click();
        WebElement nameField = wait.until(ExpectedConditions.visibilityOfElementLocated(opportunityNameField));
        nameField.clear();
        nameField.sendKeys(newName);
        driver.findElement(saveBtn).click();
        return this;
    }

    public OpportunitiesPage deleteOpportunity() {
        wait.until(ExpectedConditions.elementToBeClickable(deleteBtn)).click();
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
        return this;
    }

    public boolean isOpportunityCreated() {
        try {
            return driver.getCurrentUrl().contains("Opportunities");
        } catch (Exception e) {
            return false;
        }
    }
}
