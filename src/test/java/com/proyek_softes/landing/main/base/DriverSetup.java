package com.proyek_softes.landing.main.base;

import com.proyek_softes.landing.main.utils.BrowserDetector;
import org.openqa.selenium.WebDriver;

/**
 * DriverSetup - Base class untuk setup WebDriver dengan auto-detection browser
 */
public class DriverSetup {

    /**
     * Membuat WebDriver dengan deteksi otomatis browser yang tersedia
     * 
     * @return WebDriver instance
     */
    public static WebDriver setupDriver() {
        return BrowserDetector.createDriver();
    }

    /**
     * Membuat WebDriver dengan browser spesifik
     * 
     * @param browserType tipe browser yang ingin digunakan
     * @return WebDriver instance
     */
    public static WebDriver setupDriver(BrowserDetector.BrowserType browserType) {
        return BrowserDetector.createDriver(browserType);
    }

    /**
     * Membuat WebDriver dengan fallback otomatis ke browser lain jika gagal
     * 
     * @return WebDriver instance
     */
    public static WebDriver setupDriverWithFallback() {
        return BrowserDetector.createDriverWithFallback();
    }
}
