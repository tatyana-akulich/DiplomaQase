package by.teachmeskills.api.client;

import by.teachmeskills.ui.dto.Status;
import io.restassured.response.Response;

import java.util.Map;

public class DefectsApiClient extends BaseApiClient {
    private final static String PROJECT_CODE = "SHARELANE";

    public Response getAllDefectsWithChosenStatus(Status status) {
        Map<String, Object> params = Map.of("status", status.getText().toLowerCase());
        return get(PROJECT_CODE, params);
    }

    public Response getAllDefects() {
        return get(PROJECT_CODE);
    }

    public Response postDefect(String title, String actualResult) {
        String body = "{\"title\":\"%s\",\"actual_result\":\"%s\",\"severity\":1}";
        return post(PROJECT_CODE, String.format(body, title, actualResult));
    }

    public Response getDefectById(String id){
        return get(PROJECT_CODE + "/" + id);
    }

    public Response deleteDefectById(String id){
        return delete(PROJECT_CODE + "/" + id);
    }

    public Response updateDefectById(String id, String parameterToChange, String newValue){
        String body = "{\"%s\":\"%s\"}";
        return patch(PROJECT_CODE + "/" + id, String.format(body, parameterToChange, newValue));
    }

    public Response resolveDefectById(String id){
        String path = PROJECT_CODE + "/resolve/" + id;
        return patch(path);
    }

    public Response updateDefectStatusById(String id, Status status){
        String path = PROJECT_CODE + "/status/" + id;
        String body = "{\"status\":\"%s\"}";
        return patch(path, String.format(body, status.getText().toLowerCase()));
    }
}
