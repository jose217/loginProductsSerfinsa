package com.serfinsa.backend.service;

import com.serfinsa.backend.dto.TipoProductoDto;
import com.serfinsa.backend.repository.TipoProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TipoProductoService {

    private final TipoProductoRepository tipoProductoRepository;

    public List<TipoProductoDto> findAll() {
        return tipoProductoRepository.findAll().stream()
                .map(t -> new TipoProductoDto(t.getId(), t.getNombre()))
                .toList();
    }
}
