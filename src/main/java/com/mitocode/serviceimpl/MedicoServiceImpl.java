package com.mitocode.serviceimpl;

import com.mitocode.model.Medico;
import com.mitocode.repo.GenericRepo;
import com.mitocode.repo.MedicoRepo;
import com.mitocode.service.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicoServiceImpl extends CRUDImpl<Medico,Integer> implements MedicoService {

    @Autowired
    private MedicoRepo repo;


    @Override
    protected GenericRepo<Medico, Integer> getRepo() {
        return repo;
    }
}
