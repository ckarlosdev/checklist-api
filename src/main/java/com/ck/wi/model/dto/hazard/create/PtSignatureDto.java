package com.ck.wi.model.dto.hazard.create;

import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class PtSignatureDto implements Serializable {
    private Integer ptSignaturesId;
    private Integer employeesId;
    private byte[] imgData;
}
