package by.teachmeskills.api;

import by.teachmeskills.api.client.DefectsApiClient;
import by.teachmeskills.api.dto.defect.ApiPostDeleteUpdateResponse;
import by.teachmeskills.api.dto.defect.models.PostDefectModel;
import by.teachmeskills.ui.BaseTest;
import by.teachmeskills.ui.dto.Status;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UpdateDefectStatusTest extends BaseTest {
    private int defectId;
    private Response response;
    private PostDefectModel model;

    @Test
    public void testUpdateStatusCode200() {
        model = createModel();
        Response response = new DefectsApiClient().postDefect(model);
        ApiPostDeleteUpdateResponse postResponse = response
                .then()
                .extract()
                .body()
                .as(ApiPostDeleteUpdateResponse.class);

        defectId = postResponse.getResult().getId();
        model.setStatus(Status.INVALID.getText().toLowerCase());
        response = new DefectsApiClient().updateDefectStatusById(defectId, model);
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

        new DefectsApiClient().deleteDefectById(defectId);
    }

    @Test
    public void testUpdateStatusCode404() {
        defectId = 1000;
        response = new DefectsApiClient().updateDefectStatusById(defectId, model);
        assertThat(response.getStatusCode())
                .as("Status code should be 404 - not found")
                .isEqualTo(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void testUpdateStatusCode400() {
        defectId = 0;
        response = new DefectsApiClient().updateDefectStatusById(defectId, model);
        assertThat(response.getStatusCode())
                .as("Status code should be 400 - bad request, id should be not less than 1")
                .isEqualTo(HttpStatus.SC_BAD_REQUEST);
    }
}
