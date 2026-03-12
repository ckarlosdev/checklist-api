package com.ck.wi.model.dto;

import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class AttachmentDto implements Serializable {
    private Integer attachmentsId;
    private String family;
    private String number;
    private String name;
    private String manufacturing;
    private String model;
    private String year;
    private String purchaseDate;
    private String status;
    private String conditions;
    private String serialNumber;
    private String user;
}
