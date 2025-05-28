package com.ck.wi.service.impl.dailyReport;

import com.ck.wi.model.dao.dailyReport.PhotoDao;
import com.ck.wi.model.entity.dailyReport.Photo;
import com.ck.wi.service.dailyReport.IPhoto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhotoImpl implements IPhoto {

    @Autowired
    private PhotoDao photoDao;

    @Override
    public List<Photo> findByDailyReportId(Integer dailyReportId){
        return (List<Photo>) photoDao.findByDailyReportIdAndStatus(dailyReportId, "1");
    }
}
