package com.ck.wi.service.impl.demoChecklist;

import com.ck.wi.model.dao.JobDao;
import com.ck.wi.model.dao.demoChecklist.DemoChecklistDao;
import com.ck.wi.model.dao.demoChecklist.DemoChecklistsItemDao;
import com.ck.wi.model.dao.demoChecklist.DemoItemDao;
import com.ck.wi.model.dto.demoChecklist.DemoChecklistCreateDto;
import com.ck.wi.model.dto.demoChecklist.DemoChecklistsItemDto;
import com.ck.wi.model.entity.Job;
import com.ck.wi.model.entity.demoChecklist.DemoChecklist;
import com.ck.wi.model.entity.demoChecklist.DemoChecklistsItem;
import com.ck.wi.model.entity.demoChecklist.DemoItem;
import com.ck.wi.model.entity.silica.Silica;
import com.ck.wi.service.demoChecklist.IDemoChecklist;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DemoChecklisImpl implements IDemoChecklist {
    @Autowired
    private DemoChecklistDao demoChecklistDao;

    @Autowired
    private JobDao jobDao;

    @Autowired
    private DemoChecklistsItemDao demoChecklistsItemDao;

    @Autowired
    private DemoItemDao demoItemDao;

    @Transactional
    @Override
    public DemoChecklist processAndSaveDemoChecklist(DemoChecklistCreateDto demoChecklistCreateDto) {
        // ... (Tu código para encontrar o crear demoChecklist y job) ...
        if (demoChecklistCreateDto.getJobsId() == null) {
            throw new IllegalArgumentException("Job ID must not be null");
        }

        Job job = jobDao.findById(demoChecklistCreateDto.getJobsId())
                .orElseThrow(() -> new IllegalArgumentException("Job not found"));

        ZonedDateTime today = ZonedDateTime.now(ZoneId.of("America/Chicago"));
        DemoChecklist demoChecklist;

        if (demoChecklistCreateDto.getDemoChecklistsId() != null && demoChecklistCreateDto.getDemoChecklistsId() != 0) {
            demoChecklist = demoChecklistDao.findById(demoChecklistCreateDto.getDemoChecklistsId())
                    .orElseThrow(() -> new IllegalArgumentException("Demo Checklist not found"));

            demoChecklist.setJob(job);
            demoChecklist.setChecklistDate(demoChecklistCreateDto.getChecklistDate());
            demoChecklist.setBuildingType(demoChecklistCreateDto.getBuildingType());
            demoChecklist.setForeman(demoChecklistCreateDto.getForeman());
            demoChecklist.setNotes(demoChecklistCreateDto.getNotes());
            demoChecklist.setSignature(demoChecklistCreateDto.getSignature());
            demoChecklist.setPermits(demoChecklistCreateDto.getPermits());
            demoChecklist.setUpdatedBy(demoChecklistCreateDto.getUpdatedBy());
            demoChecklist.setUpdatedDate(today);

        } else {
            demoChecklist = DemoChecklist.builder()
                    .job(job)
                    .checklistDate(demoChecklistCreateDto.getChecklistDate())
                    .buildingType(demoChecklistCreateDto.getBuildingType())
                    .foreman(demoChecklistCreateDto.getForeman())
                    .notes(demoChecklistCreateDto.getNotes())
                    .signature(demoChecklistCreateDto.getSignature())
                    .permits(demoChecklistCreateDto.getPermits())
                    .createdBy(demoChecklistCreateDto.getCreatedBy())
                    .createdDate(today)
                    .updatedBy(demoChecklistCreateDto.getUpdatedBy())
                    .updatedDate(today)
                    .demoChecklistsStatus("1")
                    .build();
        }

        DemoChecklist demoChecklistSaved = demoChecklistDao.save(demoChecklist);

        // 2. Procesar los ítems
        List<DemoChecklistsItem> existingItems = demoChecklistSaved.getItems();

        if (demoChecklistCreateDto.getItems() == null || demoChecklistCreateDto.getItems().isEmpty()) {
            // Eliminar todos los ítems existentes si no se envían nuevos.
            if (existingItems != null && !existingItems.isEmpty()) {
                existingItems.clear();
            }
        } else {
            // Actualización inteligente:

            // Mapear los ítems existentes para una búsqueda rápida
            Map<Integer, DemoChecklistsItem> existingItemsMap = new HashMap<>();
            if (existingItems != null) {
                existingItems.forEach(item -> existingItemsMap.put(item.getDemoItem().getDemoItemsId(), item));
            }

            // Crear una lista de IDs de ítems entrantes para comparar
            Set<Integer> incomingItemIds = demoChecklistCreateDto.getItems().stream()
                    .map(DemoChecklistsItemDto::getDemoItemsId)
                    .collect(Collectors.toSet());

            // Identificar y eliminar los ítems huérfanos
            if (existingItems != null) {
                existingItems.removeIf(item -> !incomingItemIds.contains(item.getDemoItem().getDemoItemsId()));
            }

            // Procesar los ítems entrantes (crear o actualizar)
            for (DemoChecklistsItemDto demoChecklistsItemDto : demoChecklistCreateDto.getItems()) {
                DemoItem demoItem = demoItemDao.findById(demoChecklistsItemDto.getDemoItemsId())
                        .orElseThrow(() -> new IllegalArgumentException("Demo Item not found"));

                DemoChecklistsItem existingItem = existingItemsMap.get(demoChecklistsItemDto.getDemoItemsId());

                if (existingItem != null) {
                    // Actualizar el ítem existente
                    existingItem.setResponse(demoChecklistsItemDto.getResponse());
                    existingItem.setDemoItem(demoItem);
                } else {
                    // Crear un nuevo ítem y agregarlo a la colección gestionada por Hibernate
                    DemoChecklistsItem newItem = DemoChecklistsItem.builder()
                            .demoItem(demoItem)
                            .response(demoChecklistsItemDto.getResponse())
                            .dciStatus("1")
                            .demoChecklist(demoChecklistSaved)
                            .build();
                    if (existingItems == null) {
                        demoChecklistSaved.setItems(new ArrayList<>());
                        existingItems = demoChecklistSaved.getItems();
                    }
                    existingItems.add(newItem);
                }
            }
        }

        return demoChecklistDao.save(demoChecklistSaved);
    }

    public Optional<DemoChecklistCreateDto> getDemoChecklistByID(Integer id){
        Optional<DemoChecklist> demoChecklistOpt = demoChecklistDao.findById(id);

        return demoChecklistOpt.map( demoChecklist -> {
           List<DemoChecklistsItemDto> demoChecklistsItemDtos =
                   demoChecklist.getItems().stream()
                           .map( demoChecklistsItem->
                                   DemoChecklistsItemDto.builder()
                                           .demoChecklistsItemsId(demoChecklistsItem.getDemoChecklistsItemsId())
                                           .demoChecklistsId(demoChecklistsItem.getDemoChecklist().getDemoChecklistsId())
                                           .demoItemsId(demoChecklistsItem.getDemoItem().getDemoItemsId())
                                           .response(demoChecklistsItem.getResponse())
                                           .dciStatus(demoChecklistsItem.getDciStatus())
                                           .build())
                           .collect(Collectors.toList());

           return new DemoChecklistCreateDto(
                   demoChecklist.getDemoChecklistsId(),
                   demoChecklist.getJob().getJobsId(),
                   demoChecklist.getChecklistDate(),
                   demoChecklist.getBuildingType(),
                   demoChecklist.getForeman(),
                   demoChecklist.getNotes(),
                   demoChecklist.getSignature(),
                   demoChecklist.getPermits(),
                   demoChecklistsItemDtos,
                   demoChecklist.getCreatedBy(),
                   demoChecklist.getUpdatedBy());
        });
    }

    public List<DemoChecklist> getDemoChecklistByjobNumber(String jobNumber){
        Job job = jobDao.findByNumber(jobNumber).orElse(null);

        if(job != null) {
            return demoChecklistDao.findByJobAndDemoChecklistsStatusOrderByChecklistDateDesc(job, "1");
        }

        return null;
    }
}
