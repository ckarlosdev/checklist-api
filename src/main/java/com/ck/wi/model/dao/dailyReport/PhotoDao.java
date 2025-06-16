package com.ck.wi.model.dao.dailyReport;

import com.ck.wi.model.entity.dailyReport.Photo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotoDao extends CrudRepository<Photo, Integer> {
    List<Photo> findByDailyReportIdAndStatus(Integer dailyReportId, String status);

    List<Photo> findByDailyReportIdIn(List<Integer> dailyReportIds);
}
