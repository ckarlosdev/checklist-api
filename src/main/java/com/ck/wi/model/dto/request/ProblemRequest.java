package com.ck.wi.model.dto.request;

import java.util.List;

public class ProblemRequest {
    private List<Integer> drEquipmentIds;

    public List<Integer> getDrEquipmentIds(){
        return drEquipmentIds;
    }

    public void setDrEquipmentIds(List<Integer> drEquipmentIds){
        this.drEquipmentIds = drEquipmentIds;
    }
}
