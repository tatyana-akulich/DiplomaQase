package by.teachmeskills.ui.dto;

public enum Status {
    OPEN("Open"),
    IN_PROGRESS("In progress"),
    INVALID("Invalid"),
    RESOLVED("Resolved");
    String text;

    Status(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
