package com.ck.wi.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "attachments")
public class Attachment implements Serializable {
    //attachments_id, family, number, name, manufacturing, model, year, purchase_date, status, conditions, serial_number

    @Id
    @Column(name = "attachments_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer attachmentsId;

    @Column(name = "family")
    private String family;

    @Column(name = "number")
    private String number;

    @Column(name = "name")
    private String name;

    @Column(name = "manufacturing")
    private String manufacturing;

    @Column(name = "model")
    private String model;

    @Column(name = "year")
    private String year;

    @Column(name = "purchase_date")
    private String purchaseDate;

    @Column(name = "status")
    private String status;

    @Column(name = "conditions")
    private String conditions;

    @Column(name = "serial_number")
    private String serialNumber;

    @Column(name = "attachment_status")
    private String attachmentStatus;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate;

    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDateTime.now();
    }

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_date", insertable = false)
    @UpdateTimestamp
    private LocalDateTime updatedDate;
}
