package com.ck.wi.model.entity.hazard;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "pretasks_checkbox_options")
public class PretasksCheckboxOption implements Serializable {
    @Id
    @Column(name = "pretasks_checkbox_options_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pretasksCheckboxOptionsId;

    @Column(name = "name")
    private String name;

    @Column(name = "Type")
    private String Type;

    @Column(name = "description")
    private String description;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private String createdDate;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_date")
    private String updatedDate;

    @Column(name = "status")
    private String status;

}
