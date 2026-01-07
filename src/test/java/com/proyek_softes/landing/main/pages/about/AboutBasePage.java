package com.proyek_softes.landing.main.pages.about;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Base class untuk semua halaman About
 * Berisi method umum yang dapat digunakan oleh semua sub-menu About
 */
public class AboutBasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected JavascriptExecutor js;
    protected Actions actions;

    // Main About Menu
    private By aboutMenuLocator = By.cssSelector("li#menu-item-563873 > a");

    // Sub-menu locators
    private By aboutUsSubMenuLocator = By.cssSelector("li#menu-item-563874 a");
    private By roadmapSubMenuLocator = By.cssSelector("li#menu-item-563875 a");
    private By journeySubMenuLocator = By.cssSelector("li#menu-item-563876 a");
    private By newsPressSubMenuLocator = By.cssSelector("li#menu-item-563877 a");
    private By careersSubMenuLocator = By.cssSelector("li#menu-item-563878 a");
    private By contactUsSubMenuLocator = By.cssSelector("li#menu-item-563879 a");
    private By newsletterSubMenuLocator = By.cssSelector("li#menu-item-563880 a");

    // ========================================
    // CONSTRUCTOR
    // ========================================

    public AboutBasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.js = (JavascriptExecutor) driver;
        this.actions = new Actions(driver);
        PageFactory.initElements(driver, this);
    }

    // ========================================
    // COMMON METHODS - Method umum untuk semua sub-menu
    // ========================================

    /**
     * Hover ke menu About untuk membuka dropdown
     * 
     * @return true jika berhasil
     */
    public boolean hoverAboutMenu() {
        try {
            System.out.println("Hovering ke menu About");

            WebElement menu = wait.until(ExpectedConditions.visibilityOfElementLocated(aboutMenuLocator));
            actions.moveToElement(menu).perform();

            // Tunggu dropdown muncul
            Thread.sleep(500);

            System.out.println("Berhasil hover ke menu About");
            return true;
        } catch (Exception e) {
            System.out.println("Gagal hover ke menu About: " + e.getMessage());
            return false;
        }
    }

    /**
     * Navigate ke sub-menu About Us
     * 
     * @return true jika berhasil
     */
    public boolean navigateToAboutUs() {
        return navigateToSubMenu(aboutUsSubMenuLocator, "About Us");
    }

    /**
     * Navigate ke sub-menu SuiteCRM Roadmap
     * 
     * @return true jika berhasil
     */
    public boolean navigateToRoadmap() {
        return navigateToSubMenu(roadmapSubMenuLocator, "SuiteCRM Roadmap");
    }

    /**
     * Navigate ke sub-menu SuiteCRM Journey
     * 
     * @return true jika berhasil
     */
    public boolean navigateToJourney() {
        return navigateToSubMenu(journeySubMenuLocator, "SuiteCRM Journey");
    }

    /**
     * Navigate ke sub-menu News & Press
     * 
     * @return true jika berhasil
     */
    public boolean navigateToNewsPress() {
        return navigateToSubMenu(newsPressSubMenuLocator, "News & Press");
    }

    /**
     * Navigate ke sub-menu Careers
     * 
     * @return true jika berhasil
     */
    public boolean navigateToCareers() {
        return navigateToSubMenu(careersSubMenuLocator, "Careers");
    }

    /**
     * Navigate ke sub-menu Contact Us
     * 
     * @return true jika berhasil
     */
    public boolean navigateToContactUs() {
        return navigateToSubMenu(contactUsSubMenuLocator, "Contact Us");
    }

    /**
     * Navigate ke sub-menu Newsletter Sign Up
     * 
     * @return true jika berhasil
     */
    public boolean navigateToNewsletterSignUp() {
        return navigateToSubMenu(newsletterSubMenuLocator, "Newsletter Sign Up");
    }

    // ========================================
    // HELPER METHODS
    // ========================================

    /**
     * Helper method untuk navigate ke sub-menu
     * 
     * @param locator  locator dari sub-menu
     * @param menuName nama sub-menu (untuk logging)
     * @return true jika berhasil
     */
    protected boolean navigateToSubMenu(By locator, String menuName) {
        try {
            // Hover ke menu utama
            if (!hoverAboutMenu()) {
                return false;
            }

            System.out.println("Mencoba klik sub-menu: " + menuName);

            // Klik sub-menu
            WebElement subMenu = wait.until(ExpectedConditions.elementToBeClickable(locator));
            subMenu.click();

            System.out.println("Berhasil klik sub-menu: " + menuName);

            // Tunggu halaman load
            waitForPageLoad();

            return true;
        } catch (Exception e) {
            System.out.println("Gagal navigate ke sub-menu " + menuName + ": " + e.getMessage());
            return false;
        }
    }

    /**
     * Wait untuk halaman selesai loading
     */
    public void waitForPageLoad() {
        try {
            wait.until(webDriver -> ((JavascriptExecutor) webDriver)
                    .executeScript("return document.readyState").equals("complete"));
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println("Page load wait exception: " + e.getMessage());
        }
    }

    /**
     * Get current URL
     * 
     * @return current URL
     */
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    /**
     * Scroll ke element dan click
     * 
     * @param locator     locator element
     * @param elementName nama element (untuk logging)
     * @return true jika berhasil
     */
    protected boolean scrollAndClick(By locator, String elementName) {
        try {
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));

            // Scroll ke element
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
            Thread.sleep(500);

            // Click element
            wait.until(ExpectedConditions.elementToBeClickable(locator)).click();

            System.out.println("Berhasil klik: " + elementName);
            return true;
        } catch (Exception e) {
            System.out.println("Gagal klik " + elementName + ": " + e.getMessage());
            return false;
        }
    }

    /**
     * Switch ke new tab/window
     */
    public void switchToNewTab() {
        try {
            String originalWindow = driver.getWindowHandle();
            for (String windowHandle : driver.getWindowHandles()) {
                if (!windowHandle.equals(originalWindow)) {
                    driver.switchTo().window(windowHandle);
                    break;
                }
            }
            waitForPageLoad();
        } catch (Exception e) {
            System.out.println("Error switching to new tab: " + e.getMessage());
        }
    }

    /**
     * Helper method untuk scroll halaman
     * 
     * @param percentage persentase scroll (0.0 - 1.0)
     */
    protected void scrollToPercentage(double percentage) {
        try {
            js.executeScript("window.scrollTo(0, document.body.scrollHeight * " + percentage + ");");
            Thread.sleep(500);
        } catch (Exception e) {
            // Continue
        }
    }
}
