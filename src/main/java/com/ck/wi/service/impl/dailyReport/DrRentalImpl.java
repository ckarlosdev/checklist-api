package com.ck.wi.service.impl.dailyReport;

import com.ck.wi.model.dao.dailyReport.DrRentalDao;
import com.ck.wi.model.dto.dailyReport.creation.DrRentalCreateDto;
import com.ck.wi.model.entity.dailyReport.DrRental;
import com.ck.wi.service.dailyReport.IDrRental;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DrRentalImpl implements IDrRental {

    @Autowired
    private DrRentalDao drRentalDao;


    @Override
    public List<DrRentalCreateDto> getLastReportRentals(String jobNum){
        List<DrRental> rentals = drRentalDao.findRentalsFromLastReport(jobNum);

        return  rentals.stream()
                .map(this::toDto)
                .toList();
    }


    private DrRentalCreateDto toDto(DrRental rental){

        return DrRentalCreateDto.builder()
                .drRentalsId(rental.getDrRentalsId())
                .employeesId(rental.getEmployeesId())
                .equipmentType(rental.getEquipmentType())
                .equipmentName(rental.getEquipmentName())
                .company(rental.getCompany())
                .equipmentNumber(rental.getEquipmentNumber())
                .odometer(rental.getOdometer())
                .build();
    }

}
