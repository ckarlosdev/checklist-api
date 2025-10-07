package com.ck.wi.service.impl.maintenance;

import com.ck.wi.model.dao.maintenance.MaintenanceDao;
import com.ck.wi.model.dao.maintenance.MaintenanceHistoryDao;
import com.ck.wi.model.dto.demoChecklist.DemoChecklistsItemDto;
import com.ck.wi.model.dto.maintenance.MaintenanceHistoryCreateDto;
import com.ck.wi.model.dto.maintenance.MaintenanceHistoryDto;
import com.ck.wi.model.entity.maintenance.Maintenance;
import com.ck.wi.model.entity.maintenance.MaintenanceHistory;
import com.ck.wi.service.maintenance.IMaintenanceHistory;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MaintenanceHistoryImpl implements IMaintenanceHistory {

    @Autowired
    private MaintenanceHistoryDao maintenanceHistoryDao;

    @Autowired
    private MaintenanceDao maintenanceDao;

    @Transactional
    @Override
    public MaintenanceHistoryDto save(MaintenanceHistoryCreateDto maintenanceHistoryCreateDto){

        if(maintenanceHistoryCreateDto == null){
            throw new IllegalArgumentException("body request null");
        }
        LocalDate today = LocalDate.now();
        MaintenanceHistory maintenanceHistory = null;
        MaintenanceHistoryDto maintenanceHistoryDto = null;

        if(maintenanceHistoryCreateDto.getMaintenancesHistoryId() == null || maintenanceHistoryCreateDto.getMaintenancesHistoryId() == 0){
            // new register

            Maintenance maintenance = maintenanceDao.findById(maintenanceHistoryCreateDto.getMaintenancesId()).orElseThrow(null);

            if(maintenance != null){
                maintenanceHistoryDao.invalidatePreviousRecordsByEquipmentId(maintenance.getEquipmentsId());
            }

            maintenanceHistory = MaintenanceHistory.builder()
                    .maintenancesId(maintenanceHistoryCreateDto.getMaintenancesId())
                    .maintenanceDate(maintenanceHistoryCreateDto.getMaintenanceDate())
                    .employee(maintenanceHistoryCreateDto.getEmployee())
                    .odometer(maintenanceHistoryCreateDto.getOdometer())
                    .comments(maintenanceHistoryCreateDto.getComments())
                    .createdBy(maintenanceHistoryCreateDto.getCreatedBy())
                    .createdDate(today)
                    .updatedBy(maintenanceHistoryCreateDto.getCreatedBy())
                    .updatedDate(today)
                    .meStatus("1")
                    .build();

            MaintenanceHistory maintenanceHistorySaved = maintenanceHistoryDao.save(maintenanceHistory);

            maintenanceHistoryDto = MaintenanceHistoryDto.builder()
                    .maintenanceDate(maintenanceHistorySaved.getMaintenanceDate())
                    .employee(maintenanceHistorySaved.getEmployee())
                    .odometer(maintenanceHistorySaved.getOdometer())
                    .comments(maintenanceHistorySaved.getComments())
                    .createdBy(maintenanceHistorySaved.getCreatedBy())
                    .build();

        }//update

        return maintenanceHistoryDto;
    }

    @Transactional
    @Override
    public MaintenanceHistoryDto getByMaintenanceId(Integer maintenanceId){

         MaintenanceHistory maintenanceHistory = maintenanceHistoryDao.findByMaintenancesIdAndMeStatus(maintenanceId, "1");

         MaintenanceHistoryDto maintenanceHistoryDto =  MaintenanceHistoryDto.builder()
                 .maintenanceDate(maintenanceHistory.getMaintenanceDate())
                 .employee(maintenanceHistory.getEmployee())
                 .odometer(maintenanceHistory.getOdometer())
                 .comments(maintenanceHistory.getComments())
                 .createdBy(maintenanceHistory.getCreatedBy())
                 .build();

        return maintenanceHistoryDto;
    }

    @Transactional
    @Override
    public List<MaintenanceHistoryDto> getMaintenancesById(Integer maintenanceId){
        List<MaintenanceHistory> maintenanceHistoryList = maintenanceHistoryDao.findByMaintenancesIdOrderByMaintenanceDateDesc(maintenanceId);

        return maintenanceHistoryList.stream()
                .map(maintenanceHistory -> MaintenanceHistoryDto.builder()
                   .maintenanceDate(maintenanceHistory.getMaintenanceDate())
                   .employee(maintenanceHistory.getEmployee())
                   .odometer(maintenanceHistory.getOdometer())
                   .comments(maintenanceHistory.getComments())
                   .createdBy(maintenanceHistory.getCreatedBy())
                   .build()
        ).collect(Collectors.toList());
    }

}
