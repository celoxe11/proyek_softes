package com.proyek_softes.demo.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WelcomePage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Welcome Locators
    private By welcomeTitle = By.xpath("//div[@id='about_header']/h1");
    private By dashboardButton = By.cssSelector(".navbar-brand.with-home-icon");

    private By takeATourButton = By.xpath("//a[contains(text(),'Take a quick tour')]");
    private By swalNextButton = By.xpath("//button[contains(text(),'Next >')]");
    private By swalTitle = By.className("swal2-title");

    private By learnMoreButton = By.xpath("//a[contains(text(),'Learn more')]");

    public WelcomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public String getWelcomeTitle() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(welcomeTitle));
        return driver.findElement(welcomeTitle).getText();
    }

    public void clickTakeATour() {
        wait.until(ExpectedConditions.elementToBeClickable(takeATourButton));
        driver.findElement(takeATourButton).click();
    }

    public void clickNextInTour() {
        wait.until(ExpectedConditions.elementToBeClickable(swalNextButton));
        driver.findElement(swalNextButton).click();
    }
    
    public boolean isOnStep5() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(swalTitle));
        String title = driver.findElement(swalTitle).getText();
        return title.equalsIgnoreCase("Redefine Customisation");
    }

    public void clickLearnMore() {
        wait.until(ExpectedConditions.elementToBeClickable(learnMoreButton));
        driver.findElement(learnMoreButton).click();
    }

    public boolean isInSuiteAssuredPage() {
        wait.until(ExpectedConditions.urlContains("https://suitecrm.com/enterprise/suiteassured/"));
        return driver.getCurrentUrl().contains("https://suitecrm.com/enterprise/suiteassured/");
    }
}