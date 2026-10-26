package com.ck.wi.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "equipment_details")
public class EquipmentDetail {
    @Id
    @Column(name = "equipment_detail_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "equipment_id",
            nullable = false,
            unique = true
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Equipment equipment;

    @Column(name = "sub_type", nullable = false, length = 150)
    private String subtype;

    @Column(name = "license_plate", length = 50)
    private String licensePlate;
}
