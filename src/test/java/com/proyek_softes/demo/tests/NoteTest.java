package com.proyek_softes.demo.tests;

import java.util.Map;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.proyek_softes.demo.pages.notes.CreateNotePage;
import com.proyek_softes.demo.pages.notes.ImportNotePage;
import com.proyek_softes.demo.pages.notes.NotesPage;
import com.proyek_softes.demo.utils.NoteDataProvider;

import io.qameta.allure.Description;

public class NoteTest extends GenericCrudTestHelper<NotesPage, CreateNotePage> {

    /**
     * DEM-075
     * Notes - Create Note or Attachment
     */
    @Test(dataProvider = "createNoteData", dataProviderClass = NoteDataProvider.class)
    @Description("DEM-075")
    public void testDem075(Map<String, String> testData) {
        NotesPage notesPage = new NotesPage(driver);
        CreateNotePage createNotePage = new CreateNotePage(driver, wait);

        testCreateEntity(
            testData,
            v -> notesPage,
            notesPage::navigateToNotesModule,
            notesPage::navigateToCreateNote,
            v -> createNotePage,
            (page, data) -> page.addInformationFromData(data),
            createNotePage::save,
            data -> data.get("name"),
            createNotePage::isNoteSavedSuccessfully,
            "DEM-075",
            notesPage::navigateToViewNote,
            notesPage::isInFirstRow,
            v -> notesPage.getFirstRowLocator()
        );
    }

    /**
     * DEM-076
     * Notes - View
     */
    @Test(dataProvider = "viewNoteData", dataProviderClass = NoteDataProvider.class)
    @Description("DEM-076")
    public void testDem076(Map<String, String> testData) {
        NotesPage notesPage = new NotesPage(driver);

        testViewEntity(
            testData,
            v -> notesPage,
            notesPage::navigateToNotesModule,
            notesPage::navigateToViewNote,
            notesPage::clickFirstNote,
            data -> data.get("name"),
            notesPage::isNoteTitleCorrect,
            "DEM-076"
        );
    }

    /**
     * DEM-077
     * Notes - View (Edit)
     */
    @Test(dataProvider = "editNoteData", dataProviderClass = NoteDataProvider.class)
    @Description("DEM-077")
    public void testDem077(Map<String, String> testData) {
        NotesPage notesPage = new NotesPage(driver);
        CreateNotePage editNotePage = new CreateNotePage(driver, wait);

        testEditEntity(
            testData,
            v -> notesPage,
            notesPage::navigateToNotesModule,
            notesPage::navigateToViewNote,
            notesPage::clickFirstNote,
            data -> data.get("nameBeforeEdit"),
            notesPage::isNoteTitleCorrect,
            "DEM-077_View_Note_Detail",
            notesPage::editNote,
            v -> editNotePage,
            (page, data) -> page.addInformationFromData(data),
            editNotePage::save,
            data -> data.get("name"),
            editNotePage::isNoteSavedSuccessfully,
            "DEM-077"
        );
    }

    /**
     * DEM-078
     * Notes - View (Delete)
     */
    @Test
    @Description("DEM-078")
    public void testDem078() {
        try {
            login("will", "will");
            NotesPage notesPage = new NotesPage(driver);

            notesPage.navigateToNotesModule();
            notesPage.navigateToViewNote();

            String firstRowNoteName = notesPage
                    .getFirstRowNameLocator()
                    .getText()
                    .trim();

            notesPage.clickFirstNote();
            Thread.sleep(2000);

            notesPage.deleteNote();
            notesPage.clickOkInDeleteDialog();
            Thread.sleep(2000);

            notesPage.filterQuick(firstRowNoteName, false, false);

            boolean isFilterResultEmpty = notesPage.isFilterResultEmpty();
            assertTrue(
                isFilterResultEmpty,
                "Deleted note should no longer exist in the notes list"
            );

            takeElementScreenshot(
                "DEM-078_Deleted_Note_Filter_Result",
                driver.findElement(notesPage.getFilterResult())
            );

            notesPage.checkAndClearFilter();
        } catch (InterruptedException e) {
        }
    }

    /**
     * DEM-079
     * Notes - Import
     */
    @Test
    @Description("DEM-079")
    public void testDem079() {
        login("will", "will");

        NotesPage notesPage = new NotesPage(driver);
        notesPage.navigateToNotesModule();
        notesPage.navigateToImportNote();

        ImportNotePage importNotePage = new ImportNotePage(driver);

        boolean isCSV = importNotePage.verifyDownloadedTemplateIsCSV(
            10,
            "DEM-079_Download_History"
        );

        assertTrue(
            isCSV,
            "Downloaded template should be in CSV format and named contains 'notes'"
        );

        importNotePage.uploadFile("Notes.csv");

        importNotePage.clickImportCreate();
        importNotePage.clickNext();
        importNotePage.clickNext();
        importNotePage.clickNext();
        importNotePage.clickImportNow();

        boolean isRecordsImported = importNotePage.isRecordsImported();
        assertTrue(
            isRecordsImported,
            "Records from Notes.csv should be imported successfully"
        );

        takeElementScreenshot(
            "DEM-079_Import_Notes_Success",
            importNotePage.getSummaryElement()
        );
    }
}
