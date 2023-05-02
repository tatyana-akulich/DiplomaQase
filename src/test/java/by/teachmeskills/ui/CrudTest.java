package by.teachmeskills.ui;

import by.teachmeskills.ui.dto.Assignee;
import by.teachmeskills.ui.dto.Defect;
import by.teachmeskills.ui.dto.Milestone;
import by.teachmeskills.ui.dto.Severity;
import by.teachmeskills.ui.page.DefectsPage;
import by.teachmeskills.ui.page.Header;
import by.teachmeskills.ui.page.NewDefectPage;
import by.teachmeskills.ui.page.ProjectMenuPage;
import by.teachmeskills.ui.step.DefectDetailsSteps;
import by.teachmeskills.ui.step.DefectsSteps;
import by.teachmeskills.ui.step.LoginSteps;
import by.teachmeskills.ui.step.NewDefectSteps;
import com.github.javafaker.Faker;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CrudTest extends BaseTest {
    private Defect expectedDefect;
    private Defect actualDefect;

    @Test
    public void createDefectWithObligatoryFields() {
        ProjectMenuPage shareLane = new LoginSteps()
                .loginWithValidCredentials()
                .openProject(projectName.toUpperCase());
        assertThat(shareLane.isSelectedProjectOpened(projectName))
                .as("Name of chosen project should be shown at the top of left side menu - {}", projectName)
                .isTrue();

        expectedDefect = Defect.builder()
                .defectTitle(new Faker().name().nameWithMiddle())
                .actualResult(new Faker().name().name())
                .severity(Severity.CRITICAL)
                .assignee(Assignee.NOTSET)
                .build();

        shareLane.openDefectsPage();
        DefectsPage defectsPage = new NewDefectSteps().createDefectWithObligatoryFields(expectedDefect);
        assertThat(defectsPage.isConfirmCreationMessageDisplayed())
                .as("Message should be displayed on defects page after successful creation of new defect")
                .isTrue();

        assertThat(defectsPage.getAllDefectsTitles())
                .as("List of defects names should contain created one - {}", expectedDefect.getDefectTitle())
                .contains(expectedDefect.getDefectTitle());

        actualDefect = new DefectDetailsSteps().getDefect(expectedDefect.getDefectTitle());
        assertThat(actualDefect)
                .as("Actual and expected defects should be equal")
                .isEqualTo(expectedDefect);

        new DefectsSteps().deleteDefectAndConfirm(expectedDefect.getDefectTitle());
    }

    @Test
    public void createDefectWithAllFields() {
        new DefectsSteps().openDefectsPage(projectName);

        expectedDefect = Defect.builder()
                .defectTitle(new Faker().name().nameWithMiddle())
                .actualResult(new Faker().name().name())
                .severity(Severity.CRITICAL)
                .assignee(Assignee.TATYANA_AKULICH)
                .milestone(Milestone.RELEASE1)
                .tags(List.of("name"))  //change to new tag name to test functionality of adding new tags
                .build();

        new NewDefectSteps().createDefectWithAllFields(expectedDefect);
        actualDefect = new DefectDetailsSteps().getDefect(expectedDefect.getDefectTitle());
        assertThat(actualDefect)
                .as("Actual and expected defects should be equal")
                .isEqualTo(expectedDefect);

        new DefectsSteps().deleteDefectAndConfirm(expectedDefect.getDefectTitle());
    }

    @Test
    public void createDefectWithEmptyObligatoryFields() {
        DefectsPage defectsPage = new DefectsSteps().openDefectsPage(projectName);
        defectsPage.clickCreateNewDefect().clickCreateDefect();
        assertThat(defectsPage.isValidationMessageShown(NewDefectPage.DEFECT_TITLE, NewDefectPage.ACTUAL_RESULT))
                .as("Validation message should be shown if title or/and actual result is empty")
                .isTrue();

        new NewDefectPage().enterTitle("test").clickCreateDefect();
        assertThat(defectsPage.isValidationMessageShown(NewDefectPage.DEFECT_TITLE, NewDefectPage.ACTUAL_RESULT))
                .as("Validation message should be shown if actual result is empty")
                .isTrue();
        new NewDefectPage().enterActualResult("test").clickCreateDefect();
    }

    @DataProvider
    public Object[][] defectsForRemoval() {
        return new String[][]{{"test"}};
    }

    @Test(dataProvider = "defectsForRemoval")
    public void deleteDefect(String defectTitle) {
        DefectsPage shareLane = new DefectsSteps().openDefectsPage(projectName);
        String id = shareLane.getDefectId(defectTitle);
        int pageNumber = shareLane.getDefectsPage(defectTitle);
        shareLane.openPageNumber(pageNumber);
        shareLane = new DefectsSteps().deleteDefectWithoutConfirm(defectTitle);
        assertThat(shareLane.isDeleteApprovalMessageDisplayed())
                .as("After removal delete approval message should be displayed")
                .isTrue();

        assertThat(shareLane.getRemovalConfirmationMessage())
                .as("Confirmation message should contain defects id - {}", id)
                .contains(id);

        shareLane.confirmRemoval();
        assertThat(shareLane.isDeleteConfirmationMessageDisplayed())
                .as("After removal delete confirmation pop up should be displayed")
                .isTrue();

        assertThat(shareLane.getDeletePopUpText())
                .as("Pop up confirmation message should contain defects id - {}", id)
                .contains(id);

        assertThat(shareLane.getAllDefectsTitles())
                .as("Deleted defect shouldn't be in defects list")
                .doesNotContain("test");
    }

    @AfterMethod(alwaysRun = true)
    public void signOut() {
        new Header().signOut();
    }
}
