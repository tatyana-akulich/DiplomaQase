package by.teachmeskills.ui.dto;

public enum Assignee {
    NOTSET("Unassigned"),
    TATYANA_AKULICH("QA19-onl_Tatyana_Akulich"),
    KATERINA_ZHARSKAYA("Katerina_Zharkaya");
    String text;

    Assignee(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public static Assignee getAssigneeByText(String text) {
        switch (text) {
            case "Unassigned": {
                return Assignee.NOTSET;
            }
            case "QA19-onl_Tatyana_Akulich": {
                return Assignee.TATYANA_AKULICH;
            }
            case "Katerina_Zharkaya": {
                return Assignee.KATERINA_ZHARSKAYA;
            }
            default: return null;
        }
    }
}
