package com.proyek_softes.demo.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private Actions actions;

    // locators for SuiteCRM 7 demo login
    private final By usernameField = By.id("user_name");
    private final By passwordField = By.id("username_password");
    private final By loginButton = By.id("bigbutton");

    // locators for SuiteCRM 8 demo login
    private final By usernameField8 = By.name("username");
    private final By passwordField8 = By.name("password");
    private final By loginButton8 = By.id("login-button");

    // Get Started
    private final By getStartedButton = By.id("menu-item-564397");
    private final By demoLink = By.id("menu-item-564400");
    private final By demoSuiteCRM7Link = By.xpath("//span[contains(text(),'ACCESS THE SUITECRM 7 ESR DEMO')]/parent::a");
    private final By demoSuiteCRM8Link = By.xpath("//span[contains(text(),'Access the SuiteCRM 8 Demo')]/parent::a");

    // Logout
    private final By profileDropdownButton = By.id("with-label");
    private final By logoutLink = By.cssSelector(".user-dropdown.user-menu li:nth-child(5) a");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        this.actions = new Actions(driver);
    }

    public void navigateToDemo7() {
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
        actions.moveToElement(driver.findElement(getStartedButton)).perform();

        // wait until sub-menu is visible and click on Demo link
        wait.until(ExpectedConditions.visibilityOfElementLocated(demoLink)).click();

        // wait until demo links are visible
        WebElement demo8Link = wait.until(ExpectedConditions.visibilityOfElementLocated(demoSuiteCRM8Link));

        // Scroll to the link to ensure it's visible
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", demo8Link);

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

    public void navigateToLogin(int suiteVersion) {
        if (suiteVersion == 8) {
            driver.get("https://suite8demo.suiteondemand.com/#/Login");
        } else {
            driver.get("https://demo.suiteondemand.com/index.php?module=Users&action=Login");
        }
    }

    public void login(String username, String password) {

        if (driver.getCurrentUrl().contains("module=Users&action=Login") == false) {
            navigateToLogin(7);
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

        if (driver.getCurrentUrl().contains("/#/Login") == false) {
            navigateToLogin(8);
        }

        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("username")));

        // Check if login inputs are present (if not, we might already be logged in)
        if (!driver.findElements(By.name("username")).isEmpty()) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField8)).sendKeys(username);
            driver.findElement(passwordField8).sendKeys(password);
            driver.findElement(loginButton8).click();

            // Wait for dashboard to fully load after login (SPA approach)
            try {
                // First, wait for URL to change away from Login page
                wait.until(ExpectedConditions.not(
                        ExpectedConditions.urlContains("/#/Login")));
                
                // Wait for any loading overlays to disappear
                try {
                    wait.until(ExpectedConditions.invisibilityOfElementLocated(
                            By.cssSelector(".loading-overlay, .spinner, #overlay-spinner")));
                } catch (Exception e) {
                    // Loading overlay might not exist, continue
                }
                
                // Wait for document ready state
                JavascriptExecutor js = (JavascriptExecutor) driver;
                wait.until(driver -> js.executeScript("return document.readyState").equals("complete"));
                
                // Use JavaScript-based polling to wait for dashboard
                // This is more reliable for SPAs than Selenium's ExpectedConditions
                boolean dashboardFound = waitForElementWithJS(js, "dashboard", 15);
                
                // Debug: Print current URL and check page state
                System.out.println("DEBUG: Current URL = " + driver.getCurrentUrl());
                System.out.println("DEBUG: Dashboard found via JS = " + dashboardFound);
                
                // Check if there are any iframes that might contain the dashboard
                Long iframeCount = (Long) js.executeScript("return document.getElementsByTagName('iframe').length");
                System.out.println("DEBUG: Number of iframes on page = " + iframeCount);
                
                // Check what elements exist with 'dashboard' in their id or class
                String dashboardElements = (String) js.executeScript(
                    "var elements = document.querySelectorAll('[id*=\"dashboard\"], [class*=\"dashboard\"]');" +
                    "return Array.from(elements).map(e => 'Tag:' + e.tagName + ' ID:' + e.id + ' Class:' + e.className).join('; ');"
                );
                System.out.println("DEBUG: Elements with 'dashboard' = " + dashboardElements);
                
                if (dashboardFound) {
                    // Small additional wait for any remaining AJAX calls
                    Thread.sleep(1000);
                    System.out.println("✓ Login successful and dashboard loaded");
                } else {
                    // Even if not found, continue anyway - the test will verify later
                    System.out.println("Warning: Dashboard element not found via JavaScript polling, continuing anyway...");
                    Thread.sleep(2000); // Give extra time for page to stabilize
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                System.out.println("Warning: Dashboard may not be fully loaded - " + e.getMessage());
            }
        } else {
            System.out.println("Already logged in or redirected to dashboard.");
        }
    }
    
    /**
     * Uses JavaScript to poll for an element by ID, more reliable for SPAs
     */
    private boolean waitForElementWithJS(JavascriptExecutor js, String elementId, int timeoutSeconds) {
        long endTime = System.currentTimeMillis() + (timeoutSeconds * 1000);
        
        while (System.currentTimeMillis() < endTime) {
            try {
                // Check if element exists and is visible
                Boolean result = (Boolean) js.executeScript(
                    "var elem = document.getElementById('" + elementId + "');" +
                    "if (elem) {" +
                    "    var rect = elem.getBoundingClientRect();" +
                    "    var style = window.getComputedStyle(elem);" +
                    "    return rect.width > 0 && rect.height > 0 && " +
                    "           style.display !== 'none' && " +
                    "           style.visibility !== 'hidden';" +
                    "}" +
                    "return false;"
                );
                
                if (Boolean.TRUE.equals(result)) {
                    return true;
                }
                
                Thread.sleep(500);
            } catch (Exception e) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    return false;
                }
            }
        }
        return false;
    }

    public void logout() {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(profileDropdownButton));
            wait.until(ExpectedConditions.visibilityOfElementLocated(profileDropdownButton));

            // Hover over the profile button to trigger dropdown
            actions.moveToElement(driver.findElement(profileDropdownButton)).perform();

            // Wait for about link to be present in DOM
            wait.until(ExpectedConditions.presenceOfElementLocated(logoutLink));

            // Give time for CSS transition to complete
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            // Use JavaScript click since normal click may not work with hover dropdown
            org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", driver.findElement(logoutLink));

        } catch (Exception e) {
            System.out.println("Error during logout: " + e.getMessage());
        }
    }

    public boolean isInLoginPage() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(loginButton));
            return driver.findElement(loginButton).isDisplayed();
        } catch (Exception e) {
            System.out.println("Error verifying login page: " + e.getMessage());
            return false;
        }
    }
}
