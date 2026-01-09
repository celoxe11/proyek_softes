package com.proyek_softes.landing.main.components;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class Footer {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public Footer(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public void clickLink(String linkText) {
        // Menunggu halaman stabil
        wait.until(d -> ((JavascriptExecutor) d).executeScript("return document.readyState").equals("complete"));

        // Menggunakan partialLinkText agar "What is Demo" bisa menemukan link "Demo"
        // jika teks tidak persis sama
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.partialLinkText(linkText)));

        // Scroll ke tengah layar agar tidak tertutup sticky header/footer
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", element);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }

        // Klik paksa menggunakan JavaScript (mengatasi ElementClickIntercepted)
        js.executeScript("arguments[0].click();", element);
    }

    public String getPageTitle() {
        wait.until(ExpectedConditions.not(ExpectedConditions.titleIs("")));
        String title = driver.getTitle();
        System.out.println("DEBUG: Judul halaman saat ini adalah -> " + title); // Tambahkan ini
        return title;
    }

    public void back() {
        driver.navigate().back();
        // Beri jeda agar kembali ke halaman utama dengan stabil sebelum klik link
        // selanjutnya
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }
    }
}