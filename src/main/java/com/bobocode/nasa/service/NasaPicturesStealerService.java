package com.bobocode.nasa.service;

import com.bobocode.nasa.dto.nasa.CameraDto;
import com.bobocode.nasa.dto.nasa.PhotoDto;
import com.bobocode.nasa.model.Camera;
import com.bobocode.nasa.model.Photo;
import com.bobocode.nasa.repository.CameraRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NasaPicturesStealerService {

    private static final Logger LOG = LoggerFactory.getLogger(NasaPicturesStealerService.class);

    private final NasaPhotoService nasaPhotoService;
    private final CameraRepository cameraRepository;
    private final TransactionTemplate transactionTemplate;

    public NasaPicturesStealerService(
            NasaPhotoService nasaPhotoService,
            CameraRepository cameraRepository,
            TransactionTemplate transactionTemplate
    ) {
        this.nasaPhotoService = nasaPhotoService;
        this.cameraRepository = cameraRepository;
        this.transactionTemplate = transactionTemplate;
    }

    public void stealNasaPictures(int sol) {
        List<PhotoDto> nasaMarsPictures = nasaPhotoService.getNasaMarsPictures(sol);
        LOG.debug("Fetched {} photos from nasa by sol {}", nasaMarsPictures.size(), sol);
        transactionTemplate.execute(status -> saveNasaPictures(nasaMarsPictures, status));
    }

    private TransactionStatus saveNasaPictures(List<PhotoDto> nasaMarsPictures, TransactionStatus transactionStatus) {
        nasaMarsPictures.stream()
                .collect(Collectors.groupingBy(PhotoDto::camera))
                .forEach(this::mapCamera);
        return transactionStatus;
    }

    private Camera mapCamera(CameraDto cameraDto, List<PhotoDto> photoDtos) {
        Camera camera = cameraRepository.findCameraByNasaId(cameraDto.id())
                .orElseGet(() -> cameraRepository.save(new Camera(cameraDto)));
        photoDtos.forEach(photoDto -> camera.addPhoto(new Photo(photoDto)));
        return camera;
    }

}
