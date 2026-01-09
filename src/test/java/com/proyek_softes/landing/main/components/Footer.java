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
        // Simpan ID tab utama agar bisa kembali dengan pasti
        this.originalWindow = driver.getWindowHandle();
    }

    public void clickLink(String linkText, String testId) {
        wait.until(d -> ((JavascriptExecutor) d).executeScript("return document.readyState").equals("complete"));
        
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.partialLinkText(linkText)));

        // Screenshot highlight merah sebelum klik (Utility Anda)
        ScreenshotUtils.screenshotBeforeClick(driver, element, testId, linkText.replace(" ", "_"));

        // Klik menggunakan JavaScript (Paling stabil)
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);
        
        // Jeda agar browser punya waktu membuka halaman/tab baru
        try { Thread.sleep(2000); } catch (InterruptedException e) {}
    }

    public String getPageTitle() {
        // Jika link membuka tab baru, pindah fokus ke tab tersebut agar bisa ambil title
        Set<String> handles = driver.getWindowHandles();
        if (handles.size() > 1) {
            for (String handle : handles) {
                if (!handle.equals(originalWindow)) {
                    driver.switchTo().window(handle);
                }
            }
        }
        
        wait.until(ExpectedConditions.not(ExpectedConditions.titleIs("")));
        return driver.getTitle();
    }

    public void back() {
        Set<String> handles = driver.getWindowHandles();

        if (handles.size() > 1) {
            // Jika ada tab baru, tutup tab tersebut dan balik ke tab utama
            driver.close();
            driver.switchTo().window(originalWindow);
        } else {
            // Jika tetap di tab yang sama, lakukan back biasa
            driver.navigate().back();
        }

        // Beri jeda stabilitas agar halaman utama siap untuk link selanjutnya
        try {
            Thread.sleep(2500); 
        } catch (InterruptedException e) {}
    }
}