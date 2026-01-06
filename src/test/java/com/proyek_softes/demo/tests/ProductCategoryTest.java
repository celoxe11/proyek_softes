package com.proyek_softes.demo.tests;

import java.util.Map;

import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

import com.proyek_softes.demo.pages.products_categories.CreateProductCategoryPage;
import com.proyek_softes.demo.pages.products_categories.ImportProductCategoryPage;
import com.proyek_softes.demo.pages.products_categories.ProductCategoriesPage;
import com.proyek_softes.demo.utils.ProductCategoryDataProvider;

import io.qameta.allure.Description;

public class ProductCategoryTest extends GenericCrudTestHelper<ProductCategoriesPage, CreateProductCategoryPage> {

    // DEM-131 Products Categories - Create
    @Test(dataProvider = "createProductCategoryData", dataProviderClass = ProductCategoryDataProvider.class)
    @Description("DEM-131")
    public void testDem131(Map<String, String> testData) {
        ProductCategoriesPage productCategoriesPage = new ProductCategoriesPage(driver);
        CreateProductCategoryPage createProductCategoryPage = new CreateProductCategoryPage(driver, wait);

        testCreateEntity(
                testData,
                v -> productCategoriesPage,
                productCategoriesPage::navigateToProductsModule,
                productCategoriesPage::navigateToCreateProduct,
                v -> createProductCategoryPage,
                (page, data) -> page.addInformationFromData(data),
                createProductCategoryPage::save,
                data -> data.get("name"),
                createProductCategoryPage::isProductSavedSuccessfully,
                "DEM-131",
                productCategoriesPage::navigateToViewProduct,
                productCategoriesPage::isInFirstRow,
                v -> productCategoriesPage.getFirstRowLocator());
    }

    // DEM-132 Products Categories - View
    @Test(dataProvider = "viewProductCategoryData", dataProviderClass = ProductCategoryDataProvider.class)
    @Description("DEM-132")
    public void testDem132(Map<String, String> testData) {
        ProductCategoriesPage productCategoriesPage = new ProductCategoriesPage(driver);

        testViewEntity(
                testData,
                v -> productCategoriesPage,
                productCategoriesPage::navigateToProductsModule,
                productCategoriesPage::navigateToViewProduct,
                productCategoriesPage::clickFirstProduct,
                data -> data.get("name"),
                productCategoriesPage::isProductTitleCorrect,
                "DEM-132");
    }

    // DEM-133 Products Categories - View (Edit)
    @Test(dataProvider = "editProductCategoryData", dataProviderClass = ProductCategoryDataProvider.class)
    @Description("DEM-133")
    public void testDem133(Map<String, String> testData) {
        ProductCategoriesPage productCategoriesPage = new ProductCategoriesPage(driver);
        CreateProductCategoryPage editProductCategoryPage = new CreateProductCategoryPage(driver, wait);

        testEditEntity(
                testData,
                v -> productCategoriesPage,
                productCategoriesPage::navigateToProductsModule,
                productCategoriesPage::navigateToViewProduct,
                productCategoriesPage::clickFirstProduct,
                data -> data.get("nameBeforeEdit"),
                productCategoriesPage::isProductTitleCorrect,
                "DEM-133_View_Product_Category_Detail",
                productCategoriesPage::editProduct,
                v -> editProductCategoryPage,
                (page, data) -> page.addInformationFromData(data),
                editProductCategoryPage::save,
                data -> data.get("name"),
                editProductCategoryPage::isProductSavedSuccessfully,
                "DEM-133");
    }

    // DEM-134 Products Categories - View (Delete)
    @Test
    @Description("DEM-134")
    public void testDem134() {
        try {
            login("will", "will");
            ProductCategoriesPage productCategoriesPage = new ProductCategoriesPage(driver);

            productCategoriesPage.navigateToProductsModule();
            productCategoriesPage.navigateToViewProduct();

            String firstRowProductName = productCategoriesPage.getFirstRowNameLocator().getText().trim();

            productCategoriesPage.clickFirstProduct();
            Thread.sleep(2000);

            productCategoriesPage.deleteProduct();
            productCategoriesPage.clickOkInDeleteDialog();
            Thread.sleep(2000);

            productCategoriesPage.filterQuick(firstRowProductName, false);

            boolean isFilterResultEmpty = productCategoriesPage.isFilterResultEmpty();
            assertTrue(isFilterResultEmpty,
                    "Deleted product category should no longer exist in the products list");

            takeElementScreenshot(
                    "DEM-134_Deleted_Product_Category_Filter_Result",
                    driver.findElement(productCategoriesPage.getFilterResult()));

            productCategoriesPage.checkAndClearFilter();
        } catch (InterruptedException e) {
        }
    }

    // DEM-135 Products Categories - Import
    @Test
    @Description("DEM-135")
    public void testDem135() {
        login("will", "will");
        ProductCategoriesPage productCategoriesPage = new ProductCategoriesPage(driver);

        productCategoriesPage.navigateToProductsModule();
        productCategoriesPage.navigateToImportProduct();

        ImportProductCategoryPage importProductCategoryPage = new ImportProductCategoryPage(driver);

        boolean isCSV = importProductCategoryPage.verifyDownloadedTemplateIsCSV(
                10, "DEM-135_Download_History");
        assertTrue(isCSV,
                "Downloaded template should be in CSV format and named contains 'products'");

        importProductCategoryPage.uploadFile("Products_Categories.csv");

        importProductCategoryPage.clickImportCreate();
        importProductCategoryPage.clickNext();
        importProductCategoryPage.clickNext();
        importProductCategoryPage.clickNext();
        importProductCategoryPage.clickImportNow();

        boolean isRecordsImported = importProductCategoryPage.isRecordsImported();
        assertTrue(isRecordsImported,
                "Records from ProductCategories.csv should be imported successfully");

        takeElementScreenshot(
                "DEM-135_Import_Product_Categories_Success",
                importProductCategoryPage.getSummaryElement());
    }
}
