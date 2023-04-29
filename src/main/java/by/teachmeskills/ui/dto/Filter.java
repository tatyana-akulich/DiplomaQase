package by.teachmeskills.ui.dto;

public enum Filter {
    AUTHOR("Author"),
    AUTHOR_BY_REPORTER("Author By Reporter"),
    ASSIGNEE("Assignee"),
    SEVERITY("Severity"),
    MILESTONE("Milestone"),
    CREATION_DATE("Creation Date"),
    LAST_UPDATE_DATE("Last Update Date"),
    TAGS("Tags");
    String text;

    Filter(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
