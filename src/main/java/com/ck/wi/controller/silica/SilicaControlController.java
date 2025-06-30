package com.ck.wi.controller.silica;

import com.ck.wi.model.dto.silica.SilicaControlDto;
import com.ck.wi.model.entity.silica.SilicaControl;
import com.ck.wi.service.silica.IControlsDescription;
import com.ck.wi.service.silica.ISilicaControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {
        "http://127.0.0.1:5500",
        "https://oleo-soft.com",
        "http://localhost:5173",
        "https://ckarlosdev.github.io"
})
@RestController
@RequestMapping("/api/v1")
public class SilicaControlController {

    @Autowired
    private ISilicaControl silicaControlService;

    @GetMapping("/constrolsAssigned/{id}")
    public SilicaControlDto getControlsAssignedById(@PathVariable Integer id){
        SilicaControl silicaControl = silicaControlService.findById(id);

        SilicaControlDto silicaControlDto =
                new SilicaControlDto(
                        silicaControl.getSilicaControlsId(),
                        silicaControl.getControlsDescription(),
                        silicaControl.getControlAnswer());

        return silicaControlDto;
    }
}
