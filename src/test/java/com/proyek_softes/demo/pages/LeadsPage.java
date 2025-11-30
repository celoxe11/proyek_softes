package com.proyek_softes.demo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LeadsPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By createLeadBtn = By.name("Create");
    private By firstNameField = By.id("first_name");
    private By lastNameField = By.id("last_name");
    private By companyField = By.id("account_name");
    private By emailField = By.id("Leads0emailAddress0");
    private By saveBtn = By.id("SAVE");
    private By firstLeadLink = By.xpath("(//table[@class='list view table-responsive']//tr[@class='oddListRowS1']//a)[1]");
    private By editBtn = By.id("edit_button");
    private By deleteBtn = By.id("delete_button");

    public LeadsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public LeadsPage createLead(String firstName, String lastName, String company, String email) {
        wait.until(ExpectedConditions.elementToBeClickable(createLeadBtn)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameField)).sendKeys(firstName);
        driver.findElement(lastNameField).sendKeys(lastName);
        driver.findElement(companyField).sendKeys(company);
        driver.findElement(emailField).sendKeys(email);
        driver.findElement(saveBtn).click();
        return this;
    }

    public LeadsPage openFirstLead() {
        wait.until(ExpectedConditions.elementToBeClickable(firstLeadLink)).click();
        return this;
    }

    public LeadsPage updateLead(String newCompany) {
        wait.until(ExpectedConditions.elementToBeClickable(editBtn)).click();
        WebElement companyInput = wait.until(ExpectedConditions.visibilityOfElementLocated(companyField));
        companyInput.clear();
        companyInput.sendKeys(newCompany);
        driver.findElement(saveBtn).click();
        return this;
    }

    public LeadsPage deleteLead() {
        wait.until(ExpectedConditions.elementToBeClickable(deleteBtn)).click();
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
        return this;
    }

    public boolean isLeadCreated() {
        try {
            return driver.getCurrentUrl().contains("Leads");
        } catch (Exception e) {
            return false;
        }
    }
}
