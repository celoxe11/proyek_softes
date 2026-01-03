package com.proyek_softes.demo.tests;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.WebElement;

/**
 * Generic CRUD test helper to eliminate duplicate test code patterns across entity tests.
 * Provides reusable methods for common Create, View, and Edit test workflows.
 */
public abstract class GenericCrudTestHelper<ListPage, CreatePage> extends BaseTest {

    /**
     * Generic method to test creating an entity with data-driven approach.
     * 
     * @param testData Test data from data provider
     * @param listPageSupplier Function to create/get the list page object
     * @param navigateToModule Method to navigate to the module
     * @param navigateToCreate Method to navigate to create page
     * @param createPageSupplier Function to create the create page object
     * @param fillForm Method to fill the form with test data
     * @param saveMethod Method to save the form
     * @param entityNameExtractor Function to extract entity name from test data
     * @param isEntitySaved Method to verify entity was saved
     * @param screenshotName Screenshot filename
     * @param navigateToView Method to navigate to view/list page
     * @param isInFirstRow Method to verify entity appears in first row
     * @param getFirstRowLocator Method to get first row locator for screenshot
     */
    protected void testCreateEntity(
            Map<String, String> testData,
            Function<Void, ListPage> listPageSupplier,
            Runnable navigateToModule,
            Runnable navigateToCreate,
            Function<Void, CreatePage> createPageSupplier,
            BiConsumer<CreatePage, Map<String, String>> fillForm,
            Runnable saveMethod,
            Function<Map<String, String>, String> entityNameExtractor,
            Function<String, Boolean> isEntitySaved,
            String screenshotName,
            Runnable navigateToView,
            Function<String, Boolean> isInFirstRow,
            Function<Void, WebElement> getFirstRowLocator) {
        
        login("will", "will");
        
        listPageSupplier.apply(null);
        navigateToModule.run();
        navigateToCreate.run();
        
        CreatePage createPage = createPageSupplier.apply(null);
        fillForm.accept(createPage, testData);
        saveMethod.run();
        
        String entityName = entityNameExtractor.apply(testData);
        boolean isSaved = isEntitySaved.apply(entityName);
        assertTrue(isSaved, "Entity should be saved successfully");
        
        takeScreenshot(screenshotName + "_Create");
        
        navigateToView.run();
        boolean isInFirst = isInFirstRow.apply(entityName);
        assertTrue(isInFirst, "Created entity should appear in the first row of list");
        
        takeElementScreenshot(screenshotName + "_In_List", getFirstRowLocator.apply(null));
    }

    /**
     * Generic method to test viewing an entity's detail page.
     * 
     * @param testData Test data from data provider
     * @param listPageSupplier Function to create/get the list page object
     * @param navigateToModule Method to navigate to the module
     * @param navigateToView Method to navigate to view/list page
     * @param clickFirst Method to click the first entity
     * @param entityNameExtractor Function to extract entity name from test data
     * @param isTitleCorrect Method to verify the detail page title
     * @param screenshotName Screenshot filename
     */
    protected void testViewEntity(
            Map<String, String> testData,
            Function<Void, ListPage> listPageSupplier,
            Runnable navigateToModule,
            Runnable navigateToView,
            Runnable clickFirst,
            Function<Map<String, String>, String> entityNameExtractor,
            Function<String, Boolean> isTitleCorrect,
            String screenshotName) {
        
        try {
            login("will", "will");
            
            listPageSupplier.apply(null);
            navigateToModule.run();
            navigateToView.run();
            
            clickFirst.run();
            
            Thread.sleep(2000);
            
            String entityName = entityNameExtractor.apply(testData);
            boolean isOnDetailPage = isTitleCorrect.apply(entityName);
            assertTrue(isOnDetailPage, "Should be on detail page for the selected entity");
            takeScreenshot(screenshotName + "_View_Detail");
        } catch (InterruptedException e) {
        }
    }

    /**
     * Generic method to test editing an entity.
     * 
     * @param testData Test data from data provider
     * @param listPageSupplier Function to create/get the list page object
     * @param navigateToModule Method to navigate to the module
     * @param navigateToView Method to navigate to view/list page
     * @param clickFirst Method to click the first entity
     * @param entityNameBeforeEditExtractor Function to extract entity name before edit
     * @param isTitleCorrect Method to verify the detail page title
     * @param screenshotNameBefore Screenshot filename for before state
     * @param editMethod Method to click edit button
     * @param createPageSupplier Function to create the edit page object (usually same as create)
     * @param fillForm Method to fill the form with test data
     * @param saveMethod Method to save the form
     * @param entityNameExtractor Function to extract entity name after edit
     * @param isEntitySaved Method to verify entity was saved
     * @param screenshotNameAfter Screenshot filename for after state
     */
    protected void testEditEntity(
            Map<String, String> testData,
            Function<Void, ListPage> listPageSupplier,
            Runnable navigateToModule,
            Runnable navigateToView,
            Runnable clickFirst,
            Function<Map<String, String>, String> entityNameBeforeEditExtractor,
            Function<String, Boolean> isTitleCorrect,
            String screenshotNameBefore,
            Runnable editMethod,
            Function<Void, CreatePage> createPageSupplier,
            BiConsumer<CreatePage, Map<String, String>> fillForm,
            Runnable saveMethod,
            Function<Map<String, String>, String> entityNameExtractor,
            Function<String, Boolean> isEntitySaved,
            String screenshotNameAfter) {
        
        try {
            login("will", "will");
            
            listPageSupplier.apply(null);
            navigateToModule.run();
            navigateToView.run();
            
            clickFirst.run();
            
            Thread.sleep(2000);
            
            String entityNameBefore = entityNameBeforeEditExtractor.apply(testData);
            boolean isOnDetailPage = isTitleCorrect.apply(entityNameBefore);
            assertTrue(isOnDetailPage, "Should be on detail page for the selected entity");
            takeScreenshot(screenshotNameBefore + "_Before_Edit");
            
            editMethod.run();
            
            CreatePage editPage = createPageSupplier.apply(null);
            fillForm.accept(editPage, testData);
            saveMethod.run();
            
            String entityName = entityNameExtractor.apply(testData);
            boolean isSaved = isEntitySaved.apply(entityName);
            assertTrue(isSaved, "Entity should be saved successfully after editing");
            
            takeScreenshot(screenshotNameAfter + "_Edit");
        } catch (InterruptedException e) {
        }
    }
}
