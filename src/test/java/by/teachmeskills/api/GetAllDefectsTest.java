package by.teachmeskills.api;

import by.teachmeskills.api.client.DefectsApiClient;
import by.teachmeskills.api.dto.defect.ApiGetAllResponse;
import by.teachmeskills.ui.BaseTest;
import by.teachmeskills.ui.page.Header;
import by.teachmeskills.ui.step.DefectsSteps;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GetAllDefectsTest extends BaseTest {
    @Test
    public void testGetAllDefects(){
        Response allDefects = new DefectsApiClient().getAllDefects();
        assertThat(allDefects.getStatusCode())
                .as("Status code should be 200")
                .isEqualTo(200);

        ApiGetAllResponse response = allDefects
                .then()
                .extract()
                .body()
                .as(ApiGetAllResponse.class);

        int expectedAmountOfDefects = new DefectsSteps()
                .openDefectsPage(projectName)
                .clickOnStatusChoice()
                .clickSelectAllStatuses()
                .getAllDefectsTitles()
                .size();
        new Header().signOut();
        assertThat(expectedAmountOfDefects)
                .as("Expected amount of defects should be equal to total value")
                .isEqualTo(response.result.total);
    }
}
