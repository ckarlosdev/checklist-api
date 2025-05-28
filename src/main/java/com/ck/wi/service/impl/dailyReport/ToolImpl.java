package com.ck.wi.service.impl.dailyReport;

import com.ck.wi.model.dao.dailyReport.ToolDao;
import com.ck.wi.model.entity.dailyReport.Tool;
import com.ck.wi.service.dailyReport.ITool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToolImpl implements ITool {

    @Autowired
    private ToolDao toolDao;

    @Override
    public List<Tool> findByDailyReportId(Integer dailyReportId){
        return (List<Tool>) toolDao.findByDailyReportIdAndStatus(dailyReportId, "1");

    }
}
