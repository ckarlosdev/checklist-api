package com.ck.wi.service.dailyReport;

import com.ck.wi.model.dto.dailyReport.DailyReportGralDto;
import com.ck.wi.model.dto.dailyReport.DailyReportSummaryDto;
import com.ck.wi.model.dto.dailyReport.creation.DailyReportCreateDto;
import com.ck.wi.model.entity.dailyReport.DailyReport;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface IDailyReport {
    DailyReportCreateDto update(DailyReportCreateDto dailyReportCreateDto);

    DailyReportCreateDto save(DailyReportCreateDto dailyReportCreateDto, Integer jobsId);

    DailyReportCreateDto findByDailyReportID(Integer dailyReportId);

    List<DailyReport> findByNumber(String number);

    List<DailyReport> findByNumbers(List<String> numbers);

    DailyReport findById(Integer id);

    DailyReport findByNumberAndDate(String number, LocalDate date);

    List<DailyReport> findAll();

    List<DailyReportSummaryDto> findSummaryByJobNumber(String jobNumber);

    Integer findTotalDaysByJobNumber(String jobNumber, LocalDate date);

    @Transactional
    List<DailyReportGralDto> getDrGral(String jobNumber);
}
