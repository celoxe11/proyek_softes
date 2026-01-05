package com.proyek_softes.demo.pages.calendar;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NavigationChecker {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final Actions actions;

    private final By currentTab = By.className("currentTab");
    private final By pageTitle = By.className("module-title-text");

    public NavigationChecker(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(20));
        this.actions = new Actions(driver);
    }

    public boolean isPageCorrect(String expectedTab, String titleKeyword) {
        wait.until(ExpectedConditions.presenceOfElementLocated(currentTab));
        wait.until(ExpectedConditions.presenceOfElementLocated(pageTitle));

        String currentTabText = driver.findElement(currentTab).getText().trim();
        String pageTitleText = driver.findElement(pageTitle).getText().trim();

        return currentTabText.equalsIgnoreCase(expectedTab) && pageTitleText.toLowerCase().contains(titleKeyword.toLowerCase());
    }
}
