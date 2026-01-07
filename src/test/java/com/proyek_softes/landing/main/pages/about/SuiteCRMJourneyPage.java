package com.proyek_softes.landing.main.pages.about;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class SuiteCRMJourneyPage extends AboutBasePage {

    // GitHub button locator
    private By githubButtonLocator = By.cssSelector("a.fusion-button[href*='github.com']");
    
    // Join Community button locator
    private By joinCommunityButtonLocator = By.cssSelector("a.fusion-button[href*='community.suitecrm.com']");

    public SuiteCRMJourneyPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Click "Support Us on GitHub" button
     * 
     * @return true jika berhasil
     */
    public boolean clickGitHubButton() {
        scrollToPercentage(0.7);
        return scrollAndClick(githubButtonLocator, "Support Us on GitHub button");
    }

    /**
     * Click "Join the Community" button
     * 
     * @return true jika berhasil
     */
    public boolean clickJoinCommunityButton() {
        scrollToPercentage(0.5);
        return scrollAndClick(joinCommunityButtonLocator, "Join the Community button");
    }
}
