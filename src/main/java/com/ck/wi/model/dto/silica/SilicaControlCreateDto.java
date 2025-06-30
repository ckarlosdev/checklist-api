package com.ck.wi.model.dto.silica;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class SilicaControlCreateDto {
    private Integer silicaControlId;
    private Integer controlDescriptionId;
    private String controlAnswer;
}
