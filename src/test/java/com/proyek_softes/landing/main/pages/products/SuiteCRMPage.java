package com.proyek_softes.landing.main.pages.products;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Page Object Model untuk halaman SuiteCRM Product
 * URL: https://suitecrm.com/suitecrm/
 * 
 * Memisahkan locators dan interaksi UI dari logika test
 */
public class SuiteCRMPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    // Expected URL untuk halaman ini
    public static final String PAGE_URL = "https://suitecrm.com/suitecrm/";

    // Expected URL setelah klik CRM link
    public static final String CRM_PAGE_URL = "https://suitecrm.com/what-is-crm/";

    // ========================================
    // LOCATORS - Element UI
    // ========================================

    // Link "Customer Relationship Management"
    @FindBy(xpath = "//a[contains(text(), 'Customer Relationship Management')]")
    private WebElement crmLink;

    // Alternative locators untuk CRM link
    private By crmLinkByText = By.xpath("//a[contains(text(), 'Customer Relationship Management')]");
    private By crmLinkByHref = By.xpath("//a[contains(@href, 'what-is-crm')]");
    private By crmLinkByPartialText = By.partialLinkText("Customer Relationship Management");

    // Page title element
    @FindBy(xpath = "//h1")
    private WebElement pageTitle;

    // ========================================
    // CONSTRUCTOR
    // ========================================

    public SuiteCRMPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.js = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
    }

    // ========================================
    // PAGE ACTIONS - Interaksi dengan UI
    // ========================================

    /**
     * Klik link "Customer Relationship Management"
     * 
     * @return true jika berhasil
     */
    public boolean clickCRMLink() {
        try {
            System.out.println("🔄 Mencari link 'Customer Relationship Management'...");

            // Scroll ke bawah untuk memastikan link terlihat
            js.executeScript("window.scrollBy(0, 300);");
            Thread.sleep(500);

            WebElement link = findCRMLink();

            if (link == null) {
                System.out.println("❌ Link 'Customer Relationship Management' tidak ditemukan");
                return false;
            }

            // Scroll ke element
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", link);
            Thread.sleep(500);

            // Highlight element sebelum klik (untuk debugging visual)
            js.executeScript("arguments[0].style.border='3px solid red'", link);

            System.out.println("📍 Link ditemukan: " + link.getText());
            System.out.println("📍 Link href: " + link.getAttribute("href"));

            // Klik link
            try {
                link.click();
            } catch (Exception e) {
                // Fallback ke JavaScript click
                js.executeScript("arguments[0].click();", link);
            }

            System.out.println("✓ Berhasil klik link 'Customer Relationship Management'");
            return true;

        } catch (Exception e) {
            System.out.println("❌ Gagal klik CRM link: " + e.getMessage());
            return false;
        }
    }

    /**
     * Mencari CRM link dengan berbagai selector
     * 
     * @return WebElement jika ditemukan, null jika tidak
     */
    private WebElement findCRMLink() {
        // Try multiple selectors
        By[] selectors = {
                crmLinkByText,
                crmLinkByHref,
                crmLinkByPartialText,
                By.xpath(
                        "//a[contains(translate(text(), 'CUSTOMER', 'customer'), 'customer') and contains(translate(text(), 'RELATIONSHIP', 'relationship'), 'relationship')]"),
                By.cssSelector("a[href*='what-is-crm']")
        };

        for (By selector : selectors) {
            try {
                WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(selector));
                if (element != null && element.isDisplayed()) {
                    return element;
                }
            } catch (Exception e) {
                // Try next selector
                continue;
            }
        }

        return null;
    }

    /**
     * Verifikasi bahwa halaman SuiteCRM sudah dimuat
     * 
     * @return true jika berada di halaman SuiteCRM
     */
    public boolean isOnSuiteCRMPage() {
        try {
            String currentUrl = driver.getCurrentUrl();
            return currentUrl.contains("suitecrm.com/suitecrm");
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Verifikasi bahwa halaman CRM (What is CRM) sudah dimuat
     * 
     * @return true jika berada di halaman What is CRM
     */
    public boolean isOnCRMPage() {
        try {
            String currentUrl = driver.getCurrentUrl();
            boolean isCorrect = currentUrl.equals(CRM_PAGE_URL) || currentUrl.contains("what-is-crm");
            System.out.println("📍 Current URL: " + currentUrl);
            System.out.println("📍 Expected URL: " + CRM_PAGE_URL);
            System.out.println("✓ URL Match: " + isCorrect);
            return isCorrect;
        } catch (Exception e) {
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
     * Mendapatkan page title
     * 
     * @return page title text
     */
    public String getPageTitle() {
        try {
            return pageTitle.getText();
        } catch (Exception e) {
            return driver.getTitle();
        }
    }

    /**
     * Menunggu halaman selesai loading
     */
    public void waitForPageLoad() {
        try {
            wait.until(driver -> js.executeScript("return document.readyState").equals("complete"));

            // Tunggu images selesai loading
            wait.until(driver -> {
                Boolean imagesLoaded = (Boolean) js.executeScript(
                        "return Array.from(document.images).every(img => img.complete);");
                return imagesLoaded != null && imagesLoaded;
            });

            Thread.sleep(1000); // Extra wait untuk dynamic content
            System.out.println("✓ Page fully loaded");
        } catch (Exception e) {
            System.out.println("⚠️ Warning: Page load wait timeout");
        }
    }

    /**
     * Navigate langsung ke halaman SuiteCRM
     */
    public void navigateToPage() {
        driver.get(PAGE_URL);
        waitForPageLoad();
    }

    // ========================================
    // BUTTON ACTIONS - Try For Free, Book Demo, Get in Touch
    // ========================================

    /**
     * Click "Try For Free" button
     */
    public void clickTryForFreeButton() {
        try {
            scrollToButtons();
            WebElement button = findButton("Try For Free", "demo/");
            if (button != null) {
                clickButton(button, "Try For Free");
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to click Try For Free: " + e.getMessage());
        }
    }

    /**
     * Click "Book Demo" button
     */
    public void clickBookDemoButton() {
        try {
            scrollToButtons();
            WebElement button = findButton("Book Demo", "contact");
            if (button != null) {
                clickButton(button, "Book Demo");
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to click Book Demo: " + e.getMessage());
        }
    }

    /**
     * Click "Get in Touch" button
     */
    public void clickGetInTouchButton() {
        try {
            scrollToButtons();
            WebElement button = findButton("Get in Touch", "contact");
            if (button != null) {
                clickButton(button, "Get in Touch");
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to click Get in Touch: " + e.getMessage());
        }
    }

    /**
     * Scroll to buttons section
     */
    private void scrollToButtons() {
        try {
            js.executeScript("window.scrollTo(0, document.body.scrollHeight * 0.6);");
            Thread.sleep(1000);
        } catch (Exception e) {
            // Continue
        }
    }

    /**
     * Find button by text or href
     */
    private WebElement findButton(String buttonText, String hrefContains) {
        By[] selectors = {
                By.xpath("//a[contains(@class, 'fusion-button')]//span[contains(text(), '" + buttonText
                        + "')]/parent::a"),
                By.xpath("//a[contains(@class, 'fusion-button')][contains(text(), '" + buttonText + "')]"),
                By.xpath("//a[contains(@href, '" + hrefContains + "')][contains(@class, 'button')]"),
                By.xpath("//span[contains(text(), '" + buttonText + "')]/parent::a"),
                By.cssSelector("a.fusion-button[href*='" + hrefContains + "']")
        };

        for (By selector : selectors) {
            try {
                WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(selector));
                if (element != null && element.isDisplayed()) {
                    System.out.println("✓ Found button: " + buttonText);
                    return element;
                }
            } catch (Exception e) {
                continue;
            }
        }

        System.out.println("❌ Button not found: " + buttonText);
        return null;
    }

    /**
     * Click button with scroll and fallback
     */
    private void clickButton(WebElement button, String buttonName) {
        try {
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", button);
            Thread.sleep(500);

            try {
                button.click();
            } catch (Exception e) {
                js.executeScript("arguments[0].click();", button);
            }

            System.out.println("✓ Clicked button: " + buttonName);
        } catch (Exception e) {
            System.out.println("❌ Failed to click: " + buttonName + " - " + e.getMessage());
        }
    }

    // ========================================
    // CASE STUDY SECTION
    // ========================================

    /**
     * Get active case study title from slider
     */
    public String getActiveCaseStudyTitle() {
        try {
            // Scroll to case study section
            js.executeScript("window.scrollTo(0, document.body.scrollHeight * 0.7);");
            Thread.sleep(1000);

            // Find active testimonial/case study
            By[] selectors = {
                    By.cssSelector(".review.active-testimonial h2"),
                    By.cssSelector(".review.active-testimonial .awb-quote-content"),
                    By.cssSelector(".active-testimonial h2"),
                    By.xpath("//div[contains(@class, 'active-testimonial')]//h2"),
                    By.xpath("//div[contains(@class, 'review') and contains(@class, 'active')]//h2"),
                    By.cssSelector("#case_study_slider .active h2"),
                    By.cssSelector(".reviews .review h2")
            };

            for (By selector : selectors) {
                try {
                    WebElement element = driver.findElement(selector);
                    if (element != null && element.isDisplayed()) {
                        String title = element.getText().trim();
                        if (!title.isEmpty()) {
                            System.out.println("✓ Found case study title: " + title);
                            return title;
                        }
                    }
                } catch (Exception e) {
                    continue;
                }
            }

            // Fallback: get any visible case study name
            try {
                WebElement nameElement = driver.findElement(By.cssSelector(".reviews h2"));
                return nameElement.getText().trim();
            } catch (Exception e) {
                // Continue
            }

            System.out.println("⚠️ Could not find case study title");
            return "Case Study";

        } catch (Exception e) {
            System.out.println("❌ Error getting case study title: " + e.getMessage());
            return "Case Study";
        }
    }

    /**
     * Click "Read Case Study" button
     */
    public void clickReadCaseStudyButton() {
        try {
            // Scroll to case study section
            js.executeScript("window.scrollTo(0, document.body.scrollHeight * 0.7);");
            Thread.sleep(1000);

            By[] selectors = {
                    By.xpath("//a[contains(text(), 'READ CASE STUDY')]"),
                    By.xpath("//a[contains(text(), 'Read Case Study')]"),
                    By.cssSelector("a[href*='casestudy']"),
                    By.cssSelector("a[href*='.pdf']"),
                    By.cssSelector(".button a[href*='pdf']"),
                    By.xpath("//div[contains(@class, 'button')]//a[contains(@href, 'pdf')]"),
                    By.xpath("//div[contains(@class, 'active-testimonial')]//a[contains(@href, 'pdf')]"),
                    By.cssSelector(".reviews .button a")
            };

            WebElement button = null;
            for (By selector : selectors) {
                try {
                    button = wait.until(ExpectedConditions.elementToBeClickable(selector));
                    if (button != null && button.isDisplayed()) {
                        System.out.println("✓ Found Read Case Study button");
                        break;
                    }
                } catch (Exception e) {
                    continue;
                }
            }

            if (button != null) {
                js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", button);
                Thread.sleep(500);

                String href = button.getAttribute("href");
                System.out.println("📍 Case Study URL: " + href);

                try {
                    button.click();
                } catch (Exception e) {
                    js.executeScript("arguments[0].click();", button);
                }
                System.out.println("✓ Clicked Read Case Study button");
            } else {
                System.out.println("❌ Read Case Study button not found");
            }

        } catch (Exception e) {
            System.out.println("❌ Error clicking Read Case Study: " + e.getMessage());
        }
    }
}
