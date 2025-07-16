package com.ck.wi.model.entity.silica;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "silica_controls")
public class SilicaControl implements Serializable {

    @Id
    @Column(name = "silica_controls_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer silicaControlsId;

    @ManyToOne
    @JoinColumn(name = "silica_id")
    @JsonBackReference
    private Silica silica;

    @ManyToOne
    @JoinColumn(name = "controls_descriptions_id")
    @JsonBackReference
    private ControlsDescription controlsDescription;

    @Column(name = "control_answer")
    private String controlAnswer;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @Column(name = "sc_status")
    private String status;
}
