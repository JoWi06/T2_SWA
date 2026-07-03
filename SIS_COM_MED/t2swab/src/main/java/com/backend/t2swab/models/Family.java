package com.backend.t2swab.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "family")
public class Family {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFamily;

    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    private String description;

    public Family() {}

    public Long getIdFamily() { return idFamily; }
    public void setIdFamily(Long idFamily) { this.idFamily = idFamily; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}