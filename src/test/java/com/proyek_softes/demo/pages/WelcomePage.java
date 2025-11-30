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
    private By dashboardButton = By.cssSelector(".navbar-brand.with-home-icon");

    public WelcomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public String getWelcomeTitle() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(welcomeTitle));
        return driver.findElement(welcomeTitle).getText();
    }

    
}