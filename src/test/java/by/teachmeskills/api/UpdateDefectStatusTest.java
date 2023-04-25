package by.teachmeskills.api;

import by.teachmeskills.api.client.DefectsApiClient;
import by.teachmeskills.api.dto.defect.ApiPostDeleteUpdateResponse;
import by.teachmeskills.ui.BaseTest;
import by.teachmeskills.ui.dto.Status;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UpdateDefectStatusTest extends BaseTest {
    @Test
    public void testUpdateStatus() {
        String defectId = createDefectThroughApi();
        Response response = new DefectsApiClient().updateDefectStatusById(defectId, Status.INVALID);
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

        new DefectsApiClient().deleteDefectById(defectId);

        defectId = "1000";
        response = new DefectsApiClient().updateDefectStatusById(defectId, Status.RESOLVED);
        assertThat(response.getStatusCode())
                .as("Status code should be 404 - not found")
                .isEqualTo(404);

        defectId = "0";
        response = new DefectsApiClient().updateDefectStatusById(defectId, Status.OPEN);
        assertThat(response.getStatusCode())
                .as("Status code should be 400 - bad request, id should be not less than 1")
                .isEqualTo(400);
    }
}
