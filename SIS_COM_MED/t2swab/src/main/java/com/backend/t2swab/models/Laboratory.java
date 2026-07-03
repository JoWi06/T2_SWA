package com.backend.t2swab.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "laboratory")
public class Laboratory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_laboratory")
    private Long idLaboratory;

    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    private String address;

    public Laboratory() {}

    public Long getIdLaboratory() { return idLaboratory; }
    public void setIdLaboratory(Long idLaboratory) { this.idLaboratory = idLaboratory; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
}