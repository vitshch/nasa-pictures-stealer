package com.bobocode.nasa.model;

import com.bobocode.nasa.dto.nasa.CameraDto;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "cameras")
public class Camera {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long nasaId;

    @Column(nullable = false)
    private String name;

    @CreatedDate
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "camera", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Photo> photos = new ArrayList<>();

    public Camera() {
    }

    public Camera(CameraDto cameraDto) {
        this.nasaId = cameraDto.id();
        this.name = cameraDto.name();
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void addPhoto(Photo photo) {
        photo.setCamera(this);
        photos.add(photo);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Camera camera = (Camera) o;

        if (!Objects.equals(id, camera.id)) return false;
        return Objects.equals(nasaId, camera.nasaId);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (nasaId != null ? nasaId.hashCode() : 0);
        return result;
    }
}
