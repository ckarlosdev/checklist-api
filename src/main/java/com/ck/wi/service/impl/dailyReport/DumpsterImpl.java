package com.ck.wi.service.impl.dailyReport;

import com.ck.wi.model.dao.dailyReport.DumpsterDao;
import com.ck.wi.model.dto.dailyReport.DumpsterSummaryDto;
import com.ck.wi.model.entity.dailyReport.Dumpster;
import com.ck.wi.service.dailyReport.IDumpster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DumpsterImpl implements IDumpster {

    @Autowired
    private DumpsterDao dumpsterDao;

    @Override
    public Dumpster findByDailyReportId(Integer dailyReportId){
        return dumpsterDao.findByDailyReportIdAndStatus(dailyReportId, "1");
    }

    @Override
    public List<Dumpster> findByDailyReportIds(List<Integer> dailyReportIds){
        return (List<Dumpster>) dumpsterDao.findByDailyReportIdIn(dailyReportIds);
    }


    @Override
    public DumpsterSummaryDto findSummaryByJobNumber(String jobNumber){
        return dumpsterDao.findSummaryByJobNumber(jobNumber);
    }

}
