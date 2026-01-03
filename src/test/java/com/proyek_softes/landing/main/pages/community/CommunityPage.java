package com.proyek_softes.landing.main.pages.community;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CommunityPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;
    private Actions actions;

    // ========================================
    // LOCATORS - Element UI
    // ========================================

    private By communityMenuLocator = By.cssSelector("li#menu-item-564422 > a");
    private By communitySupportSubMenuLocator = By.cssSelector("li#menu-item-564423 a");
    private By sponsorshipSubMenuLocator = By.cssSelector("li#menu-item-564424 a");
    private By partnersSubMenuLocator = By.cssSelector("li#menu-item-564425 a");
    private By githubSubMenuLocator = By.cssSelector("li#menu-item-564607 a");
    private By sponsorNowButtonLocator = By.cssSelector("a.fusion-button[href*='opencollective.com/suitecrm']");
    private By openCollectiveLinkLocator = By.cssSelector("a.fusion-no-lightbox[href*='opencollective.com/suitecrm']");
    private By route4MeLinkLocator = By.cssSelector("a[href*='route4me.com']");
    private By becomeASponsorButtonLocator = By.cssSelector("a.fusion-button[href*='opencollective.com/suitecrm']");

    // Links on Partners page
    private By visitTechespertoButtonLocator = By.cssSelector("a.fusion-button[href*='techesperto.com']");

    public CommunityPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.js = (JavascriptExecutor) driver;
        this.actions = new Actions(driver);
        PageFactory.initElements(driver, this);
    }

    /**
     * Hover ke menu Community untuk membuka dropdown
     * 
     * @return true jika berhasil
     */
    public boolean hoverCommunityMenu() {
        try {
            System.out.println("🔄 Hovering ke menu Community...");

            WebElement menu = wait.until(ExpectedConditions.visibilityOfElementLocated(communityMenuLocator));
            actions.moveToElement(menu).perform();

            // Tunggu dropdown muncul
            Thread.sleep(500);

            System.out.println("✓ Berhasil hover ke menu Community");
            return true;
        } catch (Exception e) {
            System.out.println("❌ Gagal hover ke menu Community: " + e.getMessage());
            return false;
        }
    }

    /**
     * Navigate ke sub-menu Community Support
     * 
     * @return true jika berhasil
     */
    public boolean navigateToCommunitySupport() {
        try {
            System.out.println("🔄 Navigating ke Community Support...");

            // Hover dulu ke Community menu
            hoverCommunityMenu();
            Thread.sleep(500);

            // Cari dan klik Community Support submenu
            WebElement subMenu = wait.until(ExpectedConditions.elementToBeClickable(communitySupportSubMenuLocator));

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
            System.out.println("✓ Berhasil navigate ke Community Support");
            return true;
        } catch (Exception e) {
            System.out.println("❌ Gagal navigate ke Community Support: " + e.getMessage());
            return false;
        }
    }

    /**
     * Navigate ke sub-menu Sponsorship
     * 
     * @return true jika berhasil
     */
    public boolean navigateToSponsorship() {
        try {
            System.out.println("🔄 Navigating ke Sponsorship...");

            // Hover dulu ke Community menu
            hoverCommunityMenu();
            Thread.sleep(500);

            // Cari dan klik Sponsorship submenu
            WebElement subMenu = wait.until(ExpectedConditions.elementToBeClickable(sponsorshipSubMenuLocator));

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
            System.out.println("✓ Berhasil navigate ke Sponsorship");
            return true;
        } catch (Exception e) {
            System.out.println("❌ Gagal navigate ke Sponsorship: " + e.getMessage());
            return false;
        }
    }

    /**
     * Click Sponsor Now button pada halaman Sponsorship
     * 
     * @return true jika berhasil
     */
    public boolean clickSponsorNowButton() {
        try {
            System.out.println("🔄 Clicking Sponsor Now button...");

            // Wait for page load
            waitForPageLoad();

            // Cari button Sponsor Now
            WebElement sponsorButton = wait.until(ExpectedConditions.elementToBeClickable(sponsorNowButtonLocator));

            // Scroll ke element
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", sponsorButton);
            Thread.sleep(500);

            // Klik button
            try {
                sponsorButton.click();
            } catch (Exception e) {
                // Fallback to JavaScript click
                js.executeScript("arguments[0].click();", sponsorButton);
            }

            Thread.sleep(2000); // Wait for new tab/page to load
            System.out.println("✓ Berhasil click Sponsor Now button");
            return true;
        } catch (Exception e) {
            System.out.println("❌ Gagal click Sponsor Now button: " + e.getMessage());
            return false;
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
                    System.out.println("✓ Switched to new tab");
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("❌ Gagal switch ke tab baru: " + e.getMessage());
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
     * Click Open Collective link pada halaman Sponsorship
     * 
     * @return true jika berhasil
     */
    public boolean clickOpenCollectiveLink() {
        try {
            System.out.println("🔄 Clicking Open Collective link...");

            // Wait for page load
            waitForPageLoad();

            // Scroll down to find the link
            js.executeScript("window.scrollBy(0, 400);");
            Thread.sleep(500);

            // Cari link Open Collective
            WebElement openCollectiveLink = wait
                    .until(ExpectedConditions.elementToBeClickable(openCollectiveLinkLocator));

            // Scroll ke element
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", openCollectiveLink);
            Thread.sleep(500);

            // Klik link
            try {
                openCollectiveLink.click();
            } catch (Exception e) {
                // Fallback to JavaScript click
                js.executeScript("arguments[0].click();", openCollectiveLink);
            }

            Thread.sleep(2000); // Wait for new tab/page to load
            System.out.println("✓ Berhasil click Open Collective link");
            return true;
        } catch (Exception e) {
            System.out.println("❌ Gagal click Open Collective link: " + e.getMessage());
            return false;
        }
    }

    /**
     * Click Route4Me link pada halaman Sponsorship
     * 
     * @return true jika berhasil
     */
    public boolean clickRoute4MeLink() {
        try {
            System.out.println("🔄 Clicking Route4Me link...");

            // Wait for page load
            waitForPageLoad();

            // Scroll down to find the sponsors section
            js.executeScript("window.scrollTo(0, document.body.scrollHeight / 2);");
            Thread.sleep(1000);

            // Cari link Route4Me
            WebElement route4MeLink = wait.until(ExpectedConditions.elementToBeClickable(route4MeLinkLocator));

            // Scroll ke element
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", route4MeLink);
            Thread.sleep(500);

            // Klik link
            try {
                route4MeLink.click();
            } catch (Exception e) {
                // Fallback to JavaScript click
                js.executeScript("arguments[0].click();", route4MeLink);
            }

            Thread.sleep(2000); // Wait for new tab/page to load
            System.out.println("✓ Berhasil click Route4Me link");
            return true;
        } catch (Exception e) {
            System.out.println("❌ Gagal click Route4Me link: " + e.getMessage());
            return false;
        }
    }

    /**
     * Navigate ke sub-menu Partners
     * 
     * @return true jika berhasil
     */
    public boolean navigateToPartners() {
        try {
            System.out.println("🔄 Navigating ke Partners...");

            // Hover dulu ke Community menu
            hoverCommunityMenu();
            Thread.sleep(500);

            // Cari dan klik Partners submenu
            WebElement subMenu = wait.until(ExpectedConditions.elementToBeClickable(partnersSubMenuLocator));

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
            System.out.println("✓ Berhasil navigate ke Partners");
            return true;
        } catch (Exception e) {
            System.out.println("❌ Gagal navigate ke Partners: " + e.getMessage());
            return false;
        }
    }

    /**
     * Navigate ke sub-menu GitHub
     * 
     * @return true jika berhasil
     */
    public boolean navigateToGitHub() {
        try {
            System.out.println("🔄 Navigating ke GitHub...");

            // Hover dulu ke Community menu
            hoverCommunityMenu();
            Thread.sleep(500);

            // Cari dan klik GitHub submenu
            WebElement subMenu = wait.until(ExpectedConditions.elementToBeClickable(githubSubMenuLocator));

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
            System.out.println("✓ Berhasil navigate ke GitHub");
            return true;
        } catch (Exception e) {
            System.out.println("❌ Gagal navigate ke GitHub: " + e.getMessage());
            return false;
        }
    }

    /**
     * Click Become a Sponsor Today button pada halaman Sponsorship
     * 
     * @return true jika berhasil
     */
    public boolean clickBecomeASponsorButton() {
        try {
            System.out.println("🔄 Clicking Become a Sponsor Today button...");

            // Wait for page load
            waitForPageLoad();

            // Scroll down to find the button
            js.executeScript("window.scrollBy(0, 400);");
            Thread.sleep(500);

            // Cari button Become a Sponsor
            WebElement sponsorButton = wait.until(ExpectedConditions.elementToBeClickable(becomeASponsorButtonLocator));

            // Scroll ke element
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", sponsorButton);
            Thread.sleep(500);

            // Klik button
            try {
                sponsorButton.click();
            } catch (Exception e) {
                // Fallback to JavaScript click
                js.executeScript("arguments[0].click();", sponsorButton);
            }

            Thread.sleep(2000); // Wait for new tab/page to load
            System.out.println("✓ Berhasil click Become a Sponsor Today button");
            return true;
        } catch (Exception e) {
            System.out.println("❌ Gagal click Become a Sponsor Today button: " + e.getMessage());
            return false;
        }
    }

    /**
     * Click Visit Techesperto button pada halaman Partners
     * 
     * @return true jika berhasil
     */
    public boolean clickVisitTechespertoButton() {
        try {
            System.out.println("🔄 Clicking Visit Techesperto button...");

            // Wait for page load
            waitForPageLoad();

            // Scroll down to find the button
            js.executeScript("window.scrollBy(0, 300);");
            Thread.sleep(500);

            // Cari button Visit Techesperto
            WebElement techespertoButton = wait
                    .until(ExpectedConditions.elementToBeClickable(visitTechespertoButtonLocator));

            // Scroll ke element
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", techespertoButton);
            Thread.sleep(500);

            // Klik button
            try {
                techespertoButton.click();
            } catch (Exception e) {
                // Fallback to JavaScript click
                js.executeScript("arguments[0].click();", techespertoButton);
            }

            Thread.sleep(2000); // Wait for new tab/page to load
            System.out.println("✓ Berhasil click Visit Techesperto button");
            return true;
        } catch (Exception e) {
            System.out.println("❌ Gagal click Visit Techesperto button: " + e.getMessage());
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
