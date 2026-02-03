package com.ck.wi.service.impl.dailyReport;

import com.ck.wi.model.dao.dailyReport.PhotoDao;
import com.ck.wi.model.dto.dailyReport.PhotoCreateDto;
import com.ck.wi.model.entity.dailyReport.Photo;
import com.ck.wi.service.dailyReport.IPhoto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PhotoImpl implements IPhoto {

    @Autowired
    private PhotoDao photoDao;

    private static final String ACTIVE = "1";
    private static final String INACTIVE = "0";

    @Override
    public List<Photo> findByDailyReportId(Integer dailyReportId){
        return (List<Photo>) photoDao.findByDailyReportIdAndStatus(dailyReportId, "1");
    }

    @Override
    public List<Photo> findByDailyReportIds(List<Integer> dailyReportIds){
        return (List<Photo>) photoDao.findByDailyReportIdIn(dailyReportIds);
    }

    @Override
    public List<Photo> findByTypeAndReport(String type, Integer dailyReportId){
        return (List<Photo>) photoDao.findByTypeAndId(type, dailyReportId);
    }

    @Transactional
    @Override
    public void savePhotos(List<PhotoCreateDto> photosDto, Integer dailyReportId){
        LocalDate now = LocalDate.now();

        for(PhotoCreateDto dto : photosDto){
            Photo newPhoto = Photo.builder()
                .dailyReportId(dailyReportId)
                .drDate(dto.getDrDate())
                .pathId(dto.getPathId())
                .folderId(dto.getFolderId())
                .name(dto.getName())
                .type(dto.getType())
                .createdBy(dto.getUserName())
                .createdDate(now)
                .updatedBy(dto.getUserName())
                .updatedDate(now)
                .status(ACTIVE)
                .build();

            photoDao.save(newPhoto);
        }
    }

    @Transactional
    @Override
    public void updatePhotos(List<PhotoCreateDto> photosDtos, Integer dailyReportId){
        LocalDate now = LocalDate.now();
        String reportType = "";
        if(!photosDtos.isEmpty()){
            reportType = photosDtos.get(0).getType();
        }

        List<Photo> actualPhotos = photoDao
                .findByTypeAndId(reportType, dailyReportId);
        Map<Integer, Photo> actualPhotosMap = actualPhotos.stream()
                .collect(Collectors.toMap(Photo::getPhotosId, Function.identity()));
        List<Photo> photosToSave = new ArrayList<>();

        for(PhotoCreateDto dto : photosDtos){
            Integer dtoId = dto.getPhotosId();
            System.out.println("if division: ");
            System.out.println(actualPhotos);
            if(dtoId != null && actualPhotosMap.containsKey(dtoId)){
                Photo photoUpdate = actualPhotosMap.get(dtoId);
                System.out.println("if create ");
                photoUpdate.setPhotosId(dtoId);
                photoUpdate.setDrDate(dto.getDrDate());
                photoUpdate.setPathId(dto.getPathId());
                photoUpdate.setFolderId(dto.getFolderId());
                photoUpdate.setName(dto.getName());
                photoUpdate.setType(dto.getType());
                photoUpdate.setUpdatedBy(dto.getUserName());
                photoUpdate.setUpdatedDate(now);

                photosToSave.add(photoUpdate);
                actualPhotosMap.remove(dtoId);
            }else{
                System.out.println("update ");
                Photo newPhoto = Photo.builder()
                        .dailyReportId(dailyReportId)
                        .drDate(dto.getDrDate())
                        .pathId(dto.getPathId())
                        .folderId(dto.getFolderId())
                        .name(dto.getName())
                        .type(dto.getType())
                        .createdBy(dto.getUserName())
                        .createdDate(now)
                        .updatedBy(dto.getUserName())
                        .updatedDate(now)
                        .status(ACTIVE)
                        .build();
                photosToSave.add(newPhoto);
            }

            photoDao.saveAll(photosToSave);
            List<Photo> photosToDelete =
                    new ArrayList<>(actualPhotosMap.values());

            if(!photosToDelete.isEmpty()){
                photosToDelete.forEach(item -> {
                    item.setStatus(INACTIVE);
                });
                photoDao.saveAll(photosToDelete);
            }
        }
    }
}
