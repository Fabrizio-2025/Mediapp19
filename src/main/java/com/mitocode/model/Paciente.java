package com.mitocode.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="pacientes")
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPaciente;

    @Size(min=3,max = 70,message = "{nombres.size}")
    @Column(name = "nombres", nullable = false, length = 70)
    private String nombres;
    @Size(min=3,max = 70,message = "{apellidos.size}")
    @Column(name = "apellidos", nullable = false, length = 70)
    private String apellidos;
    @Size(min=8,max =8, message = "DNI debe tener 8 caracteres")
    @Column(name = "Dni", nullable = false, length = 8)
    private String dni;
    @Size(min=3,max = 150,message = "Direccion debe tener minimo 3 caracteres")
    @Column(name = "direcciones",nullable = true,length = 150)
    private String direccion;
    @Size(min=9,max = 9,message = "Telefono debe tener 9 caracteres")
    @Column(name = "telefonos",nullable = true,length = 9)
    private String telefono;
    @Email(message = "Email formato incorrecto")
    @Column(name = "email",nullable = true,length = 55)
    private String email;

    public Integer getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Integer idPaciente) {
        this.idPaciente = idPaciente;
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

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
