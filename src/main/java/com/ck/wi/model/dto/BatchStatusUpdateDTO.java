package com.ck.wi.model.dto;

import java.util.List;

public record BatchStatusUpdateDTO(
        List<Long> ids,
        String status
) {}
