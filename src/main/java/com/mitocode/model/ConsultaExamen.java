package com.mitocode.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "consulta_examen")
@IdClass(ConsultaExamenPK.class)
public class ConsultaExamen {

    @Id
    private Consulta consulta;

    @Id
    private Examen examen;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConsultaExamen that)) return false;
        return Objects.equals(consulta, that.consulta) && Objects.equals(examen, that.examen);
    }

    @Override
    public int hashCode() {
        return Objects.hash(consulta, examen);
    }
}
