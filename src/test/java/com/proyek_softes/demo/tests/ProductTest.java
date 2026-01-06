package com.proyek_softes.demo.tests;

import java.util.Map;

import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

import com.proyek_softes.demo.pages.products.CreateProductPage;
import com.proyek_softes.demo.pages.products.ImportProductPage;
import com.proyek_softes.demo.pages.products.ProductsPage;
import com.proyek_softes.demo.utils.ProductDataProvider;

import io.qameta.allure.Description;

public class ProductTest extends GenericCrudTestHelper<ProductsPage, CreateProductPage> {
    // DEM-126 Products - Create
    @Test(dataProvider = "createProductData", dataProviderClass = ProductDataProvider.class)
    @Description("DEM-126")
    public void testDem126(Map<String, String> testData) {
        ProductsPage productsPage = new ProductsPage(driver);
        CreateProductPage createProductPage = new CreateProductPage(driver, wait);

        testCreateEntity(
            testData,
            v -> productsPage,
            productsPage::navigateToProductsModule,
            productsPage::navigateToCreateProduct,
            v -> createProductPage,
            (page, data) -> page.addInformationFromData(data),
            createProductPage::save,
            data -> data.get("name"),
            createProductPage::isProductSavedSuccessfully,
            "DEM-126",
            productsPage::navigateToViewProduct,
            productsPage::isInFirstRow,
            v -> productsPage.getFirstRowLocator()
        );
    }

    // DEM-127 Products - View
    @Test(dataProvider = "viewProductData", dataProviderClass = ProductDataProvider.class)
    @Description("DEM-127")
    public void testDem127(Map<String, String> testData) {
        ProductsPage productsPage = new ProductsPage(driver);

        testViewEntity(
            testData,
            v -> productsPage,
            productsPage::navigateToProductsModule,
            productsPage::navigateToViewProduct,
            productsPage::clickFirstProduct,
            data -> data.get("name"),
            productsPage::isProductTitleCorrect,
            "DEM-127"
        );
    }

    // DEM-128 Products - View (Edit)
    @Test(dataProvider = "editProductData", dataProviderClass = ProductDataProvider.class)
    @Description("DEM-128")
    public void testDem128(Map<String, String> testData) {
        ProductsPage productsPage = new ProductsPage(driver);
        CreateProductPage editProductPage = new CreateProductPage(driver, wait);

        testEditEntity(
            testData,
            v -> productsPage,
            productsPage::navigateToProductsModule,
            productsPage::navigateToViewProduct,
            productsPage::clickFirstProduct,
            data -> data.get("nameBeforeEdit"),
            productsPage::isProductTitleCorrect,
            "DEM-128_View_Product_Detail",
            productsPage::editProduct,
            v -> editProductPage,
            (page, data) -> page.addInformationFromData(data),
            editProductPage::save,
            data -> data.get("name"),
            editProductPage::isProductSavedSuccessfully,
            "DEM-128"
        );
    }

    // DEM-129 Products - View (Delete)
    @Test
    @Description("DEM-129")
    public void testDem129() {
        try {
            login("will", "will");
            ProductsPage productsPage = new ProductsPage(driver);

            productsPage.navigateToProductsModule();
            productsPage.navigateToViewProduct();

            String firstRowProductName =
                productsPage.getFirstRowNameLocator().getText().trim();

            productsPage.clickFirstProduct();
            Thread.sleep(2000);

            productsPage.deleteProduct();
            productsPage.clickOkInDeleteDialog();
            Thread.sleep(2000);

            productsPage.filterQuick(firstRowProductName, false, false);

            boolean isFilterResultEmpty = productsPage.isFilterResultEmpty();
            assertTrue(isFilterResultEmpty,
                "Deleted product should no longer exist in the products list");

            takeElementScreenshot(
                "DEM-129_Deleted_Product_Filter_Result",
                driver.findElement(productsPage.getFilterResult())
            );

            productsPage.checkAndClearFilter();
        } catch (InterruptedException e) {
        }
    }

    // DEM-130 Products - Import
    @Test
    @Description("DEM-130")
    public void testDem130() {
        login("will", "will");
        ProductsPage productsPage = new ProductsPage(driver);

        productsPage.navigateToProductsModule();
        productsPage.navigateToImportProduct();

        ImportProductPage importProductPage = new ImportProductPage(driver);

        boolean isCSV =
            importProductPage.verifyDownloadedTemplateIsCSV(
                10, "DEM-130_Download_History"
            );
        assertTrue(isCSV,
            "Downloaded template should be in CSV format and named contains 'products'");

        importProductPage.uploadFile("Products.csv");

        importProductPage.clickImportCreate();
        importProductPage.clickNext();
        importProductPage.clickNext();
        importProductPage.clickNext();
        importProductPage.clickImportNow();

        boolean isRecordsImported = importProductPage.isRecordsImported();
        assertTrue(isRecordsImported,
            "Records from Products.csv should be imported successfully");

        takeElementScreenshot(
            "DEM-130_Import_Products_Success",
            importProductPage.getSummaryElement()
        );
    }
}
