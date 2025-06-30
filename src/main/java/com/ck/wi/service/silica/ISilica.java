package com.ck.wi.service.silica;

import com.ck.wi.model.dto.silica.SilicaControlCreateDto;
import com.ck.wi.model.dto.silica.SilicaCreateDto;
import com.ck.wi.model.dto.silica.SilicaDto;
import com.ck.wi.model.entity.Job;
import com.ck.wi.model.entity.silica.Silica;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ISilica {
    Optional<SilicaDto> getSilicaWithControls(Integer silicaId);

    List<Silica> findByJobAndEventDate(Job job, LocalDate eventDate);

    Silica processAndSaveSilica(SilicaCreateDto silicaCreateDto);
}
