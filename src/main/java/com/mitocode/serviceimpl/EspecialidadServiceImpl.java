package com.mitocode.serviceimpl;

import com.mitocode.model.Especialidad;
import com.mitocode.repo.EspecialidadRepo;
import com.mitocode.repo.GenericRepo;
import com.mitocode.service.EspecialidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EspecialidadServiceImpl extends CRUDImpl<Especialidad,Integer> implements EspecialidadService {

    @Autowired
    private EspecialidadRepo repo;


    @Override
    protected GenericRepo<Especialidad, Integer> getRepo() {
        return repo;
    }
}
