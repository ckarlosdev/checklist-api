package com.ck.wi.model.dao.silica;

import com.ck.wi.model.dto.dailyReport.DailyReportSummaryDto;
import com.ck.wi.model.dto.silica.ControlDescriptionRequestDto;
import com.ck.wi.model.entity.silica.Control;
import com.ck.wi.model.entity.silica.ControlsDescription;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ControlDescriptionDao extends CrudRepository<ControlsDescription, Integer> {
    List<ControlDescriptionRequestDto> findByControl(Control control);

    @Query(
            value = "select  controls_descriptions_id as  controlsDescriptionsId, \n " +
                    "controls_id as controlsId, \n" +
                    "control_name as controlName, \n" +
                    "component_type as componentType from controls_descriptions where controls_id = :controlsId ",
            nativeQuery = true
    )
    List<ControlDescriptionRequestDto> findDescriptionsByControlId(@Param("controlsId") Integer controlsId);
}
