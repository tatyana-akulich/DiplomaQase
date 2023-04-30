package by.teachmeskills.ui.page;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public interface BasePage {
    default BasePage openPage() {
        return null;
    }

    default boolean isPageOpened(){
        return true;
    }

    default void waitForPageLoaded() {
        new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
            }
        };
    }
    default boolean isValidationMessageShown(By locator1, By locator2) {
        SelenideElement inputForLocator1 = $(locator1);
        SelenideElement inputForLocator2 = $(locator2);
        String text = null;
        if (!(Boolean) Selenide.executeJavaScript("return arguments[0].validity.valid;", inputForLocator1)) {
            text += Selenide.executeJavaScript("return arguments[0].validationMessage;", inputForLocator1);
        } else if (!(Boolean) Selenide.executeJavaScript("return arguments[0].validity.valid;", inputForLocator2)) {
            text += Selenide.executeJavaScript("return arguments[0].validationMessage;", inputForLocator2);
        }
        return !text.isEmpty();
    }
}
