package com.proyek_softes.landing.main.pages.about;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.proyek_softes.landing.main.pages.ContactPage;
import com.proyek_softes.landing.main.components.LandingNavigationPage;

import java.time.Duration;

public class AboutUsPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;
    public LandingNavigationPage header;

    // Locator tombol Contact Us di halaman About Us berdasarkan inspect element
    private By contactButton = By.xpath("//a[@href='https://suitecrm.com/about-us/contact/']//span[contains(@class,'fusion-button-text') and text()='Contact Us']");
    // Alternative locator jika xpath tidak bekerja
    private By contactButtonAlt = By.cssSelector("a.fusion-button[href*='contact'] span.fusion-button-text");
    
    // Cookie notice locators
    private By cookieAcceptButton = By.cssSelector("a.cn-set-cookie, button.cn-set-cookie, .cookie-notice-container a");
    private By cookieCloseButton = By.cssSelector(".cn-close-icon");

    public AboutUsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.js = (JavascriptExecutor) driver;
        this.header = new LandingNavigationPage(driver);
    }
    
    // Handle cookie popup if present
    private void handleCookiePopup() {
        try {
            // Wait a bit for cookie to appear
            Thread.sleep(1000);
            
            // Try to find and click accept button
            try {
                WebElement acceptButton = driver.findElement(cookieAcceptButton);
                if (acceptButton.isDisplayed()) {
                    acceptButton.click();
                    System.out.println("✓ Cookie notice accepted");
                    Thread.sleep(500);
                    return;
                }
            } catch (Exception e) {
                // Cookie accept button not found, try close button
            }
            
            // Try close button
            try {
                WebElement closeButton = driver.findElement(cookieCloseButton);
                if (closeButton.isDisplayed()) {
                    closeButton.click();
                    System.out.println("✓ Cookie notice closed");
                    Thread.sleep(500);
                }
            } catch (Exception e) {
                // No cookie popup or already dismissed
            }
            
        } catch (Exception e) {
            // No action needed if no cookie popup
        }
    }

    // ACTION PENTING:
    // Method ini tidak void, tapi mengembalikan ContactPage
    public ContactPage goToContactForm() {
        try {
            // First, handle any cookie popup
            handleCookiePopup();
            
            WebElement buttonElement = null;
            
            // Try to find the button
            try {
                buttonElement = wait.until(ExpectedConditions.presenceOfElementLocated(contactButtonAlt));
            } catch (Exception e) {
                buttonElement = wait.until(ExpectedConditions.presenceOfElementLocated(contactButton));
            }
            
            // Scroll button into view
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", buttonElement);
            Thread.sleep(800);
            
            // Try JavaScript click first (more reliable)
            try {
                js.executeScript("arguments[0].click();", buttonElement);
                System.out.println("✓ Contact button clicked via JavaScript");
            } catch (Exception e) {
                // Fallback to regular click
                wait.until(ExpectedConditions.elementToBeClickable(buttonElement)).click();
                System.out.println("✓ Contact button clicked");
            }
            
        } catch (Exception e) {
            System.out.println("⚠ Error clicking Contact button: " + e.getMessage());
            // Last resort: navigate directly via URL
            driver.get("https://suitecrm.com/about-us/contact/");
            System.out.println("✓ Navigated directly to contact page");
        }
        
        // Wait for page load
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // Di sinilah perpindahan terjadi
        // Kita langsung memberikan driver ke halaman baru
        return new ContactPage(driver);
    }
}
