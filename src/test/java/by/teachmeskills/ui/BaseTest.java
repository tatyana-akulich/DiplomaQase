package by.teachmeskills.ui;

import com.codeborne.selenide.Selenide;

public class BaseTest {
    String projectName = "ShareLane";

    void closeBrowser() {
        Selenide.closeWindow();
        Selenide.closeWebDriver();
    }
}
