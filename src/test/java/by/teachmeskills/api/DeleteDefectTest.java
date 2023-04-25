package by.teachmeskills.api;

import by.teachmeskills.api.client.DefectsApiClient;
import by.teachmeskills.api.dto.defect.ApiPostDeleteUpdateResponse;
import by.teachmeskills.ui.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DeleteDefectTest extends BaseTest {
    @Test
    public void testDeleteDefect() {
        String defectId = createDefectThroughApi();
        Response defect = new DefectsApiClient().deleteDefectById(defectId);
        assertThat(defect.getStatusCode())
                .as("Status code should be 200")
                .isEqualTo(200);
        ApiPostDeleteUpdateResponse postDeleteResponse = defect
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

        defectId = "1000";
        defect = new DefectsApiClient().deleteDefectById(defectId);
        assertThat(defect.getStatusCode())
                .as("Status code should be 404 - not found")
                .isEqualTo(404);

        defectId = "0";
        defect = new DefectsApiClient().deleteDefectById(defectId);
        assertThat(defect.getStatusCode())
                .as("Status code should be 400 - bad request, id should be not less than 1")
                .isEqualTo(400);
    }
}
