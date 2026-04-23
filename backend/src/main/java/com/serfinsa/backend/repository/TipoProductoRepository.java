package com.serfinsa.backend.repository;

import com.serfinsa.backend.entity.TipoProducto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TipoProductoRepository extends JpaRepository<TipoProducto, Long> {
    Optional<TipoProducto> findByNombre(String nombre);
}
