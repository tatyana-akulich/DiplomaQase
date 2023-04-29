package by.teachmeskills.api.dto.defect;

import lombok.Data;

import java.util.ArrayList;
@Data
public class GetAllResult {
    public int total;
    public int filtered;
    public int count;
    public ArrayList<Entity> entities;
}

