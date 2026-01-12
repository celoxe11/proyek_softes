package com.proyek_softes.landing.main.components;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.proyek_softes.landing.main.utils.ScreenshotUtils; 
import java.util.Set;
import java.time.Duration;

public class Footer {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final String originalWindow;

    public Footer(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        this.originalWindow = driver.getWindowHandle();
    }

    public void clickLink(String linkText, String testId) {
        wait.until(d -> ((JavascriptExecutor) d).executeScript("return document.readyState").equals("complete"));
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.partialLinkText(linkText)));

        // Screenshot highlight merah sebelum klik (Smooth Scroll otomatis dari utility)
        ScreenshotUtils.screenshotBeforeClick(driver, element, testId, linkText.replace(" ", "_"));

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);
        
        try { Thread.sleep(2000); } catch (InterruptedException e) {}
    }

    public String getPageTitle() {
        // Pindah fokus jika terbuka di tab baru
        Set<String> handles = driver.getWindowHandles();
        if (handles.size() > 1) {
            for (String handle : handles) {
                if (!handle.equals(originalWindow)) {
                    driver.switchTo().window(handle);
                }
            }
        }
        wait.until(ExpectedConditions.not(ExpectedConditions.titleIs("")));
        return driver.getTitle().toLowerCase(); // Return lowercase untuk validasi assert yang lebih stabil
    }

    public void back() {
        Set<String> handles = driver.getWindowHandles();
        if (handles.size() > 1) {
            driver.close();
            driver.switchTo().window(originalWindow);
        } else {
            driver.navigate().back();
        }
        try { Thread.sleep(2500); } catch (InterruptedException e) {}
    }
}