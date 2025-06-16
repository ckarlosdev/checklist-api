package com.ck.wi.model.dto.hazard;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class PretasksCheckboxOptionDto implements Serializable {

    private Integer pretasksCheckboxOptionsId;
    private String name;
    private String Type;
    private String description;
//    private String createdBy;
//    private String createdDate;
//    private String updatedBy;
//    private String updatedDate;
//    private String status;
}
