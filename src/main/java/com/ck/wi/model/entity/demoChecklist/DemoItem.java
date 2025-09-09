package com.ck.wi.model.entity.demoChecklist;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "demo_items")
public class DemoItem implements Serializable {
    @Id
    @Column(name = "demo_items_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer demoItemsId;

    @Column(name = "item_group")
    private String itemGroup;

    @Column(name = "item_description")
    private String itemDescription;

    @Column(name = "item_type")
    private String itemType;
}
