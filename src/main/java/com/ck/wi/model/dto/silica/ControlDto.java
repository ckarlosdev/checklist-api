package com.ck.wi.model.dto.silica;

import com.ck.wi.model.entity.silica.ControlsDescription;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@ToString
@Builder
public class ControlDto implements Serializable {
    private Integer controlsId;
    private String controlGroup;
    private String controlType;
    private String typeDescription;
    private List<ControlsDescription> descriptions;

    public ControlDto(
            Integer controlsId,
            String controlGroup,
            String controlType,
            String typeDescription,
            List<ControlsDescription> descriptions
    ){
        this.controlsId = controlsId;
        this.controlGroup = controlGroup;
        this.controlType = controlType;
        this.typeDescription = typeDescription;
        this.descriptions = descriptions;
    }
}
