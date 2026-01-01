package com.proyek_softes.landing.main.pages.products;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Page Object Model untuk halaman Products
 * Memisahkan locators dan interaksi UI dari logika test
 */
public class ProductsPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;
    private Actions actions;

    // ========================================
    // LOCATORS - Element UI
    // ========================================

    // Main Products Menu
    @FindBy(xpath = "//a[contains(@class, 'menu-item') and contains(translate(., 'PRODUCTS', 'products'), 'products')]")
    private WebElement productsMenu;

    // Alternative locator for Products menu
    private By productsMenuLocator = By.xpath(
            "//li[contains(@class, 'menu-item')]//a[contains(translate(text(), 'PRODUCTS', 'products'), 'products')]");

    // Sub-menu SuiteCRM
    @FindBy(xpath = "//a[contains(@href, 'suitecrm') and contains(@class, 'menu-item')]")
    private WebElement suiteCRMSubMenu;

    // Alternative locators for SuiteCRM submenu
    private By suiteCRMSubMenuLocator = By.xpath(
            "//li[contains(@class, 'menu-item')]//a[contains(translate(text(), 'SUITECRM', 'suitecrm'), 'suitecrm')]");

    // ========================================
    // CONSTRUCTOR
    // ========================================

    public ProductsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.js = (JavascriptExecutor) driver;
        this.actions = new Actions(driver);
        PageFactory.initElements(driver, this);
    }

    // ========================================
    // PAGE ACTIONS - Interaksi dengan UI
    // ========================================

    /**
     * Hover ke menu Products untuk membuka dropdown
     * 
     * @return true jika berhasil
     */
    public boolean hoverProductsMenu() {
        try {
            System.out.println("🔄 Hovering ke menu Products...");

            WebElement menu = wait.until(ExpectedConditions.visibilityOfElementLocated(productsMenuLocator));
            actions.moveToElement(menu).perform();

            // Tunggu dropdown muncul
            Thread.sleep(500);

            System.out.println("✓ Berhasil hover ke menu Products");
            return true;
        } catch (Exception e) {
            System.out.println("❌ Gagal hover ke menu Products: " + e.getMessage());
            return false;
        }
    }

    /**
     * Klik sub-menu SuiteCRM
     * 
     * @return true jika berhasil
     */
    public boolean clickSuiteCRMSubMenu() {
        try {
            System.out.println("🔄 Mengklik sub-menu SuiteCRM...");

            // Hover dulu ke Products menu
            hoverProductsMenu();

            // Cari dan klik SuiteCRM submenu
            WebElement subMenu = wait.until(ExpectedConditions.elementToBeClickable(suiteCRMSubMenuLocator));

            // Scroll ke element jika perlu
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", subMenu);
            Thread.sleep(300);

            // Klik
            try {
                subMenu.click();
            } catch (Exception e) {
                js.executeScript("arguments[0].click();", subMenu);
            }

            System.out.println("✓ Berhasil klik sub-menu SuiteCRM");
            return true;
        } catch (Exception e) {
            System.out.println("❌ Gagal klik sub-menu SuiteCRM: " + e.getMessage());
            return false;
        }
    }

    /**
     * Navigate langsung ke halaman Products > SuiteCRM
     * 
     * @return true jika berhasil
     */
    public boolean navigateToSuiteCRM() {
        try {
            // Method 1: Via menu navigation
            if (hoverProductsMenu() && clickSuiteCRMSubMenu()) {
                waitForPageLoad();
                return true;
            }

            // Method 2: Direct URL navigation sebagai fallback
            System.out.println("⚠️ Mencoba navigasi langsung via URL...");
            driver.get("https://suitecrm.com/suitecrm/");
            waitForPageLoad();
            return true;

        } catch (Exception e) {
            System.out.println("❌ Gagal navigate ke SuiteCRM: " + e.getMessage());
            return false;
        }
    }

    /**
     * Mendapatkan current URL
     * 
     * @return current URL
     */
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    /**
     * Menunggu halaman selesai loading
     */
    public void waitForPageLoad() {
        try {
            wait.until(driver -> js.executeScript("return document.readyState").equals("complete"));
            Thread.sleep(1000); // Extra wait untuk dynamic content
        } catch (Exception e) {
            System.out.println("⚠️ Warning: Page load wait timeout");
        }
    }
}
