package com.proyek_softes.demo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private Actions actions;

    // locators for SuiteCRM 7 demo login
    private By usernameField = By.id("user_name");
    private By passwordField = By.id("username_password");
    private By loginButton = By.id("bigbutton");

    // locators for SuiteCRM 8 demo login
    private By usernameField8 = By.id("username");
    private By passwordField8 = By.id("password");
    private By loginButton8 = By.id("login-button");

    // Get Started
    private By getStartedButton = By.id("menu-item-564397");
    private By demoLink = By.id("menu-item-564400");
    private By demoSuiteCRM7Link = By.xpath("//span[contains(text(),'ACCESS THE SUITECRM 7 ESR DEMO')]/parent::a");
    private By demoSuiteCRM8Link = By.xpath("//span[contains(text(),'ACCESS THE SUITECRM 8 DEMO')]/parent::a");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void navigateToDemo7() {
        // hover over Get Started
        actions = new Actions(driver);
        actions.moveToElement(driver.findElement(getStartedButton)).perform();

        // wait until sub-menu is visible and click on Demo link
        wait.until(ExpectedConditions.visibilityOfElementLocated(demoLink)).click();

        // Wait for page to load and link to be present
        WebElement demo7Link = wait.until(ExpectedConditions.presenceOfElementLocated(demoSuiteCRM7Link));

        // Scroll to the link to ensure it's visible
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", demo7Link);

        // Wait a moment for scroll to complete
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Wait for element to be clickable and click using JavaScript if needed
        wait.until(ExpectedConditions.elementToBeClickable(demo7Link));
        try {
            demo7Link.click();
        } catch (Exception e) {
            // Fallback to JavaScript click if regular click fails
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", demo7Link);
        }
    }

    public void navigateToDemo8() {
        // hover over Get Started
        actions = new Actions(driver);
        actions.moveToElement(driver.findElement(getStartedButton)).perform();

        // wait until sub-menu is visible and click on Demo link
        wait.until(ExpectedConditions.visibilityOfElementLocated(demoLink)).click();

        // wait until demo links are visible
        WebElement demo8Link = wait.until(ExpectedConditions.visibilityOfElementLocated(demoSuiteCRM8Link));

        // Scroll to the link to ensure it's visible
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", demo8Link);

        // Wait a moment for scroll to complete
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Wait for element to be clickable and click using JavaScript if needed
        wait.until(ExpectedConditions.elementToBeClickable(demo8Link));
        try {
            demo8Link.click();
        } catch (Exception e) {
            // Fallback to JavaScript click if regular click fails
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", demo8Link);
        }
    }

    public void navigateToLogin() {
        driver.get("https://demo.suiteondemand.com/index.php?module=Users&action=Login");
    }

    public void login(String username, String password) {

        if (driver.getCurrentUrl().contains("module=Users&action=Login") == false) {
            navigateToLogin();
        }

        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("user_name")));

        // Check if login inputs are present (if not, we might already be logged in)
        if (!driver.findElements(By.id("user_name")).isEmpty()) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField)).sendKeys(username);
            driver.findElement(passwordField).sendKeys(password);
            driver.findElement(loginButton).click();

            // Wait for dashboard to fully load after login
            try {
                wait.until(ExpectedConditions
                        .presenceOfElementLocated(By.id("grouptab_0")));

                // Additional wait to ensure page is fully stable
                Thread.sleep(2000);
                System.out.println("✓ Login successful and dashboard loaded");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                System.out.println("Warning: Dashboard may not be fully loaded - " + e.getMessage());
            }
        } else {
            System.out.println("Already logged in or redirected to dashboard.");
        }
    }

    public void login8(String username, String password) {

        if (driver.getCurrentUrl().contains("module=Users&action=Login") == false) {
            navigateToLogin();
        }

        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("username")));

        // Check if login inputs are present (if not, we might already be logged in)
        if (!driver.findElements(By.id("username")).isEmpty()) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField8)).sendKeys(username);
            driver.findElement(passwordField8).sendKeys(password);
            driver.findElement(loginButton8).click();

            // Wait for dashboard to fully load after login
            try {
                wait.until(ExpectedConditions
                        .presenceOfElementLocated(By.id("grouptab_0")));

                // Additional wait to ensure page is fully stable
                Thread.sleep(2000);
                System.out.println("✓ Login successful and dashboard loaded");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                System.out.println("Warning: Dashboard may not be fully loaded - " + e.getMessage());
            }
        } else {
            System.out.println("Already logged in or redirected to dashboard.");
        }
    }
}
