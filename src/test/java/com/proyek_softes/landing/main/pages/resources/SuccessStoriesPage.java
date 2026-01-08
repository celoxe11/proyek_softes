package com.proyek_softes.landing.main.pages.resources;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object untuk halaman Success Stories
 * Extends ResourcesPage untuk menggunakan method umum
 */
public class SuccessStoriesPage extends ResourcesPage {

    // Download button for featured item (Freedom Fibre)
    private By featuredDownloadButtonLocator = By.cssSelector("article.fusion-portfolio-post.energy-fusion-col-spacing:first-child a.fusion-button");
    private By[] featuredDownloadButtonLocators = {
        By.cssSelector("article.fusion-portfolio-post.energy-fusion-col-spacing:first-child a.fusion-button"),
        By.cssSelector("article.fusion-portfolio-post:first-child a[href*='freedom-fibre-case-study']"),
        By.xpath("//article[contains(@class, 'fusion-portfolio-post')][1]//a[contains(@class, 'fusion-button')]"),
        By.xpath("//a[contains(@href, 'freedom-fibre-case-study')]"),
        By.cssSelector("a.fusion-button[href*='.pdf']")
    };

    // Download button for 4th item (Scottish Book Trust)
    private By fourthItemDownloadButtonLocator = By.xpath("//h3[contains(text(), 'Scottish Book Trust')]/ancestor::article//a[contains(@class, 'fusion-button')]");
    private By[] fourthItemDownloadButtonLocators = {
        By.xpath("//h3[contains(text(), 'Scottish Book Trust')]/ancestor::article//a[contains(@class, 'fusion-button')]"),
        By.xpath("//article[@id='portfolio-1-post-564764']//a[contains(@class, 'fusion-button')]"),
        By.xpath("//a[contains(@href, 'sbt_casestudy')]"),
        By.cssSelector("article#portfolio-1-post-564764 a.fusion-button"),
        By.xpath("//div[contains(@class, 'fusion-portfolio-content')]//h3[text()='Scottish Book Trust']/following::a[contains(@class, 'fusion-button')][1]")
    };

    // ========================================
    // CONSTRUCTOR
    // ========================================

    public SuccessStoriesPage(WebDriver driver) {
        super(driver);
    }

    // ========================================
    // PAGE ACTIONS - Specific to Success Stories
    // ========================================

    /**
     * Click Download button for featured item
     * 
     * @return true jika berhasil
     */
    public boolean clickFeaturedDownloadButton() {
        // Coba scroll ke posisi optimal dulu
        scrollToPercentage(0.3);
        
        // Coba semua locator dengan prioritas
        boolean success = scrollAndClickWithFallback(featuredDownloadButtonLocators, 
            "Featured item Download button");
        
        if (success) {
            return true;
        }
        
        // Jika gagal, coba scroll ke posisi alternatif
        scrollToPercentage(0.4);
        return scrollAndClickWithFallback(featuredDownloadButtonLocators, "Featured item Download button");
    }

    /**
     * Click Download button for 4th item
     * 
     * @return true jika berhasil
     */
    public boolean clickFourthItemDownloadButton() {
        // Coba scroll ke posisi optimal dulu
        scrollToPercentage(0.5);
        
        // Coba semua locator dengan prioritas
        boolean success = scrollAndClickWithFallback(fourthItemDownloadButtonLocators, 
            "4th item Download button");
        
        if (success) {
            return true;
        }
        
        // Jika gagal, coba scroll ke posisi alternatif
        scrollToPercentage(0.6);
        return scrollAndClickWithFallback(fourthItemDownloadButtonLocators, "4th item Download button");
    }
}
