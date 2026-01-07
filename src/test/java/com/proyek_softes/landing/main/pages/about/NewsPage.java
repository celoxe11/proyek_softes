package com.proyek_softes.landing.main.pages.about;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

/**
 * Page Object untuk halaman News & Press
 * Extends AboutBasePage untuk menggunakan method umum
 */
public class NewsPage extends AboutBasePage {

    // Locators
    private By newsArticleTitlesLocator = By.cssSelector("h2.entry-title.fusion-post-title a");

    // ========================================
    // CONSTRUCTOR
    // ========================================

    public NewsPage(WebDriver driver) {
        super(driver);
    }

    // ========================================
    // PAGE ACTIONS - Specific to News & Press
    // ========================================

    /**
     * Klik pada judul artikel berdasarkan index (1-based)
     * 
     * @param index nomor urut artikel (mulai dari 1)
     * @return true jika berhasil klik
     */
    public boolean clickNewsArticleByIndex(int index) {
        try {
            System.out.println("Looking for news article #" + index);

            // Wait for articles to load
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(newsArticleTitlesLocator));

            List<WebElement> articles = driver.findElements(newsArticleTitlesLocator);

            if (articles.isEmpty()) {
                System.out.println("No news articles found");
                return false;
            }

            if (index < 1 || index > articles.size()) {
                System.out.println("Invalid index: " + index + ". Available articles: " + articles.size());
                return false;
            }

            // Get article at index (convert to 0-based)
            WebElement article = articles.get(index - 1);
            String articleTitle = article.getText();
            System.out.println("Article #" + index + ": " + articleTitle);

            // Scroll into view
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", article);
            Thread.sleep(500);

            // Click article
            try {
                article.click();
            } catch (Exception e) {
                js.executeScript("arguments[0].click();", article);
            }

            Thread.sleep(1000);
            System.out.println("Clicked on article #" + index);
            return true;

        } catch (Exception e) {
            System.out.println("Failed to click article #" + index + ": " + e.getMessage());
            return false;
        }
    }
}
