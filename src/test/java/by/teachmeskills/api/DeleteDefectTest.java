package by.teachmeskills.api;

import by.teachmeskills.api.client.DefectsApiClient;
import by.teachmeskills.api.dto.defect.ApiPostDeleteUpdateResponse;
import by.teachmeskills.ui.BaseTest;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DeleteDefectTest extends BaseTest {
    private int defectId;
    private Response response;

    @Test
    public void testDeleteDefect() {
        defectId = createDefectThroughApi();
        response = new DefectsApiClient().deleteDefectById(defectId);
        assertThat(response.getStatusCode())
                .as("Status code should be 200")
                .isEqualTo(HttpStatus.SC_OK);
        ApiPostDeleteUpdateResponse postDeleteResponse = response
                .then()
                .extract()
                .body()
                .as(ApiPostDeleteUpdateResponse.class);

        assertThat(postDeleteResponse.isStatus())
                .as("Status should be true")
                .isEqualTo(true);

        assertThat(postDeleteResponse.getResult().getId())
                .as("Actual id should be equal to sent in request")
                .isEqualTo(defectId);

        response = new DefectsApiClient().deleteDefectById(defectId);
        assertThat(response.getStatusCode())
                .as("Status code should be 404 - not found")
                .isEqualTo(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void testDeleteDefectCode400() {
        defectId = 0;
        response = new DefectsApiClient().deleteDefectById(defectId);
        assertThat(response.getStatusCode())
                .as("Status code should be 400 - bad request, id should be not less than 1")
                .isEqualTo(HttpStatus.SC_BAD_REQUEST);
    }
}
