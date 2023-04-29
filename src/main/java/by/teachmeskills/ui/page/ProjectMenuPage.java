package by.teachmeskills.ui.page;

import org.openqa.selenium.By;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class ProjectMenuPage implements BasePage {
    private static final String PROJECT_NAME_LOCATOR = "//div[text()='%s']";
    private static final By DEFECTS = By.xpath("//span[text()='Defects']");

    public boolean isSelectedProjectOpened(String projectName) {
        return $x(String.format(PROJECT_NAME_LOCATOR, projectName)).shouldBe(visible, Duration.ofSeconds(10)).isDisplayed();
    }

    public DefectsPage openDefectsPage() {
        $(DEFECTS).shouldBe(visible, enabled).click();
        new DefectsPage().waitForLoading();
        return new DefectsPage();
    }
}
