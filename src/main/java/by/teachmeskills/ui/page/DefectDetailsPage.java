package by.teachmeskills.ui.page;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import java.util.List;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

@Log4j2
public class DefectDetailsPage implements BasePage {
    private static final By TITLE = By.xpath("//a[@title='Defects list']/ancestor::h1");
    private static final By DESCRIPTION = By.xpath("//p[contains(@data-nodeid, '3')]");
    private static final By ASSIGNEE = By.xpath("//div[text()='Assignee']/following-sibling::div/span/span");
    private static final By SEVERITY = By.xpath("//div[text()='Severity']/following-sibling::div");
    private static final By MILESTONE = By.xpath("//div[text()='Milestone']/following-sibling::div");
    private static final By TAGS = By.xpath("//div[text()='Tags']/following-sibling::div//span");
    private static final By RETURN_TO_DEFECTS_LIST = By.xpath("//a[@title='Defects list']");

    public String getTitle() {
        log.info("Getting title at DefectDetailPage");
        return $(TITLE).shouldBe(visible).getText();
    }

    public String getDescription() {
        log.info("Getting description at DefectDetailPage");
        return $(DESCRIPTION).shouldBe(visible).getText();
    }

    public String getAssignee() {
        log.info("Getting assignee at DefectDetailPage");
        return $(ASSIGNEE).shouldBe(visible).getText();
    }

    public String getSeverity() {
        log.info("Getting severity at DefectDetailPage");
        return $(SEVERITY).shouldBe(visible).getText();
    }

    public String getMilestone() {
        log.info("Getting milestone at DefectDetailPage");
        return $(MILESTONE).isDisplayed() ? $(MILESTONE).shouldBe(visible).getText() : "no milestone";
    }

    public List<String> getTags() {
        log.info("Getting tags list at DefectDetailPage");
        return $$(TAGS).size() == 0 ? null : $$(TAGS).texts();
    }

    public DefectsPage returnToDefectsList() {
        log.info("Passing from DefectDetailsPage to DefectsPage");
        $(RETURN_TO_DEFECTS_LIST).shouldBe(enabled).click();
        return new DefectsPage();
    }
}
