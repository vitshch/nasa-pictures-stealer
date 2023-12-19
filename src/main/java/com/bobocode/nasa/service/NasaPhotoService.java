package com.bobocode.nasa.service;

import com.bobocode.nasa.dto.nasa.PhotoDto;
import com.bobocode.nasa.dto.nasa.PhotoWrapperDto;
import com.bobocode.nasa.exception.RemoteCallException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Service
public class NasaPhotoService {

    private static final String API_KEY = "api_key";
    private static final String SOL = "sol";

    public String nasaApiPhotosUrl;
    public String nasaApiKey;
    public RestTemplate restTemplate;

    public NasaPhotoService(
            @Value("${nasa.api.mars.photos.url}") String nasaApiPhotosUrl,
            @Value("${nasa.api.key}") String nasaApiKey,
            RestTemplate restTemplate
    ) {
        this.nasaApiPhotosUrl = nasaApiPhotosUrl;
        this.nasaApiKey = nasaApiKey;
        this.restTemplate = restTemplate;
    }

    public List<PhotoDto> getNasaMarsPictures(int sol) {
        URI nasaUrl = buildNasaPicturesUri(sol);
        try {
            PhotoWrapperDto photoWrapperDto = restTemplate.getForObject(nasaUrl, PhotoWrapperDto.class);
            return photoWrapperDto.photos();
        } catch (RestClientException ex) {
            throw new RemoteCallException("Unable to fetch photos from nasa photo service. URL: " + nasaUrl, ex);
        }
    }

    private URI buildNasaPicturesUri(int sol) {
        return UriComponentsBuilder.fromUriString(nasaApiPhotosUrl)
                .queryParam(API_KEY, nasaApiKey)
                .queryParam(SOL, sol)
                .build()
                .toUri();
    }
}
