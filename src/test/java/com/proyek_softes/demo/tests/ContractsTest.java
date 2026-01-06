package com.proyek_softes.demo.tests;

import java.util.Map;

import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

import com.proyek_softes.demo.pages.contracts.ContractsPage;
import com.proyek_softes.demo.pages.contracts.CreateContractPage;
import com.proyek_softes.demo.pages.contracts.ImportContractPage;
import com.proyek_softes.demo.utils.ContractDataProvider;

import io.qameta.allure.Description;

public class ContractsTest extends GenericCrudTestHelper<ContractsPage, CreateContractPage> {

    @Test(dataProvider = "createContractData", dataProviderClass = ContractDataProvider.class)
    @Description("DEM-086")
    public void testDem086(Map<String, String> testData) {
        ContractsPage contractsPage = new ContractsPage(driver);
        CreateContractPage createContractPage = new CreateContractPage(driver, wait);
        testCreateEntity(
                testData,
                v -> contractsPage,
                contractsPage::navigateToContractsModule,
                contractsPage::navigateToCreateContract,
                v -> createContractPage,
                (page, data) -> page.addInformationFromData(data),
                createContractPage::save,
                data -> data.get("name"),
                createContractPage::isContractSavedSuccessfully,
                "DEM-086",
                contractsPage::navigateToViewContract,
                contractsPage::isInFirstRow,
                v -> contractsPage.getFirstRowLocator()
        );
    }

    @Test(dataProvider = "viewContractData", dataProviderClass = ContractDataProvider.class)
    @Description("DEM-087")
    public void testDem087(Map<String, String> testData) {
        ContractsPage contractsPage = new ContractsPage(driver);

        testViewEntity(
                testData,
                v -> contractsPage,
                contractsPage::navigateToContractsModule,
                contractsPage::navigateToViewContract,
                contractsPage::clickFirstContract,
                data -> data.get("name"),
                contractsPage::isContractTitleCorrect,
                "DEM-087"
        );
    }

    @Test(dataProvider = "editContractData", dataProviderClass = ContractDataProvider.class)
    @Description("DEM-088")
    public void testDem088(Map<String, String> testData) {
        ContractsPage contractsPage = new ContractsPage(driver);
        CreateContractPage createContractPage = new CreateContractPage(driver, wait);

        testEditEntity(
                testData,
                v -> contractsPage,
                contractsPage::navigateToContractsModule,
                contractsPage::navigateToViewContract,
                contractsPage::clickFirstContract,
                data -> data.get("nameBeforeEdit"),
                contractsPage::isContractTitleCorrect,
                "DEM-088_View_Contract_Detail",
                contractsPage::editContract,
                v -> createContractPage,
                (page, data) -> page.addInformationFromData(data),
                createContractPage::save,
                data -> data.get("name"),
                createContractPage::isContractSavedSuccessfully,
                "DEM-088"
        );
    }

    @Test
    @Description("DEM-089")
    public void testDem089() {
        try {
            login("will", "will");
            ContractsPage contractsPage = new ContractsPage(driver);
            contractsPage.navigateToContractsModule();
            contractsPage.navigateToViewContract();
            String firstContractName = contractsPage.getFirstRowNameLocator().getText().trim();
            contractsPage.clickFirstContract();
            Thread.sleep(2000);

            contractsPage.deleteContract();
            contractsPage.clickOkInDeleteDialog();

            // wait until return to view account
            Thread.sleep(2000);

            contractsPage.filterQuick(firstContractName, false, false);

            boolean isFilterResultEmpty = contractsPage.isFilterResultEmpty();
            assertTrue(isFilterResultEmpty, "Deleted contract should no longer exist in the contracts list");
            takeElementScreenshot("DEM-089_Deleted_Contract_Filter_Result", driver.findElement(contractsPage.getFilterResult()));
            contractsPage.checkAndClearFilter();
        } catch (InterruptedException e) {
        }

    }

    @Test
    @Description("DEM-090")
    public void testDem090() {
        try {
            login("will", "will");
            ContractsPage contractsPage = new ContractsPage(driver);
            contractsPage.navigateToContractsModule();
            contractsPage.navigateToViewContract();

            ImportContractPage importContractPage = new ImportContractPage(driver);
            boolean isCSV = importContractPage.verifyDownloadedTemplateIsCSV(10, "DEM-090_Download_History");
            assertTrue(isCSV, "Downloaded template should be in CSV format and named contains 'contracts'");

            // upload file and complete import process
            importContractPage.uploadFile("Contracts.csv");

            importContractPage.clickImportCreate();
            importContractPage.clickNext();
            importContractPage.clickNext();
            importContractPage.clickNext();
            importContractPage.clickImportNow();

            boolean isRecordsImported = importContractPage.isRecordsImported();
            assertTrue(isRecordsImported, "Records from contracts.csv should be imported successfully");
            takeElementScreenshot("DEM-090_Import_Contracts_Success", importContractPage.getSummaryElement());

        } catch (Throwable e) {
            takeScreenshot("DEM-090_Error_Current_Page");
            throw new AssertionError("Test failed: " + e.getMessage(), e);
        }
    }
}
