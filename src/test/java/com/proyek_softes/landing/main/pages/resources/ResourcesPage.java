package com.proyek_softes.landing.main.pages.resources;

import java.io.File;
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
 * Base class untuk semua halaman Resources
 * Berisi method umum yang dapat digunakan oleh semua sub-menu Resources
 */
public class ResourcesPage {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected JavascriptExecutor js;
    protected Actions actions;

    // Main Resources Menu
    private By resourcesMenuLocator = By.cssSelector("li#menu-item-564403 > a");

    // Sub-menu locators
    private By allSuiteCRMReleasesSubMenuLocator = By.cssSelector("li#menu-item-564404 a");
    private By documentationSubMenuLocator = By.cssSelector("li#menu-item-564405 a");
    private By addOnsStoreSubMenuLocator = By.cssSelector("li#menu-item-564406 a");
    private By addOnsOutlookPluginSubMenuLocator = By.cssSelector("li#menu-item-564382 a");
    private By successStoriesSubMenuLocator = By.cssSelector("li#menu-item-564407 a");

    // ========================================
    // CONSTRUCTOR
    // ========================================

    public ResourcesPage(WebDriver driver) {
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
     * Hover ke menu Resources untuk membuka dropdown
     * 
     * @return true jika berhasil
     */
    public boolean hoverResourcesMenu() {
        try {
            System.out.println("Hovering ke menu Resources");

            WebElement menu = wait.until(ExpectedConditions.visibilityOfElementLocated(resourcesMenuLocator));
            actions.moveToElement(menu).perform();

            // Tunggu dropdown muncul
            Thread.sleep(500);

            System.out.println("Berhasil hover ke menu Resources");
            return true;
        } catch (Exception e) {
            System.out.println("Gagal hover ke menu Resources: " + e.getMessage());
            return false;
        }
    }

    /**
     * Navigate ke sub-menu All SuiteCRM Releases
     * 
     * @return true jika berhasil
     */
    public boolean navigateToAllSuiteCRMReleases() {
        return navigateToSubMenu(allSuiteCRMReleasesSubMenuLocator, "All SuiteCRM Releases");
    }

    /**
     * Navigate ke sub-menu Documentation
     * 
     * @return true jika berhasil
     */
    public boolean navigateToDocumentation() {
        return navigateToSubMenu(documentationSubMenuLocator, "Documentation");
    }

    /**
     * Navigate ke sub-menu Add-ons > Store
     * 
     * @return true jika berhasil
     */
    public boolean navigateToAddOnsStore() {
        return navigateToSubMenu(addOnsStoreSubMenuLocator, "Add-ons > Store");
    }

    /**
     * Navigate ke sub-menu Add-ons > Outlook Plugin
     * 
     * @return true jika berhasil
     */
    public boolean navigateToAddOnsOutlookPlugin() {
        return navigateToSubMenu(addOnsOutlookPluginSubMenuLocator, "Add-ons > Outlook Plugin");
    }

    /**
     * Navigate ke sub-menu Success Stories
     * 
     * @return true jika berhasil
     */
    public boolean navigateToSuccessStories() {
        return navigateToSubMenu(successStoriesSubMenuLocator, "Success Stories");
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

            // Hover dulu ke Resources menu
            hoverResourcesMenu();
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
     * Check if file exists in downloads folder
     * 
     * @param fileName nama file yang dicari
     * @return true jika file ditemukan
     */
    protected boolean checkFileDownloaded(String fileName) {
        try {
            String userHome = System.getProperty("user.home");
            File downloadsFolder = new File(userHome + "/Downloads");
            
            System.out.println("Checking file: " + fileName + " in " + downloadsFolder.getAbsolutePath());
            
            // Wait up to 10 seconds for file to appear
            for (int i = 0; i < 10; i++) {
                File[] files = downloadsFolder.listFiles();
                if (files != null) {
                    for (File file : files) {
                        if (file.getName().contains(fileName)) {
                            System.out.println("File found: " + file.getName());
                            return true;
                        }
                    }
                }
                Thread.sleep(1000);
            }
            
            System.out.println("File not found: " + fileName);
            return false;
        } catch (Exception e) {
            System.out.println("Error checking file: " + e.getMessage());
            return false;
        }
    }
}
