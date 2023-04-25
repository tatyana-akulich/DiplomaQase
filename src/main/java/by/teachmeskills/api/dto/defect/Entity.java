package by.teachmeskills.api.dto.defect;

import java.util.ArrayList;
import java.util.Date;

public class Entity{
    public int id;
    public String title;
    public String actual_result;
    public String status;
    public Object milestone_id;
    public String severity;
    public int member_id;
    public int author_id;
    public ArrayList<Object> attachments;
    public ArrayList<Object> custom_fields;
    public String external_data;
    public Object resolved_at;
    public String created;
    public String updated;
    public Date created_at;
    public Date updated_at;
    public ArrayList<Object> tags;
}
