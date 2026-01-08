package com.proyek_softes.demo.pages.spot;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SpotsPage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final Actions actions;

    // Navbar untuk navigasi ke spots
    private final By navTab = By.id("grouptab_5");
    private final By subTab = By.xpath("//a[@href=\"index.php?module=Spots&action=index&parentTab=All\"]");

    private final By createSpotLink = By.xpath("//a[@data-action-name='Create']");
    private final By viewSpotLink = By.xpath("//a[@data-action-name='List']");

    private final By firstRowSpotName = By.cssSelector("table.list.view tbody tr:first-child td[type='name'] a");
    private final By firstRowLocator = By.cssSelector("table.list.view tbody tr[height='20']:first-of-type");
    private final By filterResult = By.className("msg");

    // filter locators
    private final By filterButton = By.xpath("//a[@title='Filter']");
    private final By quickFilterTab = By.xpath("//li[contains(@class, 'searchTabHandler') and contains(@class, 'basic')]/a");
    private final By modalContent = By.className("modal-content");
    private final By filterNameField = By.id("search_name_basic");
    private final By filterMyItemsCheckbox = By.id("current_user_only_basic");
    private final By filterFavoritesCheckbox = By.id("favorites_only_basic");
    private final By filterSubmitButton = By.id("search_form_submit");
    private final By filterClearButton = By.id("search_form_clear");

    public SpotsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(20));
        this.actions = new Actions(driver);
    }

    public boolean checkPageTitle(String expectedTitle) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("module-title-text")));
            String title = driver.findElement(By.className("module-title-text")).getText();
            return title.toUpperCase().contains(expectedTitle.toUpperCase());
        } catch (Exception e) {
            return false;
        }
    }

    public void navigateToSpotsModule() {
        wait.until(ExpectedConditions.presenceOfElementLocated(navTab));
        wait.until(ExpectedConditions.visibilityOfElementLocated(navTab));
        actions.moveToElement(driver.findElement(navTab)).perform();
        wait.until(ExpectedConditions.presenceOfElementLocated(subTab));
        wait.until(ExpectedConditions.visibilityOfElementLocated(subTab));
        driver.findElement(subTab).click();
    }

    public void navigateToCreateSpot() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(createSpotLink));
        wait.until(ExpectedConditions.elementToBeClickable(createSpotLink));
        driver.findElement(createSpotLink).click();
    }

    public void navigateToViewSpot() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(viewSpotLink));
        wait.until(ExpectedConditions.elementToBeClickable(viewSpotLink));
        driver.findElement(viewSpotLink).click();
    }

    public boolean isInFirstRow(String spotName) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("table.list.view")));
            By firstRowNameLocator = By.cssSelector("table.list.view tbody tr:first-child td[type='name'] a");
            wait.until(ExpectedConditions.presenceOfElementLocated(firstRowNameLocator));
            String firstRowSpotName = driver.findElement(firstRowNameLocator).getText().trim();
            System.out.println(firstRowSpotName);
            return firstRowSpotName.contains(spotName);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isSpotExistsInTable(String spotName) {
        try {
            // First check if there's a "no records" message
            By noRecordsMsg = By.className("msg");
            if (!driver.findElements(noRecordsMsg).isEmpty()) {
                return false; // No records exist
            }
            
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("table.list.view")));
            By allSpotNamesLocator = By.cssSelector("table.list.view tbody tr td[type='name'] a");
            java.util.List<org.openqa.selenium.WebElement> allSpots = driver.findElements(allSpotNamesLocator);
            
            if (allSpots.isEmpty()) {
                return false;
            }
            
            for (org.openqa.selenium.WebElement spot : allSpots) {
                if (spot.getText().trim().equals(spotName)) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public WebElement getFirstRowNameLocator() {
        wait.until(ExpectedConditions.presenceOfElementLocated(firstRowSpotName));
        return driver.findElement(firstRowSpotName);
    }

    public void clickFirstSpot() {
        getFirstRowNameLocator().click();
    }

    public WebElement getFirstRowLocator() {
        wait.until(ExpectedConditions.presenceOfElementLocated(firstRowLocator));
        return driver.findElement(firstRowLocator);
    }

    public boolean isSpotTitleCorrect(String spotName) {
        try {
            By pageTitle = By.cssSelector(".moduleTitle h2");
            String title = wait.until(ExpectedConditions.presenceOfElementLocated(pageTitle)).getText();
            System.out.println("Page title after save: " + title);
            System.out.println("Expected spot name: " + spotName);
            return title.toLowerCase().contains(spotName.toLowerCase());
        } catch (Exception e) {
            System.out.println("Error verifying spot save: " + e.getMessage());
            return false;
        }
    }

    public void editSpot() {
        // no need to wait here as wait is done in isSpotTitleCorrect
    }

    public void deleteSpot() {
        // Locate and check the first row checkbox
        By firstRowCheckbox = By.cssSelector("table.list.view tbody tr:first-child input[type='checkbox'][name='mass[]']");
        wait.until(ExpectedConditions.presenceOfElementLocated(firstRowCheckbox));
        WebElement checkbox = driver.findElement(firstRowCheckbox);
        
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
        
        // Wait for bulk action menu to be enabled
        try {
            Thread.sleep(500); // Wait for selection to register
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Click the bulk action dropdown
        By actionDropdown = By.cssSelector("ul#actionLinkTop.selectActions a.parent-dropdown-handler");
        wait.until(ExpectedConditions.elementToBeClickable(actionDropdown));
        driver.findElement(actionDropdown).click();
        
        // Wait for dropdown menu to appear
        By dropdownMenu = By.cssSelector("ul#actionLinkTop.selectActions ul.subnav");
        wait.until(ExpectedConditions.visibilityOfElementLocated(dropdownMenu));
        
        // Click the delete option
        By deleteOption = By.cssSelector("ul#actionLinkTop.selectActions ul.subnav li a#delete_listview_top");
        wait.until(ExpectedConditions.elementToBeClickable(deleteOption));
        driver.findElement(deleteOption).click();
    }

    public void clickOkInDeleteDialog() {
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
    }

    public void filterQuick(String name, boolean myItems, boolean favorites) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(filterButton));
        driver.findElement(filterButton).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(modalContent));

        wait.until(ExpectedConditions.elementToBeClickable(quickFilterTab));
        WebElement quickFilterElement = driver.findElement(quickFilterTab);
        if (!quickFilterElement.getAttribute("class").contains("active")) {
            quickFilterElement.click();
        }

        driver.findElement(filterNameField).sendKeys(name);
        WebElement myItemsCheckbox = driver.findElement(filterMyItemsCheckbox);
        if (myItemsCheckbox.isSelected() != myItems) {
            myItemsCheckbox.click();
        }
        WebElement favoritesCheckbox = driver.findElement(filterFavoritesCheckbox);
        if (favoritesCheckbox.isSelected() != favorites) {
            favoritesCheckbox.click();
        }

        driver.findElement(filterSubmitButton).click();
    }

    public boolean isFilterResultEmpty() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(filterResult));
            String msg = driver.findElement(filterResult).getText().toLowerCase();
            System.out.println("Filter result message: " + msg);
            return msg.contains("no results found");
        } catch (Exception e) {
            return false;
        }
    }

    public By getFilterResult() {
        return filterResult;
    }

    public void checkAndClearFilter() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(filterButton));
        driver.findElement(filterButton).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(modalContent));

        wait.until(ExpectedConditions.elementToBeClickable(quickFilterTab));
        WebElement quickFilterElement = driver.findElement(quickFilterTab);
        if (!quickFilterElement.getAttribute("class").contains("active")) {
            quickFilterElement.click();
        }

        wait.until(ExpectedConditions.presenceOfElementLocated(filterNameField));
        wait.until(ExpectedConditions.presenceOfElementLocated(filterMyItemsCheckbox));
        wait.until(ExpectedConditions.presenceOfElementLocated(filterFavoritesCheckbox));

        String nameFieldValue = driver.findElement(filterNameField).getAttribute("value").trim();
        boolean myItemsChecked = driver.findElement(filterMyItemsCheckbox).isSelected();
        boolean favoritesChecked = driver.findElement(filterFavoritesCheckbox).isSelected();

        if (nameFieldValue.isEmpty() && !myItemsChecked && !favoritesChecked) {
            driver.findElement(filterSubmitButton).click();
            return;
        }

        wait.until(ExpectedConditions.presenceOfElementLocated(filterClearButton));
        driver.findElement(filterClearButton).click();
        driver.findElement(filterSubmitButton).click();
    }
}
