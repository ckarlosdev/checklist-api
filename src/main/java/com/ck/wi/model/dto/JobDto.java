package com.ck.wi.model.dto;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class JobDto implements Serializable {

    private Integer jobsId;
    private String number;
    private String type;
    private String name;
    private String address;
    private String contractor;
    private String contact;
    private String status;
}
