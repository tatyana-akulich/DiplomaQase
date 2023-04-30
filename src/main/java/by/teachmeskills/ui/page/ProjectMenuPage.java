package by.teachmeskills.ui.page;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

@Log4j2
public class ProjectMenuPage implements BasePage {
    private static final String PROJECT_NAME_LOCATOR = "//div[text()='%s']";
    private static final By DEFECTS = By.xpath("//span[text()='Defects']");

    public boolean isSelectedProjectOpened(String projectName) {
        log.info("Checking if project {} is chosen", projectName);
        return $x(String.format(PROJECT_NAME_LOCATOR, projectName)).shouldBe(visible, Duration.ofSeconds(10)).isDisplayed();
    }

    public DefectsPage openDefectsPage() {
        log.info("Clicking on defects");
        $(DEFECTS).shouldBe(visible, enabled).click();
        log.error("Waiting till defects page is loaded");
        new DefectsPage().waitForLoading();
        return new DefectsPage();
    }
}
