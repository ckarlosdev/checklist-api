package com.ck.wi.service.impl.dailyReport;

import com.ck.wi.model.dao.dailyReport.DrEmployeeDao;
import com.ck.wi.model.dto.dailyReport.DrEmployeeHoursDto;
import com.ck.wi.model.entity.dailyReport.DrEmployee;
import com.ck.wi.service.dailyReport.IDrEmployee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DrEmployeeImpl implements IDrEmployee {
    @Autowired
    private DrEmployeeDao drEmployeeDao;

    @Override
    public List<DrEmployee> findByDailyReportId(Integer dailyReportId){
        return (List<DrEmployee>) drEmployeeDao.findByDailyReportIdAndStatus(dailyReportId, "1");
    }

    @Override
    public List<DrEmployee> findByDailyReportIds(List<Integer> dailyReportIds){
        return (List<DrEmployee>) drEmployeeDao.findByDailyReportIdIn(dailyReportIds);
    }

    @Override
    public List<DrEmployeeHoursDto> findHoursByJobNumber(String jobNumber){
        return  (List<DrEmployeeHoursDto>) drEmployeeDao.findHoursByNumber(jobNumber);
    }
}
