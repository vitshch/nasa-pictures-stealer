package com.bobocode.nasa.dto.nasa;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PhotoDto(
        Long id,
        Integer sol,
        CameraDto camera,
        @JsonProperty("img_src")
        String imgSrc
) {
}
