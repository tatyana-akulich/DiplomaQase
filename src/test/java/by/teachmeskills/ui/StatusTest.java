package by.teachmeskills.ui;

import by.teachmeskills.ui.dto.Status;
import by.teachmeskills.ui.page.DefectsPage;
import by.teachmeskills.ui.page.Header;
import by.teachmeskills.ui.step.DefectsSteps;
import org.testng.annotations.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class StatusTest extends BaseTest {
    private DefectsPage defectsPage;

    @BeforeClass
    public void openDefectsPage() {
        defectsPage = new DefectsSteps().
                openDefectsPage(projectName);
    }

    @Test
    public void testStatusFilterSettings() {
        assertThat(defectsPage.isStatusFilterEnabled())
                .as("Status filter should be enabled on defects page")
                .isTrue();

        defectsPage.clearStatusFilter("status").selectStatus(Status.OPEN.getText());
        assertThat(defectsPage.getStatusFilterText())
                .as("Type of status should be visible after 'Status:'")
                .contains(Status.OPEN.getText());

        defectsPage.selectStatus(Status.RESOLVED.getText());
        assertThat(defectsPage.isStatusChosen(Status.OPEN.getText())
                & defectsPage.isStatusChosen(Status.RESOLVED.getText()))
                .as("Multiple status choice should be enabled")
                .isTrue();

        assertThat(defectsPage.getStatusFilterText())
                .as("Type of status is multiple, if more than 1 variant is chosen")
                .contains("multiple");

        assertThat(defectsPage.isSelectAllButtonDisplayed())
                .as("Select all button should be enabled unless all statuses are chosen")
                .isTrue();

        defectsPage.selectStatuses(List.of(Status.IN_PROGRESS.getText(), Status.INVALID.getText()));

        assertThat(defectsPage.isSelectAllButtonDisplayed())
                .as("Select all button should be disabled when all statuses are chosen")
                .isFalse();

        defectsPage.selectStatus(Status.OPEN.getText());
        assertThat(defectsPage.isStatusChosen(Status.OPEN.getText()))
                .as("Element should be not selected after second click")
                .isFalse();

        defectsPage.clickSelectAllStatuses();
        assertThat(defectsPage.isSelectAllButtonDisplayed())
                .as("Select all button should disappear after it was clicked")
                .isFalse();

        assertThat(defectsPage.isStatusChosen(Status.OPEN.getText()))
                .as("All element should be selected after select all was clicked")
                .isTrue();
    }

    @DataProvider
    public Object[][] statusesForFilter() {
        return new String[][]{{Status.OPEN.getText()}, {Status.OPEN.getText(), Status.RESOLVED.getText()}, {Status.INVALID.getText()}};
    }

    @Test(dataProvider = "statusesForFilter")
    public void testStatusFilterResults(String[] statusesForFilter) {
        defectsPage.clearStatusFilter("status").passToFirstOrOnlyPage();
        List<String> allDefectTitles = defectsPage.getAllDefectsTitles();
        List<String> expectedResult;
        List<String> statusesForFilters = Arrays.asList(statusesForFilter);
        defectsPage.passToFirstOrOnlyPage();
        expectedResult = defectsPage.getDefectsByFilter(allDefectTitles, statusesForFilters, "status");

        List<String> actualResult = new DefectsSteps().getStatusFilterResult(statusesForFilters);
        assertThat(actualResult)
                .as("Result of filtering should coincide with expected")
                .isEqualTo(expectedResult);
    }

    @AfterClass
    public void signOut() {
        new Header().signOut();
        closeBrowser();
    }
}
