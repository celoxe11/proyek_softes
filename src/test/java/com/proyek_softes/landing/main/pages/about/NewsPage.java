package com.proyek_softes.landing.main.pages.about;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Page Object untuk halaman News & Press
 * URL: https://suitecrm.com/about/newsroom/news/
 */
public class NewsPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    // Locators
    private By newsArticleTitles = By.cssSelector("h2.entry-title.fusion-post-title a");
    private By newsArticlesContainer = By.cssSelector("article.post");
    private By pageTitle = By.cssSelector("h1.entry-title, h1.page-title, .fusion-page-title h1");

    public NewsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.js = (JavascriptExecutor) driver;
    }

    /**
     * Mendapatkan jumlah artikel news yang ditampilkan
     * 
     * @return jumlah artikel
     */
    public int getNewsArticleCount() {
        try {
            List<WebElement> articles = driver.findElements(newsArticleTitles);
            System.out.println("📰 Found " + articles.size() + " news articles");
            return articles.size();
        } catch (Exception e) {
            System.out.println("❌ Failed to count articles: " + e.getMessage());
            return 0;
        }
    }

    /**
     * Klik pada judul artikel berdasarkan index (1-based)
     * 
     * @param index nomor urut artikel (mulai dari 1)
     * @return true jika berhasil klik
     */
    public boolean clickNewsArticleByIndex(int index) {
        try {
            System.out.println("🔍 Looking for news article #" + index);

            // Wait for articles to load
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(newsArticleTitles));

            List<WebElement> articles = driver.findElements(newsArticleTitles);

            if (articles.isEmpty()) {
                System.out.println("❌ No news articles found");
                return false;
            }

            if (index < 1 || index > articles.size()) {
                System.out.println("❌ Invalid index: " + index + ". Available articles: " + articles.size());
                return false;
            }

            // Get article at index (convert to 0-based)
            WebElement article = articles.get(index - 1);
            String articleTitle = article.getText();
            String articleHref = article.getAttribute("href");

            System.out.println("📰 Article #" + index + ": " + articleTitle);
            System.out.println("🔗 URL: " + articleHref);

            // Scroll into view
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", article);
            Thread.sleep(500);

            // Click article
            try {
                article.click();
            } catch (Exception e) {
                // Fallback to JavaScript click
                js.executeScript("arguments[0].click();", article);
            }

            Thread.sleep(1500); // Wait for page to load
            System.out.println("✓ Clicked on article #" + index);
            return true;

        } catch (Exception e) {
            System.out.println("❌ Failed to click article #" + index + ": " + e.getMessage());
            return false;
        }
    }

    /**
     * Klik pada artikel berdasarkan judul yang mengandung teks tertentu
     * 
     * @param titleContains teks yang harus ada di judul
     * @return true jika berhasil klik
     */
    public boolean clickNewsArticleByTitle(String titleContains) {
        try {
            System.out.println("🔍 Looking for article containing: " + titleContains);

            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(newsArticleTitles));
            List<WebElement> articles = driver.findElements(newsArticleTitles);

            for (WebElement article : articles) {
                String title = article.getText();
                if (title.toLowerCase().contains(titleContains.toLowerCase())) {
                    System.out.println("📰 Found article: " + title);

                    js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", article);
                    Thread.sleep(500);

                    try {
                        article.click();
                    } catch (Exception e) {
                        js.executeScript("arguments[0].click();", article);
                    }

                    Thread.sleep(1500);
                    System.out.println("✓ Clicked on article: " + title);
                    return true;
                }
            }

            System.out.println("❌ Article not found with title containing: " + titleContains);
            return false;

        } catch (Exception e) {
            System.out.println("❌ Failed to find article: " + e.getMessage());
            return false;
        }
    }

    /**
     * Mendapatkan URL saat ini
     * 
     * @return current URL
     */
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    /**
     * Mendapatkan judul halaman saat ini
     * 
     * @return page title
     */
    public String getPageTitle() {
        return driver.getTitle();
    }

    /**
     * Memverifikasi URL saat ini sama dengan expected URL
     * 
     * @param expectedUrl URL yang diharapkan
     * @return true jika URL sama
     */
    public boolean verifyCurrentUrl(String expectedUrl) {
        try {
            Thread.sleep(1000); // Wait for page to fully load
            String currentUrl = driver.getCurrentUrl();
            boolean matches = currentUrl.equals(expectedUrl);

            System.out.println("📍 Current URL: " + currentUrl);
            System.out.println("📍 Expected URL: " + expectedUrl);
            System.out.println("✓ URL Match: " + matches);

            return matches;
        } catch (Exception e) {
            System.out.println("❌ Failed to verify URL: " + e.getMessage());
            return false;
        }
    }

    /**
     * Memverifikasi URL mengandung teks tertentu
     * 
     * @param urlContains teks yang harus ada di URL
     * @return true jika URL mengandung teks
     */
    public boolean verifyUrlContains(String urlContains) {
        try {
            Thread.sleep(1000);
            String currentUrl = driver.getCurrentUrl();
            boolean contains = currentUrl.contains(urlContains);

            System.out.println("📍 Current URL: " + currentUrl);
            System.out.println("📍 Should contain: " + urlContains);
            System.out.println("✓ Contains: " + contains);

            return contains;
        } catch (Exception e) {
            System.out.println("❌ Failed to verify URL: " + e.getMessage());
            return false;
        }
    }

    /**
     * Scroll ke bawah halaman untuk melihat lebih banyak artikel
     */
    public void scrollDownToLoadMore() {
        try {
            js.executeScript("window.scrollBy(0, 500);");
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println("⚠️ Failed to scroll: " + e.getMessage());
        }
    }

    /**
     * Print semua judul artikel yang tersedia
     */
    public void printAllArticleTitles() {
        try {
            List<WebElement> articles = driver.findElements(newsArticleTitles);
            System.out.println("\n📰 Available News Articles (" + articles.size() + " total):");
            System.out.println("═══════════════════════════════════════════════════════════════");

            int index = 1;
            for (WebElement article : articles) {
                String title = article.getText();
                String href = article.getAttribute("href");
                System.out.println(index + ". " + title);
                System.out.println("   URL: " + href);
                index++;
            }
            System.out.println("═══════════════════════════════════════════════════════════════\n");

        } catch (Exception e) {
            System.out.println("❌ Failed to print articles: " + e.getMessage());
        }
    }
}
