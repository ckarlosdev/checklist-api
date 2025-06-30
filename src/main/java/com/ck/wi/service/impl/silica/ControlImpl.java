package com.ck.wi.service.impl.silica;

import com.ck.wi.model.dao.silica.ControlDao;
import com.ck.wi.model.dto.silica.ControlDto;
import com.ck.wi.model.dto.silica.ControlsDescriptionDto;
import com.ck.wi.model.entity.silica.Control;
import com.ck.wi.service.silica.IControl;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ControlImpl implements IControl {

    @Autowired
    private ControlDao controlDao;

    @Transactional
    public List<Control> findAll(){
        return (List<Control>) controlDao.findAll();
    }

//    @Transactional
//    public List<ControlDto> getAllWithDescriptions(){
//        List<Control> controls = (List<Control>) controlDao.findAll();
//
//        return controls.stream()
//                .map(control -> {
//                    ControlDto.builder()
//                            .controlsId(control.getControlsId())
//                            .controlGroup(control.getControlGroup())
//                            .controlType(control.getControlType())
//                            .typeDescription(control.getTypeDescription())
//                            .descriptions(null)
//                            .build()
//                })
//                .collect(Collectors.toList());
//    }

//    @Transactional
//    public List<ControlDto> getAllWithDescriptions(){
//        List<Control> controls = (List<Control>) controlDao.findAll();
//
//        return controls.stream()
//                .map(control -> {
//                    // For each Control, convert its associated descriptions to DTOs
//                    List<ControlsDescriptionDto> descriptionDTOs = null;
////                    List<ControlsDescriptionDto> descriptionDTOs = control.getDescriptions().stream()
////                            .map(desc ->
////                                    new ControlsDescriptionDto(
////                                            desc.getControl(), // Assuming ControlDto needs the parent Control entity/ID, review your ControlsDescriptionDto constructor
////                                            desc.getControlsDescriptionsId(),
////                                            desc.getControlName(),
////                                            desc.getComponentType()))
////                            .collect(Collectors.toList());
//
//                    // Then, create a new ControlDto with the current Control's data and its descriptions
//                    return new ControlDto(
//                            control.getControlsId(),
//                            control.getControlGroup(),
//                            control.getControlType(),
//                            control.getTypeDescription(),
//                            descriptionDTOs);
//                })
//                .collect(Collectors.toList());
//    }

//    @Transactional
//    public Optional<ControlDto> getControlWithDescriptions(Integer id){
//        Optional<Control> controlOptional = controlDao.findById(id);
//
//        return controlOptional.map(control -> {
//            List<ControlsDescriptionDto> descriptionDTOs = control.getDescriptions().stream()
//                    .map(desc ->
//                            new ControlsDescriptionDto(
//                                    desc.getControl(),
//                                    desc.getControlsDescriptionsId(),
//                                    desc.getControlName(),
//                                    desc.getComponentType()))
//                    .collect(Collectors.toList());
//
//            // Crea y retorna el ControlDTO con las descripciones anidadas
//            return new ControlDto(control.getControlsId(),
//                    control.getControlGroup(),
//                    control.getControlType(),
//                    control.getTypeDescription(),
//                    descriptionDTOs);
//        });
//    }

}
