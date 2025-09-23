package com.ck.wi.service.impl.odometer;

import com.ck.wi.model.dao.EquipmentDao;
import com.ck.wi.model.dao.odometer.OdometerDao;
import com.ck.wi.model.dao.odometer.OdometersHistoryDao;
import com.ck.wi.model.dto.odometer.OdometerDto;
import com.ck.wi.model.dto.odometer.OdometersHistoryDto;
import com.ck.wi.model.entity.Equipment;
import com.ck.wi.model.entity.odometer.Odometer;
import com.ck.wi.model.entity.odometer.OdometersHistory;
import com.ck.wi.service.odometer.IOdometer;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class OdometerImpl implements IOdometer {

    @Autowired
    private OdometerDao odometerDao;

    @Autowired
    private OdometersHistoryDao odometersHistoryDao;

    @Autowired
    private EquipmentDao equipmentDao;

    @Transactional
    @Override
    public OdometerDto processAndSaveOdometer(OdometerDto odometerDto){

        if(odometerDto == null){
            throw new IllegalArgumentException("odometer data must not be null");
        }

        Equipment equipment = equipmentDao.findById(odometerDto.getEquipmentsId())
                .orElseThrow(() -> new IllegalArgumentException("Equipment not found"));

        if (odometerDto.getOdometersId() != null) {
            odometersHistoryDao.invalidatePreviousRecords(odometerDto.getOdometersId());
        }

        ZonedDateTime today = ZonedDateTime.now(ZoneId.of("America/Chicago"));

        Odometer odometer;

        if(odometerDto.getOdometersId() != null && odometerDto.getOdometersId() != 0){
            // update data
            odometer = odometerDao.findById(odometerDto.getOdometersId())
                    .orElseThrow(() -> new IllegalArgumentException("Odometer data not found"));

            odometer.setEquipment(equipment);
            odometer.setOdometer(odometerDto.getOdometer());

        }else{
            // create a new register
            odometer = Odometer.builder()
                    .equipment(equipment)
                    .odometer(odometerDto.getOdometer())
                    .build();
        }

        Odometer odometerSaved = odometerDao.save(odometer);

        OdometersHistory odometersHistory = OdometersHistory.builder()
                .odometersId(odometerSaved.getOdometersId())
                .previousLecture(odometerDto.getOdometersHistory().getPreviousLecture())
                .newLecture(odometerDto.getOdometer())
                .reportedBy(odometerDto.getOdometersHistory().getReportedBy())
                .reportedDate(odometerDto.getOdometersHistory().getReportedDate())
                .createdBy(odometerDto.getOdometersHistory().getCreatedBy())
                .createdDate(today)
                .odometersStatus("1")
                .build();

        OdometersHistory odometersHistorySaved = odometersHistoryDao.save(odometersHistory);


        OdometersHistoryDto odometersHistorySavedDto = OdometersHistoryDto.builder()
                .odometersHistoryId(odometersHistorySaved.getOdometersHistoryId())
                .odometersId(odometersHistorySaved.getOdometersId())
                .previousLecture(odometersHistorySaved.getPreviousLecture())
                .newLecture(odometersHistorySaved.getNewLecture())
                .reportedBy(odometersHistorySaved.getReportedBy())
                .reportedDate(odometersHistorySaved.getReportedDate())
                .createdBy(odometersHistorySaved.getCreatedBy())
                .build();

        return OdometerDto.builder()
                .odometersId(odometerSaved.getOdometersId())
                .equipmentsId(equipment.getEquipmentsId())
                .odometer(odometerSaved.getOdometer())
                .odometersHistory(odometersHistorySavedDto)
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public OdometerDto findByEquipmentsId(Integer equipmentsId){

        Equipment equipment = equipmentDao.findById(equipmentsId).orElse(null);

        Odometer odometer = odometerDao.findByEquipment(equipment).orElse(null);


        if(odometer != null){
            OdometersHistory odometersHistory = odometersHistoryDao.findByOdometersIdAndOdometersStatus(odometer.getOdometersId(), "1");

            OdometersHistoryDto odometersHistoryDto = OdometersHistoryDto.builder()
                    .odometersHistoryId(odometersHistory.getOdometersHistoryId())
                    .odometersId(odometersHistory.getOdometersId())
                    .previousLecture(odometersHistory.getPreviousLecture())
                    .newLecture(odometersHistory.getNewLecture())
                    .reportedBy(odometersHistory.getReportedBy())
                    .reportedDate(odometersHistory.getReportedDate())
                    .createdBy(odometersHistory.getCreatedBy())
                    .build();

            return OdometerDto.builder()
                    .odometersId(odometer.getOdometersId())
                    .equipmentsId(odometer.getEquipment().getEquipmentsId())
                    .odometer(odometer.getOdometer())
                    .odometersHistory(odometersHistoryDto)
                    .build();
        }else{
            return null;
        }

    }

    @Transactional(readOnly = true)
    @Override
    public OdometerDto findOdometerById(Integer odometerId){

        Odometer odometer = odometerDao.findById(odometerId).orElse(null);

        if(odometer != null){
            OdometersHistory odometersHistory = odometersHistoryDao.findByOdometersIdAndOdometersStatus(odometer.getOdometersId(), "1");

            OdometersHistoryDto odometersHistoryDto = OdometersHistoryDto.builder()
                    .odometersHistoryId(odometersHistory.getOdometersHistoryId())
                    .odometersId(odometersHistory.getOdometersId())
                    .previousLecture(odometersHistory.getPreviousLecture())
                    .newLecture(odometersHistory.getNewLecture())
                    .reportedBy(odometersHistory.getReportedBy())
                    .reportedDate(odometersHistory.getReportedDate())
                    .createdBy(odometersHistory.getCreatedBy())
                    .build();

            return OdometerDto.builder()
                    .odometersId(odometer.getOdometersId())
                    .equipmentsId(odometer.getEquipment().getEquipmentsId())
                    .odometer(odometer.getOdometer())
                    .odometersHistory(odometersHistoryDto)
                    .build();
        }else{
            return null;
        }

    }

    @Transactional(readOnly = true)
    @Override
    public List<Odometer> findAll(){
        return (List<Odometer>) odometerDao.findAll();
    }
}
