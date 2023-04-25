package by.teachmeskills.ui;

import by.teachmeskills.api.client.DefectsApiClient;
import by.teachmeskills.api.dto.defect.ApiPostDeleteUpdateResponse;
import com.codeborne.selenide.Selenide;
import com.github.javafaker.Faker;
import io.restassured.response.Response;

public class BaseTest {
    protected String projectName = "ShareLane";

    protected String createDefectThroughApi(){
        Response response = new DefectsApiClient().postDefect(new Faker().name().nameWithMiddle(),
                new Faker().name().title());
        ApiPostDeleteUpdateResponse postDeleteResponse = response
                .then()
                .extract()
                .body()
                .as(ApiPostDeleteUpdateResponse.class);

        return String.valueOf(postDeleteResponse.result.id);
    }

    void closeBrowser() {
        Selenide.closeWindow();
        Selenide.closeWebDriver();
    }
}
