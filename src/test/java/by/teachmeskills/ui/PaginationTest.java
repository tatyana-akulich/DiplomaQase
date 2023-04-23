package by.teachmeskills.ui;

import by.teachmeskills.ui.page.DefectsPage;
import by.teachmeskills.ui.page.Header;
import by.teachmeskills.ui.step.DefectsSteps;
import org.testng.annotations.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PaginationTest extends BaseTest {
    @Test
    public void testPagination() {
        DefectsPage defects = new DefectsSteps().openDefectsPage(projectName);
        int amountOfPages = defects.getAmountOfPages();
        if (amountOfPages > 1) {
            assertThat(defects.isNavigationElementDisabled("‹"))
                    .as("On first page back arrow should be disabled")
                    .isTrue();

            assertThat(defects.isNavigationElementEnabled("›"))
                    .as("On first page forward arrow should be enabled")
                    .isTrue();

            List<String> currentPageElements = defects.getDefectsNamesFromPage();
            defects.openPageNumber("›");
            List<String> secondPageElements = defects.getDefectsNamesFromPage();
            assertThat(currentPageElements)
                    .as("After click on navigation arrow page content should change")
                    .isNotEqualTo(secondPageElements);

            defects.openPageNumber("1");
            currentPageElements = defects.getDefectsNamesFromPage();
            assertThat(currentPageElements)
                    .as("After click on another page number content should change")
                    .isNotEqualTo(secondPageElements);

            defects.openPageNumber(String.valueOf(amountOfPages));
            assertThat(defects.isNavigationElementDisabled("›"))
                    .as("On last page forward arrow should be disabled")
                    .isTrue();

            assertThat(defects.isNavigationElementEnabled("‹"))
                    .as("On last page back arrow should be enabled")
                    .isTrue();

            new Header().signOut();
        }
    }
}
