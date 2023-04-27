package by.teachmeskills.api.dto.defect;

import lombok.Data;

@Data
public class ApiPostDeleteUpdateResponse {
    public boolean status;
    public PostDeleteUpdateResult result;
}
