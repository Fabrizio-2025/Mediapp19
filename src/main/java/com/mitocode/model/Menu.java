package com.mitocode.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "menu")
public class Menu {

    @Id
    private Integer idMenu;

    @Column(name = "Icono",length = 25)
    private String icono;

    @Column(name = "nombre",length = 25)
    private String nombre;

    @Column(length = 50,name = "url")
    private String url;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "menu_rol",joinColumns = @JoinColumn(name = "id_menu",referencedColumnName = "idMenu"),
    inverseJoinColumns = @JoinColumn(name = "id_rol",referencedColumnName = "idRol"))
    private List<Rol>roles;

    public Integer getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(Integer idMenu) {
        this.idMenu = idMenu;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
