package com.ck.wi.controller.silica;

import com.ck.wi.model.dto.silica.ControlDto;
import com.ck.wi.model.entity.silica.Control;
import com.ck.wi.model.entity.silica.ControlsDescription;
import com.ck.wi.service.silica.IControl;
import com.ck.wi.service.silica.IControlsDescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = {
        "http://127.0.0.1:5500",
        "https://oleo-soft.com",
        "http://localhost:5173",
        "https://ckarlosdev.github.io"
})
@RestController
@RequestMapping("/api/v1")
public class ControlController {

    @Autowired
    private IControl controlService;

    @Autowired
    private IControlsDescription controlsDescriptionService;

    @GetMapping("controls")
    public List<ControlDto> getAllControls() {
        List<Control> controls = controlService.findAll();
        List<ControlDto> controlDtos = new ArrayList<>();

        controls.forEach(control -> {

            List<ControlsDescription> controlsDescriptions =
                    controlsDescriptionService.findByControl(control);

            ControlDto controlDto = ControlDto.builder()
                    .controlsId(control.getControlsId())
                    .controlGroup(control.getControlGroup())
                    .controlType(control.getControlType())
                    .typeDescription(control.getTypeDescription())
                    .descriptions(controlsDescriptions)
                    .build();

            controlDtos.add(controlDto);
        });

        return controlDtos;
    }
}
