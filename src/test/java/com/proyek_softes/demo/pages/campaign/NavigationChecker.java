package com.proyek_softes.demo.pages.campaign;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NavigationChecker {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By currentTab = By.className("currentTab");
    private final By pageTitle = By.className("module-title-text");

    public NavigationChecker(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(20));
    }

    public boolean isPageCorrect(String expectedTab, String titleKeyword) {
        wait.until(ExpectedConditions.presenceOfElementLocated(currentTab));
        wait.until(ExpectedConditions.presenceOfElementLocated(pageTitle));

        String currentTabText = driver.findElement(currentTab).getText().trim();
        String pageTitleText = driver.findElement(pageTitle).getText().trim();

        return currentTabText.equalsIgnoreCase(expectedTab) && pageTitleText.toLowerCase().contains(titleKeyword.toLowerCase());
    }
    
    public boolean isCurrentTabCorrect(String expectedTab) {
        try {
            By currentTab = By.className("currentTab");
            String tabText = wait.until(ExpectedConditions.presenceOfElementLocated(currentTab)).getText();
            System.out.println("Current tab after save: " + tabText);
            return tabText.equalsIgnoreCase(expectedTab);
        } catch (Exception e) {
            System.out.println("Error verifying current tab: " + e.getMessage());
            return false;
        }
    }

    public boolean isModulePageTitleCorrect(String titleKeyword) {
        try {
            By pageTitle = By.cssSelector(".moduleTitle h2");
            String title = wait.until(ExpectedConditions.presenceOfElementLocated(pageTitle)).getText();
            System.out.println("Page title after save: " + title);
            return title.toLowerCase().contains(titleKeyword.toLowerCase());
        } catch (Exception e) {
            System.out.println("Error verifying email template save: " + e.getMessage());
            return false;
        }
    }
}
