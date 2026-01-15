package com.ck.wi.model.dto.hazard.create;

import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class PreTaskOptionCreateDto implements Serializable {
    private Integer pretasksOptionsId;
    private Integer pretasksCheckboxOptionsId;
    private String other;
}
