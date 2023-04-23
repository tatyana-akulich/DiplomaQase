package by.teachmeskills.ui.step;

import by.teachmeskills.ui.dto.Assignee;
import by.teachmeskills.ui.dto.Defect;
import by.teachmeskills.ui.dto.Milestone;
import by.teachmeskills.ui.dto.Severity;
import by.teachmeskills.ui.page.DefectDetailsPage;
import by.teachmeskills.ui.page.DefectsPage;

public class DefectDetailsSteps {
    private DefectsPage defectsPage;
    private DefectDetailsPage defectDetailsPage;

    public DefectDetailsSteps() {
        this.defectsPage = new DefectsPage();
        this.defectDetailsPage = new DefectDetailsPage();
    }

    public Defect getDefect(String title) {
        defectsPage.openPageNumber("1").openDefectsDetails(title);
        Defect actualDefect = Defect.builder()
                .defectTitle(defectDetailsPage.getTitle())
                .actualResult(defectDetailsPage.getDescription())
                .assignee(Assignee.getAssigneeByText(defectDetailsPage.getAssignee()))
                .milestone(Milestone.getMilestoneByText(defectDetailsPage.getMilestone()))
                .severity(Severity.valueOf(defectDetailsPage.getSeverity().toUpperCase()))
                .tags(defectDetailsPage.getTags())
                .build();
        defectDetailsPage.returnToDefectsList();
        return actualDefect;
    }
}
