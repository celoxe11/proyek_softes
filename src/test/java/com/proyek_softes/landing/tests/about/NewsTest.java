package com.proyek_softes.landing.tests.about;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.proyek_softes.landing.main.base.BaseLandingTest;
import com.proyek_softes.landing.main.pages.about.NewsPage;

import io.qameta.allure.Description;

/**
 * Test class untuk halaman News & Press
 * Berisi test case ABT-003
 */
public class NewsTest extends BaseLandingTest {

    private static final String EXPECTED_ARTICLE_URL = "https://suitecrm.com/suitecrm-8-8-1-7-14-7-security-maintenance-patch-released/";

    @Test(priority = 1)
    @Description("ABT-003")
    public void testAbt003() {
        navigateToHome();

        NewsPage newsPage = new NewsPage(driver);

        boolean hoverSuccess = newsPage.hoverAboutMenu();
        assertTrue(hoverSuccess, "Harus berhasil hover ke menu About");

        boolean navSuccess = newsPage.navigateToNewsPress();
        assertTrue(navSuccess, "Harus berhasil navigate ke News & Press");

        newsPage.waitForPageLoad();
        takeScreenshot("ABT-003_NewsPress_Page");

        // Klik judul news ke-5
        boolean clickSuccess = newsPage.clickNewsArticleByIndex(5);
        assertTrue(clickSuccess, "Harus berhasil klik judul news ke-5");

        newsPage.waitForPageLoad();
        waitSeconds(2);

        String currentUrl = newsPage.getCurrentUrl();
        System.out.println("Current URL: " + currentUrl);

        boolean urlCorrect = currentUrl.startsWith(EXPECTED_ARTICLE_URL)
                || currentUrl.contains("suitecrm-8-8-1-7-14-7-security-maintenance-patch-released");
        assertTrue(urlCorrect,
                "URL harus " + EXPECTED_ARTICLE_URL + " tapi actual: " + currentUrl);

        takeScreenshot("ABT-003_Article_Page");
    }
}
