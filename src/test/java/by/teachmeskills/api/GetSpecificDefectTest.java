package by.teachmeskills.api;

import by.teachmeskills.api.client.DefectsApiClient;
import by.teachmeskills.api.dto.defect.ApiGetByIdResponse;
import by.teachmeskills.ui.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GetSpecificDefectTest extends BaseTest {
    @Test
    public void testGetDefectById() {
        String defectId = "1";
        Response defect = new DefectsApiClient().getDefectById(defectId);
        assertThat(defect.getStatusCode())
                .as("Status code should be 200")
                .isEqualTo(200);

        ApiGetByIdResponse response = defect
                .then()
                .extract()
                .body()
                .as(ApiGetByIdResponse.class);

        assertThat(response.status)
                .as("Status should be true")
                .isEqualTo(true);

        assertThat(response.result.id)
                .as("Actual id should be equal to sent in request")
                .isEqualTo(Integer.parseInt(defectId));

        defectId = "1000";
        defect = new DefectsApiClient().getDefectById(defectId);
        assertThat(defect.getStatusCode())
                .as("Status code should be 404 - not found")
                .isEqualTo(404);

        defectId = "0";
        defect = new DefectsApiClient().getDefectById(defectId);
        assertThat(defect.getStatusCode())
                .as("Status code should be 400 - bad request, id should be not less than 1")
                .isEqualTo(400);
    }
}
