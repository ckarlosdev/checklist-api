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
    public DemoChecklist processAndSaveDemoChecklist(DemoChecklistCreateDto demoChecklistCreateDto){
        if(demoChecklistCreateDto.getJobsId() == null){
            throw new IllegalArgumentException("Job ID must not be null");
        }

        Optional<Job> jobOpt = jobDao.findById(demoChecklistCreateDto.getJobsId());
        ZoneId yourZone = ZoneId.of("America/Chicago");
        ZonedDateTime today = ZonedDateTime.now(yourZone);

        if(jobOpt.isPresent()){
            DemoChecklist demoChecklist;
            if(demoChecklistCreateDto.getDemoChecklistsId() != null && demoChecklistCreateDto.getDemoChecklistsId() != 0){
                demoChecklist = demoChecklistDao.findById(demoChecklistCreateDto.getDemoChecklistsId())
                        .orElseThrow(() -> new IllegalArgumentException("Demo Checkist not found"));

                demoChecklist.setJob(jobOpt.get());
                demoChecklist.setChecklistDate(demoChecklistCreateDto.getChecklistDate());
                demoChecklist.setBuildingType(demoChecklistCreateDto.getBuildingType());
                demoChecklist.setForeman(demoChecklistCreateDto.getForeman());
                demoChecklist.setNotes(demoChecklistCreateDto.getNotes());
                demoChecklist.setSignature(demoChecklistCreateDto.getSignature());
                demoChecklist.setPermits(demoChecklistCreateDto.getPermits());
                demoChecklist.setUpdatedBy(demoChecklistCreateDto.getUpdatedBy());
                demoChecklist.setUpdatedDate(today);

            }else{
                demoChecklist = DemoChecklist.builder()
                        .job(jobOpt.get())
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

            if(demoChecklistCreateDto.getItems() == null || demoChecklistCreateDto.getItems().isEmpty()){
                if(demoChecklistSaved.getItems() != null){
                    demoChecklistSaved.getItems().clear();
                }
            }else{
                Map<Integer, DemoChecklistsItem> existingItemsMap =
                        demoChecklistSaved.getItems() != null
                                ? demoChecklistSaved.getItems().stream()
                                .filter(c ->  c.getDemoChecklistsItemsId() != null)
                                .collect(Collectors.toMap(DemoChecklistsItem::getDemoChecklistsItemsId,c -> c))
                                : new HashMap<>();

                List<DemoChecklistsItem> updatedItems = new ArrayList<>();

                for(DemoChecklistsItemDto demoChecklistsItemDto : demoChecklistCreateDto.getItems()){
                    DemoItem demoItem = demoItemDao.findById(demoChecklistsItemDto.getDemoItemsId())
                            .orElseThrow(() -> new IllegalArgumentException("demoItem not found"));

                    DemoChecklistsItem demoChecklistsItem;
                    if(demoChecklistsItemDto.getDemoChecklistsId() != null && demoChecklistsItemDto.getDemoChecklistsId() != 0 && existingItemsMap.containsKey(demoChecklistsItemDto.getDemoChecklistsId())){
                        // update
                        demoChecklistsItem = existingItemsMap.get(demoChecklistsItemDto.getDemoChecklistsId());
                        demoChecklistsItem.setDemoItem(demoItem);
                        demoChecklistsItem.setResponse(demoChecklistsItemDto.getResponse());
                        demoChecklistsItem.setDciStatus("1");
                    }else{
                        // create
                        demoChecklistsItem = DemoChecklistsItem.builder()
                                .demoItem(demoItem)
                                .response(demoChecklistsItemDto.getResponse())
                                .dciStatus("1")
                                .build();
                    }

                    demoChecklistsItem.setDemoChecklist(demoChecklistSaved);
                    updatedItems.add(demoChecklistsItem);
                }

                if(demoChecklistSaved.getItems() != null){
                    demoChecklistSaved.getItems().clear();
                    demoChecklistSaved.getItems().addAll(updatedItems);
                } else {
                    demoChecklistSaved.setItems(updatedItems);
                }
            }

            return demoChecklistDao.save(demoChecklistSaved);
        } else {
            throw new IllegalArgumentException("job not found");
        }
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
}
