package com.ck.wi.service.silica;

import com.ck.wi.model.dto.silica.ControlDescriptionRequestDto;
import com.ck.wi.model.dto.silica.ControlsDescriptionDto;
import com.ck.wi.model.entity.silica.Control;
import com.ck.wi.model.entity.silica.ControlsDescription;

import java.util.List;
import java.util.Optional;

public interface IControlsDescription {
    Optional<ControlsDescription> findById(Integer id);

    List<ControlDescriptionRequestDto> findByControl(Control control);

    List<ControlDescriptionRequestDto> findByControlId(Integer controlId);
}
