package com.ck.wi.model.entity.demoChecklist;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "demo_checklists_items")
public class DemoChecklistsItem implements Serializable {

    @Id
    @Column(name = "demo_checklists_items_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer demoChecklistsItemsId;

    @ManyToOne
    @JoinColumn(name = "demo_checklists_id")
    @JsonBackReference
    private DemoChecklist demoChecklist;

    @ManyToOne
    @JoinColumn(name = "demo_items_id")
    @JsonBackReference
    private DemoItem demoItem;

    @Column(name = "response")
    private String response;

    @Column(name = "dci_status")
    private String dciStatus;

}
