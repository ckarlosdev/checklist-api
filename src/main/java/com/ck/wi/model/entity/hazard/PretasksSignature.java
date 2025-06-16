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
@Table(name = "pretasks_signatures")
public class PretasksSignature  implements Serializable {

    @Id
    @Column(name = "pretasks_signatures_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pretasksSignaturesId;

    @Column(name = "pre_tasks_id")
    private Integer preTasksId;

    @Column(name = "date")
    private String date;

    @Column(name = "path_id")
    private String pathId;

    @Column(name = "folder_id")
    private String folderId;

    @Column(name = "name")
    private String name;

    @Column(name = "employee")
    private String employee;

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
