package com.mitocode.serviceimpl;

import com.mitocode.model.Paciente;
import com.mitocode.repo.GenericRepo;
import com.mitocode.repo.PacienteRepo;
import com.mitocode.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteServiceImpl extends CRUDImpl<Paciente,Integer> implements PacienteService {

    @Autowired
    private PacienteRepo repo;


    @Override
    protected GenericRepo<Paciente, Integer> getRepo() {
        return repo;
    }
}
