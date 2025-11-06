package com.ck.wi.service.dailyReport;

import com.ck.wi.model.dto.dailyReport.DailyReportGralDto;
import com.ck.wi.model.dto.dailyReport.DailyReportSummaryDto;
import com.ck.wi.model.entity.dailyReport.DailyReport;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface IDailyReport {
    List<DailyReport> findByNumber(String number);

    List<DailyReport> findByNumbers(List<String> numbers);

    DailyReport findById(Integer id);

    DailyReport findByNumberAndDate(String number, LocalDate date);

    List<DailyReport> findAll();

    List<DailyReportSummaryDto> findSummaryByJobNumber(String jobNumber);

    @Transactional
    List<DailyReportGralDto> getDrGral(String jobNumber);
}
