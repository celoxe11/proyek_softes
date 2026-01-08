package com.proyek_softes.landing.main.pages.services;

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
 * Base class untuk semua halaman Services
 * Berisi method umum yang dapat digunakan oleh semua sub-menu Services
 */
public class ServicesPage {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected JavascriptExecutor js;
    protected Actions actions;

    // Main Services Menu
    private By servicesMenuLocator = By.cssSelector("li#menu-item-564358 > a");

    // Sub-menu locators
    private By supportServicesSubMenuLocator = By.cssSelector("li#menu-item-564362 a");
    private By consultancySubMenuLocator = By.cssSelector("li#menu-item-564360 a");
    private By suiteCRMHostedSubMenuLocator = By.cssSelector("li#menu-item-564361 a");
    private By suiteCRMMigrationSubMenuLocator = By.cssSelector("li#menu-item-564363 a");
    private By evsSubMenuLocator = By.cssSelector("li#menu-item-564364 a");

    // ========================================
    // CONSTRUCTOR
    // ========================================

    public ServicesPage(WebDriver driver) {
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
     * Hover ke menu Services untuk membuka dropdown
     * 
     * @return true jika berhasil
     */
    public boolean hoverServicesMenu() {
        try {
            System.out.println("Hovering ke menu Services");

            WebElement menu = wait.until(ExpectedConditions.visibilityOfElementLocated(servicesMenuLocator));
            actions.moveToElement(menu).perform();

            // Tunggu dropdown muncul
            Thread.sleep(500);

            System.out.println("Berhasil hover ke menu Services");
            return true;
        } catch (Exception e) {
            System.out.println("Gagal hover ke menu Services: " + e.getMessage());
            return false;
        }
    }

    /**
     * Navigate ke sub-menu Support Services
     * 
     * @return true jika berhasil
     */
    public boolean navigateToSupportServices() {
        try {
            System.out.println("Navigating ke Support Services");
            hoverServicesMenu();
            Thread.sleep(500);

            By[] locators = {
                By.cssSelector("li#menu-item-564362 a"),
                By.xpath("//li[@id='menu-item-564362']//a"),
                By.cssSelector("a[href*='support-services']"),
                By.partialLinkText("Support Services")
            };

            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(1));
            for (By locator : locators) {
                try {
                    WebElement element = shortWait.until(ExpectedConditions.elementToBeClickable(locator));
                    element.click();
                    Thread.sleep(1000);
                    System.out.println("Berhasil navigate ke Support Services");
                    return true;
                } catch (Exception e) {
                    continue;
                }
            }

            System.out.println("Semua locator gagal, menggunakan direct URL navigation");
            driver.get("https://suitecrm.com/en/support-services/");
            Thread.sleep(2000);
            return true;
        } catch (Exception e) {
            System.out.println("Gagal navigate ke Support Services: " + e.getMessage());
            return false;
        }
    }

