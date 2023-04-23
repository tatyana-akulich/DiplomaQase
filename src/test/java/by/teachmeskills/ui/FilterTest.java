package by.teachmeskills.ui;

import by.teachmeskills.ui.dto.Severity;
import by.teachmeskills.ui.page.DefectsPage;
import by.teachmeskills.ui.page.Header;
import by.teachmeskills.ui.step.DefectsSteps;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
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
        int pageNumber = 1;
        int titleNumber = 1;
        defectsPage.clearStatusFilter("severity").passToFirstOrOnlyPage();
        List<String> allDefectTitles = defectsPage.getAllDefectsTitles();
        List<String> expectedResult = new ArrayList<>();
        List<String> severityForFilters = Arrays.asList(severityForFilter);
        defectsPage.passToFirstOrOnlyPage();
        for (String title : allDefectTitles
        ) {
            if (titleNumber > 10) {
                pageNumber++;
                defectsPage.openPageNumber(String.valueOf(pageNumber));
                titleNumber = 1;
            }
            for (String severity : severityForFilters
            ) {
                if (defectsPage.getDefectsSeverity(title).equals(severity)) {
                    expectedResult.add(title);
                }
            }
            titleNumber++;
        }

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
