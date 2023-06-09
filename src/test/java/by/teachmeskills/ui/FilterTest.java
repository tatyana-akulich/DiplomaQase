package by.teachmeskills.ui;

import by.teachmeskills.ui.dto.Severity;
import by.teachmeskills.ui.page.DefectsPage;
import by.teachmeskills.ui.page.Header;
import by.teachmeskills.ui.step.DefectsSteps;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FilterTest extends BaseTest {
    private DefectsPage defectsPage;

    @BeforeClass
    public void openDefectsPage() {
        defectsPage = new DefectsSteps().
                openDefectsPage(projectName);
    }

    @DataProvider
    public Object[][] severityForFilter() {
        return new String[][]{{Severity.CRITICAL.getText()}, {Severity.BLOCKER.getText()}};
    }

    @Test(dataProvider = "severityForFilter")
    public void testSeverityFilter(String[] severityForFilter) {
        defectsPage.clearFilter("severity").passToFirstOrOnlyPage();
        List<String> allDefectTitles = defectsPage.getAllDefectsTitles();
        List<String> expectedResult;
        List<String> severityForFilters = Arrays.asList(severityForFilter);
        defectsPage.passToFirstOrOnlyPage();
        expectedResult = defectsPage.getDefectsByFilter(allDefectTitles, severityForFilters, "severity");

        List<String> actualResult = new DefectsSteps().getSeverityFilterResult(severityForFilters);
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
