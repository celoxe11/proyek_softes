package com.proyek_softes.demo.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WelcomePage {

    private WebDriver driver;
    private WebDriverWait wait;
    private Actions actions;

    // Welcome Locators
    private final By welcomeTitle = By.xpath("//div[@id='about_header']/h1");
    // private final By dashboardButton = By.cssSelector(".navbar-brand.with-home-icon");

    private final By takeATourButton = By.xpath("//a[contains(text(),'Take a quick tour')]");
    private final By swalNextButton = By.xpath("//button[contains(text(),'Next >')]");
    private final By swalFinishButton = By.xpath("//button[contains(text(),'Finished')]");
    private final By swalTitle = By.className("swal2-title");

    private final By learnMoreButton = By.xpath("//a[contains(text(),'Learn more')]");

    private final By profileDropdownButton = By.id("with-label");
    private final By aboutLink = By.cssSelector(".user-dropdown.user-menu li:nth-child(4) a");
    private final By aboutSuiteCRMHeading = By.xpath("//h3[text()='About SuiteCRM']");

    public WelcomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.actions = new Actions(driver);
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

    public void clickFinishedInTour() {
        wait.until(ExpectedConditions.elementToBeClickable(swalFinishButton));
        driver.findElement(swalFinishButton).click();
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
        // Store the original window handle
        String originalWindow = driver.getWindowHandle();

        // Wait for new tab to open
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));

        // Switch to the new tab
        for (String windowHandle : driver.getWindowHandles()) {
            if (!windowHandle.equals(originalWindow)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }

        // Wait for the URL to contain the expected URL
        wait.until(ExpectedConditions.urlContains("https://suitecrm.com/enterprise/suiteassured/"));
        return driver.getCurrentUrl().contains("https://suitecrm.com/enterprise/suiteassured/");
    }

    public void navigateToAboutPage() {
        wait.until(ExpectedConditions.presenceOfElementLocated(profileDropdownButton));
        wait.until(ExpectedConditions.visibilityOfElementLocated(profileDropdownButton));
        
        // Hover over the profile button to trigger dropdown
        actions.moveToElement(driver.findElement(profileDropdownButton)).perform();
        
        // Wait for about link to be present in DOM
        wait.until(ExpectedConditions.presenceOfElementLocated(aboutLink));
        
        // Give time for CSS transition to complete
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Use JavaScript click since normal click may not work with hover dropdown
        org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", driver.findElement(aboutLink));
    }

    public boolean isInAboutPage() {
        try {
            // Wait for About page to load and check for "About SuiteCRM" heading
            wait.until(ExpectedConditions.visibilityOfElementLocated(aboutSuiteCRMHeading));
            return driver.findElement(aboutSuiteCRMHeading).isDisplayed();

        } catch (Exception e) {
            System.out.println("Error navigating to About page: " + e.getMessage());
            return false;
        }
    }
}
