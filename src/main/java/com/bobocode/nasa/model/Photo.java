package com.bobocode.nasa.model;

import com.bobocode.nasa.dto.nasa.PhotoDto;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "photos")
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long nasaId;

    @Column(nullable = false)
    private String imgSrc;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "camera_id")
    private Camera camera;

    private LocalDateTime createdAt;

    public Photo() {
    }

    public Photo(PhotoDto photoDto) {
        this.nasaId = photoDto.id();
        this.imgSrc = photoDto.imgSrc();
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNasaId() {
        return nasaId;
    }

    public void setNasaId(Long nasaId) {
        this.nasaId = nasaId;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Photo photo = (Photo) o;

        if (!Objects.equals(id, photo.id)) return false;
        return Objects.equals(nasaId, photo.nasaId);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (nasaId != null ? nasaId.hashCode() : 0);
        return result;
    }
}
