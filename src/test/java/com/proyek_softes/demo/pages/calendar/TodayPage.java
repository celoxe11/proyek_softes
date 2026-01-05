package com.proyek_softes.demo.pages.calendar;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TodayPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By dateHeading = By.xpath("//div[contains(@class, 'col-xs-10') and contains(@class, 'text-center')]/h3");

    public TodayPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(20));
    }

    public boolean isDisplayedDateCorrect() {
        try {
            // Get the displayed date text from the page
            wait.until(ExpectedConditions.presenceOfElementLocated(dateHeading));
            String displayedDateText = driver.findElement(dateHeading).getText().trim();
            System.out.println("Displayed date: " + displayedDateText);

            // Get today's date
            LocalDate today = LocalDate.now();
            
            // Format today's date to match the displayed format: "Monday  January 5 2026"
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE  MMMM d yyyy", Locale.ENGLISH);
            String expectedDateText = today.format(formatter);
            System.out.println("Expected date: " + expectedDateText);

            // Compare the dates (case-insensitive and trim any extra spaces)
            return displayedDateText.replaceAll("\\s+", " ").equalsIgnoreCase(expectedDateText.replaceAll("\\s+", " "));
        } catch (Exception e) {
            System.err.println("Error checking date: " + e.getMessage());
            return false;
        }
    }

    public String getDisplayedDate() {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(dateHeading));
            return driver.findElement(dateHeading).getText().trim();
        } catch (Exception e) {
            System.err.println("Error getting displayed date: " + e.getMessage());
            return "";
        }
    }

    public WebElement getDateHeadingLocator() {
        return driver.findElement(dateHeading);
    }
}
