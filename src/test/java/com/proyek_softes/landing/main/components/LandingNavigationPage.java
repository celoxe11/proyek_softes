package com.proyek_softes.landing.main.components;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Page Object for Landing Page Navigation
 * Based on inspect element: fusion-main-menu structure
 */
public class LandingNavigationPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private Actions actions;
    private JavascriptExecutor js;

    // Main navigation menu locators
    private Map<String, By> mainMenuMap;

    // Sub-menu map: parent menu -> (submenu name -> locator)
    private Map<String, Map<String, By>> subMenuMap;

    // Third-level menu map: parent.submenu -> (third-level name -> locator)
    private Map<String, Map<String, By>> thirdLevelMenuMap;

    public LandingNavigationPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.actions = new Actions(driver);
        this.js = (JavascriptExecutor) driver;

        initializeMainMenu();
        initializeSubMenus();
        initializeThirdLevelMenus();
    }

    private void initializeMainMenu() {
        mainMenuMap = new HashMap<>();

        // Main navigation menu items - using ID only (title attribute not always
        // present)
        mainMenuMap.put("about", By.cssSelector("li#menu-item-564732 > a"));
        mainMenuMap.put("products", By.cssSelector("li#menu-item-564354 > a"));
        mainMenuMap.put("services", By.cssSelector("li#menu-item-564358 > a"));
        mainMenuMap.put("resources", By.cssSelector("li#menu-item-564402 > a"));
        mainMenuMap.put("community", By.cssSelector("li#menu-item-564422 > a"));
        mainMenuMap.put("getstarted", By.cssSelector("li#menu-item-564397 > a"));
    }

    private void initializeSubMenus() {
        subMenuMap = new HashMap<>();

        // About sub-menu - based on actual HTML structure from inspect element
        Map<String, By> aboutMenu = new HashMap<>();
        aboutMenu.put("aboutus", By.cssSelector("li#menu-item-564390 a"));
        aboutMenu.put("newspress", By.cssSelector("li#menu-item-564388 a"));
        aboutMenu.put("roadmap", By.cssSelector("li#menu-item-564373 a"));
        aboutMenu.put("journey", By.cssSelector("li#menu-item-564375 a"));
        aboutMenu.put("careers", By.cssSelector("li#menu-item-564386 a"));
        aboutMenu.put("newsletter", By.cssSelector("li#menu-item-564413 a"));
        aboutMenu.put("contactus", By.cssSelector("li#menu-item-564414 a"));
        subMenuMap.put("about", aboutMenu);

        // Products sub-menu
        Map<String, By> productsMenu = new HashMap<>();
        productsMenu.put("suitecrm", By.cssSelector("li#menu-item-564355 a"));
        productsMenu.put("suiteassured", By.cssSelector("li#menu-item-564356 a"));
        subMenuMap.put("products", productsMenu);

        // Services sub-menu
        Map<String, By> servicesMenu = new HashMap<>();
        servicesMenu.put("support", By.cssSelector("li#menu-item-564362 a"));
        servicesMenu.put("consultancy", By.cssSelector("li#menu-item-564360 a"));
        servicesMenu.put("suitehosted", By.cssSelector("li#menu-item-564361 a"));
        servicesMenu.put("suitemigration", By.cssSelector("li#menu-item-564363 a"));
        servicesMenu.put("enterprise", By.cssSelector("li#menu-item-564364 a"));
        subMenuMap.put("services", servicesMenu);

        // Resources sub-menu
        Map<String, By> resourcesMenu = new HashMap<>();
        resourcesMenu.put("downloadsuite", By.cssSelector("li#menu-item-564403 a"));
        resourcesMenu.put("allsuitecrm", By.cssSelector("li#menu-item-564421 a"));
        resourcesMenu.put("documentation", By.cssSelector("li#menu-item-564405 a"));
        resourcesMenu.put("addons", By.cssSelector("li#menu-item-564406 a"));
        resourcesMenu.put("success", By.cssSelector("li#menu-item-564407 a"));
        resourcesMenu.put("comparesuite", By.cssSelector("li#menu-item-564420 a"));
        resourcesMenu.put("translations", By.cssSelector("li#menu-item-564410 a"));
        resourcesMenu.put("training", By.cssSelector("li#menu-item-564577 a"));
        resourcesMenu.put("client", By.cssSelector("li#menu-item-564396 a"));
        subMenuMap.put("resources", resourcesMenu);

        // Community sub-menu
        Map<String, By> communityMenu = new HashMap<>();
        communityMenu.put("community", By.cssSelector("li#menu-item-564423 a"));
        communityMenu.put("sponsorship", By.cssSelector("li#menu-item-564424 a"));
        communityMenu.put("partners", By.cssSelector("li#menu-item-564425 a"));
        communityMenu.put("github", By.cssSelector("li#menu-item-564607 a"));
        subMenuMap.put("community", communityMenu);

        // Get Started sub-menu
        Map<String, By> getStartedMenu = new HashMap<>();
        getStartedMenu.put("download", By.cssSelector("li#menu-item-564607 a"));
        getStartedMenu.put("demosuitecrm", By.cssSelector("li#menu-item-564400 a"));
        getStartedMenu.put("suitecrmhosted", By.cssSelector("li#menu-item-564401 a"));
        subMenuMap.put("getstarted", getStartedMenu);
    }

    /**
     * Initialize third-level menus (sub-sub menus)
     * Based on screenshot provided: Resources > Documentation has sub-items
     */
    private void initializeThirdLevelMenus() {
        thirdLevelMenuMap = new HashMap<>();

        // Resources > Documentation > [User Guide, Developer Guide, Reports and
        // Documents]
        Map<String, By> documentationSubMenu = new HashMap<>();
        documentationSubMenu.put("userguide", By.cssSelector("li#menu-item-564416 a"));
        documentationSubMenu.put("developerguide", By.cssSelector("li#menu-item-564417 a"));
        documentationSubMenu.put("reportsdocuments", By.cssSelector("li#menu-item-564418 a"));
        thirdLevelMenuMap.put("resources.documentation", documentationSubMenu);
    }

    public boolean navigateToMainMenu(String menuName) {
        try {
            menuName = menuName.toLowerCase();
            By menuLocator = mainMenuMap.get(menuName);

            if (menuLocator == null) {
                System.out.println("❌ Menu not found: " + menuName);
                return false;
            }

            WebElement menuElement = wait.until(ExpectedConditions.elementToBeClickable(menuLocator));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", menuElement);
            Thread.sleep(300);
            menuElement.click();
            Thread.sleep(1000);

            System.out.println("✓ Navigated to main menu: " + menuName);
            return true;

        } catch (Exception e) {
            System.out.println("❌ Failed to navigate to: " + menuName + " - " + e.getMessage());
            return false;
        }
    }

    public boolean navigateToSubMenu(String parentMenu, String subMenu) {
        try {
            parentMenu = parentMenu.toLowerCase();
            subMenu = subMenu.toLowerCase();

            System.out.println("🔍 Navigating: " + parentMenu + " -> " + subMenu);

            By parentLocator = mainMenuMap.get(parentMenu);
            if (parentLocator == null) {
                System.out.println("❌ Parent menu not found: " + parentMenu);
                return false;
            }

            WebElement parentElement = wait.until(ExpectedConditions.presenceOfElementLocated(parentLocator));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", parentElement);
            Thread.sleep(500);

            // Hover to show dropdown - retry mechanism
            for (int i = 0; i < 3; i++) {
                actions.moveToElement(parentElement).perform();
                Thread.sleep(1000); // Increased wait time

                Map<String, By> subMenus = subMenuMap.get(parentMenu);
                if (subMenus == null || !subMenus.containsKey(subMenu)) {
                    System.out.println("⚠️  Sub-menu not configured: " + subMenu + " (skipping)");
                    return false;
                }

                By subMenuLocator = subMenus.get(subMenu);

                // Check if element exists and is visible before clicking
                List<WebElement> elements = driver.findElements(subMenuLocator);
                if (elements.isEmpty()) {
                    System.out.println("⚠️  Element not found in DOM: " + subMenu + " (attempt " + (i + 1) + "/3)");
                    if (i < 2)
                        continue; // Retry
                    else {
                        System.out.println("⚠️  Skipping " + subMenu + " - element tidak ada di website");
                        return false;
                    }
                }

                // Try to wait for element to be clickable
                try {
                    WebElement subMenuElement = wait.until(ExpectedConditions.elementToBeClickable(subMenuLocator));

                    // Scroll element into view
                    js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});",
                            subMenuElement);
                    Thread.sleep(300);

                    // Try normal click first
                    try {
                        subMenuElement.click();
                    } catch (Exception e) {
                        // Fallback to JavaScript click
                        js.executeScript("arguments[0].click();", subMenuElement);
                    }

                    Thread.sleep(1000);
                    System.out.println("✓ Navigated to: " + parentMenu + " -> " + subMenu);
                    return true;

                } catch (Exception e) {
                    if (i < 2) {
                        System.out.println("⚠️  Retry " + (i + 2) + "/3 for: " + subMenu);
                        Thread.sleep(500);
                    } else {
                        System.out
                                .println("⚠️  Skipping " + subMenu + " - element tidak clickable setelah 3 percobaan");
                        return false;
                    }
                }
            }

            return false;

        } catch (Exception e) {
            System.out.println("❌ Failed: " + parentMenu + " -> " + subMenu + " - " + e.getMessage());
            return false;
        }
    }

    /**
     * Navigate to third-level menu (sub-sub menu)
     * Example: navigateToThirdLevelMenu("resources", "documentation", "userguide")
     * This will: Resources (hover) → Documentation (hover) → User Guide (click)
     */
    public boolean navigateToThirdLevelMenu(String parentMenu, String subMenu, String thirdLevelMenu) {
        try {
            parentMenu = parentMenu.toLowerCase();
            subMenu = subMenu.toLowerCase();
            thirdLevelMenu = thirdLevelMenu.toLowerCase();

            System.out.println("🔍 Navigating: " + parentMenu + " → " + subMenu + " → " + thirdLevelMenu);

            // Step 1: Hover over parent menu
            By parentLocator = mainMenuMap.get(parentMenu);
            if (parentLocator == null) {
                System.out.println("❌ Parent menu not found: " + parentMenu);
                return false;
            }

            WebElement parentElement = wait.until(ExpectedConditions.presenceOfElementLocated(parentLocator));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", parentElement);
            Thread.sleep(500);
            actions.moveToElement(parentElement).perform();
            Thread.sleep(1000);

            // Step 2: Hover over sub-menu (don't click, just hover)
            Map<String, By> subMenus = subMenuMap.get(parentMenu);
            if (subMenus == null || !subMenus.containsKey(subMenu)) {
                System.out.println("❌ Sub-menu not found: " + subMenu);
                return false;
            }

            By subMenuLocator = subMenus.get(subMenu);
            WebElement subMenuElement = wait.until(ExpectedConditions.presenceOfElementLocated(subMenuLocator));
            actions.moveToElement(subMenuElement).perform();
            Thread.sleep(1000); // Wait for third-level menu to appear

            // Step 3: Click on third-level menu
            String thirdLevelKey = parentMenu + "." + subMenu;
            Map<String, By> thirdLevelMenus = thirdLevelMenuMap.get(thirdLevelKey);
            if (thirdLevelMenus == null || !thirdLevelMenus.containsKey(thirdLevelMenu)) {
                System.out.println("❌ Third-level menu not found: " + thirdLevelMenu);
                return false;
            }

            By thirdLevelLocator = thirdLevelMenus.get(thirdLevelMenu);

            // Check if element exists
            List<WebElement> elements = driver.findElements(thirdLevelLocator);
            if (elements.isEmpty()) {
                System.out.println("⚠️ Third-level element not found in DOM: " + thirdLevelMenu);
                return false;
            }

            WebElement thirdLevelElement = wait.until(ExpectedConditions.elementToBeClickable(thirdLevelLocator));
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", thirdLevelElement);
            Thread.sleep(300);

            // Try to click
            try {
                thirdLevelElement.click();
            } catch (Exception e) {
                js.executeScript("arguments[0].click();", thirdLevelElement);
            }

            Thread.sleep(1000);
            System.out.println("✓ Navigated to: " + parentMenu + " → " + subMenu + " → " + thirdLevelMenu);
            return true;

        } catch (Exception e) {
            System.out.println(
                    "❌ Failed: " + parentMenu + " → " + subMenu + " → " + thirdLevelMenu + " - " + e.getMessage());
            return false;
        }
    }

    public boolean isMainMenuVisible(String menuName) {
        try {
            By menuLocator = mainMenuMap.get(menuName.toLowerCase());
            if (menuLocator == null) {
                return false;
            }

            // Check presence in DOM
            List<WebElement> elements = driver.findElements(menuLocator);
            return !elements.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public String getCurrentTitle() {
        return driver.getTitle();
    }

    public boolean verifyUrlContains(String expectedText) {
        try {
            Thread.sleep(1000);
            String currentUrl = driver.getCurrentUrl().toLowerCase();
            boolean contains = currentUrl.contains(expectedText.toLowerCase());
            System.out.println("   URL: " + currentUrl + " contains '" + expectedText + "' = " + contains);
            return contains;
        } catch (Exception e) {
            return false;
        }
    }

    public void navigateToHome(String baseUrl) {
        try {
            driver.navigate().to(baseUrl);
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println("❌ Failed to navigate to home");
        }
    }

    public Map<String, By> getSubMenuItems(String parentMenu) {
        return subMenuMap.get(parentMenu.toLowerCase());
    }

    public int getMainMenuCount() {
        return mainMenuMap.size();
    }

    public int getSubMenuCount(String parentMenu) {
        Map<String, By> subMenus = subMenuMap.get(parentMenu.toLowerCase());
        return subMenus != null ? subMenus.size() : 0;
    }

    public boolean verifyAllMainMenusVisible() {
        for (String menuName : mainMenuMap.keySet()) {
            if (!isMainMenuVisible(menuName)) {
                System.out.println("❌ Main menu not visible: " + menuName);
                return false;
            }
        }
        System.out.println("✓ All main menus are visible");
        return true;
    }

    public boolean isSubMenuDropdownVisible(String parentMenu) {
        try {
            parentMenu = parentMenu.toLowerCase();
            By parentLocator = mainMenuMap.get(parentMenu);
            WebElement parentElement = driver.findElement(parentLocator);
            actions.moveToElement(parentElement).perform();
            Thread.sleep(500);

            Map<String, By> subMenus = subMenuMap.get(parentMenu);
            if (subMenus != null && !subMenus.isEmpty()) {
                By firstSubMenu = subMenus.values().iterator().next();
                WebElement subMenuElement = driver.findElement(firstSubMenu);
                return subMenuElement.isDisplayed();
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}
