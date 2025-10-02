package com.ck.wi.service.impl.maintenance;

import com.ck.wi.model.dao.maintenance.MaintenanceDao;
import com.ck.wi.model.dao.maintenance.MaintenanceHistoryDao;
import com.ck.wi.model.dto.maintenance.MaintenanceDto;
import com.ck.wi.model.dto.maintenance.MaintenanceHistoryDto;
import com.ck.wi.model.entity.maintenance.Maintenance;
import com.ck.wi.model.entity.maintenance.MaintenanceHistory;
import com.ck.wi.service.maintenance.IMaintenance;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MaintenanceImpl implements IMaintenance {

    @Autowired
    private MaintenanceDao maintenanceDao;

    @Autowired
    private MaintenanceHistoryDao maintenanceHistoryDao;

    @Transactional
    @Override
    public Maintenance save(MaintenanceDto maintenanceDto){

        if(maintenanceDto.getEquipmentsId() == null || maintenanceDto.getEquipmentsId() == 0){
            throw new IllegalArgumentException("Equipment ID must not be null");
        }

        LocalDate today = LocalDate.now();
        Maintenance maintenance;

        if(maintenanceDto.getMaintenancesId() == null || maintenanceDto.getMaintenancesId() == 0){
            maintenanceDao.invalidatePreviousRecords(maintenanceDto.getEquipmentsId());
            maintenance = Maintenance.builder()
                    .equipmentsId(maintenanceDto.getEquipmentsId())
                    .maintenanceType(maintenanceDto.getMaintenanceType())
                    .measureType(maintenanceDto.getMeasureType())
                    .frecuency(maintenanceDto.getFrecuency())
                    .description(maintenanceDto.getDescription())
                    .createdBy(maintenanceDto.getCreatedBy())
                    .createdDate(today)
                    .updatedBy(maintenanceDto.getCreatedBy())
                    .updatedDate(today)
                    .maintenancesStatus("1")
                    .build();
        }else{
            maintenance = maintenanceDao.findById(maintenanceDto.getMaintenancesId())
                    .orElseThrow(() -> new IllegalArgumentException("Maintenance not found to update"));

            maintenance.setEquipmentsId(maintenanceDto.getEquipmentsId());
            maintenance.setMaintenanceType(maintenanceDto.getMaintenanceType());
            maintenance.setMeasureType(maintenanceDto.getMeasureType());
            maintenance.setFrecuency(maintenanceDto.getFrecuency());
            maintenance.setDescription(maintenanceDto.getDescription());
            maintenance.setUpdatedBy(maintenanceDto.getCreatedBy());
            maintenance.setUpdatedDate(today);


        }

        Maintenance maintenanceSaved = maintenanceDao.save(maintenance);
        return maintenanceSaved;
//        MaintenanceHistory maintenanceHistorySaved = null;
//        MaintenanceHistoryDto maintenanceHistoryDto = null;

//        if(maintenanceSaved != null && maintenanceDto.getMaintenanceHistoryDto() != null){
//                maintenanceHistoryDao.invalidatePreviousRecordsByEquipmentId(maintenanceSaved.getEquipmentsId());
//
//            MaintenanceHistory maintenanceHistory = MaintenanceHistory.builder()
//                    .maintenancesId(maintenanceSaved.getMaintenancesId())
//                    .maintenanceDate(maintenanceDto.getMaintenanceHistoryDto().getMaintenanceDate())
//                    .employee(maintenanceDto.getMaintenanceHistoryDto().getEmployee())
//                    .odometer(maintenanceDto.getMaintenanceHistoryDto().getOdometer())
//                    .comments(maintenanceDto.getMaintenanceHistoryDto().getComments())
//                    .createdBy(maintenanceDto.getMaintenanceHistoryDto().getCreatedBy())
//                    .createdDate(today)
//                    .updatedBy(maintenanceDto.getMaintenanceHistoryDto().getCreatedBy())
//                    .updatedDate(today)
//                    .meStatus("1")
//                    .build();
//
//            maintenanceHistorySaved = maintenanceHistoryDao.save(maintenanceHistory);

//            maintenanceHistoryDto = MaintenanceHistoryDto.builder()
//                    .maintenanceDate(maintenanceHistorySaved.getMaintenanceDate())
//                    .employee(maintenanceHistorySaved.getEmployee())
//                    .odometer(maintenanceHistorySaved.getOdometer())
//                    .comments(maintenanceHistorySaved.getComments())
//                    .createdBy(maintenanceHistorySaved.getCreatedBy())
//                    .build();
//        }

        // build DTO to return it




//        return MaintenanceDto.builder()
//                .maintenancesId(maintenanceSaved.getMaintenancesId())
//                .equipmentsId(maintenanceSaved.getEquipmentsId())
//                .maintenanceType(maintenanceSaved.getMaintenanceType())
//                .frecuency(maintenanceSaved.getFrecuency())
//                .description(maintenanceSaved.getDescription())
//                .createdBy(maintenanceSaved.getCreatedBy())
////                .maintenanceHistoryDto(maintenanceHistoryDto)
//                .build();
    }

    @Transactional
    @Override
    public List<MaintenanceDto> findAll(){

        List<Maintenance> maintenanceList = (List<Maintenance>) maintenanceDao.findByMaintenancesStatus("1");

        return maintenanceList.stream().map(maintenance -> {

            MaintenanceHistory maintenanceHistory =
                    maintenanceHistoryDao.findByMaintenancesIdAndMeStatus(maintenance.getMaintenancesId(), "1");

            MaintenanceHistoryDto maintenanceHistoryDto = null;

            if (maintenanceHistory != null) {
                maintenanceHistoryDto =
                        MaintenanceHistoryDto.builder()
                                .maintenanceDate(maintenanceHistory.getMaintenanceDate())
                                .employee(maintenanceHistory.getEmployee())
                                .odometer(maintenanceHistory.getOdometer())
                                .comments(maintenanceHistory.getComments())
                                .createdBy(maintenanceHistory.getCreatedBy())
                                .build();
            }

            return MaintenanceDto.builder()
                    .maintenancesId(maintenance.getMaintenancesId())
                    .equipmentsId(maintenance.getEquipmentsId())
                    .maintenanceType(maintenance.getMaintenanceType())
                    .measureType(maintenance.getMeasureType())
                    .frecuency(maintenance.getFrecuency())
                    .description(maintenance.getDescription())
                    .createdBy(maintenance.getCreatedBy())
                    .maintenanceHistoryDto(maintenanceHistoryDto)
                    .build();

        }).toList();

    }
}
