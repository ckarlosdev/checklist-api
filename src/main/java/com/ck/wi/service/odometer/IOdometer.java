package com.ck.wi.service.odometer;


import com.ck.wi.model.dto.odometer.OdometerDto;
import com.ck.wi.model.dto.odometer.OdometerSaveDto;
import com.ck.wi.model.entity.odometer.Odometer;

import java.util.List;

public interface IOdometer {

    OdometerDto processAndSaveOdometer(OdometerDto odometer);

    void saveOdometerByEquipmentId(OdometerSaveDto odometerSaveDto);

    OdometerDto findByEquipmentsId(Integer equipmentsId);

    OdometerDto findOdometerById(Integer odometerId);

    List<Odometer> findAll();

}
