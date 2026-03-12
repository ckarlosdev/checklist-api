package com.ck.wi.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "equipments")
public class Equipment implements Serializable {
    //equipments_id, family, number, name, manufacturing, model, year, purchase_date, status, condition, serial_number, hour

    @Id
    @Column(name = "equipments_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer equipmentsId;

    @Column(name = "family")
    private String family;

    @Column(name = "number")
    private String number;

    @Column(name = "name")
    private String name;

    @Column(name = "manufacturing")
    private String manufacturing;

    @Column(name = "model")
    private String model;

    @Column(name = "`year`")
    private String year;

    @Column(name = "purchaseDate")
    private String purchaseDate;

    @Column(name = "`status`")
    private String status;

    @Column(name = "`condition`")
    private String condition;

    @Column(name = "serialNumber")
    private String serialNumber;

    @Column(name = "hour")
    private float hour;

    @Column(name = "equipment_status")
    private String equipmentStatus;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date", insertable = false, updatable = false)
    private String createdDate;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_date", insertable = false)
    @UpdateTimestamp
    private String updatedDate;
}