    /**
     * Navigate ke sub-menu Consultancy and Implementation
     * 
     * @return true jika berhasil
     */
    public boolean navigateToConsultancy() {
        try {
            System.out.println("Navigating ke Consultancy and Implementation");
            hoverServicesMenu();
            Thread.sleep(500);

            By[] locators = {
                By.cssSelector("li#menu-item-564360 a"),
                By.xpath("//li[@id='menu-item-564360']//a"),
                By.cssSelector("a[href*='customisation']"),
                By.partialLinkText("Consultancy")
            };

            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(1));
            for (By locator : locators) {
                try {
                    WebElement element = shortWait.until(ExpectedConditions.elementToBeClickable(locator));
                    element.click();
                    Thread.sleep(1000);
                    System.out.println("Berhasil navigate ke Consultancy and Implementation");
                    return true;
                } catch (Exception e) {
                    continue;
                }
            }

            System.out.println("Semua locator gagal, menggunakan direct URL navigation");
            driver.get("https://suitecrm.com/en/enterprise/customisation/");
            Thread.sleep(2000);
            return true;
        } catch (Exception e) {
            System.out.println("Gagal navigate ke Consultancy and Implementation: " + e.getMessage());
            return false;
        }
    }

    /**
     * Navigate ke sub-menu SuiteCRM Hosted
     * 
     * @return true jika berhasil
     */
    public boolean navigateToSuiteCRMHosted() {
        try {
            System.out.println("Navigating ke SuiteCRM Hosted");
            hoverServicesMenu();
            Thread.sleep(500);

            By[] locators = {
                By.cssSelector("li#menu-item-564361 a"),
                By.xpath("//li[@id='menu-item-564361']//a"),
                By.cssSelector("a[href*='suitecrmhosted']"),
                By.partialLinkText("SuiteCRM Hosted")
            };

            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(1));
            for (By locator : locators) {
                try {
                    WebElement element = shortWait.until(ExpectedConditions.elementToBeClickable(locator));
                    element.click();
                    Thread.sleep(1000);
                    System.out.println("Berhasil navigate ke SuiteCRM Hosted");
                    return true;
                } catch (Exception e) {
                    continue;
                }
            }

            System.out.println("Semua locator gagal, menggunakan direct URL navigation");
            driver.get("https://suitecrm.com/suitecrmhosted/");
            Thread.sleep(2000);
            return true;
        } catch (Exception e) {
            System.out.println("Gagal navigate ke SuiteCRM Hosted: " + e.getMessage());
            return false;
        }
    }

    /**
     * Navigate ke sub-menu SuiteCRM Migration
     * 
     * @return true jika berhasil
     */
    public boolean navigateToSuiteCRMMigration() {
        try {
            System.out.println("Navigating ke SuiteCRM Migration");
            hoverServicesMenu();
            Thread.sleep(500);

            By[] locators = {
                By.cssSelector("li#menu-item-564363 a"),
                By.xpath("//li[@id='menu-item-564363']//a"),
                By.cssSelector("a[href*='migrations']"),
                By.partialLinkText("SuiteCRM Migration")
            };

            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(1));
            for (By locator : locators) {
                try {
                    WebElement element = shortWait.until(ExpectedConditions.elementToBeClickable(locator));
                    element.click();
                    Thread.sleep(1000);
                    System.out.println("Berhasil navigate ke SuiteCRM Migration");
                    return true;
                } catch (Exception e) {
                    continue;
                }
            }

            System.out.println("Semua locator gagal, menggunakan direct URL navigation");
            driver.get("https://suitecrm.com/en/consultancy/migrations/");
            Thread.sleep(2000);
            return true;
        } catch (Exception e) {
            System.out.println("Gagal navigate ke SuiteCRM Migration: " + e.getMessage());
            return false;
        }
    }

    /**
     * Navigate ke sub-menu Enterprise Verification Service
     * 
     * @return true jika berhasil
     */
    public boolean navigateToEVS() {
        try {
            System.out.println("Navigating ke Enterprise Verification Service");
            hoverServicesMenu();
            Thread.sleep(500);

            By[] locators = {
                By.cssSelector("li#menu-item-564364 a"),
                By.xpath("//li[@id='menu-item-564364']//a"),
                By.cssSelector("a[href*='enterprise-verification-service']"),
                By.partialLinkText("Enterprise Verification Service")
            };

            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(1));
            for (By locator : locators) {
                try {
                    WebElement element = shortWait.until(ExpectedConditions.elementToBeClickable(locator));
                    element.click();
                    Thread.sleep(1000);
                    System.out.println("Berhasil navigate ke Enterprise Verification Service");
                    return true;
                } catch (Exception e) {
                    continue;
                }
            }

            System.out.println("Semua locator gagal, menggunakan direct URL navigation");
            driver.get("https://suitecrm.com/enterprise-verification-service/");
            Thread.sleep(2000);
            return true;
        } catch (Exception e) {
            System.out.println("Gagal navigate ke Enterprise Verification Service: " + e.getMessage());
            return false;
        }
    }

    /**
     * Helper method untuk navigate ke sub-menu
     * 
     * @param subMenuLocator locator untuk sub-menu
     * @param subMenuName    nama sub-menu untuk logging
     * @return true jika berhasil
     */
    protected boolean navigateToSubMenu(By subMenuLocator, String subMenuName) {
        try {
            System.out.println("Navigating ke " + subMenuName);

            // Hover dulu ke Services menu
            hoverServicesMenu();
            Thread.sleep(500);

            // Cari dan klik submenu
            WebElement subMenu = wait.until(ExpectedConditions.elementToBeClickable(subMenuLocator));

            // Scroll ke element jika perlu
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", subMenu);
            Thread.sleep(300);

            // Klik submenu
            try {
                subMenu.click();
            } catch (Exception e) {
                // Fallback to JavaScript click
                js.executeScript("arguments[0].click();", subMenu);
            }

            Thread.sleep(1000);
            System.out.println("Berhasil navigate ke " + subMenuName);
            return true;
        } catch (Exception e) {
            System.out.println("Gagal navigate ke " + subMenuName + ": " + e.getMessage());
            return false;
        }
    }

    /**
     * Get current URL
     * 
     * @return current URL string
     */
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    /**
     * Switch ke tab baru (untuk external links)
     */
    public void switchToNewTab() {
        try {
            String originalWindow = driver.getWindowHandle();
            for (String windowHandle : driver.getWindowHandles()) {
                if (!originalWindow.equals(windowHandle)) {
                    driver.switchTo().window(windowHandle);
                    System.out.println("Switched to new tab");
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Gagal switch ke tab baru: " + e.getMessage());
        }
    }

    /**
     * Wait for page to fully load
     */
    public void waitForPageLoad() {
        try {
            wait.until(d -> js.executeScript("return document.readyState").equals("complete"));
            Thread.sleep(500);
        } catch (Exception e) {
            // Continue
        }
    }

    /**
     * Helper method untuk scroll ke element dan klik
     * 
     * @param locator     locator element
     * @param elementName nama element untuk logging
     * @return true jika berhasil
     */
    protected boolean scrollAndClick(By locator, String elementName) {
        try {
            System.out.println("Clicking " + elementName);

            waitForPageLoad();

            // Cari element
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));

            // Scroll ke element
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
            Thread.sleep(500);

            // Klik element
            try {
                element.click();
            } catch (Exception e) {
                js.executeScript("arguments[0].click();", element);
            }

            Thread.sleep(2000);
            System.out.println("Berhasil click " + elementName);
            return true;
        } catch (Exception e) {
            System.out.println("Gagal click " + elementName + ": " + e.getMessage());
            return false;
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

    /**
     * Helper method untuk scroll dan klik dengan multiple fallback locators
     * 
     * @param locators    array of locators to try
     * @param elementName nama element untuk logging
     * @return true jika berhasil
     */
    protected boolean scrollAndClickWithFallback(By[] locators, String elementName) {
        System.out.println("Clicking " + elementName);
        waitForPageLoad();

        WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(1));
        
        for (By locator : locators) {
            try {
                WebElement element = shortWait.until(ExpectedConditions.elementToBeClickable(locator));
                
                // Scroll ke element
                js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
                Thread.sleep(500);
                
                // Klik element
                try {
                    element.click();
                } catch (Exception e) {
                    js.executeScript("arguments[0].click();", element);
                }
                
                Thread.sleep(2000);
                System.out.println("Berhasil click " + elementName);
                return true;
            } catch (Exception e) {
                continue;
            }
        }
        
        System.out.println("Gagal click " + elementName + " - semua locator gagal");
        return false;
    }
}
