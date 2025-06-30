package com.ck.wi.model.dto.silica;

import com.ck.wi.model.entity.silica.Control;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ControlsDescriptionDto implements Serializable {
    private Integer controlsDescriptionsId;
    private Control control;
    private String controlName;
    private String componentType;

    public ControlsDescriptionDto( Control control, Integer controlsDescriptionsId, String controlName, String componentType) {
        this.controlsDescriptionsId = controlsDescriptionsId;
        this.controlName = controlName;
        this.componentType = componentType;
    }
}
