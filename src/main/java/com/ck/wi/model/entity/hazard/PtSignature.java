package com.ck.wi.model.entity.hazard;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "pt_signatures")
public class PtSignature implements Serializable {
    @Id
    @Column(name = "pt_signatures_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ptSignaturesId;

    @Column(name = "pre_tasks_id")
    private Integer preTasksId;

    @Column(name = "employees_id")
    private Integer employeesId;

    @Column(name = "img_data")
    private byte[] imgData;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "udpated_date")
    private LocalDateTime updatedDate;

    @Column(name = "pt_signatures_status")
    private String ptSignaturesStatus;
}
