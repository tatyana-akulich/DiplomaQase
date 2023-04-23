package by.teachmeskills.ui.page;

import org.openqa.selenium.By;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Condition.*;

public class ProjectsPage extends Header {
    private final static By TITLE = By.xpath("//h1 [text() = 'Projects']");
    private final static String PROJECT_LINK_LOCATOR = "//a[contains(@href, '%s') and @class= 'project-name']";

    public boolean isOpened() {
        return $(TITLE).isDisplayed();
    }

    public ProjectMenuPage openProject(String projectName) {
        $x(String.format(PROJECT_LINK_LOCATOR, projectName.toUpperCase())).shouldBe(enabled, Duration.ofSeconds(10)).click();
        return new ProjectMenuPage();
    }
}
