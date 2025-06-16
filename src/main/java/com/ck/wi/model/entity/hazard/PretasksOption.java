package com.ck.wi.model.entity.hazard;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "pretasks_options")
public class PretasksOption implements Serializable {

    @Id
    @Column(name = "pretasks_options_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pretasksOptionsId;

    @ManyToOne
    @JoinColumn(name = "pre_tasks_id")
    private PreTask preTask;

    @Column(name = "pretasks_checkbox_options_id")
    private Integer pretasksCheckboxOptionsId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pretasks_checkbox_options_id", insertable = false, updatable = false)
    private PretasksCheckboxOption checkboxOption;

    @Column(name = "other")
    private String other;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_date")
    private Date updatedDate;

    @Column(name = "status")
    private String status;
}
