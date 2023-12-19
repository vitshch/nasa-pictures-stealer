package com.bobocode.nasa.controller;

import com.bobocode.nasa.dto.ErrorDto;
import com.bobocode.nasa.dto.NasaPicturesStealDto;
import com.bobocode.nasa.service.NasaPicturesStealerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pictures/steal")
public class NasaPicturesStealerController {

    private static final Logger LOG = LoggerFactory.getLogger(NasaPicturesStealerController.class);

    public NasaPicturesStealerService nasaPicturesStealerService;

    public NasaPicturesStealerController(NasaPicturesStealerService nasaPicturesStealerService) {
        this.nasaPicturesStealerService = nasaPicturesStealerService;
    }

    @PostMapping
    public void steal(@RequestBody NasaPicturesStealDto stealDto) {
        nasaPicturesStealerService.stealNasaPictures(stealDto.sol());
    }

    @ExceptionHandler
    private ResponseEntity<?> handleException(Throwable exception) {
        LOG.error("Exception occurred", exception);
        return ResponseEntity.internalServerError()
                .body(new ErrorDto("Unable to process the request", exception.getMessage()));
    }
}
