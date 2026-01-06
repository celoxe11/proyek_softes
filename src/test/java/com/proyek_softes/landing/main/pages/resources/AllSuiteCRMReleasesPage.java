package com.proyek_softes.landing.main.pages.resources;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object untuk halaman All SuiteCRM Releases
 * Extends ResourcesPage untuk menggunakan method umum
 */
public class AllSuiteCRMReleasesPage extends ResourcesPage {

    // Support SuiteCRM on Open Collective banner
    private By openCollectiveBannerLocator = By.cssSelector("a[href*='opencollective.com/suitecrm']");

    // Download link for 7.15 ESR - zip
    private By zip715ESRLocator = By.xpath("//h2[contains(text(), '7.15 ESR')]/following::table[1]//tbody/tr[1]//a[contains(text(), 'zip')]");

    // Release notes link for 7.15 ESR
    private By releaseNotes715ESRLocator = By.xpath("//h2[contains(text(), '7.15 ESR')]/following::table[1]//tbody/tr[1]//a[contains(text(), 'Release notes')]");

    // Install Guide link for 7.15 ESR
    private By installGuide715ESRLocator = By.xpath("//h2[contains(text(), '7.15 ESR')]/following::table[1]//tbody/tr[1]//a[contains(text(), 'Install Guide')]");

    // Upgrade Guide link for 7.15.0 Upgrade from 7.14.x
    private By upgradeGuide715UpgradeLocator = By.xpath("//h2[contains(text(), '7.15 ESR')]/following::table[1]//tbody/tr[2]//a[contains(text(), 'Upgrade Guide')]");

    // ========================================
    // CONSTRUCTOR
    // ========================================

    public AllSuiteCRMReleasesPage(WebDriver driver) {
        super(driver);
    }

    // ========================================
    // PAGE ACTIONS - Specific to All SuiteCRM Releases
    // ========================================

    /**
     * Click Support SuiteCRM on Open Collective banner
     * 
     * @return true jika berhasil
     */
    public boolean clickOpenCollectiveBanner() {
        scrollToPercentage(0.3);
        return scrollAndClick(openCollectiveBannerLocator, "Support SuiteCRM on Open Collective banner");
    }

    /**
     * Click zip download link for 7.15 ESR
     * 
     * @return true jika berhasil
     */
    public boolean clickZip715ESR() {
        scrollToPercentage(0.5);
        return scrollAndClick(zip715ESRLocator, "7.15 ESR zip download link");
    }

    /**
     * Click Release notes link for 7.15 ESR
     * 
     * @return true jika berhasil
     */
    public boolean clickReleaseNotes715ESR() {
        scrollToPercentage(0.5);
        return scrollAndClick(releaseNotes715ESRLocator, "7.15 ESR Release notes link");
    }

    /**
     * Click Install Guide link for 7.15 ESR
     * 
     * @return true jika berhasil
     */
    public boolean clickInstallGuide715ESR() {
        scrollToPercentage(0.5);
        return scrollAndClick(installGuide715ESRLocator, "7.15 ESR Install Guide link");
    }

    /**
     * Click Upgrade Guide link for 7.15.0 Upgrade from 7.14.x
     * 
     * @return true jika berhasil
     */
    public boolean clickUpgradeGuide715Upgrade() {
        scrollToPercentage(0.5);
        return scrollAndClick(upgradeGuide715UpgradeLocator, "7.15.0 Upgrade from 7.14.x Upgrade Guide link");
    }

    /**
     * Verify file downloaded with specific name
     * 
     * @param fileName nama file yang diharapkan
     * @return true jika file ditemukan
     */
    public boolean verifyFileDownloaded(String fileName) {
        return checkFileDownloaded(fileName);
    }
}
