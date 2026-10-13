package com.ck.wi.model.dto;

public record EquipmentResponseDto(
        Integer equipmentsId,
        String family,
        String number,
        String name,
        float hour,
        String subtype,
        String licensePlate
) {}
