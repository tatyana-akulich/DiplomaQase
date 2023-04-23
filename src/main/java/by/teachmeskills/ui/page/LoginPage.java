package by.teachmeskills.ui.page;

import by.teachmeskills.util.PropertiesLoader;
import org.openqa.selenium.By;

import java.time.Duration;
import java.util.Properties;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class LoginPage implements BasePage {
    public LoginPage() {
    }

    public final static By EMAIL = By.id("inputEmail");
    public final static By PASSWORD = By.id("inputPassword");
    private final static By LOGIN_BUTTON = By.id("btnLogin");
    private static final By TITLE = By.id("Symbols");

    public LoginPage openPage() {
        open("/login");
        getWebDriver().manage().window().maximize();
        return this;
    }

    public boolean isOpened() {
        $(TITLE).shouldBe(visible, Duration.ofSeconds(5));
        return $(TITLE).isDisplayed();
    }

    public LoginPage enterValidEmail() {
        Properties properties = PropertiesLoader.loadProperties();
        $(EMAIL).shouldBe(visible, enabled).sendKeys(properties.getProperty("email"));
        return this;
    }

    public LoginPage enterValidPassword() {
        Properties properties = PropertiesLoader.loadProperties();
        $(PASSWORD).shouldBe(visible, enabled).sendKeys(properties.getProperty("password"));
        return this;
    }

    public LoginPage enterEmail(String email) {
        $(EMAIL).shouldBe(visible, enabled).sendKeys(email);
        return this;
    }

    public LoginPage enterPassword(String password) {
        $(PASSWORD).shouldBe(visible, enabled).sendKeys(password);
        return this;
    }

    public ProjectsPage clickLogin() {
        $(LOGIN_BUTTON).click();
        return new ProjectsPage();
    }
}
