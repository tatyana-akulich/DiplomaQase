package by.teachmeskills.api;

import by.teachmeskills.api.client.DefectsApiClient;
import by.teachmeskills.api.dto.defect.ApiPostDeleteUpdateResponse;
import by.teachmeskills.ui.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class UpdateDefectTest extends BaseTest {
    @Test
    public void testUpdateDefect() {
        String defectId = createDefectThroughApi();
        Response response = new DefectsApiClient().updateDefectById(defectId, "severity", "4");
        assertThat(response.getStatusCode())
                .as("Status code should be 200")
                .isEqualTo(200);
        ApiPostDeleteUpdateResponse postDeleteResponse = response
                .then()
                .extract()
                .body()
                .as(ApiPostDeleteUpdateResponse.class);

        assertThat(postDeleteResponse.status)
                .as("Status should be true")
                .isEqualTo(true);

        assertThat(postDeleteResponse.result.id)
                .as("Actual id should be equal to sent in request")
                .isEqualTo(Integer.parseInt(defectId));

        new DefectsApiClient().deleteDefectById(String.valueOf(postDeleteResponse.result.id));

        defectId = "1000";
        response = new DefectsApiClient().updateDefectById(defectId, "severity", "4");
        assertThat(response.getStatusCode())
                .as("Status code should be 404 - not found")
                .isEqualTo(404);

        defectId = "0";
        response = new DefectsApiClient().updateDefectById(defectId, "severity", "4");
        assertThat(response.getStatusCode())
                .as("Status code should be 400 - bad request, id should be not less than 1")
                .isEqualTo(400);
    }
}
