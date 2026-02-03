package com.ck.wi.controller.dailyReport;

import com.ck.wi.model.dto.dailyReport.PhotoCreateDto;
import com.ck.wi.model.dto.dailyReport.PhotoDto;
import com.ck.wi.model.dto.request.PhotoRequest;
import com.ck.wi.model.entity.dailyReport.Photo;
import com.ck.wi.service.dailyReport.IPhoto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = {
        "http://127.0.0.1:5500",
        "https://oleo-soft.com",
        "http://localhost:5173",
        "https://ckarlosdev.github.io"
})
@RestController
@RequestMapping("/api/v1")
public class PhotoController {

    @Autowired
    private IPhoto photoService;

    @PostMapping("photo/{dailyReportId}")
    public void savePhotos(
            @RequestBody List<PhotoCreateDto> photos,
            @PathVariable Integer dailyReportId
    ){
        photoService.savePhotos(photos, dailyReportId);
    }

    @PutMapping("photo/{dailyReportId}")
    public void updatePhotos(
            @RequestBody List<PhotoCreateDto> photos,
            @PathVariable Integer dailyReportId
    ){
        photoService.updatePhotos(photos, dailyReportId);
    }

    @GetMapping("photo/type/{dailyReportId}")
    public List<PhotoDto> getPhotosByType(
            @PathVariable Integer dailyReportId,
            @RequestParam(required = false) String typeReport)
    {

        List<Photo> photos = photoService.findByTypeAndReport(typeReport, dailyReportId);

        return photos.stream()
                .map(photo ->
                        PhotoDto.builder()
                                .photosId(photo.getPhotosId())
                                .dailyReportId(photo.getDailyReportId())
                                .drDate(photo.getDrDate())
                                .pathId(photo.getPathId())
                                .folderId(photo.getFolderId())
                                .name(photo.getName())
                                .type(photo.getType())
                                .status(photo.getStatus())
                                .build())
                .collect(Collectors.toList());
    }

    @GetMapping("photo/dailyReport/{dailyReportId}")
    public List<PhotoDto> getPhotosByDailyReportId(@PathVariable Integer dailyReportId){
        List<Photo> photos = photoService.findByDailyReportId(dailyReportId);

        return photos.stream()
                .map(photo ->
                        PhotoDto.builder()
                                .photosId(photo.getPhotosId())
                                .dailyReportId(photo.getDailyReportId())
                                .drDate(photo.getDrDate())
                                .pathId(photo.getPathId())
                                .folderId(photo.getFolderId())
                                .name(photo.getName())
                                .type(photo.getType())
                                .status(photo.getStatus())
                                .build())
                .collect(Collectors.toList());
    }

    @GetMapping("photo/dailyReports")
    public List<PhotoDto> getPhotosByDailyReportIds(@RequestBody PhotoRequest request){
        List<Photo> photos = photoService.findByDailyReportIds(request.getDailyReportIds());

        return photos.stream()
                .map(photo ->
                        PhotoDto.builder()
                                .photosId(photo.getPhotosId())
                                .dailyReportId(photo.getDailyReportId())
                                .drDate(photo.getDrDate())
                                .pathId(photo.getPathId())
                                .folderId(photo.getFolderId())
                                .name(photo.getName())
                                .type(photo.getType())
                                .status(photo.getStatus())
                                .build())
                .collect(Collectors.toList());
    }
}
