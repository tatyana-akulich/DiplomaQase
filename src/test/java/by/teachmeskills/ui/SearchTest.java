package by.teachmeskills.ui;

import by.teachmeskills.ui.page.DefectsPage;
import by.teachmeskills.ui.page.Header;
import by.teachmeskills.ui.step.DefectsSteps;
import org.testng.annotations.*;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SearchTest extends BaseTest {
    private List<String> allTitles;

    @BeforeClass
    public void openDefectsPage() {
        allTitles = new DefectsSteps().openDefectsPage(projectName).getAllDefectsTitles();
    }

    @DataProvider
    public Object[][] textForSearch() {
        return new String[][]{{"name"}, {"   email   "}, {"andjfklg"}};
    }

    @Test(dataProvider = "textForSearch")
    public void testSearchTitle(String lineForSearch) {
        DefectsPage defectsPage = new DefectsPage();
        List<String> expectedResult = new ArrayList<>();
        List<String> actualResult = new ArrayList<>();

        for (String title : allTitles
        ) {
            if (title.toLowerCase().contains(lineForSearch.trim().toLowerCase())) {
                expectedResult.add(title);
            }
        }
        assertThat(defectsPage.isSearchFieldEnabled())
                .as("Search field should ne enabled on defects page")
                .isTrue();

        defectsPage.enterLineForSearch(lineForSearch);
        assertThat(defectsPage.getSearchFieldValue())
                .as("Typed in search field text should be equal to search field value")
                .isEqualTo(lineForSearch);

        if (!defectsPage.isUnsuccessfulSearchMessageDisplayed()) {
            actualResult = defectsPage.getAllDefectsTitles();
        }

        assertThat(actualResult)
                .as("Expected and actual search results should be equal")
                .isEqualTo(expectedResult);
    }
    @Test
    public void testSearchStatus(){//failed test
        DefectsPage defectsPage = new DefectsPage();
        List<String> expectedResult = new ArrayList<>();
        List<String> actualResult = new ArrayList<>();
        String statusForSearch = "";

        for (String title : allTitles
        ) {
            if (title.toLowerCase().contains(statusForSearch.trim().toLowerCase())) {
                expectedResult.add(title);
            }
        }


    }

    @AfterClass
    public void signOut(){
        new Header().signOut();
    }
}
