package com.ck.wi.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "jobs")
public class Job implements Serializable {
    // jobs_id, number, type, name, address, contractor, contact, status

    @Id
    @Column(name = "jobs_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer jobsId;

    @Column(name = "number")
    private String number;

    @Column(name = "type")
    private String type;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "contractor")
    private String contractor;

    @Column(name = "contact")
    private String contact;

    @Column(name = "status")
    private String status;

//    @OneToMany(mappedBy = "job")
//    private List<Checklist> checklist;
}
