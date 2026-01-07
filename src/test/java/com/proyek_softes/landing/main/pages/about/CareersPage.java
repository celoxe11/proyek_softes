package com.proyek_softes.landing.main.pages.about;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class CareersPage extends AboutBasePage {

    // Locator untuk button Find Out More
    private By findOutMoreButtonLocator = By.cssSelector("a.fusion-button[href*='indeed.com']");
    private By findOutMoreButtonAltLocator = By.xpath("//a[contains(@href,'indeed.com')]");
    private By findOutMoreButtonTextLocator = By.xpath("//a[contains(text(),'FIND OUT MORE') or contains(text(),'Find Out More')]");

    public CareersPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Klik button Find Out More
     * 
     * @return true jika berhasil klik button
     */
    public boolean clickFindOutMoreButton() {
        try {
            System.out.println("Mencari button Find Out More");

            // Scroll ke tengah halaman dulu
            scrollToPercentage(50);
            waitSeconds(1);

            // Try multiple selectors dengan scrollAndClick
            boolean clicked = false;
            
            // Coba CSS selector pertama
            try {
                clicked = scrollAndClick(findOutMoreButtonLocator, "Find Out More button");
                if (clicked) {
                    System.out.println("Button ditemukan dengan CSS selector");
                    waitSeconds(2);
                    return true;
                }
            } catch (Exception e1) {
                System.out.println("CSS selector tidak berhasil, mencoba XPath href");
            }

            // Coba XPath href
            if (!clicked) {
                try {
                    clicked = scrollAndClick(findOutMoreButtonAltLocator, "Find Out More button");
                    if (clicked) {
                        System.out.println("Button ditemukan dengan XPath href");
                        waitSeconds(2);
                        return true;
                    }
                } catch (Exception e2) {
                    System.out.println("XPath href tidak berhasil, mencoba XPath text");
                }
            }

            // Coba XPath text
            if (!clicked) {
                clicked = scrollAndClick(findOutMoreButtonTextLocator, "Find Out More button");
                if (clicked) {
                    System.out.println("Button ditemukan dengan XPath text");
                    waitSeconds(2);
                    return true;
                }
            }

            return false;

        } catch (Exception e) {
            System.out.println("Error klik button Find Out More: " + e.getMessage());
            return false;
        }
    }

    /**
     * Helper method untuk wait
     */
    private void waitSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
