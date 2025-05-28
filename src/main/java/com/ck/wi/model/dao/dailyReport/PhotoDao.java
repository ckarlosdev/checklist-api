package com.ck.wi.model.dao.dailyReport;

import com.ck.wi.model.entity.dailyReport.Photo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PhotoDao extends CrudRepository<Photo, Integer> {
    List<Photo> findByDailyReportIdAndStatus(Integer dailyReportId, String status);
}
