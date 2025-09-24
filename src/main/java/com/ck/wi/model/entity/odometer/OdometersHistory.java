package com.ck.wi.model.entity.odometer;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.cglib.core.Local;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "odometers_history")
public class OdometersHistory implements Serializable {
    @Id
    @Column(name = "odometers_history_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer odometersHistoryId;

    @Column(name = "odometers_id")
    private Integer odometersId;

    @Column(name = "previous_lecture")
    private Double previousLecture;

    @Column(name = "new_lecture")
    private Double newLecture;

    @Column(name = "reported_by")
    private String reportedBy;

    @Column(name = "reported_date")
    private LocalDate reportedDate;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private ZonedDateTime createdDate;

    @Column(name = "odometers_status")
    private String odometersStatus;

}
