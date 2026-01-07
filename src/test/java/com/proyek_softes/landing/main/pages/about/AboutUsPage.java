package com.proyek_softes.landing.main.pages.about;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class AboutUsPage extends AboutBasePage {

    // Always Open Source link
    private By alwaysOpenSourceLinkLocator = By.cssSelector("a[href*='always-open-source']");

    // Contact Us button
    private By contactUsButtonLocator = By.cssSelector("a.fusion-button[href*='contact']");

    public AboutUsPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Click Always Open Source link
     * 
     * @return true jika berhasil
     */
    public boolean clickAlwaysOpenSourceLink() {
        return scrollAndClick(alwaysOpenSourceLinkLocator, "Always Open Source link");
    }

    /**
     * Click Contact Us button
     * 
     * @return true jika berhasil
     */
    public boolean clickContactUsButton() {
        scrollToPercentage(0.8);
        return scrollAndClick(contactUsButtonLocator, "Contact Us button");
    }
}
