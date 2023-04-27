package by.teachmeskills.api.client;

import by.teachmeskills.api.dto.defect.models.PostDefectModel;
import io.restassured.response.Response;

import java.util.Map;

public class DefectsApiClient extends BaseApiClient {
    private final static String PROJECT_CODE = "SHARELANE";
    private final static String DEFECT_URL = BASE_URL + "/v1/defect/";
    private final static String POST_DEFECT_URL = DEFECT_URL + "/{code}";
    private final static String GET_ALL_DEFECTS_URL = DEFECT_URL + "/{code}";
    private final static String DELETE_DEFECT_URL = DEFECT_URL + "/{code}/{id}";
    private final static String GET_DEFECT_URL = DEFECT_URL + "/{code}/{id}";
    private final static String UPDATE_DEFECT_URL = DEFECT_URL + "/{code}/{id}";
    private final static String RESOLVE_DEFECT_URL = DEFECT_URL + "{code}/resolve/{id}";
    private final static String UPDATE_DEFECT_STATUS_URL = DEFECT_URL + "{code}/status/{id}";

    public Response getAllDefects() {
        return get(GET_ALL_DEFECTS_URL, Map.of("code", PROJECT_CODE));
    }

    public Response postDefect(PostDefectModel model) {
        return post(POST_DEFECT_URL, Map.of("code", PROJECT_CODE), model);
    }

    public Response getDefectById(int id) {
        return get(GET_DEFECT_URL, Map.of("code", PROJECT_CODE, "id", id));
    }

    public Response deleteDefectById(int id) {
        return delete(DELETE_DEFECT_URL, Map.of("code", PROJECT_CODE, "id", id));
    }

    public Response updateDefectById(int id, PostDefectModel model) {
        return patch(UPDATE_DEFECT_URL, Map.of("code", PROJECT_CODE, "id", id),
                model);
    }

    public Response resolveDefectById(int id) {
        return patch(RESOLVE_DEFECT_URL, Map.of("code", PROJECT_CODE, "id", id));
    }

    public Response updateDefectStatusById(int id, PostDefectModel model) {
        return patch(UPDATE_DEFECT_STATUS_URL, Map.of("code", PROJECT_CODE, "id", id),
                model);
    }
}
