package by.teachmeskills.ui.step;

import by.teachmeskills.ui.dto.Filter;
import by.teachmeskills.ui.page.DefectsPage;

import java.util.List;

public class DefectsSteps {
    private LoginSteps loginSteps;
    private DefectsPage defectsPage;

    public DefectsSteps() {
        this.loginSteps = new LoginSteps();
        this.defectsPage = new DefectsPage();
    }

    public DefectsPage openDefectsPage(String projectName) {
        return loginSteps
                .loginWithValidCredentials()
                .openProject(projectName)
                .openDefectsPage();
    }

    public DefectsPage deleteDefectAndConfirm(String title) {
        return defectsPage.clickAdditionalOptions(title)
                .clickDelete(title)
                .confirmRemoval();
    }

    public DefectsPage deleteDefectWithoutConfirm(String title) {
        return defectsPage.clickAdditionalOptions(title)
                .clickDelete(title);
    }

    public List<String> getStatusFilterResult(List<String> statuses) {
        return defectsPage.selectStatuses(statuses)
                .openPageNumber(1)
                .getAllDefectsTitles();
    }

    public List<String> getSeverityFilterResult(List<String> statuses) {
        List<String> filteredBySeverityList = defectsPage.clickAddFilter()
                .chooseFilterType(Filter.SEVERITY.getText())
                .chooseSeverityTypes(statuses)
                .openPageNumber(1)
                .getAllDefectsTitles();
        defectsPage.closeFilter(Filter.SEVERITY.getText());
        return filteredBySeverityList;
    }
}
