package com.mitocode.serviceimpl;

import com.mitocode.model.Consulta;
import com.mitocode.model.Examen;
import com.mitocode.repo.ConsultaExamenRepo;
import com.mitocode.repo.ConsultaRepo;
import com.mitocode.repo.GenericRepo;
import com.mitocode.service.ConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ConsultaServiceImpl extends CRUDImpl<Consulta,Integer> implements ConsultaService {

    @Autowired
    private ConsultaRepo repo;

    @Autowired
    private ConsultaExamenRepo ceRepo;

    @Override
    protected GenericRepo<Consulta, Integer> getRepo() {
        return repo;
    }

    @Transactional
    @Override
    public Consulta registrarTransaccional(Consulta consulta, List<Examen> examenes) throws Exception {
        consulta.getDetalleConsulta().forEach(det -> det.setConsulta(consulta));
        repo.save(consulta);

        examenes.forEach(ex->ceRepo.registrar(consulta.getIdConsulta(), ex.getIdExamen()));

        return consulta;
    }
}
