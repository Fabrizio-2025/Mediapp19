package com.mitocode.model;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.security.PrivilegedAction;

@Entity
@Table(name="medicos")
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idMedico;
    @Column(name = "nombres",nullable = false,length = 70)
    private String nombres;
    @Column(name = "apellidos",nullable = false,length = 70)
    private String apellidos;
    @Column(name = "cmp",nullable = false,length = 14,unique = true)
    private String cmp;
    @Column(name = "foto_url",nullable = true)
    private String fotoUrl;


    public Integer getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(Integer idMedico) {
        this.idMedico = idMedico;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCmp() {
        return cmp;
    }

    public void setCmp(String cmp) {
        this.cmp = cmp;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }
}
