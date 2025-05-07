package com.ck.wi.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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

    @Column(name = "year")
    private String year;

    @Column(name = "purchaseDate")
    private String purchaseDate;

    @Column(name = "status")
    private String status;

    @Column(name = "condition")
    private String condition;

    @Column(name = "serialNumber")
    private String serialNumber;

    @Column(name = "hour")
    private float hour;

//    @OneToMany(mappedBy = "equipment")
//    @JsonManagedReference
//    private List<Checklist> checklist;
}
