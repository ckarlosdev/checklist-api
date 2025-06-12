package com.ck.wi.model.entity.dailyReport;

import com.ck.wi.model.dto.dailyReport.DrEquipmentDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "problems")
public class Problem implements Serializable {

    @Id
    @Column(name = "problems_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer problemsId;

//    @Column(name = "dr_equipments_id")
    @ManyToOne
    @JoinColumn(name = "dr_equipments_id")
    @JsonBackReference
    private DrEquipment drEquipment;

    @Column(name = "type")
    private String type;

    @Column(name = "priority")
    private String priority;

    @Column(name = "description")
    private String description;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_date")
    private Date updatedDate;

    @Column(name = "status")
    private String status;
}
