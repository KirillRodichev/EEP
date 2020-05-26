package model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class LoadedEquipment {
    private int equipmentNumber;
    private List<Integer> equipmentIDsForSinglePage;
}
