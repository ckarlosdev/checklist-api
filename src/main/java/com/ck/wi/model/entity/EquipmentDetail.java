package com.ck.wi.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "equipment")
@Entity
@Table(name = "equipment_details")
public class EquipmentDetail {
    @Id
    @Column(name = "equipment_detail_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_id", nullable = false)
    private Equipment equipment;

    @Column(name = "sub_type", nullable = false, length = 150)
    private String subtype;

    @Column(name = "license_plate", length = 50)
    private String licensePlate;
}
