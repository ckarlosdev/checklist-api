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
@Table(name = "controls_descriptions")
public class ControlsDescription {

    @Id
    @Column(name = "controls_descriptions_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer controlsDescriptionsId;

    @ManyToOne
    @JoinColumn(name = "controls_id")
    private Control control;

    @Column(name = "control_name")
    private String controlName;

    @Column(name = "component_type")
    private String componentType;

    @Column(name = "cd_status")
    private String cdStatus;

//    @OneToMany(mappedBy = "controlsDescription", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<SilicaControl> silicaControls;

}
