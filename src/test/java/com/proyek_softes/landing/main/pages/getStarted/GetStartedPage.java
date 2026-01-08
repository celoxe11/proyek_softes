package com.proyek_softes.landing.main.pages.getStarted;

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

public class GetStartedPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;
    private Actions actions;

    // Main Get Started Menu
    private By getStartedMenuLocator = By.cssSelector("li#menu-item-564397 > a");

    // Sub-menu Download (confirmed from DOM: menu-item-564399)
    private By downloadSubMenuLocator = By.cssSelector("li#menu-item-564399 a");

    // Sub-menu Demo SuiteCRM
    private By demoSubMenuLocator = By.cssSelector("li#menu-item-564400 a");

    // Sub-menu SuiteCRM Hosted
    private By hostedSubMenuLocator = By.cssSelector("li#menu-item-564401 a");

    // Links on Download page - SuiteCRM 7.15 section
    private By suiteCRM715ReleaseNotesLinkLocator = By.cssSelector("a[href*='docs.suitecrm.com/admin/releases/7.15']");
    private By suiteCRM715UpgradesLinkLocator = By
            .xpath("(//a[contains(@href, 'upgrade-suitecrm') or contains(@href, 'suitecrm.com/upgrade')])[1]");
    private By suiteCRM715DownloadButtonLocator = By.cssSelector("a.fusion-button[href*='suitecrm-7-15']");

    // Links on Download page - SuiteCRM 8.9 section
    private By suiteCRM89ReleaseNotesLinkLocator = By
            .cssSelector("a[href*='docs.suitecrm.com/8.x/admin/releases/8.9']");
    private By suiteCRM89UpgradesLinkLocator = By
            .xpath("(//a[contains(@href, 'upgrade-suitecrm') or contains(@href, 'suitecrm.com/upgrade')])[2]");
    private By suiteCRM89DownloadButtonLocator = By.cssSelector("a.fusion-button[href*='suitecrm-8-9']");

    // Buttons on Download page - "A helping hand" section
    private By registerForFreeButtonLocator = By.cssSelector("a.fusion-button[href*='community.suitecrm.com']");
    private By discoverButtonLocator = By.cssSelector("a.fusion-button[href*='enterprise/suiteassured']");
    private By findOutMoreButtonLocator = By.cssSelector("a.fusion-button[href*='enterprise-verification-service']");

    // Links on Download page - "Get started, the right way" section
    private By stepByStepGuideLinkLocator = By.cssSelector("a[href*='downloading-installing']");
    private By userGuideLinkLocator = By.cssSelector("a[href*='docs.suitecrm.com/user']");
    private By chooseYoursTodayLinkLocator = By.cssSelector("a[href*='crowdin.com/project/suitecrmtranslations']");

    // Button on Download page - SuiteCRM Hosted section
    private By get30DayFreeTrialButtonLocator = By.cssSelector("a.fusion-button[href*='suitecrmhosted']");

    // ========================================
    // CONSTRUCTOR
    // ========================================

    public GetStartedPage(WebDriver driver) {
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
     * Hover ke menu Get Started untuk membuka dropdown
     * 
     * @return true jika berhasil
     */
    public boolean hoverGetStartedMenu() {
        try {
            System.out.println("Hovering ke menu Get Started");

            WebElement menu = wait.until(ExpectedConditions.visibilityOfElementLocated(getStartedMenuLocator));
            actions.moveToElement(menu).perform();

            // Tunggu dropdown muncul
            Thread.sleep(500);

            System.out.println("Berhasil hover ke menu Get Started");
            return true;
        } catch (Exception e) {
            System.out.println("Gagal hover ke menu Get Started: " + e.getMessage());
            return false;
        }
    }

    /**
     * Navigate ke sub-menu Download
     * 
     * @return true jika berhasil
     */
    public boolean navigateToDownload() {
        try {
            System.out.println("Navigating ke Download");

            // Hover dulu ke Get Started menu
            hoverGetStartedMenu();
            Thread.sleep(300);

            // Quick attempt with very short timeout (1 second only)
            // Based on DOM: li#menu-item-564399 contains nested div structure
            By[] downloadLocators = {
                    By.cssSelector("li#menu-item-564399 a"),
                    By.xpath("//li[@id='menu-item-564399']//a")
            };

            WebElement subMenu = null;
            WebDriverWait veryShortWait = new WebDriverWait(driver, Duration.ofSeconds(1));
            
            for (By locator : downloadLocators) {
                try {
                    subMenu = veryShortWait.until(ExpectedConditions.presenceOfElementLocated(locator));
                    if (subMenu != null && subMenu.isDisplayed()) {
                        System.out.println("Download submenu found, clicking");
                        try {
                            js.executeScript("arguments[0].click();", subMenu);
                            Thread.sleep(1000);
                            System.out.println("Berhasil navigate ke Download via menu");
                            return true;
                        } catch (Exception e) {
                            // Click failed, continue to direct navigation
                            break;
                        }
                    }
                } catch (Exception e) {
                    // Timeout, try next locator
                    continue;
                }
            }

            // Direct navigation (fast and reliable)
            System.out.println("Using direct URL navigation (fastest method)");
            driver.get("https://suitecrm.com/download/");
            Thread.sleep(500);
            System.out.println("Berhasil navigate ke Download via direct URL");
            return true;
        } catch (Exception e) {
            System.out.println("Gagal navigate ke Download: " + e.getMessage());
            return false;
        }
    }

    /**
     * Navigate ke sub-menu Demo SuiteCRM
     * 
     * @return true jika berhasil
     */
    public boolean navigateToDemo() {
        try {
            System.out.println("Navigating ke Demo SuiteCRM");

            // Hover dulu ke Get Started menu
            hoverGetStartedMenu();
            Thread.sleep(300);

            // Quick attempt with very short timeout (1 second only)
            By[] demoLocators = {
                    By.cssSelector("li#menu-item-564400 a"),
                    By.xpath("//li[@id='menu-item-564400']//a"),
                    By.cssSelector("a[href*='demo']"),
                    By.partialLinkText("Demo")
            };

            WebElement subMenu = null;
            WebDriverWait veryShortWait = new WebDriverWait(driver, Duration.ofSeconds(1));
            
            for (By locator : demoLocators) {
                try {
                    subMenu = veryShortWait.until(ExpectedConditions.presenceOfElementLocated(locator));
                    if (subMenu != null && subMenu.isDisplayed()) {
                        System.out.println("Demo submenu found, clicking");
                        try {
                            js.executeScript("arguments[0].click();", subMenu);
                            Thread.sleep(1000);
                            System.out.println("Berhasil navigate ke Demo via menu");
                            return true;
                        } catch (Exception e) {
                            // Click failed, continue to direct navigation
                            break;
                        }
                    }
                } catch (Exception e) {
                    // Timeout, try next locator
                    continue;
                }
            }

            // Direct navigation (fast and reliable)
            System.out.println("Using direct URL navigation (fastest method)");
            driver.get("https://suitecrm.com/demo/");
            Thread.sleep(500);
            System.out.println("Berhasil navigate ke Demo via direct URL");
            return true;

        } catch (Exception e) {
            System.out.println("Gagal navigate ke Demo SuiteCRM: " + e.getMessage());
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

            // Hover dulu ke Get Started menu
            hoverGetStartedMenu();
            Thread.sleep(300);

            // Quick attempt with very short timeout
            By[] hostedLocators = {
                    By.cssSelector("li#menu-item-564401 a"),
                    By.xpath("//li[@id='menu-item-564401']//a"),
                    By.cssSelector("a[href*='suitecrmhosted']"),
                    By.partialLinkText("Hosted")
            };

            WebElement subMenu = null;
            WebDriverWait veryShortWait = new WebDriverWait(driver, Duration.ofSeconds(1));
            
            for (By locator : hostedLocators) {
                try {
                    subMenu = veryShortWait.until(ExpectedConditions.presenceOfElementLocated(locator));
                    if (subMenu != null && subMenu.isDisplayed()) {
                        System.out.println("SuiteCRM Hosted submenu found, clicking...");
                        try {
                            js.executeScript("arguments[0].click();", subMenu);
                            Thread.sleep(1000);
                            System.out.println("Berhasil navigate ke SuiteCRM Hosted via menu");
                            return true;
                        } catch (Exception e) {
                            break;
                        }
                    }
                } catch (Exception e) {
                    continue;
                }
            }

            // Direct navigation (fast and reliable)
            System.out.println("Using direct URL navigation (fastest method)");
            driver.get("https://suitecrm.com/suitecrmhosted/");
            Thread.sleep(500);
            System.out.println("Berhasil navigate ke SuiteCRM Hosted via direct URL");
            return true;
        } catch (Exception e) {
            System.out.println("Gagal navigate ke SuiteCRM Hosted: " + e.getMessage());
            return false;
        }
    }

    /**
     * Click SuiteCRM 7.15 release notes link
     * 
     * @return true jika berhasil
     */
    public boolean clickSuiteCRM715ReleaseNotesLink() {
        try {
            System.out.println("Clicking SuiteCRM 7.15 release notes link...");

            waitForPageLoad();

            // Cari link release notes
            WebElement link = wait.until(ExpectedConditions.elementToBeClickable(suiteCRM715ReleaseNotesLinkLocator));

            // Scroll ke element
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", link);
            Thread.sleep(500);

            // Klik link
            try {
                link.click();
            } catch (Exception e) {
                js.executeScript("arguments[0].click();", link);
            }

            Thread.sleep(2000);
            System.out.println("Berhasil click SuiteCRM 7.15 release notes link");
            return true;
        } catch (Exception e) {
            System.out.println("Gagal click SuiteCRM 7.15 release notes link: " + e.getMessage());
            return false;
        }
    }

    /**
     * Click "For upgrades and other options, click here" link for 7.15
     * 
     * @return true jika berhasil
     */
    public boolean clickSuiteCRM715UpgradesLink() {
        try {
            System.out.println("Clicking SuiteCRM 7.15 upgrades link");

            waitForPageLoad();

            // Cari link upgrade - first one on page (7.15 section)
            WebElement link = wait.until(ExpectedConditions.elementToBeClickable(suiteCRM715UpgradesLinkLocator));

            // Scroll ke element
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", link);
            Thread.sleep(500);

            // Klik link
            try {
                link.click();
            } catch (Exception e) {
                js.executeScript("arguments[0].click();", link);
            }

            Thread.sleep(2000);
            System.out.println("Berhasil click SuiteCRM 7.15 upgrades link");
            return true;
        } catch (Exception e) {
            System.out.println("Gagal click SuiteCRM 7.15 upgrades link: " + e.getMessage());
            return false;
        }
    }

    /**
     * Click SuiteCRM 8.9 release notes link
     * 
     * @return true jika berhasil
     */
    public boolean clickSuiteCRM89ReleaseNotesLink() {
        try {
            System.out.println("Clicking SuiteCRM 8.9 release notes link");

            waitForPageLoad();

            // Scroll ke section 8.9
            js.executeScript("window.scrollBy(0, 300);");
            Thread.sleep(500);

            // Cari link release notes
            WebElement link = wait.until(ExpectedConditions.elementToBeClickable(suiteCRM89ReleaseNotesLinkLocator));

            // Scroll ke element
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", link);
            Thread.sleep(500);

            // Klik link
            try {
                link.click();
            } catch (Exception e) {
                js.executeScript("arguments[0].click();", link);
            }

            Thread.sleep(2000);
            System.out.println("Berhasil click SuiteCRM 8.9 release notes link");
            return true;
        } catch (Exception e) {
            System.out.println("Gagal click SuiteCRM 8.9 release notes link: " + e.getMessage());
            return false;
        }
    }

    /**
     * Click "For upgrades and other options, click here" link for 8.9
     * 
     * @return true jika berhasil
     */
    public boolean clickSuiteCRM89UpgradesLink() {
        try {
            System.out.println("Clicking SuiteCRM 8.9 upgrades link");

            waitForPageLoad();

            // Scroll ke section 8.9
            js.executeScript("window.scrollBy(0, 300);");
            Thread.sleep(500);

            // Cari link upgrade - second one on page (8.9 section)
            WebElement link = wait.until(ExpectedConditions.elementToBeClickable(suiteCRM89UpgradesLinkLocator));

            // Scroll ke element
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", link);
            Thread.sleep(500);

            // Klik link
            try {
                link.click();
            } catch (Exception e) {
                js.executeScript("arguments[0].click();", link);
            }

            Thread.sleep(2000);
            System.out.println("Berhasil click SuiteCRM 8.9 upgrades link");
            return true;
        } catch (Exception e) {
            System.out.println("Gagal click SuiteCRM 8.9 upgrades link: " + e.getMessage());
            return false;
        }
    }

    /**
     * Click Download button for SuiteCRM 7.15
     * 
     * @return true jika berhasil
     */
    public boolean clickDownloadSuiteCRM715Button() {
        try {
            System.out.println("Clicking Download SuiteCRM 7.15 button");

            waitForPageLoad();

            // Cari button download
            WebElement button = wait.until(ExpectedConditions.elementToBeClickable(suiteCRM715DownloadButtonLocator));

            // Scroll ke element
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", button);
            Thread.sleep(500);

            // Klik button
            try {
                button.click();
            } catch (Exception e) {
                js.executeScript("arguments[0].click();", button);
            }

            Thread.sleep(3000); // Wait for download to start
            System.out.println("Berhasil click Download SuiteCRM 7.15 button");
            return true;
        } catch (Exception e) {
            System.out.println("Gagal click Download SuiteCRM 7.15 button: " + e.getMessage());
            return false;
        }
    }

    /**
     * Click Download button for SuiteCRM 8.9
     * 
     * @return true jika berhasil
     */
    public boolean clickDownloadSuiteCRM89Button() {
        try {
            System.out.println("Clicking Download SuiteCRM 8.9 button");

            waitForPageLoad();

            // Scroll ke section 8.9
            js.executeScript("window.scrollBy(0, 300);");
            Thread.sleep(500);

            // Cari button download
            WebElement button = wait.until(ExpectedConditions.elementToBeClickable(suiteCRM89DownloadButtonLocator));

            // Scroll ke element
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", button);
            Thread.sleep(500);

            // Klik button
            try {
                button.click();
            } catch (Exception e) {
                js.executeScript("arguments[0].click();", button);
            }

            Thread.sleep(3000); // Wait for download to start
            System.out.println("Berhasil click Download SuiteCRM 8.9 button");
            return true;
        } catch (Exception e) {
            System.out.println("Gagal click Download SuiteCRM 8.9 button: " + e.getMessage());
            return false;
        }
    }

    /**
     * Check if file exists in downloads folder
     * 
     * @param fileNamePattern the file name pattern to search for
     * @return true if file exists
     */
    public boolean isFileDownloaded(String fileNamePattern) {
        try {
            String downloadPath = System.getProperty("user.home") + "/Downloads";
            File downloadDir = new File(downloadPath);

            if (!downloadDir.exists()) {
                System.out.println("Downloads directory not found");
                return false;
            }

            File[] files = downloadDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.getName().contains(fileNamePattern) && file.getName().endsWith(".zip")) {
                        System.out.println("✓ Found downloaded file: " + file.getName());
                        return true;
                    }
                }
            }

            System.out.println("File not found with pattern: " + fileNamePattern);
            return false;
        } catch (Exception e) {
            System.out.println("Error checking for downloaded file: " + e.getMessage());
            return false;
        }
    }

    /**
     * Get downloaded file name
     * 
     * @param fileNamePattern the file name pattern to search for
     * @return file name or null if not found
     */
    public String getDownloadedFileName(String fileNamePattern) {
        try {
            String downloadPath = System.getProperty("user.home") + "/Downloads";
            File downloadDir = new File(downloadPath);

            if (!downloadDir.exists()) {
                return null;
            }

            File[] files = downloadDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.getName().contains(fileNamePattern) && file.getName().endsWith(".zip")) {
                        return file.getName();
                    }
                }
            }

            return null;
        } catch (Exception e) {
            return null;
        }
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
     * Get current URL
     * 
     * @return current URL string
     */
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    /**
     * Click Register for Free button
     * 
     * @return true jika berhasil
     */
    public boolean clickRegisterForFreeButton() {
        try {
            System.out.println("Clicking Register for Free button");

            waitForPageLoad();

            // Scroll down to find the button
            js.executeScript("window.scrollTo(0, document.body.scrollHeight / 2);");
            Thread.sleep(500);

            // Cari button Register for Free
            WebElement button = wait.until(ExpectedConditions.elementToBeClickable(registerForFreeButtonLocator));

            // Scroll ke element
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", button);
            Thread.sleep(500);

            // Klik button
            try {
                button.click();
            } catch (Exception e) {
                js.executeScript("arguments[0].click();", button);
            }

            Thread.sleep(2000);
            System.out.println("Berhasil click Register for Free button");
            return true;
        } catch (Exception e) {
            System.out.println("Gagal click Register for Free button: " + e.getMessage());
            return false;
        }
    }

    /**
     * Click Discover If This Could be Right for You button
     * 
     * @return true jika berhasil
     */
    public boolean clickDiscoverButton() {
        try {
            System.out.println("Clicking Discover If This Could be Right for You button");

            waitForPageLoad();

            // Scroll down to find the button
            js.executeScript("window.scrollTo(0, document.body.scrollHeight / 2);");
            Thread.sleep(500);

            // Cari button Discover
            WebElement button = wait.until(ExpectedConditions.elementToBeClickable(discoverButtonLocator));

            // Scroll ke element
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", button);
            Thread.sleep(500);

            // Klik button
            try {
                button.click();
            } catch (Exception e) {
                js.executeScript("arguments[0].click();", button);
            }

            Thread.sleep(2000);
            System.out.println("Berhasil click Discover button");
            return true;
        } catch (Exception e) {
            System.out.println("Gagal click Discover button: " + e.getMessage());
            return false;
        }
    }

    /**
     * Click Find Out More button
     * 
     * @return true jika berhasil
     */
    public boolean clickFindOutMoreButton() {
        try {
            System.out.println("Clicking Find Out More button");

            waitForPageLoad();

            // Scroll down to find the button
            js.executeScript("window.scrollTo(0, document.body.scrollHeight / 2);");
            Thread.sleep(500);

            // Cari button Find Out More
            WebElement button = wait.until(ExpectedConditions.elementToBeClickable(findOutMoreButtonLocator));

            // Scroll ke element
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", button);
            Thread.sleep(500);

            // Klik button
            try {
                button.click();
            } catch (Exception e) {
                js.executeScript("arguments[0].click();", button);
            }

            Thread.sleep(2000);
            System.out.println("Berhasil click Find Out More button");
            return true;
        } catch (Exception e) {
            System.out.println("Gagal click Find Out More button: " + e.getMessage());
            return false;
        }
    }

    /**
     * Click "a step-by-step guide" link
     * 
     * @return true jika berhasil
     */
    public boolean clickStepByStepGuideLink() {
        try {
            System.out.println("Clicking step-by-step guide link");

            waitForPageLoad();

            // Scroll down to find the link
            js.executeScript("window.scrollTo(0, document.body.scrollHeight / 2);");
            Thread.sleep(500);

            // Cari link
            WebElement link = wait.until(ExpectedConditions.elementToBeClickable(stepByStepGuideLinkLocator));

            // Scroll ke element
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", link);
            Thread.sleep(500);

            // Klik link
            try {
                link.click();
            } catch (Exception e) {
                js.executeScript("arguments[0].click();", link);
            }

            Thread.sleep(2000);
            System.out.println("Berhasil click step-by-step guide link");
            return true;
        } catch (Exception e) {
            System.out.println("Gagal click step-by-step guide link: " + e.getMessage());
            return false;
        }
    }

    /**
     * Click "Check out our user guide" link
     * 
     * @return true jika berhasil
     */
    public boolean clickUserGuideLink() {
        try {
            System.out.println("Clicking user guide link");

            waitForPageLoad();

            // Scroll down to content area
            js.executeScript("window.scrollTo(0, document.body.scrollHeight * 0.5);");
            Thread.sleep(1000);

            // Multiple locators untuk user guide link
            By[] locators = {
                    By.cssSelector("a[href*='docs.suitecrm.com/user']"),
                    By.xpath("//a[contains(@href, 'docs.suitecrm.com/user')]"),
                    By.partialLinkText("user guide"),
                    By.xpath("//a[contains(text(), 'user guide')]")
            };

            WebElement link = null;
            for (By locator : locators) {
                try {
                    link = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
                    if (link != null && link.isDisplayed()) {
                        System.out.println("User guide link found");
                        break;
                    }
                } catch (Exception e) {
                    continue;
                }
            }

            if (link == null) {
                System.out.println("User guide link not found");
                return false;
            }

            // Scroll ke element
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", link);
            Thread.sleep(500);

            // Klik link
            try {
                link.click();
            } catch (Exception e) {
                js.executeScript("arguments[0].click();", link);
            }

            Thread.sleep(1000);
            System.out.println("✓ Berhasil click user guide link");
            return true;
        } catch (Exception e) {
            System.out.println("Gagal click user guide link: " + e.getMessage());
            return false;
        }
    }

    /**
     * Click "Choose yours today" link
     * 
     * @return true jika berhasil
     */
    public boolean clickChooseYoursTodayLink() {
        try {
            System.out.println("Clicking Choose yours today link");

            waitForPageLoad();

            // Scroll down to find the link
            js.executeScript("window.scrollTo(0, document.body.scrollHeight / 2);");
            Thread.sleep(500);

            // Cari link
            WebElement link = wait.until(ExpectedConditions.elementToBeClickable(chooseYoursTodayLinkLocator));

            // Scroll ke element
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", link);
            Thread.sleep(500);

            // Klik link
            try {
                link.click();
            } catch (Exception e) {
                js.executeScript("arguments[0].click();", link);
            }

            Thread.sleep(2000);
            System.out.println("Berhasil click Choose yours today link");
            return true;
        } catch (Exception e) {
            System.out.println("Gagal click Choose yours today link: " + e.getMessage());
            return false;
        }
    }

    /**
     * Click "Get 30-Day Free Trial" button
     * 
     * @return true jika berhasil
     */
    public boolean clickGet30DayFreeTrialButton() {
        try {
            System.out.println("Clicking Get 30-Day Free Trial button");

            waitForPageLoad();

            // Scroll down to find the button
            js.executeScript("window.scrollTo(0, document.body.scrollHeight * 0.8);");
            Thread.sleep(500);

            // Cari button
            WebElement button = wait.until(ExpectedConditions.elementToBeClickable(get30DayFreeTrialButtonLocator));

            // Scroll ke element
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", button);
            Thread.sleep(500);

            // Klik button
            try {
                button.click();
            } catch (Exception e) {
                js.executeScript("arguments[0].click();", button);
            }

            Thread.sleep(2000);
            System.out.println("Berhasil click Get 30-Day Free Trial button");
            return true;
        } catch (Exception e) {
            System.out.println("Gagal click Get 30-Day Free Trial button: " + e.getMessage());
            return false;
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
}
