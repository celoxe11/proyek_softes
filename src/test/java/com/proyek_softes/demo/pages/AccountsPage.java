package com.proyek_softes.demo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class AccountsPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By createAccountBtn = By.name("Create");
    private By accountNameField = By.id("name");
    private By phoneField = By.id("phone_office");
    private By websiteField = By.id("website");
    private By saveBtn = By.id("SAVE");
    private By successMessage = By.xpath("//*[contains(@class, 'alert')]");
    private By firstAccountLink = By.xpath("(//table[@class='list view table-responsive']//tr[@class='oddListRowS1']//a)[1]");
    private By editBtn = By.id("edit_button");
    private By deleteBtn = By.id("delete_button");
    private By confirmDeleteBtn = By.xpath("//input[@value='Delete']");

    public AccountsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public AccountsPage createAccount(String accountName, String phone, String website) {
        wait.until(ExpectedConditions.elementToBeClickable(createAccountBtn)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(accountNameField)).sendKeys(accountName);
        driver.findElement(phoneField).sendKeys(phone);
        driver.findElement(websiteField).sendKeys(website);
        driver.findElement(saveBtn).click();
        return this;
    }

    public AccountsPage openFirstAccount() {
        wait.until(ExpectedConditions.elementToBeClickable(firstAccountLink)).click();
        return this;
    }

    public AccountsPage updateAccount(String newAccountName) {
        wait.until(ExpectedConditions.elementToBeClickable(editBtn)).click();
        WebElement nameField = wait.until(ExpectedConditions.visibilityOfElementLocated(accountNameField));
        nameField.clear();
        nameField.sendKeys(newAccountName);
        driver.findElement(saveBtn).click();
        return this;
    }

    public AccountsPage deleteAccount() {
        wait.until(ExpectedConditions.elementToBeClickable(deleteBtn)).click();
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
        return this;
    }

    public boolean isAccountCreated() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}
