package com.ck.wi.model.dto.request;

import java.util.List;

public class DumpsterRequest {
    private List<Integer> dailyReportIds;

    public List<Integer> getDailyReportIds(){
        return dailyReportIds;
    }

    public void setDailyReportIds(List<Integer> dailyReportIds){
        this.dailyReportIds = dailyReportIds;
    }
}
