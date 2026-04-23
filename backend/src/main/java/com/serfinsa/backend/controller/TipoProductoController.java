package com.serfinsa.backend.controller;

import com.serfinsa.backend.dto.TipoProductoDto;
import com.serfinsa.backend.service.TipoProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tipo-producto")
@RequiredArgsConstructor
public class TipoProductoController {

    private final TipoProductoService tipoProductoService;

    @GetMapping
    public ResponseEntity<List<TipoProductoDto>> findAll() {
        return ResponseEntity.ok(tipoProductoService.findAll());
    }
}
