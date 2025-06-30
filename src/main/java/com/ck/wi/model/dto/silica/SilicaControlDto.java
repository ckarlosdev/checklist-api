package com.ck.wi.model.dto.silica;

import com.ck.wi.model.entity.silica.Control;
import com.ck.wi.model.entity.silica.ControlsDescription;
import com.ck.wi.model.entity.silica.Silica;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class SilicaControlDto implements Serializable {
    private Integer silicaControlId;
    private ControlsDescription controlDescription;
    private String controlAnswer;
}
