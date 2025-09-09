package com.ck.wi.model.dto.demoChecklist;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class DemoChecklistsItemDto {

    private Integer demoChecklistsItemsId;
    private Integer demoChecklistsId;
    private Integer demoItemsId;
    private String response;
    private String dciStatus;

}
