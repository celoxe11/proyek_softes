package com.proyek_softes.landing.main.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.BufferedReader;
import java.io.InputStreamReader;


public class BrowserDetector {

    public enum BrowserType {
        CHROME,
        CHROMIUM,
        FIREFOX,
        EDGE,
        NONE
    }

    /**
     * Mendeteksi browser yang tersedia di sistem
     * 
     * @return BrowserType yang tersedia
     */
    public static BrowserType detectAvailableBrowser() {
        System.out.println("🔍 Mendeteksi browser yang tersedia...");

        // Cek Chrome
        if (isBrowserInstalled("google-chrome") || isBrowserInstalled("chrome")) {
            System.out.println("✓ Google Chrome ditemukan");
            return BrowserType.CHROME;
        }

        // Cek Chromium
        if (isBrowserInstalled("chromium-browser") || isBrowserInstalled("chromium")) {
            System.out.println("✓ Chromium ditemukan");
            return BrowserType.CHROMIUM;
        }

        // Cek Firefox
        if (isBrowserInstalled("firefox")) {
            System.out.println("✓ Firefox ditemukan");
            return BrowserType.FIREFOX;
        }

        // Cek Edge
        if (isBrowserInstalled("microsoft-edge") || isBrowserInstalled("edge")) {
            System.out.println("✓ Microsoft Edge ditemukan");
            return BrowserType.EDGE;
        }

        System.out.println("❌ Tidak ada browser yang ditemukan");
        return BrowserType.NONE;
    }

    /**
     * Cek apakah browser terinstall di sistem
     * 
     * @param browserCommand command browser (contoh: "firefox", "google-chrome")
     * @return true jika browser terinstall
     */
    private static boolean isBrowserInstalled(String browserCommand) {
        try {
            String os = System.getProperty("os.name").toLowerCase();
            
            // Untuk Windows, cek langsung di lokasi instalasi default
            if (os.contains("win")) {
                return isBrowserInstalledWindows(browserCommand);
            } else {
                // Untuk Linux/Mac, gunakan 'which'
                String[] command = new String[] { "which", browserCommand };
                Process process = Runtime.getRuntime().exec(command);
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line = reader.readLine();
                return line != null && !line.isEmpty();
            }
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Cek browser installation di Windows menggunakan path langsung
     */
    private static boolean isBrowserInstalledWindows(String browserCommand) {
        try {
            java.io.File browserFile = null;
            
            // Cek Chrome
            if (browserCommand.contains("chrome") && !browserCommand.contains("chromium")) {
                browserFile = new java.io.File("C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe");
                if (!browserFile.exists()) {
                    browserFile = new java.io.File("C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe");
                }
                if (!browserFile.exists()) {
                    browserFile = new java.io.File(System.getenv("LOCALAPPDATA") + "\\Google\\Chrome\\Application\\chrome.exe");
                }
            }
            // Cek Chromium
            else if (browserCommand.contains("chromium")) {
                browserFile = new java.io.File("C:\\Program Files\\Chromium\\Application\\chrome.exe");
                if (!browserFile.exists()) {
                    browserFile = new java.io.File(System.getenv("LOCALAPPDATA") + "\\Chromium\\Application\\chrome.exe");
                }
            }
            // Cek Firefox
            else if (browserCommand.contains("firefox")) {
                browserFile = new java.io.File("C:\\Program Files\\Mozilla Firefox\\firefox.exe");
                if (!browserFile.exists()) {
                    browserFile = new java.io.File("C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe");
                }
            }
            // Cek Edge
            else if (browserCommand.contains("edge")) {
                browserFile = new java.io.File("C:\\Program Files (x86)\\Microsoft\\Edge\\Application\\msedge.exe");
                if (!browserFile.exists()) {
                    browserFile = new java.io.File("C:\\Program Files\\Microsoft\\Edge\\Application\\msedge.exe");
                }
            }
            
            return browserFile != null && browserFile.exists();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Membuat WebDriver berdasarkan browser yang tersedia
     * 
     * @return WebDriver instance
     * @throws RuntimeException jika tidak ada browser yang tersedia
     */
    public static WebDriver createDriver() {
        BrowserType browserType = detectAvailableBrowser();
        return createDriver(browserType);
    }

    /**
     * Membuat WebDriver berdasarkan BrowserType yang ditentukan
     * 
     * @param browserType tipe browser
     * @return WebDriver instance
     * @throws RuntimeException jika browser tidak tersedia
     */
    public static WebDriver createDriver(BrowserType browserType) {
        WebDriver driver;

        switch (browserType) {
            case CHROME:
                System.out.println("🚀 Memulai Google Chrome...");
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;

            case CHROMIUM:
                System.out.println("🚀 Memulai Chromium...");
                WebDriverManager.chromiumdriver().setup();
                driver = new ChromeDriver();
                break;

            case FIREFOX:
                System.out.println("🚀 Memulai Firefox...");
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;

            case EDGE:
                System.out.println("🚀 Memulai Microsoft Edge...");
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;

            default:
                throw new RuntimeException(
                        "❌ Tidak ada browser yang tersedia! " +
                                "Silakan install salah satu: Chrome, Chromium, Firefox, atau Edge");
        }

        driver.manage().window().maximize();
        System.out.println("✓ Browser berhasil dijalankan");
        return driver;
    }

    /**
     * Membuat WebDriver dengan fallback otomatis
     * Mencoba browser satu per satu sampai menemukan yang tersedia
     * 
     * @return WebDriver instance
     */
    public static WebDriver createDriverWithFallback() {
        BrowserType[] browsers = {
                BrowserType.CHROME,
                BrowserType.CHROMIUM,
                BrowserType.FIREFOX,
                BrowserType.EDGE
        };

        for (BrowserType browser : browsers) {
            try {
                return createDriver(browser);
            } catch (Exception e) {
                System.out.println("⚠ " + browser + " tidak tersedia, mencoba browser lain...");
            }
        }

        throw new RuntimeException(
                "❌ Tidak ada browser yang dapat dijalankan! " +
                        "Silakan install salah satu: Chrome, Chromium, Firefox, atau Edge");
    }

    /**
     * Mendapatkan nama browser yang sedang digunakan
     * 
     * @param browserType tipe browser
     * @return nama browser
     */
    public static String getBrowserName(BrowserType browserType) {
        switch (browserType) {
            case CHROME:
                return "Google Chrome";
            case CHROMIUM:
                return "Chromium";
            case FIREFOX:
                return "Mozilla Firefox";
            case EDGE:
                return "Microsoft Edge";
            default:
                return "Unknown";
        }
    }
}
