package com.ck.wi.model.dto.request;

import java.util.List;

public class DailyReportRequest {
    private List<String> jobNumbers;

    public List<String> getJobNumbers(){
        return jobNumbers;
    }

    public void setJobNumbers(List<String> jobNumbers){
        this.jobNumbers = jobNumbers;
    }
}
