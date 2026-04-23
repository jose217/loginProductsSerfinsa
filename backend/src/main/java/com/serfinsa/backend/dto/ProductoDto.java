package com.serfinsa.backend.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ProductoDto {

    private Long id;

    @NotBlank
    @Size(min = 2, max = 150)
    private String nombre;

    @Size(max = 500)
    private String descripcion;

    @NotNull
    @DecimalMin(value = "0.00")
    @Digits(integer = 8, fraction = 2)
    private BigDecimal precio;

    @NotNull
    @Min(0)
    private Integer stock;

    @NotNull
    private Long tipoProductoId;

    private String tipoProductoNombre;

    private Boolean activo;

    private LocalDateTime fechaCreacion;

    private LocalDateTime fechaActualizacion;
}
