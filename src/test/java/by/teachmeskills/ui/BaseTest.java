package by.teachmeskills.ui;

import by.teachmeskills.api.client.DefectsApiClient;
import by.teachmeskills.api.dto.defect.ApiPostDeleteUpdateResponse;
import by.teachmeskills.api.dto.defect.models.PostDefectModel;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;

public class BaseTest {
    protected String projectName = "ShareLane";

    @BeforeMethod
    void setUp(ITestContext testContext) {
        WebDriver driver = WebDriverRunner.getWebDriver();
        testContext.setAttribute("driver", driver);}

    protected int createDefectThroughApi() {
        PostDefectModel model = PostDefectModel.builder()
                .title(new Faker().name().nameWithMiddle())
                .actual_result(new Faker().name().title())
                .severity(4)
                .build();
        Response response = new DefectsApiClient().postDefect(model);
        ApiPostDeleteUpdateResponse postDeleteResponse = response
                .then()
                .extract()
                .body()
                .as(ApiPostDeleteUpdateResponse.class);

        return postDeleteResponse.getResult().getId();
    }

    protected PostDefectModel createModel(){
        return PostDefectModel.builder()
                .title(new Faker().name().nameWithMiddle())
                .actual_result(new Faker().name().title())
                .severity(4)
                .build();
    }

    void closeBrowser() {
        Selenide.closeWindow();
        Selenide.closeWebDriver();
    }
}
