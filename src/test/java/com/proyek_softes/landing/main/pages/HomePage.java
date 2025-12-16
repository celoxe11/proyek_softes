package com.proyek_softes.landing.main.pages;

import org.openqa.selenium.WebDriver;
import com.proyek_softes.landing.main.components.LandingNavigationPage;
import com.proyek_softes.landing.main.pages.about.AboutUsPage;

public class HomePage {
    private WebDriver driver;
    public LandingNavigationPage header;
    
    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.header = new LandingNavigationPage(driver);
    }
    
    // Navigate to About Us page via header navigation
    public AboutUsPage goToAboutUs() {
        header.navigateToSubMenu("about", "aboutus");
        return new AboutUsPage(driver);
    }
}
