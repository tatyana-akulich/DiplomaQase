package by.teachmeskills.ui.step;

import by.teachmeskills.ui.dto.Defect;
import by.teachmeskills.ui.page.DefectsPage;

public class NewDefectSteps {
    private DefectsPage defectsPage;

    public NewDefectSteps() {
        this.defectsPage = new DefectsPage();
    }

    public DefectsPage createDefectWithObligatoryFields(Defect defect) {
        return defectsPage
                .clickCreateNewDefect()
                .enterTitle(defect.getDefectTitle())
                .enterActualResult(defect.getActualResult())
                .enterSeverity(defect.getSeverity().getLocator())
                .clickCreateDefect();
    }

    public DefectsPage createDefectWithAllFields(Defect defect) {
        return defectsPage
                .clickCreateNewDefect()
                .enterTitle(defect.getDefectTitle())
                .enterActualResult(defect.getActualResult())
                .enterSeverity(defect.getSeverity().getLocator())
                .enterAssignee(defect.getAssignee().getText())
                .enterMilestone(defect.getMilestone().getText())
                .enterTags(defect.getTags())
                .clickCreateDefect();
    }
}
