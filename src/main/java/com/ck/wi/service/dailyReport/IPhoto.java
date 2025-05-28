package com.ck.wi.service.dailyReport;

import com.ck.wi.model.entity.dailyReport.Photo;

import java.util.List;

public interface IPhoto {
    List<Photo> findByDailyReportId(Integer id);
}
