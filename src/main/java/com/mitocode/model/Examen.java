package com.mitocode.model;

import jakarta.persistence.*;

import java.util.Objects;


@Entity
@Table(name = "examen")
public class Examen {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idExamen;

    @Column(name = "Nombres",nullable = false,length = 70)
    private String nombre;

    @Column(name = "Description",nullable = true,length = 171)
    private String descripcion;


    public Integer getIdExamen() {
        return idExamen;
    }

    public void setIdExamen(Integer idExamen) {
        this.idExamen = idExamen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Examen examen)) return false;
        return Objects.equals(idExamen, examen.idExamen);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idExamen);
    }
}
