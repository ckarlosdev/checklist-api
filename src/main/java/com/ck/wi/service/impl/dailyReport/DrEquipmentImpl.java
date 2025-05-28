package com.ck.wi.service.impl.dailyReport;

import com.ck.wi.model.dao.dailyReport.DrEquipmentDao;
import com.ck.wi.model.entity.dailyReport.DrEquipment;
import com.ck.wi.service.dailyReport.IDrEquipment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DrEquipmentImpl implements IDrEquipment {

    @Autowired
    public DrEquipmentDao drEquipmentDao;

    @Override
    public List<DrEquipment> findByDailyReportId(Integer dailyReportId){
        return (List<DrEquipment>) drEquipmentDao.findByDailyReportIdAndStatus(dailyReportId, "1");
    }
}
