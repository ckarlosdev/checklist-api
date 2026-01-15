package com.ck.wi.service.impl.dailyReport;

import com.ck.wi.model.dao.EmployeeDao;
import com.ck.wi.model.dao.dailyReport.DrEmployeeDao;
import com.ck.wi.model.dto.dailyReport.DrEmployeeHoursDto;
import com.ck.wi.model.dto.dailyReport.creation.DrEmployeeCreateDto;
import com.ck.wi.model.dto.dailyReport.creation.DrEquipmentCreateDto;
import com.ck.wi.model.dto.dailyReport.creation.DrToolCreateDto;
import com.ck.wi.model.entity.Employee;
import com.ck.wi.model.entity.dailyReport.DrEmployee;
import com.ck.wi.service.dailyReport.IDrEmployee;
import org.hibernate.sql.ast.tree.expression.Over;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DrEmployeeImpl implements IDrEmployee {
    @Autowired
    private DrEmployeeDao drEmployeeDao;

    @Autowired
    private EmployeeDao employeeDao;

    @Override
    public List<DrEmployee> findByDailyReportId(Integer dailyReportId){
        return (List<DrEmployee>) drEmployeeDao.findByDailyReportIdAndStatus(dailyReportId, "1");
    }

    @Override
    public List<DrEmployee> findByDailyReportIds(List<Integer> dailyReportIds){
        return (List<DrEmployee>) drEmployeeDao.findByDailyReportIdIn(dailyReportIds);
    }

    @Override
    public List<DrEmployeeHoursDto> findHoursByJobNumber(String jobNumber){
        return  (List<DrEmployeeHoursDto>) drEmployeeDao.findHoursByNumber(jobNumber);
    }

    @Override
    public Double findTotalHours(String jobNumber, LocalDate date){
        return drEmployeeDao.findTotalHourLessADate(jobNumber, date);
    }

    @Override
    public List<DrEmployeeCreateDto> getLastReportEmployees(String jobNum){
        List<DrEmployee> employees = drEmployeeDao.findEmployeesFromLastReport(jobNum);

        return employees.stream()
                .map(this::toDto)
                .toList();
    }

    private DrEmployeeCreateDto toDto(DrEmployee employee){
        Employee emp = employeeDao.findByEmployeeNumber(employee.getEmployeesId());

        return DrEmployeeCreateDto.builder()
                .drEmployeesId(employee.getDrEmployeesId())
                .employeesId(emp.getEmployeesId())
                .inHour(employee.getInHour())
                .outHour(employee.getOutHour())
                .lunch(employee.getLunch())
                .ppe(employee.getPpe())
                .comment(employee.getComment())
                .build();
    }
}
