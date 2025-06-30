package com.ck.wi.model.entity.silica;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "controls")
public class Control {

    @Id
    @Column(name = "controls_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer controlsId;

    @Column(name = "control_group")
    private String controlGroup;

    @Column(name = "control_type")
    private String controlType;

    @Column(name = "type_description")
    private String typeDescription;

    @Column(name = "control_status")
    private String controlStatus;

    // Relationship: One Control has many ControlsDescriptions
//    @OneToMany(mappedBy = "control", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
//    private List<ControlsDescription> descriptions;

}
