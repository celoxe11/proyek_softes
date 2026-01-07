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


public class ProductsPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;
    private Actions actions;

    // ========================================
    // LOCATORS - Element UI
    // ========================================

    // Main Products Menu - using multiple locators for robustness
    private By productsMenuLocator = By.cssSelector("li#menu-item-563881 > a");
    private By productsMenuAlt1 = By.xpath("//nav[@id='menu-main-menu-1']//a[text()='Products']");
    private By productsMenuAlt2 = By.xpath("//a[contains(text(), 'Products') and contains(@class, 'fusion-')]");
    private By productsMenuAlt3 = By.linkText("Products");

    // Sub-menu SuiteCRM
    private By suiteCRMSubMenuLocator = By.cssSelector("li#menu-item-563882 a");
    private By suiteCRMSubMenuAlt1 = By.xpath("//nav[@id='menu-main-menu-1']//a[contains(text(), 'SuiteCRM')]");
    private By suiteCRMSubMenuAlt2 = By.linkText("SuiteCRM");

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
            System.out.println("Hovering ke menu Products");

            WebElement menu = null;
            
            // Try primary locator
            try {
                menu = wait.until(ExpectedConditions.visibilityOfElementLocated(productsMenuLocator));
                System.out.println("Menu Products ditemukan dengan CSS selector (menu-item-563881)");
            } catch (Exception e1) {
                System.out.println("Primary locator gagal, mencoba alternative");
                
                // Try alternative locators
                try {
                    menu = wait.until(ExpectedConditions.visibilityOfElementLocated(productsMenuAlt1));
                    System.out.println("Menu Products ditemukan dengan XPath nav");
                } catch (Exception e2) {
                    try {
                        menu = wait.until(ExpectedConditions.visibilityOfElementLocated(productsMenuAlt2));
                        System.out.println("Menu Products ditemukan dengan XPath fusion");
                    } catch (Exception e3) {
                        menu = wait.until(ExpectedConditions.visibilityOfElementLocated(productsMenuAlt3));
                        System.out.println("Menu Products ditemukan dengan linkText");
                    }
                }
            }
            
            if (menu != null) {
                // Scroll to menu if needed
                js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", menu);
                Thread.sleep(300);
                
                actions.moveToElement(menu).perform();
                Thread.sleep(500);

                System.out.println("Berhasil hover ke menu Products");
                return true;
            }
            
            return false;
        } catch (Exception e) {
            System.out.println("Gagal hover ke menu Products: " + e.getMessage());
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
            System.out.println("Mengklik sub-menu SuiteCRM");

            // Hover dulu ke Products menu
            hoverProductsMenu();

            WebElement subMenu = null;
            
            // Try primary locator
            try {
                subMenu = wait.until(ExpectedConditions.elementToBeClickable(suiteCRMSubMenuLocator));
                System.out.println("SuiteCRM submenu ditemukan dengan CSS selector");
            } catch (Exception e1) {
                System.out.println("Primary locator gagal, mencoba alternative...");
                
                // Try alternative locators
                try {
                    subMenu = wait.until(ExpectedConditions.elementToBeClickable(suiteCRMSubMenuAlt1));
                    System.out.println("SuiteCRM submenu ditemukan dengan XPath nav");
                } catch (Exception e2) {
                    subMenu = wait.until(ExpectedConditions.elementToBeClickable(suiteCRMSubMenuAlt2));
                    System.out.println("SuiteCRM submenu ditemukan dengan linkText");
                }
            }
            
            if (subMenu != null) {
                // Scroll ke element jika perlu
                js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", subMenu);
                Thread.sleep(300);

                // Klik menggunakan Actions
                try {
                    actions.moveToElement(subMenu).click().perform();
                } catch (Exception e) {
                    // Fallback ke JS click
                    js.executeScript("arguments[0].click();", subMenu);
                }

                Thread.sleep(500);
                System.out.println("Berhasil klik sub-menu SuiteCRM");
                return true;
            }
            
            return false;
        } catch (Exception e) {
            System.out.println("Gagal klik sub-menu SuiteCRM: " + e.getMessage());
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
            System.out.println("Mencoba navigasi langsung via URL...");
            driver.get("https://suitecrm.com/suitecrm/");
            waitForPageLoad();
            return true;

        } catch (Exception e) {
            System.out.println("Gagal navigate ke SuiteCRM: " + e.getMessage());
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
            System.out.println("Warning: Page load wait timeout");
        }
    }

    // ========================================
    // SUITEASSURED NAVIGATION
    // ========================================

    /**
     * Navigate ke halaman SuiteASSURED
     * 
     * @return true jika berhasil
     */
    public boolean navigateToSuiteAssured() {
        try {
            // Hover ke Products menu
            hoverProductsMenu();
            Thread.sleep(500);

            // Cari submenu SuiteASSURED
            By[] selectors = {
                    By.xpath("//li[contains(@class, 'menu-item')]//a[contains(text(), 'SuiteASSURED')]"),
                    By.xpath("//a[contains(@href, 'suiteassured')]"),
                    By.xpath("//a[contains(translate(text(), 'SUITEASSURED', 'suiteassured'), 'suiteassured')]"),
                    By.cssSelector("a[href*='suiteassured']")
            };

            WebElement subMenu = null;
            for (By selector : selectors) {
                try {
                    subMenu = wait.until(ExpectedConditions.elementToBeClickable(selector));
                    if (subMenu != null && subMenu.isDisplayed()) {
                        break;
                    }
                } catch (Exception e) {
                    continue;
                }
            }

            if (subMenu != null) {
                js.executeScript("arguments[0].click();", subMenu);
                System.out.println("Berhasil klik sub-menu SuiteASSURED");
                return true;
            }

            // Fallback: direct URL
            System.out.println("Submenu not found, navigating directly...");
            driver.get("https://suitecrm.com/enterprise/suiteassured/");
            return true;

        } catch (Exception e) {
            System.out.println("Gagal navigate ke SuiteASSURED: " + e.getMessage());
            return false;
        }
    }

    /**
     * Click Contact Us button pada halaman SuiteASSURED
     */
    public void clickContactUsButton() {
        try {
            // Scroll down to find button
            js.executeScript("window.scrollBy(0, 300);");
            Thread.sleep(500);

            By[] selectors = {
                    By.xpath("//a[contains(@class, 'fusion-button')]//span[contains(text(), 'CONTACT US')]/parent::a"),
                    By.xpath("//a[contains(@class, 'fusion-button')][contains(text(), 'CONTACT US')]"),
                    By.xpath("//span[contains(text(), 'CONTACT US')]/parent::a"),
                    By.cssSelector("a.fusion-button[href*='contact']"),
                    By.xpath("//a[contains(@href, 'contact')][contains(@class, 'button')]")
            };

            WebElement button = null;
            for (By selector : selectors) {
                try {
                    button = wait.until(ExpectedConditions.elementToBeClickable(selector));
                    if (button != null && button.isDisplayed()) {
                        System.out.println("Found Contact Us button");
                        break;
                    }
                } catch (Exception e) {
                    continue;
                }
            }

            if (button != null) {
                js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", button);
                Thread.sleep(500);

                try {
                    button.click();
                } catch (Exception e) {
                    js.executeScript("arguments[0].click();", button);
                }
                System.out.println("Clicked Contact Us button");
            } else {
                System.out.println("Contact Us button not found");
            }

        } catch (Exception e) {
            System.out.println("Error clicking Contact Us: " + e.getMessage());
        }
    }
}
