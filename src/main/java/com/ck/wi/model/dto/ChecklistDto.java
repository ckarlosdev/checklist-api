package com.ck.wi.model.dto;

import com.ck.wi.model.entity.Employee;
import com.ck.wi.model.entity.Equipment;
import com.ck.wi.model.entity.Job;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ChecklistDto implements Serializable {

    private Integer checklistsId;
//    private Equipment equipment;
//    private Employee employee;
//    private Job job;
    private Integer equipmentsId;
    private Integer employeesId;
    private Integer jobsId;
    private String type;
    private Date date;
    private String odometer;
    private String oil;
    private String hydraulic;
    private String filter;
    private String radiator;
    private String track;
    private String attachment;
    private String leaking;
    private String diesel;
    private String clean;
    private String comment;
    private String createdBy;
    private Date createdDate;
    private String updatedBy;
    private Date updatedDate;
    private String status;
}
