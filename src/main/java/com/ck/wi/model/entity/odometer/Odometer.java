package com.ck.wi.model.entity.odometer;

import com.ck.wi.model.entity.Equipment;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "odometers")
public class Odometer implements Serializable {

    @Id
    @Column(name = "odometers_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer odometersId;

    @ManyToOne
    @JoinColumn(name = "equipments_id")
    @JsonBackReference
    private Equipment equipment;

    @Column(name = "odometer")
    private Double odometer;

}
