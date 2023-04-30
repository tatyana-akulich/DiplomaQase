package by.teachmeskills.ui.page;

import by.teachmeskills.util.PropertiesLoader;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;

import java.time.Duration;
import java.util.Properties;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

@Log4j2
public class LoginPage implements BasePage {
    public LoginPage() {
    }

    public final static By EMAIL = By.id("inputEmail");
    public final static By PASSWORD = By.id("inputPassword");
    private final static By LOGIN_BUTTON = By.id("btnLogin");
    private static final By TITLE = By.id("Symbols");

    public LoginPage openPage() {
        log.info("Opening login page");
        open("/login");
        getWebDriver().manage().window().maximize();
        return this;
    }

    public boolean isOpened() {
        log.info("Checking if login page is opened");
        $(TITLE).shouldBe(visible, Duration.ofSeconds(5));
        return $(TITLE).isDisplayed();
    }

    public LoginPage enterValidEmail() {
        log.error("Getting email from properties");
        Properties properties = PropertiesLoader.loadProperties();
        log.error("Entering email from properties in login field");
        $(EMAIL).shouldBe(visible, enabled).sendKeys(properties.getProperty("email"));
        return this;
    }

    public LoginPage enterValidPassword() {
        log.error("Getting password from properties");
        Properties properties = PropertiesLoader.loadProperties();
        log.error("Entering password from properties in password field");
        $(PASSWORD).shouldBe(visible, enabled).sendKeys(properties.getProperty("password"));
        return this;
    }

    public LoginPage enterEmail(String email) {
        log.info("Entering email {}", email);
        $(EMAIL).shouldBe(visible, enabled).sendKeys(email);
        return this;
    }

    public LoginPage enterPassword(String password) {
        log.info("Entering password {}", password);
        $(PASSWORD).shouldBe(visible, enabled).sendKeys(password);
        return this;
    }

    public ProjectsPage clickLogin() {
        log.info("Clicking on login button");
        $(LOGIN_BUTTON).click();
        return new ProjectsPage();
    }
}
