package com.ck.wi.service.dailyReport;

import com.ck.wi.model.dto.dailyReport.PhotoCreateDto;
import com.ck.wi.model.dto.dailyReport.PhotoDto;
import com.ck.wi.model.entity.dailyReport.Photo;

import java.util.List;

public interface IPhoto {
    List<Photo> findByDailyReportId(Integer dailyReportId);

    List<Photo> findByDailyReportIds(List<Integer> dailyReportIds);

    void savePhotos(List<PhotoCreateDto> photos, Integer dailyReportId);

    void updatePhotos(List<PhotoCreateDto> photos, Integer dailyReportId);

    List<Photo> findByTypeAndReport(String type, Integer dailyReportId);

}
