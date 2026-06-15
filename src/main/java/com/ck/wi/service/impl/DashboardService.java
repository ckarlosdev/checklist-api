package com.ck.wi.service.impl;

import com.ck.wi.model.dao.assignment.AssignmentDao;
import com.ck.wi.model.dao.dailyReport.DailyReportDao;
import com.ck.wi.model.dto.dashboard.CalendarEventDTO;
import com.ck.wi.model.dto.dashboard.TimelineDataDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    @Autowired
    private AssignmentDao assignmentDao;

    @Autowired
    private DailyReportDao dailyReportDao;

    @Transactional(readOnly = true)
    public List<CalendarEventDTO> getEvents(String startStr, String endStr){
        LocalDate start = LocalDate.parse(startStr);
        LocalDate end = LocalDate.parse(endStr);
        List<Object[]> results = assignmentDao.findRawCalendarData(start, end);

        return results.stream().map(row -> {

            return new CalendarEventDTO(
                    ((Number) row[4]).longValue(),           // daily_report_id (Es la col 4 en tu SQL)
                    ((Number) row[0]).longValue(),           // jobs_id (Es la col 0)
                    ((java.sql.Date) row[1]).toLocalDate(),  // date (Es la col 1)
                    ((java.sql.Time) row[2]).toLocalTime(),  // start (Es la col 2)
                    ((java.sql.Time) row[3]).toLocalTime(),  // end (Es la col 3)
                    new CalendarEventDTO.ReportStatusDTO(
                            ((Number) row[4]).longValue(),       // daily
                            row[5] != null ? ((Number) row[5]).longValue() : null, // pretask (H)
                            row[7] != null ? ((Number) row[7]).longValue() : null, // silica (S)
                            row[6] != null ? ((Number) row[6]).longValue() : null, // checklist (C)
                            row[8] != null ? ((Number) row[8]).longValue() : null  // demo (M)
                    )
            );
        }).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TimelineDataDto> getTimelineDate(String jobNumber){
        List<Object[]> results = dailyReportDao.findDateByJobNumber(jobNumber);

        return results.stream().map(row -> {
            return new TimelineDataDto(
                    row[0] != null ? ((Number) row[0]).intValue() : null, // drId
                    row[1] != null ? row[1].toString() : null,             // drDate
                    row[2] != null ? (String) row[2] : null                // foreman
            );
        }).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CalendarEventDTO> getEventsById(Integer jobId){

        List<Object[]> results = assignmentDao.findRawCalendarDataById(jobId);

        return results.stream().map(row -> {

            return new CalendarEventDTO(
                    ((Number) row[4]).longValue(),           // daily_report_id (Es la col 4 en tu SQL)
                    ((Number) row[0]).longValue(),           // jobs_id (Es la col 0)
                    ((java.sql.Date) row[1]).toLocalDate(),  // date (Es la col 1)
                    ((java.sql.Time) row[2]).toLocalTime(),  // start (Es la col 2)
                    ((java.sql.Time) row[3]).toLocalTime(),  // end (Es la col 3)
                    new CalendarEventDTO.ReportStatusDTO(
                            ((Number) row[4]).longValue(),       // daily
                            row[5] != null ? ((Number) row[5]).longValue() : null, // pretask (H)
                            row[7] != null ? ((Number) row[7]).longValue() : null, // silica (S)
                            row[6] != null ? ((Number) row[6]).longValue() : null, // checklist (C)
                            row[8] != null ? ((Number) row[8]).longValue() : null  // demo (M)
                    )
            );
        }).collect(Collectors.toList());
    }
}
