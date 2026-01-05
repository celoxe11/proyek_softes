package com.proyek_softes.demo.pages.calendar;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CalendarPage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final Actions actions;

    private final By navTab = By.id("grouptab_3");
    private final By subTab = By.id("moduleTab_6_Calendar");

    private final By scheduleMeetingLink = By.xpath("//a[@data-action-name='Schedule_Meeting']");
    private final By scheduleCallLink = By.xpath("//a[@data-action-name='Schedule_Call']");
    private final By createTaskLink = By.xpath("//a[@data-action-name='Create']");
    private final By todayLink = By.xpath("//a[@data-action-name='Today']");

    public CalendarPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(20));
        this.actions = new Actions(driver);
    }

    public void navigateToCalendarModule() {
        wait.until(ExpectedConditions.presenceOfElementLocated(navTab));
        wait.until(ExpectedConditions.visibilityOfElementLocated(navTab));
        actions.moveToElement(driver.findElement(navTab)).perform();
        wait.until(ExpectedConditions.presenceOfElementLocated(subTab));
        wait.until(ExpectedConditions.visibilityOfElementLocated(subTab));
        driver.findElement(subTab).click();
    }

    public void navigateToScheduleMeeting() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(scheduleMeetingLink));
        wait.until(ExpectedConditions.elementToBeClickable(scheduleMeetingLink));
        driver.findElement(scheduleMeetingLink).click();
    }

    public void navigateToScheduleCall() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(scheduleCallLink));
        wait.until(ExpectedConditions.elementToBeClickable(scheduleCallLink));
        driver.findElement(scheduleCallLink).click();
    }

    public void navigateToCreateTask() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(createTaskLink));
        wait.until(ExpectedConditions.elementToBeClickable(createTaskLink));
        driver.findElement(createTaskLink).click();
    }

    public void navigateToTodayView() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(todayLink));
        wait.until(ExpectedConditions.elementToBeClickable(todayLink));
        driver.findElement(todayLink).click();
    }

}
