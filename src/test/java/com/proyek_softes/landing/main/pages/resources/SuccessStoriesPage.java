package com.proyek_softes.landing.main.pages.resources;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object untuk halaman Success Stories
 * Extends ResourcesPage untuk menggunakan method umum
 */
public class SuccessStoriesPage extends ResourcesPage {

    // Download button for featured item (Freedom Fibre)
    private By featuredDownloadButtonLocator = By.cssSelector("article.fusion-portfolio-post.energy-fusion-col-spacing:first-child a.case_button");

    // Download button for 4th item (Scottish Book Trust)
    private By fourthItemDownloadButtonLocator = By.cssSelector("article.fusion-portfolio-post.energy-fusion-col-spacing:nth-child(4) a.case_button");

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
        scrollToPercentage(0.3);
        return scrollAndClick(featuredDownloadButtonLocator, "Featured item Download button");
    }

    /**
     * Click Download button for 4th item
     * 
     * @return true jika berhasil
     */
    public boolean clickFourthItemDownloadButton() {
        scrollToPercentage(0.5);
        return scrollAndClick(fourthItemDownloadButtonLocator, "4th item Download button");
    }
}
