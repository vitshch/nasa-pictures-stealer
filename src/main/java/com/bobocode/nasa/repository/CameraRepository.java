package com.bobocode.nasa.repository;

import com.bobocode.nasa.model.Camera;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CameraRepository extends JpaRepository<Camera, Long> {
    Optional<Camera> findCameraByNasaId(Long nasaId);
}
