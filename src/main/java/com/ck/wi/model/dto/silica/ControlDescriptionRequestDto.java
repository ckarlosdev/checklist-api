package com.ck.wi.model.dto.silica;

import com.ck.wi.model.entity.silica.Control;
import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@ToString
@Builder
public class ControlDescriptionRequestDto implements Serializable {

    private Integer controlsDescriptionsId;
    private Integer controlsId;
    private String controlName;
    private String componentType;

    public ControlDescriptionRequestDto(  Integer controlsDescriptionsId, Integer controlsId, String controlName, String componentType) {
        this.controlsDescriptionsId = controlsDescriptionsId;
        this.controlsId = controlsId;
        this.controlName = controlName;
        this.componentType = componentType;
    }
}
