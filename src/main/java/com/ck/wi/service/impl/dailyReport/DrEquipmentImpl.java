package com.ck.wi.service.impl.dailyReport;

import com.ck.wi.model.dao.EmployeeDao;
import com.ck.wi.model.dao.dailyReport.DrEquipmentDao;
import com.ck.wi.model.dto.dailyReport.creation.DrEquipmentCreateDto;
import com.ck.wi.model.entity.Employee;
import com.ck.wi.model.entity.Equipment;
import com.ck.wi.model.entity.dailyReport.DrEquipment;
import com.ck.wi.service.dailyReport.IDrEquipment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DrEquipmentImpl implements IDrEquipment {

    @Autowired
    private DrEquipmentDao drEquipmentDao;

    @Autowired
    private EmployeeDao employeeDao;

    @Override
    public List<DrEquipment> findByDailyReportId(Integer dailyReportId){
        return (List<DrEquipment>) drEquipmentDao.findByDailyReportIdAndStatus(dailyReportId, "1");
    }

    @Override
    public List<DrEquipment> findByDailyReportIds(List<Integer> dailyReportIds){
        return (List<DrEquipment>) drEquipmentDao.findByDailyReportIdIn(dailyReportIds);
    }

    @Override
    public List<Integer> findDrEquipmentIdsByDrId(Integer drId){
        return (List<Integer>) drEquipmentDao.findDrEquipmentIdsByDR(drId);
    }

    @Override
    public List<DrEquipmentCreateDto> getLastReportEquipments(String jobNum){
        List<DrEquipment> equipments = drEquipmentDao.findEquipmentsFromLastReport(jobNum);

        return equipments.stream()
                .map(this::toDto)
                .toList();
    }

    private DrEquipmentCreateDto toDto(DrEquipment equipment){
        Integer empId = null;
        if(equipment.getEmployeesId() != null && !equipment.getEmployeesId().equals("N/A")){
            Employee emp = employeeDao.findByEmployeeNumber(equipment.getEmployeesId());
            empId = emp.getEmployeesId();
        }

        return DrEquipmentCreateDto.builder()
                .drEquipmentsId(equipment.getEquipmentsId())
                .equipmentsId(equipment.getEquipmentsId())
                .employeesId(empId)
                .type(equipment.getType())
                .initialHour(equipment.getInitialHour())
                .newHour(equipment.getNewHour())
                .build();
    }
}
