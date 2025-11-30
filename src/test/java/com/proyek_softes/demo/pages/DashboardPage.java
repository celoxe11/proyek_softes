package com.proyek_softes.demo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DashboardPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By dashboardButton = By.cssSelector(".navbar-brand.with-home-icon");
    private By dashboardPresentation = By.xpath("//a[@id='tab0']");
    private By actionsDropdown = By.xpath("//li[@id='tab-actions']/a");
    private By addDashletButton = By.className("addDashlets");
    private By addDashletModal = By.className("modal-add-dashlet");

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
    }

    public void navigateToDashboard() {
        WebElement dashboardBtn = driver.findElement(dashboardButton);
        dashboardBtn.click();
    }

    public String getDashboardTitle() {
        return driver.findElement(dashboardPresentation).getText();
    }

    public void actionsDropdownClicked() {
        driver.findElement(actionsDropdown).click();
    }

    public void addDashlet() {
        driver.findElement(addDashletButton).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(addDashletModal));
    }
}
