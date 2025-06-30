package com.ck.wi.service.silica;

import com.ck.wi.model.dto.silica.ControlDto;
import com.ck.wi.model.entity.silica.Control;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public interface IControl {

    List<Control> findAll();

//    Optional<ControlDto> getControlWithDescriptions(Integer id);

//    List<ControlDto> getAllWithDescriptions();
}
