package by.teachmeskills.api;

import by.teachmeskills.api.client.DefectsApiClient;
import by.teachmeskills.api.dto.defect.ApiPostDeleteUpdateResponse;
import by.teachmeskills.api.dto.defect.models.PostDefectModel;
import by.teachmeskills.ui.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PostDefectTest extends BaseTest {
    @Test
    public void testPostDefect() {
        PostDefectModel model = createModel();
        Response response = new DefectsApiClient().postDefect(model);
        ApiPostDeleteUpdateResponse postResponse = response
                .then()
                .extract()
                .body()
                .as(ApiPostDeleteUpdateResponse.class);

        assertThat(postResponse.isStatus())
                .as("Status should be true")
                .isEqualTo(true);

        assertThat(postResponse.getResult().getId())
                .as("Id for new test should be generated")
                .isNotNull();

        new DefectsApiClient().deleteDefectById(postResponse.getResult().getId());
    }
}
