package com.backend.t2swab.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_product")
    private Long idProduct;

    @NotBlank(message = "La descripción no puede estar vacía")
    @Column(nullable = false)
    private String description;

    @NotNull(message = "Debe especificar si está expirado")
    private Boolean expired;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "La presentación es obligatoria")
    private String presentation;

    @NotNull(message = "El stock es obligatorio")
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock;

    @NotNull(message = "El precio unitario es obligatorio")
    @DecimalMin(value = "0.0", inclusive = true, message = "El precio no puede ser menor a 0")
    @Column(name = "unit_price")
    private Double unitPrice;

    @ManyToOne
    @JoinColumn(name = "id_category", nullable = false)
    @NotNull(message = "La categoría es obligatoria")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "id_family", nullable = false)
    @NotNull(message = "La familia es obligatoria")
    private Family family;

    @ManyToOne
    @JoinColumn(name = "id_laboratory", nullable = false)
    @NotNull(message = "El laboratorio es obligatorio")
    private Laboratory laboratory;
}