package com.mitocode.serviceimpl;

import com.mitocode.model.Examen;
import com.mitocode.repo.ExamenRepo;
import com.mitocode.repo.GenericRepo;
import com.mitocode.service.ExamenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExamenServiceImpl extends CRUDImpl<Examen,Integer> implements ExamenService {

    @Autowired
    private ExamenRepo repo;


    @Override
    protected GenericRepo<Examen, Integer> getRepo() {
        return repo;
    }
}
