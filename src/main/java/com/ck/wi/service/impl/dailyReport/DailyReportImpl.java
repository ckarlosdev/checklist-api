package com.ck.wi.service.impl.dailyReport;

import com.ck.wi.model.dao.dailyReport.DailyReportDao;
import com.ck.wi.model.entity.dailyReport.DailyReport;
import com.ck.wi.service.dailyReport.IDailyReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DailyReportImpl implements IDailyReport {

    @Autowired
    private DailyReportDao dailyReportDao;

    @Override
    public List<DailyReport> findByNumber(String number){
        return (List<DailyReport>) dailyReportDao.findByNumberAndStatus(number, "1");
    }

    @Override
    public List<DailyReport> findByNumbers(List<String> numbers){
        return (List<DailyReport>) dailyReportDao.findByNumberIn(numbers);
    }

    @Override
    public DailyReport findById(Integer id){
        return dailyReportDao.findById(id).orElse(null);
    }

    @Override
    public DailyReport findByNumberAndDate(String number, LocalDate date){
        return dailyReportDao.findByNumberAndDate(number, date);
    }

    @Override
    public List<DailyReport> findAll() {
        return (List<DailyReport>) dailyReportDao.findAll();
    }
}
