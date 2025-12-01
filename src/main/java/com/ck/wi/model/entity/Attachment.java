package com.ck.wi.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "attachments")
public class Attachment implements Serializable {
    //attachments_id, family, number, name, manufacturing, model, year, purchase_date, status, conditions, serial_number

    @Id
    @Column(name = "attachments_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer attachments_id;

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

    @Column(name = "purchase_date")
    private String purchaseDate;

    @Column(name = "status")
    private String status;

    @Column(name = "conditions")
    private String conditions;

    @Column(name = "serial_number")
    private String serialNumber;
}
