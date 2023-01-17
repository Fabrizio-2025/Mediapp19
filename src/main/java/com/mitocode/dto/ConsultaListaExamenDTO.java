package com.mitocode.dto;

import com.mitocode.model.Examen;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class ConsultaListaExamenDTO {

    @NotNull
    private ConsultaDTO consulta;

    @NotNull
    private List<ExamenDTO> lstExamen;

    public ConsultaDTO getConsulta() {
        return consulta;
    }

    public void setConsulta(ConsultaDTO consulta) {
        this.consulta = consulta;
    }

    public List<ExamenDTO> getLstExamen() {
        return lstExamen;
    }

    public void setLstExamen(List<ExamenDTO> lstExamen) {
        this.lstExamen = lstExamen;
    }
}
