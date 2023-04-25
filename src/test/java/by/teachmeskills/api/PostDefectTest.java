package by.teachmeskills.api;

import by.teachmeskills.api.client.DefectsApiClient;
import by.teachmeskills.api.dto.defect.ApiPostDeleteUpdateResponse;
import by.teachmeskills.ui.BaseTest;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PostDefectTest extends BaseTest {
    @Test
    public void testPostDefect() {
        Response response = new DefectsApiClient().postDefect(new Faker().name().nameWithMiddle(),
                new Faker().name().title());
        assertThat(response.getStatusCode())
                .as("Status code should be 200")
                .isEqualTo(200);

        ApiPostDeleteUpdateResponse postResponse = response
                .then()
                .extract()
                .body()
                .as(ApiPostDeleteUpdateResponse.class);

        assertThat(postResponse.status)
                .as("Status should be true")
                .isEqualTo(true);

        assertThat(postResponse.result.id)
                .as("Id for new test should be generated")
                .isNotNull();

        new DefectsApiClient().deleteDefectById(String.valueOf(postResponse.result.id));
    }
}
