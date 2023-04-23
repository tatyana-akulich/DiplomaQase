package by.teachmeskills.ui.dto;

public enum Severity {
    NOTSET("react-select-2-option-0", "Not set"),
    BLOCKER("react-select-2-option-1", "Blocker"),
    CRITICAL("react-select-2-option-2", "Critical"),
    MAJOR("react-select-2-option-3", "Major"),
    NORMAL("react-select-2-option-4", "Normal"),
    MINOR("react-select-2-option-5", "Minor"),
    TRIVIAL("react-select-2-option-6", "Trivial");

    String locator;
    String text;

    Severity(String locator, String text) {
        this.locator = locator;
        this.text = text;
    }

    public String getLocator() {
        return locator;
    }

    public String getText() {
        return text;
    }
}
