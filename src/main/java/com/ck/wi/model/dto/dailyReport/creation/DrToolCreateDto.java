package com.ck.wi.model.dto.dailyReport.creation;

import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class DrToolCreateDto implements Serializable {
    private Integer drToolId;
    private Integer qty;
    private String name;
    private String other;
    private String comments;
}
