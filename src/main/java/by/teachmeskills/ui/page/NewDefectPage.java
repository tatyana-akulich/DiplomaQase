package by.teachmeskills.ui.page;

import by.teachmeskills.ui.dto.Defect;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import java.util.List;
import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

@Log4j2

public class NewDefectPage implements BasePage {

    private static final By TITLE_CREATE_DEFECT = By.xpath("//h1[text()= 'Create defect']");
    public static final By DEFECT_TITLE = By.id("title");
    public static final By ACTUAL_RESULT = By.id("actual_result");
    private static final By MILESTONE = By.xpath("//div[text()='Not set']");
    private static final String MILESTONE_LOCATOR = "//div[text()='%s']";
    private static final By SEVERITY = By.xpath("//div[@id='severityGroup']/div");
    private static final By ASSIGNEE = By.xpath("//label[text()='Assignee']/following-sibling::div");
    private static final String ASSIGNEE_LOCATOR = "//div[text()='%s']";
    private static final By TAGS = By.xpath("//div[text()='Select...']");
    private static final By ENTER_NEW_TAG = By.id("react-select-4-input");
    private static final String EXISTING_TAG_LOCATOR = "//div[text()='%s']";
    private static final String NEW_TAG_LOCATOR = "//div[text()='Create \"%s\"']";
    private static final By ADD_ATTACHMENT = By.xpath("//button[text()='Add attachment']");
    private static final By BUTTON_CREATE_DEFECT = By.xpath("//button[text()='Create defect']");
    private static final By BUTTON_CANCEL = By.xpath("//button[text()='Cancel']");


    public DefectsPage clickCreateDefect() {
       log.info("Clicking create defect button");
        $(BUTTON_CREATE_DEFECT).shouldBe(visible, enabled).click();
        return new DefectsPage();
    }

    public NewDefectPage enterTitle(String title) {
        log.info("Entering title {}", title);
        $(DEFECT_TITLE).shouldBe(visible, enabled).sendKeys(title);
        return this;
    }

    public NewDefectPage enterActualResult(String result) {
        log.info("Entering actual result {}", result);
        $(ACTUAL_RESULT).shouldBe(enabled).sendKeys(result);
        return this;
    }

    public NewDefectPage enterSeverity(String severity) {
        log.info("Clicking on severity dropdown");
        $(SEVERITY).shouldBe(visible, enabled).click();
        log.info("Choosing of severity {}", severity);
        $(By.id(severity)).shouldBe(visible, enabled).click();
        return this;
    }

    public NewDefectPage enterAssignee(String assignee) {
        log.info("Clicking on assignee dropdown");
        $(ASSIGNEE).shouldBe(visible, enabled).click();
        log.info("Choosing of assignee {}", assignee);
        $x(String.format(ASSIGNEE_LOCATOR, assignee)).shouldBe(visible, enabled).click();
        return this;
    }

    public NewDefectPage enterMilestone(String milestone) {
        log.info("Clicking on milestone dropdown");
        $(MILESTONE).shouldBe(enabled).click();
        log.info("Choosing of milestone {}", milestone);
        $x(String.format(MILESTONE_LOCATOR, milestone)).shouldBe(visible, enabled).click();
        return this;
    }

    public NewDefectPage enterTags(List<String> tags) {
        int index = 1;
        for (String tag : tags) {
            log.info("Checking if {} is in tagsSet", tag);
            if (Defect.tagsSet.contains(tag)) {
                log.info("Clicking on tags dropdown");
                $(TAGS).shouldBe(visible, enabled).click();
                log.info("Choosing of existing tag {}", tag);
                $x(String.format(EXISTING_TAG_LOCATOR, tag)).shouldBe(enabled).click();
            } else {
                log.info("Clicking on tags dropdown");
                $(TAGS).shouldBe(visible, enabled).click();
                log.info("Entering new tag {}", tag);
                $(ENTER_NEW_TAG).shouldBe(visible, enabled).val(tag);
                log.info("Choosing of new tag {}", tag);
                $(By.xpath(String.format(NEW_TAG_LOCATOR, tag))).shouldBe(visible, enabled).click();
                index++;
                Defect.tagsSet.add(tag);
            }
        }
        return this;
    }
}
