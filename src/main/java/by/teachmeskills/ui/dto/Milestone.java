package by.teachmeskills.ui.dto;

public enum Milestone {
    NOTSET("Not set"),
    RELEASE1("Release 1.0");
    String text;

    Milestone(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public static Milestone getMilestoneByText(String text) {
        switch (text) {
            case "Not set": {
                return Milestone.NOTSET;
            }

            case "Release 1.0": {
                return Milestone.RELEASE1;
            }
            default:
                return null;
        }
    }
}
