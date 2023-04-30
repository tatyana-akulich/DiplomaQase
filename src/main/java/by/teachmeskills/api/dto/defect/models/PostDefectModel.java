package by.teachmeskills.api.dto.defect.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostDefectModel {
    String title;
    String actual_result;
    int severity;
    String status;
}
