package com.ck.wi.model.entity.hazard;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "pretasks_options")
public class PretasksOption implements Serializable {

    @Id
    @Column(name = "pretasks_options_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pretasksOptionsId;

    @ToString.Exclude // <--- Rompe el ciclo en el log
    @EqualsAndHashCode.Exclude // <--- Rompe el ciclo en comparaciones
    @JsonIgnore // <--- Evita bucles infinitos en el JSON de salida
    @ManyToOne
    @JoinColumn(name = "pre_tasks_id")
    private PreTask preTask;

    @Column(name = "pretasks_checkbox_options_id")
    private Integer pretasksCheckboxOptionsId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pretasks_checkbox_options_id", insertable = false, updatable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private PretasksCheckboxOption checkboxOption;

    @Column(name = "other")
    private String other;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @Column(name = "status")
    private String status;
}
