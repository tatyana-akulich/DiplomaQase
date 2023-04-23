package by.teachmeskills.ui.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
public class Defect {
    private String defectTitle;
    private String actualResult;
    private Milestone milestone;
    private Severity severity;
    private Assignee assignee;
    private List<String> tags;
    public static Set<String> tagsSet = new HashSet<>(Arrays.asList("name", "registration"));
}
