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
    private By zip715ESRLocator = By.xpath("//h2[contains(text(), '7.15 ESR')]/following::table[1]//tbody/tr[1]//a[contains(@href, '.zip')]");
    private By[] zip715ESRLocators = {
        By.xpath("//h2[contains(text(), '7.15 ESR')]/following::table[1]//tbody/tr[1]//a[contains(@href, '.zip')]"),
        By.xpath("//h2[contains(text(), '7.15 ESR')]/following::table[1]//tbody/tr[1]//a[text()='Download']"),
        By.xpath("//h2[contains(text(), '7.15 ESR')]/following::table[1]//tbody/tr[1]/td[last()]//a"),
        By.cssSelector("table tbody tr td a[href*='suitecrm-7-15'][href$='.zip']")
    };

    // Release notes link for 7.15 ESR
    private By releaseNotes715ESRLocator = By.xpath("//h2[contains(text(), '7.15 ESR')]/following::table[1]//tbody/tr[1]//a[contains(text(), 'Release notes')]");
    private By[] releaseNotes715ESRLocators = {
        By.xpath("//h2[contains(text(), '7.15 ESR')]/following::table[1]//tbody/tr[1]//a[contains(text(), 'Release notes')]"),
        By.xpath("//a[contains(@href, 'releases/7.15.x')][contains(text(), 'Release notes')]"),
        By.xpath("//table//tbody/tr[1]//a[text()='Release notes']"),
        By.cssSelector("a[href*='releases/7.15']")
    };

    // Install Guide link for 7.15 ESR
    private By installGuide715ESRLocator = By.xpath("//h2[contains(text(), '7.15 ESR')]/following::table[1]//tbody/tr[1]//a[contains(text(), 'Install Guide')]");
    private By[] installGuide715ESRLocators = {
        By.xpath("//h2[contains(text(), '7.15 ESR')]/following::table[1]//tbody/tr[1]//a[contains(text(), 'Install Guide')]"),
        By.xpath("//a[contains(@href, 'installation-guide')][contains(text(), 'Install Guide')]"),
        By.xpath("//table//tbody/tr[1]//a[text()='Install Guide']"),
        By.cssSelector("a[href*='installation-guide']")
    };

    // Upgrade Guide link for 7.15.0 Upgrade from 7.14.x
    private By upgradeGuide715UpgradeLocator = By.xpath("//h2[contains(text(), '7.15 ESR')]/following::table[1]//tbody/tr[2]//a[contains(text(), 'Upgrade Guide')]");
    private By[] upgradeGuide715UpgradeLocators = {
        By.xpath("//h2[contains(text(), '7.15 ESR')]/following::table[1]//tbody/tr[2]//a[contains(text(), 'Upgrade Guide')]"),
        By.xpath("//a[contains(@href, 'installation-guide/upgrading')][contains(text(), 'Upgrade Guide')]"),
        By.xpath("//table//tbody/tr//a[text()='Upgrade Guide']"),
        By.cssSelector("a[href*='installation-guide/upgrading']")
    };

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
        // Coba scroll ke berbagai posisi untuk menemukan link
        double[] scrollPositions = {0.5, 0.6, 0.4, 0.7, 0.3};
        
        for (double position : scrollPositions) {
            scrollToPercentage(position);
            
            // Coba semua locator
            boolean success = scrollAndClickWithFallback(zip715ESRLocators, 
                "7.15 ESR zip download link");
            
            if (success) {
                return true;
            }
        }
        
        // Jika semua gagal, coba tanpa scroll percentage dulu
        return scrollAndClickWithFallback(zip715ESRLocators, "7.15 ESR zip download link");
    }

    /**
     * Click Release notes link for 7.15 ESR
     * 
     * @return true jika berhasil
     */
    public boolean clickReleaseNotes715ESR() {
        // Coba scroll ke berbagai posisi untuk menemukan link
        double[] scrollPositions = {0.5, 0.6, 0.4, 0.7, 0.3};
        
        for (double position : scrollPositions) {
            scrollToPercentage(position);
            
            // Coba semua locator
            boolean success = scrollAndClickWithFallback(releaseNotes715ESRLocators, 
                "7.15 ESR Release notes link");
            
            if (success) {
                return true;
            }
        }
        
        // Jika semua gagal, coba tanpa scroll percentage dulu
        return scrollAndClickWithFallback(releaseNotes715ESRLocators, "7.15 ESR Release notes link");
    }

    /**
     * Click Install Guide link for 7.15 ESR
     * 
     * @return true jika berhasil
     */
    public boolean clickInstallGuide715ESR() {
        // Coba scroll ke berbagai posisi untuk menemukan link
        double[] scrollPositions = {0.5, 0.6, 0.4, 0.7, 0.3};
        
        for (double position : scrollPositions) {
            scrollToPercentage(position);
            
            // Coba semua locator
            boolean success = scrollAndClickWithFallback(installGuide715ESRLocators, 
                "7.15 ESR Install Guide link");
            
            if (success) {
                return true;
            }
        }
        
        // Jika semua gagal, coba tanpa scroll percentage dulu
        return scrollAndClickWithFallback(installGuide715ESRLocators, "7.15 ESR Install Guide link");
    }

    /**
     * Click Upgrade Guide link for 7.15.0 Upgrade from 7.14.x
     * 
     * @return true jika berhasil
     */
    public boolean clickUpgradeGuide715Upgrade() {
        // Coba scroll ke berbagai posisi untuk menemukan link
        double[] scrollPositions = {0.5, 0.6, 0.4, 0.7, 0.3};
        
        for (double position : scrollPositions) {
            scrollToPercentage(position);
            
            // Coba semua locator
            boolean success = scrollAndClickWithFallback(upgradeGuide715UpgradeLocators, 
                "7.15.0 Upgrade from 7.14.x Upgrade Guide link");
            
            if (success) {
                return true;
            }
        }
        
        // Jika semua gagal, coba tanpa scroll percentage dulu
        return scrollAndClickWithFallback(upgradeGuide715UpgradeLocators, "7.15.0 Upgrade from 7.14.x Upgrade Guide link");
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
