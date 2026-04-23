package com.serfinsa.backend.service;

import com.serfinsa.backend.dto.ProductoDto;
import com.serfinsa.backend.entity.Producto;
import com.serfinsa.backend.entity.TipoProducto;
import com.serfinsa.backend.entity.Usuario;
import com.serfinsa.backend.repository.ProductoRepository;
import com.serfinsa.backend.repository.TipoProductoRepository;
import com.serfinsa.backend.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final TipoProductoRepository tipoProductoRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    public List<ProductoDto> findAll() {
        return productoRepository.findAll().stream().map(this::toDto).toList();
    }

    @Transactional(readOnly = true)
    public ProductoDto findById(Long id) {
        return productoRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    public ProductoDto create(ProductoDto dto, String emailCreador) {
        TipoProducto tipo = tipoProductoRepository.findById(dto.getTipoProductoId())
                .orElseThrow(() -> new RuntimeException("Tipo de producto no encontrado"));
        Usuario creador = usuarioRepository.findByEmail(emailCreador).orElse(null);

        Producto producto = Producto.builder()
                .nombre(dto.getNombre())
                .descripcion(dto.getDescripcion())
                .precio(dto.getPrecio())
                .stock(dto.getStock())
                .tipoProducto(tipo)
                .creadoPor(creador)
                .activo(true)
                .build();
        return toDto(productoRepository.save(producto));
    }

    public ProductoDto update(Long id, ProductoDto dto) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        TipoProducto tipo = tipoProductoRepository.findById(dto.getTipoProductoId())
                .orElseThrow(() -> new RuntimeException("Tipo de producto no encontrado"));

        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        producto.setStock(dto.getStock());
        producto.setTipoProducto(tipo);
        return toDto(productoRepository.save(producto));
    }

    public void delete(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new RuntimeException("Producto no encontrado");
        }
        productoRepository.deleteById(id);
    }

    private ProductoDto toDto(Producto p) {
        ProductoDto dto = new ProductoDto();
        dto.setId(p.getId());
        dto.setNombre(p.getNombre());
        dto.setDescripcion(p.getDescripcion());
        dto.setPrecio(p.getPrecio());
        dto.setStock(p.getStock());
        dto.setTipoProductoId(p.getTipoProducto().getId());
        dto.setTipoProductoNombre(p.getTipoProducto().getNombre());
        dto.setActivo(p.getActivo());
        dto.setFechaCreacion(p.getFechaCreacion());
        dto.setFechaActualizacion(p.getFechaActualizacion());
        return dto;
    }
}
