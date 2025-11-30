package com.proyek_softes.demo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class WelcomePage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Welcome Locators
    private By welcomeTitle = By.xpath("//div[@id='about_header']/h1");
    private By accountsTab = By.id("grouptab_0");
    private By accountsMenu = By.id("moduleTab_9_Accounts");
    private By contactsMenu = By.id("moduleTab_9_Contacts");
    private By opportunitiesMenu = By.id("moduleTab_9_Opportunities");
    private By leadsMenu = By.id("moduleTab_9_Leads");

    public WelcomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public String getWelcomeTitle() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(welcomeTitle));
        return driver.findElement(welcomeTitle).getText();
    }

    public AccountsPage navigateToAccounts() {
        wait.until(ExpectedConditions.elementToBeClickable(accountsTab)).click();
        wait.until(ExpectedConditions.elementToBeClickable(accountsMenu)).click();
        return new AccountsPage(driver);
    }

    public ContactsPage navigateToContacts() {
        wait.until(ExpectedConditions.elementToBeClickable(accountsTab)).click();
        wait.until(ExpectedConditions.elementToBeClickable(contactsMenu)).click();
        return new ContactsPage(driver);
    }

    public OpportunitiesPage navigateToOpportunities() {
        wait.until(ExpectedConditions.elementToBeClickable(accountsTab)).click();
        wait.until(ExpectedConditions.elementToBeClickable(opportunitiesMenu)).click();
        return new OpportunitiesPage(driver);
    }

    public LeadsPage navigateToLeads() {
        wait.until(ExpectedConditions.elementToBeClickable(accountsTab)).click();
        wait.until(ExpectedConditions.elementToBeClickable(leadsMenu)).click();
        return new LeadsPage(driver);
    }
}