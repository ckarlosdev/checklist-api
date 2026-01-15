package com.ck.wi.model.entity.hazard;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "pre_tasks")
public class PreTask implements Serializable {

    @Id
    @Column(name = "pre_tasks_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer preTasksId;

    @Column(name = "jobs_Id")
    private Integer jobsId;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "supervisor")
    private String supervisor;

    @Column(name = "comment")
    private String comment;

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

    @Column(name = "token")
    private String token;

    @OneToMany(mappedBy = "preTask", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Activity> activities;

    @OneToMany(mappedBy = "preTask", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<PretasksOption> pretasksOption;
}
