package com.mitocode.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class PacienteDTO {
    private Integer idPaciente;
    @Size(min=3,max = 70,message = "{nombres.size}")
    private String nombres;
    @Size(min=3,max = 70,message = "{apellidos.size}")
    private String apellidos;
    @Size(min=8,max =8, message = "DNI debe tener 8 caracteres")
    private String dni;
    @Size(min=3,max = 150,message = "Direccion debe tener minimo 3 caracteres")
    private String direccion;
    @Size(min=9,max = 9,message = "Telefono debe tener 9 caracteres")
    private String telefono;
    @Email(message = "Email formato incorrecto")
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
