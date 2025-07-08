package com.ck.wi.model.entity.silica;

import com.ck.wi.model.dto.silica.SilicaControlDto;
import com.ck.wi.model.entity.Employee;
import com.ck.wi.model.entity.Job;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "silica")
public class Silica {

    @Id
    @Column(name = "silica_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer silicaId;

    @ManyToOne
    @JoinColumn(name = "jobs_id")
    @JsonBackReference
    private Job job;

    @ManyToOne
    @JoinColumn(name = "employees_id")
    @JsonBackReference
    private Employee employee;

    @Column(name = "event_date")
    private LocalDate eventDate;

    @Column(name = "work_description")
    private String workDescription;

//    @Column(name = "diagram_id")
//    private String diagramId;
//
//    @Column(name = "diagram_folder")
//    private String diagramFolder;

    @Column(name = "plan_area")
    private String ventilationArea;

    @Column(name = "plan_date")
    private Date datePlan;

    @Column(name = "plan_equipment")
    private String equipmentDescription;

    @Column(name = "signature_id")
    private String signatureId;

    @Column(name = "signature_folder")
    private String signatureFolder;

    @Column(name = "diagram_data")
    private String diagramData;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @Column(name = "silica_status")
    private String status;

    @OneToMany(mappedBy = "silica", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<SilicaControl> silicaControls;
}
